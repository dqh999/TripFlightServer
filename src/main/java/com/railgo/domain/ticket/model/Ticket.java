package com.railgo.domain.ticket.model;

import com.railgo.domain.ticket.type.TicketStatus;
import com.railgo.domain.train.model.schedule.TrainSchedule;
import com.railgo.domain.utils.model.BaseModel;
import com.railgo.domain.utils.valueObject.Email;
import com.railgo.domain.utils.valueObject.Id;
import com.railgo.domain.utils.valueObject.Money;

import java.util.List;


public class Ticket extends BaseModel {
    private Id id;
    private TrainSchedule trainSchedule;
    private Id startStationId;
    private Id endStationId;
    private Money totalPrice;
    private Integer totalPassengers;
    private List<Passenger> passengers;
    private Email contactEmail;
    private TicketStatus status;

    public Ticket() {
        this.id = new Id();
    }

    public Ticket(String id, TrainSchedule trainSchedule, String startStationId, String endStationId, Money totalPrice,Integer totalPassengers, List<Passenger> passengers,String contactEmail, String status) {
        this.id = new Id(id);
        this.trainSchedule = trainSchedule;
        this.startStationId = new Id(startStationId);
        this.endStationId = new Id(endStationId);
        this.totalPrice = totalPrice;
        this.totalPassengers = totalPassengers;
        this.passengers = passengers;
        this.contactEmail = new Email(contactEmail);
        this.status = TicketStatus.valueOf(status);
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public TrainSchedule getTrainSchedule() {
        return trainSchedule;
    }

    public void setTrainSchedule(TrainSchedule trainSchedule) {
        this.trainSchedule = trainSchedule;
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

    public Integer getTotalPassengers() {
        return totalPassengers;
    }

    public void setTotalPassengers(Integer totalPassengers) {
        this.totalPassengers = totalPassengers;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public String getContactEmail() {
        return contactEmail.getValue();
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = new Email(contactEmail);
    }

    public String getStatus() {
        return status.getValue();
    }

    public void setStatus(String status) {
        this.status = TicketStatus.valueOf(status);
    }
}
