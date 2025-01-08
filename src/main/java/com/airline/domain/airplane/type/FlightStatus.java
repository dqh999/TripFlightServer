package com.airline.domain.airplane.type;

public enum FlightStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    MAINTENANCE("MAINTENANCE"),
    RETIRED("RETIRED"),
    DELAYED("DELAYED"),
    SUSPENDED("SUSPENDED");

    private final String value;

    FlightStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}