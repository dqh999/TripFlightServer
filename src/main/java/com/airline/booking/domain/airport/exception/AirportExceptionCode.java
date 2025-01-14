package com.airline.booking.domain.airport.exception;

import com.airline.booking.domain.utils.exception.ExceptionCode;

public enum AirportExceptionCode implements ExceptionCode {
    AIR_PORT_NOT_FOUND("AIR_PORT_NOT_FOUND", 404);


    private final String type;
    private final int code;

    AirportExceptionCode(String type, int code) {
        this.type = type;
        this.code = code;
    }
    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
