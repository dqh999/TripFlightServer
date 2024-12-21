package com.railgo.application.train.dataTransferObject.response;


import java.time.LocalDateTime;

public class TrainScheduleResponse {
    private String id;
    private TrainResponse train;
    private String departureStationId;
    private String arrivalStationId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer totalStops;
    private String status;

    public TrainScheduleResponse() {
    }

    public TrainScheduleResponse(String id, TrainResponse train, String departureStationId, String arrivalStationId, LocalDateTime departureTime, LocalDateTime arrivalTime, Integer totalStops, String status) {
        this.id = id;
        this.train = train;
        this.departureStationId = departureStationId;
        this.arrivalStationId = arrivalStationId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalStops = totalStops;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TrainResponse getTrain() {
        return train;
    }

    public void setTrain(TrainResponse train) {
        this.train = train;
    }

    public String getDepartureStationId() {
        return departureStationId;
    }

    public void setDepartureStationId(String departureStationId) {
        this.departureStationId = departureStationId;
    }

    public String getArrivalStationId() {
        return arrivalStationId;
    }

    public void setArrivalStationId(String arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Integer getTotalStops() {
        return totalStops;
    }

    public void setTotalStops(Integer totalStops) {
        this.totalStops = totalStops;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
