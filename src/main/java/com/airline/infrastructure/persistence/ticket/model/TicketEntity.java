package com.airline.infrastructure.persistence.ticket.model;

import com.airline.domain.utils.valueObject.Money;
import com.airline.infrastructure.persistence.Flight.model.schedule.FlightScheduleEntity;
import com.airline.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_tickets")
public class TicketEntity extends BaseEntity {
    @Id
    private String id;
    @Column(name = "Flight_schedule_id")
    private String FlightScheduleId;
    @Column(name = "start_airline_id")
    private String startairlineId;
    @Column(name = "end_airline_id")
    private String endairlineId;
    @Column(name = "child_seats", columnDefinition = "0")
    private Integer childSeats;
    @Column(name = "adult_seats", columnDefinition = "0")
    private Integer adultSeats;
    @Column(name = "senior_seats", columnDefinition = "0")
    private Integer seniorSeats;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    private String currency;
    @Column(name = "contact_email")
    private String contactEmail;
    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;
    @Column(name = "payment_id")
    private String paymentId;
    private String status;

    public TicketEntity() {
    }

    public TicketEntity(String id, String FlightScheduleId, String startairlineId, String endairlineId,Integer childSeats,Integer adultSeats,Integer seniorSeats, Money totalPrice, String contactEmail,LocalDateTime expirationTime, String paymentId, String status) {
        this.id = id;
        this.FlightScheduleId = FlightScheduleId;
        this.startairlineId = startairlineId;
        this.endairlineId = endairlineId;
        this.childSeats = childSeats;
        this.adultSeats = adultSeats;
        this.seniorSeats = seniorSeats;
        this.totalPrice = totalPrice.getValue();
        this.currency = totalPrice.getCurrency();
        this.contactEmail = contactEmail;
        this.expirationTime = expirationTime;
        this.paymentId = paymentId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFlightScheduleId() {
        return FlightScheduleId;
    }

    public void setFlightScheduleId(String FlightScheduleId) {
        this.FlightScheduleId = FlightScheduleId;
    }

    public String getStartairlineId() {
        return startairlineId;
    }

    public void setStartairlineId(String startairlineId) {
        this.startairlineId = startairlineId;
    }

    public String getEndairlineId() {
        return endairlineId;
    }

    public void setEndairlineId(String endairlineId) {
        this.endairlineId = endairlineId;
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

    public Money getTotalPrice() {
        return new Money(this.totalPrice, this.currency);
    }

    public void setTotalPrice(Money totalPrice) {
        this.totalPrice = totalPrice.getValue();
        this.currency = totalPrice.getCurrency();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
