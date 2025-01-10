package com.flight.server.domain.airline.type;

public enum AirlineStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    SUSPENDED("SUSPENDED"),
    TERMINATED("TERMINATED");

    private final String value;

    AirlineStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
