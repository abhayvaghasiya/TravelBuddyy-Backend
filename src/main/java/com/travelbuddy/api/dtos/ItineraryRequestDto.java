package com.travelbuddy.api.dtos;

import com.travelbuddy.api.models.AgeGroup;
import com.travelbuddy.api.models.TransportMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryRequestDto {
    private Long destinationId;
    private AgeGroup ageGroup;
    private BigDecimal budget;
    private TransportMode transportMode;
    private Integer durationDays;
} 