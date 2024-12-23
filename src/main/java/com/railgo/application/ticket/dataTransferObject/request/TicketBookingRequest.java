package com.railgo.application.ticket.dataTransferObject.request;


import com.railgo.application.ticket.dataTransferObject.PassengerDTO;

import java.util.List;

public class TicketBookingRequest {
    private String trainScheduleId;
    private String startStationId;
    private String endStationId;
    private List<PassengerDTO> passengers;
    private String contactEmail;

    public TicketBookingRequest() {
    }

    public TicketBookingRequest(String trainScheduleId, String startStationId, String endStationId, List<PassengerDTO> passengers, String contactEmail) {
        this.trainScheduleId = trainScheduleId;
        this.startStationId = startStationId;
        this.endStationId = endStationId;
        this.passengers = passengers;
        this.contactEmail = contactEmail;
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

    public List<PassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
