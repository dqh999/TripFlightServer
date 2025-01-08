package com.railgo.domain.train.type;

public enum TrainStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    MAINTENANCE("MAINTENANCE"),
    RETIRED("RETIRED"),
    DELAYED("DELAYED"),
    SUSPENDED("SUSPENDED");

    private final String value;

    TrainStatus(String value) {
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