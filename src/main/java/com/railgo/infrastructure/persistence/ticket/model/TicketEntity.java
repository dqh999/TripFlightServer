package com.railgo.infrastructure.persistence.ticket.model;

import com.railgo.domain.utils.valueObject.Money;
import com.railgo.infrastructure.persistence.train.model.schedule.TrainScheduleEntity;
import com.railgo.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_tickets")
public class TicketEntity extends BaseEntity {
    @Id
    private String id;
    @ManyToOne
    @JoinColumn(name = "train_schedule_id")
    private TrainScheduleEntity trainSchedule;
    @Column(name = "start_station_id")
    private String startStationId;
    @Column(name = "end_station_id")
    private String endStationId;
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

    public TicketEntity(String id, TrainScheduleEntity trainSchedule, String startStationId, String endStationId,Integer childSeats,Integer adultSeats,Integer seniorSeats, Money totalPrice, String contactEmail,LocalDateTime expirationTime, String paymentId, String status) {
        this.id = id;
        this.trainSchedule = trainSchedule;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
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

    public TrainScheduleEntity getTrainSchedule() {
        return trainSchedule;
    }

    public void setTrainSchedule(TrainScheduleEntity trainSchedule) {
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
