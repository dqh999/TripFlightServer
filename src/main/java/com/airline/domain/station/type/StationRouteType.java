package com.railgo.domain.station.type;

public enum StationRouteType {
    REGULAR("REGULAR"),     // Normal railway route
    HIGH_SPEED("HIGH_SPEED"); // High-speed railway route

    private final String value;

    StationRouteType(String value) {
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
