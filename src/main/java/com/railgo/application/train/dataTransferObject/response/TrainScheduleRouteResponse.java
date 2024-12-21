package com.railgo.application.train.dataTransferObject.response;

import java.time.LocalDateTime;

public class TrainScheduleRouteResponse {
    private String id;
    private String stationId;
    private LocalDateTime arrivalTime;

    public TrainScheduleRouteResponse() {
    }

    public TrainScheduleRouteResponse(String id, String stationId, LocalDateTime arrivalTime) {
        this.id = id;
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
