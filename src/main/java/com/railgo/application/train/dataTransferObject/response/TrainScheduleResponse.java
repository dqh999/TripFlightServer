package com.railgo.application.train.dataTransferObject.response;


import com.railgo.application.station.dataTransferObject.response.StationResponse;
import com.railgo.domain.utils.valueObject.Money;

import java.time.LocalDateTime;
import java.util.List;

public class TrainScheduleResponse {
    private String id;
    private TrainResponse train;
    private StationResponse departureStation;
    private StationResponse arrivalStation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Integer totalSeats;
    private Money ticketPrice;
    private Integer totalStops;
    private List<TrainScheduleStopResponse> stops;
    private String status;

    public TrainScheduleResponse() {
    }

    public TrainScheduleResponse(String id, TrainResponse train, StationResponse departureStation, StationResponse arrivalStation, LocalDateTime departureTime, LocalDateTime arrivalTime,Integer totalSeats, Money ticketPrice, Integer totalStops, List<TrainScheduleStopResponse> stops, String status) {
        this.id = id;
        this.train = train;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.ticketPrice = ticketPrice;
        this.totalStops = totalStops;
        this.stops = stops;
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

    public StationResponse getDepartureStation() {
        return departureStation;
    }

    public void setDepartureStation(StationResponse departureStation) {
        this.departureStation = departureStation;
    }

    public StationResponse getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(StationResponse arrivalStation) {
        this.arrivalStation = arrivalStation;
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

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
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

    public List<TrainScheduleStopResponse> getStops() {
        return stops;
    }

    public void setStops(List<TrainScheduleStopResponse> stops) {
        this.stops = stops;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
