package com.railgo.domain.station.valueObject;

import com.railgo.domain.utils.exception.BusinessException;

public class Distance {
    private final double value;

    public Distance(double distance) {
        if (distance <= 0) {
            throw new BusinessException("distance <= 0");
        }
        value = distance;
    }
    public double getValue() {
        return value;
    }
}
