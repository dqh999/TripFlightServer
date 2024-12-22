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
    private Train train;
    private String departureStationId;
    private String arrivalStationId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Money ticketPrice;
    private Integer totalStops;
    private List<TrainScheduleRoute> routes;
    private TrainScheduleStatus status;

    public TrainSchedule() {
        this.id = new Id();
    }

    public TrainSchedule(Train train, String departureStationId, String arrivalStationId, LocalDateTime departureTime, LocalDateTime arrivalTime,Money ticketPrice) {
        this.id = new Id();
        this.train = train;
        this.departureStationId = departureStationId;
        this.arrivalStationId = arrivalStationId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.ticketPrice = ticketPrice;
    }

    public TrainSchedule(String id, Train train, String departureStationId, String arrivalStationId, LocalDateTime departureTime, LocalDateTime arrivalTime, Money ticketPrice, Integer totalStops, List<TrainScheduleRoute> routes, String status) {
        this.id = new Id(id);
        this.train = train;
        this.departureStationId = departureStationId;
        this.arrivalStationId = arrivalStationId;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.ticketPrice = ticketPrice;
        this.totalStops = totalStops;
        this.routes = routes;
        this.status = TrainScheduleStatus.valueOf(status);
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
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

    public Money getTicketPrice() {
        return this.ticketPrice;
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
