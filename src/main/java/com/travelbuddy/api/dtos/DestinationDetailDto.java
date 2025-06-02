package com.travelbuddy.api.dtos;

import com.travelbuddy.api.models.AgeGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DestinationDetailDto {
    private Long id;
    private String name;
    private String country;
    private String shortDescription;
    private BigDecimal typicalCost;
    private Set<AgeGroup> suitableAgeGroups;
    private String imageUrl;
    private List<AttractionDto> attractions;
} 