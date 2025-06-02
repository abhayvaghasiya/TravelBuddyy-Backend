package com.travelbuddy.api.services;

import com.travelbuddy.api.dtos.ItineraryRequestDto;
import com.travelbuddy.api.dtos.ItineraryResponseDto;
 
public interface ItineraryService {
    ItineraryResponseDto createItinerary(ItineraryRequestDto request);
} 