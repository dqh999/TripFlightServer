package com.railgo.infrastructure.persistence.train.model.schedule;


import com.railgo.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_train_schedule_routes")
public class TrainScheduleRouteEntity extends BaseEntity {
    @Id
    private String id;
    @Column(name = "schedule_id")
    private String scheduleId;
    @Column(name = "station_id")
    private String stationId;
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    public TrainScheduleRouteEntity() {
    }

    public TrainScheduleRouteEntity(String id, String scheduleId, String stationId, LocalDateTime arrivalTime) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
