package com.flight.server.application.ticket.dataTransferObject;

import java.time.LocalDate;

public class PassengerDTO {
    private String fullName;
    private LocalDate dateOfBirth;

    public PassengerDTO() {}
    public PassengerDTO(String fullName, LocalDate dateOfBirth) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
