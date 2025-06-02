package com.travelbuddy.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal entryFee;
    private String imageUrl;
} 