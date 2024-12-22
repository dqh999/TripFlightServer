package com.railgo.domain.train.model.schedule;

import com.railgo.domain.utils.valueObject.Id;
import com.railgo.domain.utils.valueObject.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TrainScheduleRoute {
    private Id id;
    private String scheduleId;
    private String stationId;
    private LocalDateTime arrivalTime;
    private Money ticketPrice;

    public TrainScheduleRoute() {
        this.id = new Id();
    }

    public TrainScheduleRoute(String stationId, LocalDateTime arrivalTime,Money ticketPrice) {
        this.id = new Id();
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
        this.ticketPrice = ticketPrice;
    }

    public TrainScheduleRoute(String id, String scheduleId, String stationId, LocalDateTime arrivalTime, Money ticketPrice) {
        this.id = new Id(id);
        this.scheduleId = scheduleId;
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
        this.ticketPrice = ticketPrice;
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

    public Money getTicketPrice() {
        return this.ticketPrice;
    }

    public void setTicketPrice(Money ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
