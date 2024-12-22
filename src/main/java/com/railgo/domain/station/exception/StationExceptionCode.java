package com.railgo.domain.station.exception;

import com.railgo.domain.utils.exception.ExceptionCode;

public enum StationExceptionCode implements ExceptionCode {
    STATION_ROUTE_NOT_FOUND("STATION_ROUTE_NOT_FOUND",404),
    STATION_NOT_FOUND("STATION", 404);


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
