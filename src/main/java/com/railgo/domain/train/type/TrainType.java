package com.railgo.domain.train.type;

public enum TrainType {
    PASSENGER("PASSENGER"),      // Passenger trains, including intercity and local
    FREIGHT("FREIGHT"),          // Freight trains for goods transport
    HIGH_SPEED("HIGH_SPEED"),    // High-speed trains designed for fast travel
    SUBURBAN("SUBURBAN"),        // Trains serving suburban areas
    LUXURY("LUXURY");            // High-end trains offering luxurious services
    private final String value;

    TrainType(String value) {
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