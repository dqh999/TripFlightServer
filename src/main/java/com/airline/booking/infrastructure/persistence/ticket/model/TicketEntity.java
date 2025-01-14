package com.airline.booking.infrastructure.persistence.ticket.model;

import com.airline.booking.domain.utils.valueObject.Money;
import com.airline.booking.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_tickets")
public class TicketEntity extends BaseEntity {
    @Id
    private String id;
    @Column(name = "flight_id")
    private String flightId;
    @Column(name = "child_seats", columnDefinition = "0")
    private Integer childSeats;
    @Column(name = "adult_seats", columnDefinition = "0")
    private Integer adultSeats;
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

    public TicketEntity(String id, String flightId,Integer childSeats,Integer adultSeats, Money totalPrice, String contactEmail,LocalDateTime expirationTime, String paymentId, String status) {
        this.id = id;
        this.flightId = flightId;
        this.childSeats = childSeats;
        this.adultSeats = adultSeats;
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

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
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
