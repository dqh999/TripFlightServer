package com.airline.domain.ticket.model;

import com.airline.domain.ticket.type.TicketStatus;
import com.airline.domain.utils.model.BaseModel;
import com.airline.domain.utils.valueObject.Email;
import com.airline.domain.utils.valueObject.Id;
import com.airline.domain.utils.valueObject.Money;

import java.time.LocalDateTime;

public class Ticket extends BaseModel {
    private Id id;
    private Id flightScheduleId;
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

    public Ticket(String id, String flightScheduleId, Money totalPrice, Integer childSeats, Integer adultSeats, Integer seniorSeats, String contactEmail, LocalDateTime expirationTime, String paymentId, String status) {
        this.id =  new Id(id);
        this.flightScheduleId = new Id(flightScheduleId);
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

    public String getFlightScheduleId() {
        return flightScheduleId.getValue();
    }

    public void setFlightScheduleId(String flightScheduleId) {
        this.flightScheduleId = new Id(flightScheduleId);
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
