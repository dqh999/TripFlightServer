package com.railgo.domain.train.type;

public enum TrainScheduleStatus {
    PENDING("PENDING"),
    SCHEDULED("SCHEDULED"),
    CANCELLED("CANCELLED"),
    COMPLETED("COMPLETED"),
    IN_PROGRESS("IN_PROGRESS");

    private final String value;

    TrainScheduleStatus(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
