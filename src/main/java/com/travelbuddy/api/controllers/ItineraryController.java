package com.travelbuddy.api.controllers;

import com.travelbuddy.api.dtos.ItineraryRequestDto;
import com.travelbuddy.api.dtos.ItineraryResponseDto;
import com.travelbuddy.api.services.ItineraryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/itinerary")
@RequiredArgsConstructor
@Tag(name = "Itinerary", description = "API for generating travel itineraries")
public class ItineraryController {

    private final ItineraryService itineraryService;

    @PostMapping
    @Operation(summary = "Create itinerary", description = "Generate a travel itinerary based on destination, age group, budget and transport mode")
    public ResponseEntity<ItineraryResponseDto> createItinerary(@Valid @RequestBody ItineraryRequestDto request) {
        ItineraryResponseDto itinerary = itineraryService.createItinerary(request);
        return ResponseEntity.ok(itinerary);
    }
} 