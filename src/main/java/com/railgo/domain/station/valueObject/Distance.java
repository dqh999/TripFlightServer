package com.railgo.domain.station.valueObject;

import com.railgo.domain.utils.exception.BusinessException;

public class Distance {
    private final Double value;

    public Distance(Double distance) {
        if (distance <= 0) {
            throw new BusinessException("distance <= 0");
        }
        this.value = distance;
    }
    public Double getValue() {
        return value;
    }
}
