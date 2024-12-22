package com.railgo.infrastructure.persistence.ticket.model;

import com.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tbl_tickets")
public class TicketEntity {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id", nullable = false)
    private TrainScheduleEntity schedule;
    @Column(name = "start_station_id")
    private String startStationId;
    @Column(name = "end_station_id")
    private String endStationId;
    @Column(name = "return_ticket_id")
    private String returnTicketId;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    private String currency;
    private String status;

    public TicketEntity() {
    }

    public TicketEntity(String id, String userId, TrainScheduleEntity schedule, String startStationId, String endStationId, String returnTicketId, BigDecimal totalPrice, String currency, String status) {
        this.id = id;
        this.userId = userId;
        this.schedule = schedule;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.returnTicketId = returnTicketId;
        this.totalPrice = totalPrice;
        this.currency = currency;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public TrainScheduleEntity getSchedule() {
        return schedule;
    }

    public void setSchedule(TrainScheduleEntity schedule) {
        this.schedule = schedule;
    }

    public String getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(String startStationId) {
        this.startStationId = startStationId;
    }

    public String getEndStationId() {
        return endStationId;
    }

    public void setEndStationId(String endStationId) {
        this.endStationId = endStationId;
    }

    public String getReturnTicketId() {
        return returnTicketId;
    }

    public void setReturnTicketId(String returnTicketId) {
        this.returnTicketId = returnTicketId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
