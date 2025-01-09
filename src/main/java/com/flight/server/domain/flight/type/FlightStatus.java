package com.flight.server.domain.flight.type;

public enum FlightStatus {
    SCHEDULED("SCHEDULED"),
    DELAYED("DELAYED"),
    CANCELLED("CANCELLED"),
    COMPLETED("COMPLETED"),
    IN_FLIGHT("IN_FLIGHT"),
    BOARDING("BOARDING");

    private final String value;

    FlightStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
