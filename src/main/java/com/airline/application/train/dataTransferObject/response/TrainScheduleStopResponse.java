package com.railgo.application.train.dataTransferObject.response;

import com.railgo.domain.utils.valueObject.Money;

import java.time.LocalDateTime;

public class TrainScheduleStopResponse {
    private String id;
    private Integer stopOrder;
    private String stationId;
    private LocalDateTime departureTime;
    private String nextStationId;
    private LocalDateTime arrivalTime;
    private Money ticketPrice;

    public TrainScheduleStopResponse() {
    }

    public TrainScheduleStopResponse(String id, Integer stopOrder, String stationId, LocalDateTime departureTime, String nextStationId, LocalDateTime arrivalTime, Money ticketPrice) {
        this.id = id;
        this.stopOrder = stopOrder;
        this.stationId = stationId;
        this.departureTime = departureTime;
        this.nextStationId = nextStationId;
        this.arrivalTime = arrivalTime;
        this.ticketPrice = ticketPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStopOrder() {
        return stopOrder;
    }

    public void setStopOrder(Integer stopOrder) {
        this.stopOrder = stopOrder;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getNextStationId() {
        return nextStationId;
    }

    public void setNextStationId(String nextStationId) {
        this.nextStationId = nextStationId;
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
