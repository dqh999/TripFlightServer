package com.railgo.infrastructure.persistence.train.model.schedule;


import com.railgo.domain.utils.valueObject.Money;
import com.railgo.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_train_schedule_stops")
public class TrainScheduleStopEntity extends BaseEntity {
    @Id
    private String id;
    @Column(name = "schedule_id")
    private String scheduleId;
    @Column(name = "station_id")
    private String stationId;
    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;
    @Column(name = "available_seats")
    private Integer availableSeats;
    @Column(name = "ticket_price")
    private BigDecimal ticketPrice;
    private String currency;

    public TrainScheduleStopEntity() {
    }

    public TrainScheduleStopEntity(String id, String scheduleId, String stationId, LocalDateTime arrivalTime,Integer availableSeats, BigDecimal ticketPrice, String currency) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.stationId = stationId;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        this.ticketPrice = ticketPrice;
        this.currency = currency;
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
        return new Money(ticketPrice,currency);
    }

    public void setTicketPrice(Money ticketPrice) {
        this.ticketPrice = ticketPrice.getValue();
        this.currency = ticketPrice.getCurrency();
    }
}
