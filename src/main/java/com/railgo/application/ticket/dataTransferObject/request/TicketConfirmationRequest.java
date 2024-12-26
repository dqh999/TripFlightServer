package com.railgo.application.ticket.dataTransferObject.request;

public class TicketConfirmationRequest {
    private String ipAddress;
    private String customerName;
    private String contactEmail;

    public TicketConfirmationRequest() {
    }


    public TicketConfirmationRequest(String ipAddress,String customerName, String contactEmail) {
        this.ipAddress = ipAddress;
        this.customerName = customerName;
        this.contactEmail = contactEmail;
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
}
