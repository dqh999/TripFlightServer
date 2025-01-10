package com.flight.server.domain.flight.excpetion;

import com.flight.server.domain.utils.exception.ExceptionCode;

public enum FlightExceptionCode implements ExceptionCode {
    FLIGHT_NOT_FOUND("FLIGHT_NOT_FOUND",404);
    private final String type;
    private final int code;

    FlightExceptionCode(String type, int code) {
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
