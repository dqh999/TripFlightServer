package com.airline.domain.airplane.type;

public enum FlightScheduleStatus {
    PENDING("PENDING"),
    SCHEDULED("SCHEDULED"),
    CANCELLED("CANCELLED"),
    COMPLETED("COMPLETED"),
    IN_PROGRESS("IN_PROGRESS");

    private final String value;

    FlightScheduleStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
