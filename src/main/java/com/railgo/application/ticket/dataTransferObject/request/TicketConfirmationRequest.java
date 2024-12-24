package com.railgo.application.ticket.dataTransferObject.request;

public class TicketConfirmationRequest {
    private String contactEmail;

    public TicketConfirmationRequest() {
    }

    public TicketConfirmationRequest(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
}
