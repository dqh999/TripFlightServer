package com.airline.booking.application.filght.exception;

import com.airline.booking.domain.exception.ExceptionCode;

public enum FlightApplicationExceptionCode implements ExceptionCode {
    Flight_NOT_FOUND("Flight_NOT_FOUND", 404);

    private final String type;
    private final int code;

    FlightApplicationExceptionCode(String type, int code) {
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
