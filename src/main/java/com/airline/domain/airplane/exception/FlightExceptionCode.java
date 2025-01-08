package com.airline.domain.airplane.exception;

import com.airline.domain.utils.exception.ExceptionCode;

public enum FlightExceptionCode implements ExceptionCode {
    FLIGHT_NOT_FOUND("FLIGHT_NOT_FOUND", 404),
    FLIGHT_ALREADY_EXISTS("FLIGHT_ALREADY_EXISTS", 409),

    FLIGHT_SCHEDULE_NOT_FOUND("FLIGHT_SCHEDULE_NOT_FOUND", 404),
    FLIGHT_SCHEDULE_CONFLICT("FLIGHT_SCHEDULE_CONFLICT", 409),
    FLIGHT_SCHEDULE_INVALID("FLIGHT_SCHEDULE_INVALID", 400),
    FLIGHT_SCHEDULE_INVALID_PARAM("FLIGHT_SCHEDULE_INVALID_PARAM", 400),

    FLIGHT_INVALID_STATUS("FLIGHT_INVALID_STATUS", 400),
    FLIGHT_NOT_AVAILABLE("FLIGHT_NOT_AVAILABLE", 404);

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
