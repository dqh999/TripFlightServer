package com.railgo.application.train.dataTransferObject.response;

import com.railgo.domain.utils.valueObject.Money;

import java.time.LocalDateTime;

public class TrainScheduleStopResponse {
    private String id;
    private String stationId;
    private LocalDateTime arrivalTime;
    private Money ticketPrice;

    public TrainScheduleStopResponse() {
    }

    public TrainScheduleStopResponse(String id, String stationId, LocalDateTime arrivalTime, Money ticketPrice) {
        this.id = id;
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
        this.ticketPrice = ticketPrice;
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

    public Money getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Money ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
