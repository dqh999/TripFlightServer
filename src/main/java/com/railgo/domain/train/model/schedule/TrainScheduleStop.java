package com.railgo.domain.train.model.schedule;

import com.railgo.domain.utils.valueObject.Id;
import com.railgo.domain.utils.valueObject.Money;

import java.time.LocalDateTime;

public class TrainScheduleStop {
    private Id id;
    private String scheduleId;
    private String stationId;
    private LocalDateTime arrivalTime;
    private Integer availableSeats;
    private Money ticketPrice;

    public TrainScheduleStop() {
        this.id = new Id();
    }

    public TrainScheduleStop(String stationId, LocalDateTime arrivalTime,Integer availableSeats, Money ticketPrice) {
        this.id = new Id();
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice;
    }

    public TrainScheduleStop(String id, String scheduleId, String stationId, LocalDateTime arrivalTime,Integer availableSeats, Money ticketPrice) {
        this.id = new Id(id);
        this.scheduleId = scheduleId;
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
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

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Money getTicketPrice() {
        return this.ticketPrice;
    }

    public void setTicketPrice(Money ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
