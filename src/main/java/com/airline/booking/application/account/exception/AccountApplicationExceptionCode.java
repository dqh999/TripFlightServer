package com.airline.booking.application.account.exception;

import com.airline.booking.domain.exception.ExceptionCode;

public enum AccountApplicationExceptionCode implements ExceptionCode {
    LOCK_ALREADY_ACQUIRED("CACHE_LOCK", 3001),
    RESOURCE_EXISTS("CACHE_VALIDATION", 3002),

    INVALID("INVALID", 400),

    USER_NOT_AUTHENTICATED("ACCOUNT", 403);
    private final String type;
    private final int code;

    AccountApplicationExceptionCode(String type, int code) {
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
