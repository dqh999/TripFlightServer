package com.airline.booking.application.constant;

public enum Locale {
    VIETNAM("vn");

    private final String value;
    Locale(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
}
