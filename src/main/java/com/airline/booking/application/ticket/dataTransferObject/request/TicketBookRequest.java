package com.airline.booking.application.ticket.dataTransferObject.request;

import com.airline.booking.application.ticket.dataTransferObject.PassengerDTO;

import java.util.List;

public class TicketBookRequest {
    private String ipAddress;
    private String customerName;
    private String contactEmail;
    private List<PassengerDTO> passengers;

    public TicketBookRequest() {
    }


    public TicketBookRequest(String ipAddress, String customerName, String contactEmail, List<PassengerDTO> passengers) {
        this.ipAddress = ipAddress;
        this.customerName = customerName;
        this.contactEmail = contactEmail;
        this.passengers = passengers;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public TicketBookRequest(List<PassengerDTO> passengers) {
        this.passengers = passengers;
    }
}
