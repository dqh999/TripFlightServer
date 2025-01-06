package com.railgo.infrastructure.persistence.train.model.schedule;


import com.railgo.domain.utils.valueObject.Money;
import com.railgo.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_train_schedule_stops")
public class TrainScheduleStopEntity extends BaseEntity {
    @Id
    private String id;
    @Column(name = "schedule_id")
    private String scheduleId;
    @Column(name = "stop_order")
    private Integer stopOrder;
    @Column(name = "station_id")
    private String stationId;
    @Column(name = "departure_time")
    private LocalDateTime departureTime;
    @Column(name = "next_station_id")
    private String nextStationId;
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;
    @Column(name = "available_seats")
    private Integer availableSeats;
    @Column(name = "ticket_price")
    private BigDecimal ticketPrice;
    private String currency;
    @Version
    private int version;

    public TrainScheduleStopEntity() {
    }

    public TrainScheduleStopEntity(String id, String scheduleId, Integer stopOrder, String stationId, LocalDateTime departureTime, String nextStationId, LocalDateTime arrivalTime, Integer availableSeats, Money ticketPrice,int version) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.stopOrder = stopOrder;
        this.stationId = stationId;
        this.departureTime = departureTime;
        this.nextStationId = nextStationId;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice.getValue();
        this.currency = ticketPrice.getCurrency();
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
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

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Money getTicketPrice() {
        return new Money(this.ticketPrice, this.currency);
    }

    public void setTicketPrice(Money ticketPrice) {
        this.ticketPrice = ticketPrice.getValue();
        this.currency = ticketPrice.getCurrency();
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
