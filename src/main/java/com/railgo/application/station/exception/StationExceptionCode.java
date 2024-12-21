package com.railgo.application.station.exception;

import com.railgo.domain.utils.exception.ExceptionCode;


public enum StationExceptionCode  implements ExceptionCode {
    ROUTE_NOT_FOUND("ROUTE_NOT_FOUND", 404);

    private final String type;
    private final int code;

    StationExceptionCode(String type, int code) {
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