package com.airline.booking.domain.flight.excpetion;

import com.airline.booking.domain.utils.exception.ExceptionCode;

public enum FlightExceptionCode implements ExceptionCode {
    FLIGHT_NOT_FOUND("FLIGHT_NOT_FOUND", 404),
    FLIGHT_ALREADY_BOOKED("FLIGHT_ALREADY_BOOKED", 409),
    FLIGHT_NOT_AVAILABLE("FLIGHT_NOT_AVAILABLE", 404),
    FLIGHT_STATUS_INVALID("FLIGHT_STATUS_INVALID", 400),
    FLIGHT_CREATION_FAILED("FLIGHT_CREATION_FAILED", 500),
    FLIGHT_UPDATE_FAILED("FLIGHT_UPDATE_FAILED", 500),
    FLIGHT_DELETION_FAILED("FLIGHT_DELETION_FAILED", 500),

    FLIGHT_MISSING_FIELD("FLIGHT_MISSING_FIELD", 400),  // Thiếu trường bắt buộc
    FLIGHT_INVALID_FIELD("FLIGHT_INVALID_FIELD", 400),  // Dữ liệu không hợp lệ
    FLIGHT_INVALID_AIRPORT_CODE("FLIGHT_INVALID_AIRPORT_CODE", 400),  // Mã sân bay không hợp lệ
    FLIGHT_INVALID_TIME("FLIGHT_INVALID_TIME", 400),  // Thời gian không hợp lệ (ví dụ: thời gian hạ cánh trước thời gian khởi hành)
    FLIGHT_DUPLICATE_CODE("FLIGHT_DUPLICATE_CODE", 409);  // Mã chuyến bay trùng
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
