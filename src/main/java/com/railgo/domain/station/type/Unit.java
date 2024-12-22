package com.railgo.domain.station.type;

public enum Unit {
    KILOMETER("km"),
    METER("m"),
    MILE("mile");

    private final String value;

    Unit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}