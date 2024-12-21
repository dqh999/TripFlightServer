package com.railgo.application.train.dataTransferObject.request;

import java.time.LocalDateTime;

public class AddTrainScheduleRequest {
    private String trainId;
    private String departureStationId;
    private String arrivalStationId;
    private LocalDateTime departureTime;

    public AddTrainScheduleRequest() {
    }

    public AddTrainScheduleRequest(String trainId, String departureStationId, String arrivalStationId,LocalDateTime departureTime) {
        this.trainId = trainId;
        this.departureStationId = departureStationId;
        this.arrivalStationId = arrivalStationId;
        this.departureTime = departureTime;
    }

    public String getTrainId() {
        return trainId;
    }

    public void setTrainId(String trainId) {
        this.trainId = trainId;
    }

    public String getDepartureStationId() {
        return departureStationId;
    }

    public void setDepartureStationId(String departureStationId) {
        this.departureStationId = departureStationId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalStationId() {
        return arrivalStationId;
    }

    public void setArrivalStationId(String arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
    }
}
