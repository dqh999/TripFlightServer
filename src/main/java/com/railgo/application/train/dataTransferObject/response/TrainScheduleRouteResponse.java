package com.railgo.application.train.dataTransferObject.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TrainScheduleRouteResponse {
    private String id;
    private String stationId;
    private LocalDateTime arrivalTime;
    private BigDecimal ticketPrice;
    private String currency;

    public TrainScheduleRouteResponse() {
    }

    public TrainScheduleRouteResponse(String id, String stationId, LocalDateTime arrivalTime, BigDecimal ticketPrice, String currency) {
        this.id = id;
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
        this.ticketPrice = ticketPrice;
        this.currency = currency;
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

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
