package com.travelbuddy.api.controllers;

import com.travelbuddy.api.dtos.DestinationDetailDto;
import com.travelbuddy.api.dtos.DestinationDto;
import com.travelbuddy.api.models.AgeGroup;
import com.travelbuddy.api.services.DestinationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@RequiredArgsConstructor
@Tag(name = "Destinations", description = "API for managing travel destinations")
public class DestinationController {

    private final DestinationService destinationService;

    @GetMapping
    @Operation(summary = "Get all destinations", description = "Retrieve all destinations or filter by age group and budget")
    public ResponseEntity<List<DestinationDto>> getAllDestinations(
            @RequestParam(required = false) AgeGroup ageGroup,
            @RequestParam(required = false) BigDecimal budget) {
        
        List<DestinationDto> destinations = destinationService.getDestinationsByAgeGroupAndBudget(ageGroup, budget);
        return ResponseEntity.ok(destinations);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get destination by ID", description = "Retrieve a destination with its attractions by ID")
    public ResponseEntity<DestinationDetailDto> getDestinationById(@PathVariable Long id) {
        DestinationDetailDto destination = destinationService.getDestinationById(id);
        return ResponseEntity.ok(destination);
    }
} 