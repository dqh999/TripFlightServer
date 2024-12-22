package com.railgo.domain.train.model.schedule;

import com.railgo.domain.train.model.Train;
import com.railgo.domain.train.type.TrainScheduleStatus;
import com.railgo.domain.utils.valueObject.Id;
import com.railgo.domain.utils.valueObject.Money;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TrainSchedule {
    private Id id;
    private String creatorId;
    private Train train;
    private String departureStationId;
    private String arrivalStationId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer totalStops;
    private Money ticketPrice;
    private List<TrainScheduleRoute> routes;
    private TrainScheduleStatus status;

    public TrainSchedule() {
        this.id = new Id();
    }

    public TrainSchedule(String id, String creatorId, Train train, String departureStationId, String arrivalStationId, LocalDateTime departureTime, LocalDateTime arrivalTime, Integer totalStops, BigDecimal ticketPrice, String currency, List<TrainScheduleRoute> routes, String status) {
        this.id = new Id(id);
        this.creatorId = creatorId;
        this.train = train;
        this.departureStationId = departureStationId;
        this.arrivalStationId = arrivalStationId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalStops = totalStops;
        this.ticketPrice = new Money(ticketPrice,currency);
        this.routes = routes;
        this.status = TrainScheduleStatus.valueOf(status);
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
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

    public BigDecimal getTicketPrice() {
        return this.ticketPrice.getValue();
    }
    public String getCurrency() {
        return this.ticketPrice.getCurrency();
    }
    public void setTicketPrice(BigDecimal ticketPrice, String currency) {
        this.ticketPrice = new Money(ticketPrice,currency);
    }
    public List<TrainScheduleRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<TrainScheduleRoute> routes) {
        this.routes = routes;
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = TrainScheduleStatus.valueOf(status);
    }
}
