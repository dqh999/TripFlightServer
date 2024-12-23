package com.railgo.domain.ticket.model;

import com.railgo.domain.utils.model.BaseModel;
import com.railgo.domain.utils.valueObject.Id;

import java.time.LocalDate;

public class Passenger extends BaseModel {
    private Id id;
    private String ticketId;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;

    public Passenger() {
        this.id = new Id();
    }
    public Passenger(String id, String ticketId, String firstName, String lastName, String gender, LocalDate dateOfBirth) {
        this.id = new Id(id);
        this.ticketId = ticketId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
