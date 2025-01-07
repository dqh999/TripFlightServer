package com.railgo.domain.ticket.model;

import com.railgo.domain.ticket.type.TicketStatus;
import com.railgo.domain.utils.model.BaseModel;
import com.railgo.domain.utils.valueObject.Email;
import com.railgo.domain.utils.valueObject.Id;
import com.railgo.domain.utils.valueObject.Money;

import java.time.LocalDateTime;

public class Ticket extends BaseModel {
    private Id id;
    private Id trainScheduleId;
    private Id startStationId;
    private Id endStationId;
    private Money totalPrice;
    private Integer childSeats = 0;
    private Integer adultSeats = 1;
    private Integer seniorSeats = 0;
    private Email contactEmail;
    private LocalDateTime expirationTime;
    private Id paymentId;
    private TicketStatus status;

    public Ticket() {
        this.id = new Id();
    }

    public Ticket(String id, String trainScheduleId, String startStationId, String endStationId, Money totalPrice, Integer childSeats, Integer adultSeats, Integer seniorSeats, String contactEmail, LocalDateTime expirationTime, String paymentId, String status) {
        this.id =  new Id(id);
        this.trainScheduleId = new Id(trainScheduleId);
        this.startStationId = new Id(startStationId);
        this.endStationId = new Id(endStationId);
        this.totalPrice = totalPrice;
        this.childSeats = childSeats;
        this.adultSeats = adultSeats;
        this.seniorSeats = seniorSeats;
        this.contactEmail = contactEmail != null && !contactEmail.isEmpty() ? new Email(contactEmail) : null;
        this.expirationTime = expirationTime;
        this.paymentId = paymentId != null ? new Id(paymentId) : null;
        this.status = status != null ? TicketStatus.valueOf(status) : null;
    }


    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getTrainScheduleId() {
        return trainScheduleId.getValue();
    }

    public void setTrainScheduleId(String trainScheduleId) {
        this.trainScheduleId = new Id(trainScheduleId);
    }

    public String getStartStationId() {
        return startStationId.getValue();
    }

    public void setStartStationId(String startStationId) {
        this.startStationId = new Id(startStationId);
    }

    public String getEndStationId() {
        return endStationId.getValue();
    }

    public void setEndStationId(String endStationId) {
        this.endStationId = new Id(endStationId);
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Money totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getChildSeats() {
        return childSeats;
    }

    public void setChildSeats(Integer childSeats) {
        this.childSeats = childSeats;
    }

    public Integer getAdultSeats() {
        return adultSeats;
    }

    public void setAdultSeats(Integer adultSeats) {
        this.adultSeats = adultSeats;
    }

    public Integer getSeniorSeats() {
        return seniorSeats;
    }

    public void setSeniorSeats(Integer seniorSeats) {
        this.seniorSeats = seniorSeats;
    }

    public Integer calculateTotalSeats() {
        return this.childSeats  + this.adultSeats + this.seniorSeats;
    }
    public String getContactEmail() {
        return contactEmail != null ? contactEmail.getValue() : "";
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail != null && !contactEmail.isEmpty() ? new Email(contactEmail) : null;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getPaymentId() {
        return paymentId != null ? paymentId.getValue() : null;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = new Id(paymentId);
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = TicketStatus.valueOf(status);
    }
}
