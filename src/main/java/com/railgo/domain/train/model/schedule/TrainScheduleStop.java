package com.railgo.domain.train.model.schedule;

import com.railgo.domain.utils.model.BaseModel;
import com.railgo.domain.utils.valueObject.Id;
import com.railgo.domain.utils.valueObject.Money;

import java.time.LocalDateTime;

public class TrainScheduleStop extends BaseModel {
    private Id id;
    private Id scheduleId;
    private Integer stopOrder;
    private Id stationId;
    private LocalDateTime departureTime;
    private Id nextStationId;
    private LocalDateTime arrivalTime;
    private Integer availableSeats;
    private Money ticketPrice;

    public TrainScheduleStop() {
        this.id = new Id();
    }


    public TrainScheduleStop(Integer stopOrder, String stationId, LocalDateTime departureTime, String nextStationId, LocalDateTime arrivalTime, Integer availableSeats, Money ticketPrice) {
        this.id = new Id();
        this.stopOrder = stopOrder;
        this.stationId = new Id(stationId);
        this.departureTime = departureTime;
        this.nextStationId = new Id(nextStationId);
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice;
    }

    public TrainScheduleStop(String id, String scheduleId, Integer stopOrder, String stationId, LocalDateTime departureTime, String nextStationId, LocalDateTime arrivalTime, Integer availableSeats, Money ticketPrice) {
        this.id = new Id(id);
        this.scheduleId = new Id(scheduleId);
        this.stopOrder = stopOrder;
        this.stationId = new Id(stationId);
        this.departureTime = departureTime;
        this.nextStationId = new Id(nextStationId);
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
        return scheduleId.getValue();
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = new Id(scheduleId);
    }

    public Integer getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(Integer stopOrder) {
        this.stopOrder = stopOrder;
    }

    public String getStationId() {
        return stationId.getValue();
    }

    public void setStationId(String stationId) {
        this.stationId = new Id(stationId);
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getNextStationId() {
        return nextStationId.getValue();
    }

    public void setNextStationId(String nextStationId) {
        this.nextStationId = new Id(nextStationId);
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
        return ticketPrice;
    }

    public void setTicketPrice(Money ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
