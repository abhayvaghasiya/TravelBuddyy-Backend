package com.travelbuddy.api.repositories;

import com.travelbuddy.api.models.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    List<Attraction> findByDestinationId(Long destinationId);
} 