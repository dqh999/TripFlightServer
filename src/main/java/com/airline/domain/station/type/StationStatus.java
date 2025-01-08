package com.railgo.domain.station.type;

public enum StationStatus {
    OPERATIONAL("OPERATIONAL"),
    UNDER_MAINTENANCE("UNDER_MAINTENANCE"),
    CLOSED("CLOSED"),
    SUSPENDED("SUSPENDED"),
    RENOVATING("RENOVATING"),
    TEMPORARY_CLOSURE("TEMPORARY_CLOSURE"),
    IN_SERVICE("IN_SERVICE"),
    PENDING("PENDING");

    private final String value;

    StationStatus(String value) {
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
