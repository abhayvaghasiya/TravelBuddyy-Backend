package com.travelbuddy.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDayDto {
    private Integer dayNumber;
    private List<ItineraryActivityDto> activities;
    private String morningDescription;
    private String afternoonDescription;
    private String eveningDescription;
} 