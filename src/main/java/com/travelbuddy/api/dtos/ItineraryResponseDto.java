package com.travelbuddy.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryResponseDto {
    private DestinationDto destination;
    private List<ItineraryDayDto> days;
    private Map<String, BigDecimal> costBreakdown;
    private BigDecimal totalCost;
    private boolean withinBudget;
    private String suggestedTransportation;
    private List<String> tips;
} 