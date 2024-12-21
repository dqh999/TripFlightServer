package com.railgo.domain.station.type;

public enum StationRouteStatus {
    OPERATIONAL("OPERATIONAL"),   // Route is operational and in use
    UNDER_MAINTENANCE("UNDER_MAINTENANCE"); // Route is temporarily under maintenance

    private final String value;

    StationRouteStatus(String value) {
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
