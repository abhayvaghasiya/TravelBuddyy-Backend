package com.travelbuddy.api.services;

import com.travelbuddy.api.dtos.AttractionDto;
import com.travelbuddy.api.dtos.DestinationDetailDto;
import com.travelbuddy.api.dtos.DestinationDto;
import com.travelbuddy.api.models.AgeGroup;
import com.travelbuddy.api.models.Attraction;
import com.travelbuddy.api.models.Destination;
import com.travelbuddy.api.repositories.AttractionRepository;
import com.travelbuddy.api.repositories.DestinationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;
    private final AttractionRepository attractionRepository;

    @Override
    public List<DestinationDto> getAllDestinations() {
        return destinationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DestinationDetailDto getDestinationById(Long id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Destination not found with id: " + id));
        
        List<Attraction> attractions = attractionRepository.findByDestinationId(id);
        
        return convertToDetailDto(destination, attractions);
    }

    @Override
    public List<DestinationDto> getDestinationsByAgeGroupAndBudget(AgeGroup ageGroup, BigDecimal budget) {
        List<Destination> destinations;
        
        if (ageGroup != null && budget != null) {
            destinations = destinationRepository.findByAgeGroupAndMaxBudget(ageGroup, budget);
        } else if (ageGroup != null) {
            destinations = destinationRepository.findByAgeGroup(ageGroup);
        } else if (budget != null) {
            destinations = destinationRepository.findByTypicalCostLessThanEqual(budget);
        } else {
            destinations = destinationRepository.findAll();
        }
        
        return destinations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    private DestinationDto convertToDto(Destination destination) {
        return new DestinationDto(
                destination.getId(),
                destination.getName(),
                destination.getCountry(),
                destination.getShortDescription(),
                destination.getTypicalCost(),
                destination.getSuitableAgeGroups(),
                destination.getImageUrl()
        );
    }
    
    private DestinationDetailDto convertToDetailDto(Destination destination, List<Attraction> attractions) {
        List<AttractionDto> attractionDtos = attractions.stream()
                .map(attraction -> new AttractionDto(
                        attraction.getId(),
                        attraction.getName(),
                        attraction.getDescription(),
                        attraction.getEntryFee(),
                        attraction.getImageUrl()
                ))
                .collect(Collectors.toList());
        
        return new DestinationDetailDto(
                destination.getId(),
                destination.getName(),
                destination.getCountry(),
                destination.getShortDescription(),
                destination.getTypicalCost(),
                destination.getSuitableAgeGroups(),
                destination.getImageUrl(),
                attractionDtos
        );
    }
} 