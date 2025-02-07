package com.airline.booking.application.ticket.dataTransferObject.response;

import java.util.List;

public class CartResponse {
    private String id;
    private String sessionId;
    private Integer quantity;
    private List<TicketConfirmResponse> tickets;

    public CartResponse() {
    }

    public CartResponse(String id, String sessionId, Integer quantity, List<TicketConfirmResponse> tickets) {
        this.id = id;
        this.sessionId = sessionId;
        this.quantity = quantity;
        this.tickets = tickets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<TicketConfirmResponse> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketConfirmResponse> tickets) {
        this.tickets = tickets;
    }
}
