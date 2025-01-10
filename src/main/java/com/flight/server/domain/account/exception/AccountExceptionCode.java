package com.flight.server.domain.account.exception;

import com.flight.server.domain.utils.exception.ExceptionCode;

public enum AccountExceptionCode implements ExceptionCode {
    ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND", 404),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", 401),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", 423),
    ACCOUNT_ALREADY_EXISTS("ACCOUNT_ALREADY_EXISTS", 409),

    ACCOUNT_INACTIVE("ACCOUNT_INACTIVE", 400),
    INVALID_NAME("INVALID_NAME", 400),
    INVALID_DATE_OF_BIRTH("INVALID_DATE_OF_BIRTH", 400),
    INVALID_PHONE_NUMBER("INVALID_PHONE_NUMBER", 400),
    INVALID_EMAIL("INVALID_EMAIL", 400),
    INVALID_PASSWORD_FORMAT("INVALID_PASSWORD_FORMAT", 400),
    EXPIRED_PASSWORD("EXPIRED_PASSWORD", 400),

    UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS", 403),
    EMAIL_NOT_VERIFIED("EMAIL_NOT_VERIFIED", 400),
    INVALID_TOKEN("INVALID_TOKEN", 401);

    private final String type;
    private final int code;

    AccountExceptionCode(String type, int code) {
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
