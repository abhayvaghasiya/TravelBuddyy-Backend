package com.travelbuddy.api.config;

import com.travelbuddy.api.models.AgeGroup;
import com.travelbuddy.api.models.Attraction;
import com.travelbuddy.api.models.Destination;
import com.travelbuddy.api.repositories.AttractionRepository;
import com.travelbuddy.api.repositories.DestinationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final DestinationRepository destinationRepository;
    private final AttractionRepository attractionRepository;

    @Bean
    @Transactional
    public CommandLineRunner loadData() {
        return args -> {
            // Clear existing data
            attractionRepository.deleteAll();
            destinationRepository.deleteAll();

            // Create destinations
            Destination paris = createDestination(
                    "Paris", 
                    "France", 
                    "Known as the City of Love, Paris offers culture, art, and exquisite cuisine.",
                    new BigDecimal("1200.00"),
                    "https://images.unsplash.com/photo-1502602898657-3e91760cbb34?w=600&auto=format&fit=crop",
                    Set.of(AgeGroup.ADULT, AgeGroup.MIDDLE_AGED, AgeGroup.SENIOR)
            );

            Destination barcelona = createDestination(
                    "Barcelona", 
                    "Spain", 
                    "A vibrant city known for its architecture, culture, and beaches.",
                    new BigDecimal("900.00"),
                    "https://images.unsplash.com/photo-1539037116277-4db20889f2d4?w=600&auto=format&fit=crop",
                    Set.of(AgeGroup.STUDENT, AgeGroup.ADULT, AgeGroup.MIDDLE_AGED)
            );

            Destination rome = createDestination(
                    "Rome", 
                    "Italy", 
                    "The Eternal City with ancient ruins, art, and delicious Italian cuisine.",
                    new BigDecimal("1100.00"),
                    "https://images.unsplash.com/photo-1531572753322-ad063cecc140?w=600&auto=format&fit=crop",
                    Set.of(AgeGroup.ADULT, AgeGroup.MIDDLE_AGED, AgeGroup.SENIOR, AgeGroup.RETIREE)
            );

            Destination bangkok = createDestination(
                    "Bangkok", 
                    "Thailand", 
                    "A bustling city with vibrant street life, ornate temples, and food stalls.",
                    new BigDecimal("800.00"),
                    "https://images.unsplash.com/photo-1508009603885-50cf7c8dd0d5?w=600&auto=format&fit=crop",
                    Set.of(AgeGroup.STUDENT, AgeGroup.ADULT)
            );

            Destination bali = createDestination(
                    "Bali", 
                    "Indonesia", 
                    "A tropical paradise with beaches, rice terraces, and a unique culture.",
                    new BigDecimal("950.00"),
                    "https://images.unsplash.com/photo-1537996194471-e657df975ab4?w=600&auto=format&fit=crop",
                    Set.of(AgeGroup.STUDENT, AgeGroup.ADULT, AgeGroup.MIDDLE_AGED)
            );

            // Save destinations
            destinationRepository.save(paris);
            destinationRepository.save(barcelona);
            destinationRepository.save(rome);
            destinationRepository.save(bangkok);
            destinationRepository.save(bali);

            // Create attractions for Paris
            createAttraction(
                    "Eiffel Tower", 
                    "Iconic iron tower offering stunning views of Paris.", 
                    new BigDecimal("25.00"), 
                    "https://images.unsplash.com/photo-1543349689-9a4d426bee8e?w=400&auto=format&fit=crop",
                    paris
            );
            
            createAttraction(
                    "Louvre Museum", 
                    "World's largest art museum and home to the Mona Lisa.", 
                    new BigDecimal("17.00"), 
                    "https://images.unsplash.com/photo-1565099824688-e93eb20fe622?w=400&auto=format&fit=crop",
                    paris
            );
            
            createAttraction(
                    "Notre-Dame Cathedral", 
                    "Medieval Catholic cathedral with Gothic architecture.", 
                    new BigDecimal("0.00"), 
                    "https://images.unsplash.com/photo-1478391679764-b2d8b3cd1e94?w=400&auto=format&fit=crop",
                    paris
            );

            // Create attractions for Barcelona
            createAttraction(
                    "Sagrada Familia", 
                    "Gaudi's iconic unfinished church with stunning architecture.", 
                    new BigDecimal("26.00"), 
                    "https://images.unsplash.com/photo-1583779457094-ab6f9e393f1e?w=400&auto=format&fit=crop",
                    barcelona
            );
            
            createAttraction(
                    "Park Güell", 
                    "Colorful park with amazing buildings and tile work by Gaudi.", 
                    new BigDecimal("10.00"), 
                    "https://images.unsplash.com/photo-1542601906990-b4d3fb778b09?w=400&auto=format&fit=crop",
                    barcelona
            );
            
            createAttraction(
                    "La Rambla", 
                    "Famous boulevard with shops, restaurants, and street performers.", 
                    new BigDecimal("0.00"), 
                    "https://images.unsplash.com/photo-1561070791-2526d30994b5?w=400&auto=format&fit=crop",
                    barcelona
            );

            // Create attractions for Rome
            createAttraction(
                    "Colosseum", 
                    "Ancient amphitheater used for gladiatorial contests.", 
                    new BigDecimal("16.00"), 
                    "https://images.unsplash.com/photo-1552832230-c0197dd311b5?w=400&auto=format&fit=crop",
                    rome
            );
            
            createAttraction(
                    "Vatican Museums", 
                    "Museums displaying works from the extensive collection of the Catholic Church.", 
                    new BigDecimal("17.00"), 
                    "https://images.unsplash.com/photo-1531572753322-ad063cecc140?w=400&auto=format&fit=crop",
                    rome
            );
            
            createAttraction(
                    "Trevi Fountain", 
                    "Baroque fountain known for the tradition of throwing coins into it.", 
                    new BigDecimal("0.00"), 
                    "https://images.unsplash.com/photo-1525874684015-58379d421a52?w=400&auto=format&fit=crop",
                    rome
            );

            // Create attractions for Bangkok
            createAttraction(
                    "Grand Palace", 
                    "Complex of buildings serving as the official residence of the Kings of Thailand.", 
                    new BigDecimal("15.00"), 
                    "https://images.unsplash.com/photo-1528181304800-259b08848526?w=400&auto=format&fit=crop",
                    bangkok
            );
            
            createAttraction(
                    "Wat Arun", 
                    "Buddhist temple on the Chao Phraya River known for its porcelain decoration.", 
                    new BigDecimal("2.00"), 
                    "https://images.unsplash.com/photo-1570168007204-dfb528c6958f?w=400&auto=format&fit=crop",
                    bangkok
            );
            
            createAttraction(
                    "Chatuchak Weekend Market", 
                    "Massive market with over 8,000 stalls selling everything from clothing to pets.", 
                    new BigDecimal("0.00"), 
                    "https://images.unsplash.com/photo-1577979749830-f1d742b96791?w=400&auto=format&fit=crop",
                    bangkok
            );

            // Create attractions for Bali
            createAttraction(
                    "Uluwatu Temple", 
                    "Ancient sea temple perched on a steep cliff.", 
                    new BigDecimal("3.00"), 
                    "https://images.unsplash.com/photo-1604841847684-b08fc0d49c1d?w=400&auto=format&fit=crop",
                    bali
            );
            
            createAttraction(
                    "Ubud Monkey Forest", 
                    "Natural sanctuary and temple complex with over 700 monkeys.", 
                    new BigDecimal("5.00"), 
                    "https://images.unsplash.com/photo-1578005163550-df2099a8d1ae?w=400&auto=format&fit=crop",
                    bali
            );
            
            createAttraction(
                    "Tegallalang Rice Terraces", 
                    "Stunning rice terraces showing the Balinese irrigation system.", 
                    new BigDecimal("2.00"), 
                    "https://images.unsplash.com/photo-1531089073319-17596b946d42?w=400&auto=format&fit=crop",
                    bali
            );

            System.out.println("Sample data loaded successfully!");
        };
    }

    private Destination createDestination(String name, String country, String description, BigDecimal cost, String imageUrl, Set<AgeGroup> ageGroups) {
        Destination destination = new Destination();
        destination.setName(name);
        destination.setCountry(country);
        destination.setShortDescription(description);
        destination.setTypicalCost(cost);
        destination.setImageUrl(imageUrl);
        destination.setSuitableAgeGroups(new HashSet<>(ageGroups));
        return destination;
    }

    private Attraction createAttraction(String name, String description, BigDecimal entryFee, String imageUrl, Destination destination) {
        Attraction attraction = new Attraction();
        attraction.setName(name);
        attraction.setDescription(description);
        attraction.setEntryFee(entryFee);
        attraction.setImageUrl(imageUrl);
        attraction.setDestination(destination);
        return attractionRepository.save(attraction);
    }
} 