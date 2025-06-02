package com.travelbuddy.api.models;

public enum TransportMode {
    WALKING("Walking", 0.0),
    PUBLIC_TRANSPORT("Public Transport", 20.0),
    TAXI("Taxi", 50.0),
    RENTAL_CAR("Rental Car", 100.0),
    TRAIN("Train", 80.0),
    BUS("Bus", 40.0),
    FLIGHT("Flight", 200.0);

    private final String displayName;
    private final Double averageCost;

    TransportMode(String displayName, Double averageCost) {
        this.displayName = displayName;
        this.averageCost = averageCost;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Double getAverageCost() {
        return averageCost;
    }
} 