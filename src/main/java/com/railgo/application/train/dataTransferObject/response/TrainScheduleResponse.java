package com.railgo.application.train.dataTransferObject.response;


import com.railgo.domain.utils.valueObject.Money;

import java.time.LocalDateTime;
import java.util.List;

public class TrainScheduleResponse {
    private String id;
    private TrainResponse train;
    private String departureStationId;
    private String arrivalStationId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Money ticketPrice;
    private Integer totalStops;
    private List<TrainScheduleRouteResponse> routes;
    private String status;

    public TrainScheduleResponse() {
    }

    public TrainScheduleResponse(String id, TrainResponse train, String departureStationId, String arrivalStationId, LocalDateTime departureTime, LocalDateTime arrivalTime, Money ticketPrice, Integer totalStops, List<TrainScheduleRouteResponse> routes, String status) {
        this.id = id;
        this.train = train;
        this.departureStationId = departureStationId;
        this.arrivalStationId = arrivalStationId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.ticketPrice = ticketPrice;
        this.totalStops = totalStops;
        this.routes = routes;
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

    public Money getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Money ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getTotalStops() {
        return totalStops;
    }

    public void setTotalStops(Integer totalStops) {
        this.totalStops = totalStops;
    }

    public List<TrainScheduleRouteResponse> getRoutes() {
        return routes;
    }

    public void setRoutes(List<TrainScheduleRouteResponse> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
