package com.flight.server.domain.airline.type;

public enum AirlineStatus {
    ACTIVE(0),
    INACTIVE(1),
    SUSPENDED(2),
    TERMINATED(3);

    private final int value;

    AirlineStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static AirlineStatus fromValue(int value) {
        for (AirlineStatus status : AirlineStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
