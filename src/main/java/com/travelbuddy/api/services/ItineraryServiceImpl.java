package com.travelbuddy.api.services;

import com.travelbuddy.api.dtos.*;
import com.travelbuddy.api.models.Attraction;
import com.travelbuddy.api.models.Destination;
import com.travelbuddy.api.repositories.AttractionRepository;
import com.travelbuddy.api.repositories.DestinationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItineraryServiceImpl implements ItineraryService {

    private final DestinationRepository destinationRepository;
    private final AttractionRepository attractionRepository;
    
    @Override
    public ItineraryResponseDto createItinerary(ItineraryRequestDto request) {
        // Retrieve destination
        Destination destination = destinationRepository.findById(request.getDestinationId())
                .orElseThrow(() -> new EntityNotFoundException("Destination not found with id: " + request.getDestinationId()));
        
        // Retrieve attractions
        List<Attraction> attractions = attractionRepository.findByDestinationId(request.getDestinationId());
        
        // Calculate duration - default to 3 if not specified
        int duration = (request.getDurationDays() != null && request.getDurationDays() > 0) ? 
                request.getDurationDays() : 3;
        
        // Generate itinerary
        DestinationDto destinationDto = new DestinationDto(
                destination.getId(),
                destination.getName(),
                destination.getCountry(),
                destination.getShortDescription(),
                destination.getTypicalCost(),
                destination.getSuitableAgeGroups(),
                destination.getImageUrl()
        );
        
        List<ItineraryDayDto> days = generateItineraryDays(attractions, duration);
        Map<String, BigDecimal> costBreakdown = calculateCostBreakdown(destination, attractions, request);
        BigDecimal totalCost = costBreakdown.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        boolean withinBudget = (request.getBudget() == null) || totalCost.compareTo(request.getBudget()) <= 0;
        
        return new ItineraryResponseDto(
                destinationDto,
                days,
                costBreakdown,
                totalCost,
                withinBudget,
                request.getTransportMode().getDisplayName(),
                generateTips(request, withinBudget)
        );
    }
    
    private List<ItineraryDayDto> generateItineraryDays(List<Attraction> attractions, int duration) {
        List<ItineraryDayDto> days = new ArrayList<>();
        
        // Distribute attractions across days
        List<Attraction> remainingAttractions = new ArrayList<>(attractions);
        Collections.shuffle(remainingAttractions);
        
        for (int day = 1; day <= duration; day++) {
            int attractionsPerDay = Math.max(1, remainingAttractions.size() / (duration - day + 1));
            List<Attraction> dayAttractions = new ArrayList<>();
            
            for (int i = 0; i < attractionsPerDay && !remainingAttractions.isEmpty(); i++) {
                dayAttractions.add(remainingAttractions.remove(0));
            }
            
            List<ItineraryActivityDto> activities = createActivitiesFromAttractions(dayAttractions, day);
            
            ItineraryDayDto dayDto = new ItineraryDayDto(
                    day,
                    activities,
                    generateDayDescription("morning", day, duration),
                    generateDayDescription("afternoon", day, duration),
                    generateDayDescription("evening", day, duration)
            );
            
            days.add(dayDto);
        }
        
        return days;
    }
    
    private List<ItineraryActivityDto> createActivitiesFromAttractions(List<Attraction> attractions, int day) {
        List<ItineraryActivityDto> activities = new ArrayList<>();
        LocalTime currentTime = LocalTime.of(9, 0); // Start at 9 AM
        
        for (Attraction attraction : attractions) {
            LocalTime endTime = currentTime.plusHours(2); // Assume 2 hours per attraction
            
            ItineraryActivityDto activity = new ItineraryActivityDto(
                    attraction.getName(),
                    attraction.getDescription(),
                    currentTime,
                    endTime,
                    "At " + attraction.getName(),
                    attraction.getEntryFee(),
                    attraction.getId()
            );
            
            activities.add(activity);
            currentTime = endTime.plusMinutes(30); // 30 minute break between activities
        }
        
        // Add lunch and dinner
        activities.add(new ItineraryActivityDto(
                "Lunch",
                "Enjoy a local meal",
                LocalTime.of(12, 30),
                LocalTime.of(13, 30),
                "Local restaurant",
                new BigDecimal("15.00"),
                null
        ));
        
        activities.add(new ItineraryActivityDto(
                "Dinner",
                "Experience local cuisine",
                LocalTime.of(19, 0),
                LocalTime.of(20, 30),
                "Recommended restaurant",
                new BigDecimal("25.00"),
                null
        ));
        
        return activities;
    }
    
    private String generateDayDescription(String timeOfDay, int day, int totalDays) {
        if ("morning".equals(timeOfDay)) {
            if (day == 1) {
                return "Start your journey with breakfast at a local café.";
            } else if (day == totalDays) {
                return "Enjoy your last morning with a relaxing breakfast.";
            } else {
                return "Begin your day with sightseeing and exploration.";
            }
        } else if ("afternoon".equals(timeOfDay)) {
            return "Visit popular attractions and enjoy local cuisine for lunch.";
        } else { // evening
            if (day == totalDays) {
                return "Finish your trip with a special dinner and evening walk.";
            } else {
                return "Relax with dinner and perhaps experience local nightlife.";
            }
        }
    }
    
    private Map<String, BigDecimal> calculateCostBreakdown(Destination destination, List<Attraction> attractions, ItineraryRequestDto request) {
        Map<String, BigDecimal> costBreakdown = new HashMap<>();
        
        // Calculate accommodation cost (assume per day)
        BigDecimal accommodationCost = getAccommodationCost(request.getAgeGroup()).multiply(BigDecimal.valueOf(request.getDurationDays() != null ? request.getDurationDays() : 3));
        costBreakdown.put("Accommodation", accommodationCost);
        
        // Calculate food cost (assume 3 meals per day)
        BigDecimal foodCost = BigDecimal.valueOf(40).multiply(BigDecimal.valueOf(request.getDurationDays() != null ? request.getDurationDays() : 3));
        costBreakdown.put("Food", foodCost);
        
        // Calculate attractions cost
        BigDecimal attractionsCost = attractions.stream()
                .map(Attraction::getEntryFee)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        costBreakdown.put("Attractions", attractionsCost);
        
        // Calculate transportation cost
        BigDecimal transportCost = BigDecimal.valueOf(request.getTransportMode().getAverageCost());
        costBreakdown.put("Transportation", transportCost);
        
        // Add miscellaneous costs
        costBreakdown.put("Miscellaneous", BigDecimal.valueOf(50));
        
        return costBreakdown;
    }
    
    private BigDecimal getAccommodationCost(com.travelbuddy.api.models.AgeGroup ageGroup) {
        switch (ageGroup) {
            case STUDENT:
                return new BigDecimal("50.00");
            case ADULT:
                return new BigDecimal("100.00");
            case MIDDLE_AGED:
                return new BigDecimal("120.00");
            case SENIOR:
            case RETIREE:
                return new BigDecimal("90.00");
            default:
                return new BigDecimal("80.00");
        }
    }
    
    private List<String> generateTips(ItineraryRequestDto request, boolean withinBudget) {
        List<String> tips = new ArrayList<>();
        
        // Add age-specific tips
        switch (request.getAgeGroup()) {
            case STUDENT:
                tips.add("Look for student discounts at attractions with a valid student ID.");
                tips.add("Consider staying in hostels to save on accommodation costs.");
                break;
            case ADULT:
                tips.add("Many attractions offer family packages if traveling with children.");
                tips.add("Consider mid-range hotels for a balance of comfort and cost.");
                break;
            case MIDDLE_AGED:
                tips.add("Look for guided tours to enhance your cultural experience.");
                tips.add("Consider boutique hotels for a more personalized stay.");
                break;
            case SENIOR:
            case RETIREE:
                tips.add("Many attractions offer senior discounts - don't forget to ask!");
                tips.add("Consider hotels with good accessibility features.");
                break;
        }
        
        // Add budget-related tips
        if (!withinBudget) {
            tips.add("Your current plan exceeds your budget. Consider reducing the number of paid attractions.");
            tips.add("Look for free walking tours and public parks to save money.");
            tips.add("Consider street food or local markets instead of restaurants for some meals.");
        }
        
        // Add transport tips
        switch (request.getTransportMode()) {
            case WALKING:
                tips.add("Pack comfortable walking shoes and a water bottle.");
                break;
            case PUBLIC_TRANSPORT:
                tips.add("Consider buying a multi-day public transport pass to save money.");
                break;
            case TAXI:
                tips.add("Pre-book taxis when possible to avoid being overcharged.");
                break;
            case RENTAL_CAR:
                tips.add("Book your rental car in advance for the best rates.");
                tips.add("Check parking availability at your accommodation and attractions.");
                break;
        }
        
        return tips;
    }
} 