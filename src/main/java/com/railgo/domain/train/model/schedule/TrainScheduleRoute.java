package com.railgo.domain.train.model.schedule;

import com.railgo.domain.utils.valueObject.Id;

import java.time.LocalDateTime;

public class TrainScheduleRoute {
    private Id id;
    private String scheduleId;
    private String stationId;
    private LocalDateTime arrivalTime;

    public TrainScheduleRoute() {
        this.id = new Id();
    }
    public TrainScheduleRoute(String stationId, LocalDateTime arrivalTime){
        this.id = new Id();
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
    }
    public TrainScheduleRoute(String id, String scheduleId, String stationId, LocalDateTime arrivalTime) {
        this.id = new Id(id);
        this.scheduleId = scheduleId;
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
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
