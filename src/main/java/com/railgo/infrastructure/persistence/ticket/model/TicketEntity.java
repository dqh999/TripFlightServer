package com.railgo.infrastructure.persistence.ticket.model;

import com.railgo.domain.utils.valueObject.Money;
import com.railgo.infrastructure.persistence.utils.BaseEntity;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tbl_tickets")
public class TicketEntity extends BaseEntity {
    @Id
    private String id;
    @Column(name = "train_schedule_id")
    private String trainScheduleId;
    @Column(name = "start_station_id")
    private String startStationId;
    @Column(name = "end_station_id")
    private String endStationId;
    @Column(name = "total_passengers")
    private Integer totalPassengers;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    private String currency;
    @OneToMany(mappedBy = "ticketId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PassengerEntity> passengers;
    @Column(name = "contact_email",nullable = false)
    private String contactEmail;
    private String status;

    public TicketEntity() {
    }

    public TicketEntity(String id, String trainScheduleId, String startStationId, String endStationId,Integer totalPassengers, Money totalPrice, List<PassengerEntity> passengers, String contactEmail, String status) {
        this.id = id;
        this.trainScheduleId = trainScheduleId;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.totalPassengers = totalPassengers;
        this.totalPrice = totalPrice.getValue();
        this.currency = totalPrice.getCurrency();
        this.currency = currency;
        this.passengers = passengers;
        this.contactEmail = contactEmail;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrainScheduleId() {
        return trainScheduleId;
    }

    public void setTrainScheduleId(String trainScheduleId) {
        this.trainScheduleId = trainScheduleId;
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

    public Integer getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(Integer totalPassengers) {
        this.totalPassengers = totalPassengers;
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

    public List<PassengerEntity> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerEntity> passengers) {
        this.passengers = passengers;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
