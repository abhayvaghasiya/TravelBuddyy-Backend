package com.travelbuddy.api.models;

public enum AgeGroup {
    STUDENT("Student", "Age 18-25, focus on budget and adventure"),
    ADULT("Adult", "Age 26-40, balanced experience and comfort"),
    MIDDLE_AGED("Middle-aged", "Age 41-60, focus on comfort and cultural experiences"),
    SENIOR("Senior", "Age 61-75, focus on comfort and accessibility"),
    RETIREE("Retiree", "Age 76+, focus on leisure and accessibility");

    private final String displayName;
    private final String description;

    AgeGroup(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
} 