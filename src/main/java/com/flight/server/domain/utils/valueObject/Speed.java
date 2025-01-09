package com.flight.server.domain.utils.valueObject;

public class Speed {
    private final double value;

    public Speed(double value) {
        if (value < 0) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }
    public double getValue() {
        return this.value;
    }
}
