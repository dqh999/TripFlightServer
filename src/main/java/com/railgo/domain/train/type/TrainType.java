package com.railgo.domain.train.type;

import java.math.BigDecimal;

public enum TrainType {
    REGULAR("REGULAR", BigDecimal.valueOf(1.0)),
    HIGH_SPEED("HIGH_SPEED", BigDecimal.valueOf(1.5)),
    LUXURY("LUXURY", BigDecimal.valueOf(3.0));

    private final String value;
    private final BigDecimal ratePerKm;

    TrainType(String value, BigDecimal ratePerKm) {
        this.value = value;
        this.ratePerKm = ratePerKm;
    }

    public String getValue() {
        return value;
    }

    public BigDecimal getRatePerKm() {
        return ratePerKm;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
