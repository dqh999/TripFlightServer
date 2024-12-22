package com.railgo.domain.ticket.model;

import com.railgo.domain.account.model.User;
import com.railgo.domain.ticket.type.TicketStatus;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.utils.valueObject.Id;
import com.railgo.domain.utils.valueObject.Money;

import java.math.BigDecimal;


public class Ticket {
    private Id id;
    private User user;
    private TrainSchedule trainSchedule;
    private String startStationId;
    private String endStationId;
    private Id returnTicketId;
    private Money totalPrice;
    private TicketStatus status;

    public Ticket() {
        this.id = new Id();
    }


    public Ticket(String id, User user, TrainSchedule trainSchedule, String startStationId, String endStationId, Id returnTicketId, BigDecimal totalPrice, String currency, String status) {
        this.id = new Id(id);
        this.user = user;
        this.trainSchedule = trainSchedule;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.returnTicketId = returnTicketId;
        this.totalPrice = new Money(totalPrice, currency);
        this.status = TicketStatus.valueOf(status);
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TrainSchedule getTrainSchedule() {
        return trainSchedule;
    }

    public void setTrainSchedule(TrainSchedule trainSchedule) {
        this.trainSchedule = trainSchedule;
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
        return this.returnTicketId.getValue();
    }

    public void setReturnTicketId(String returnTicketId) {
        this.returnTicketId = new Id(returnTicketId);
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice.getValue();
    }
    public String getCurrency() {
        return this.totalPrice.getCurrency();
    }
    public void setTotalPrice(BigDecimal totalPrice, String currency) {
        this.totalPrice = new Money(totalPrice, currency);
    }


    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = TicketStatus.valueOf(status);
    }
}
