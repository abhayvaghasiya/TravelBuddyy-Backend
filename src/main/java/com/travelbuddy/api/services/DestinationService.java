package com.travelbuddy.api.services;

import com.travelbuddy.api.dtos.DestinationDetailDto;
import com.travelbuddy.api.dtos.DestinationDto;
import com.travelbuddy.api.models.AgeGroup;

import java.math.BigDecimal;
import java.util.List;

public interface DestinationService {
    List<DestinationDto> getAllDestinations();
    DestinationDetailDto getDestinationById(Long id);
    List<DestinationDto> getDestinationsByAgeGroupAndBudget(AgeGroup ageGroup, BigDecimal budget);
} 