package com.airline.booking.domain.ticket.model;

import com.airline.booking.domain.utils.valueObject.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Cart {
    private Id id;
    private String sessionId;
    private Integer quantity;
    private List<Id> ticketIds;

    public Cart() {
        this.id = new Id();
    }

    public Cart(String id, String sessionId, Integer quantity, List<String> ticketIds) {
        this.id = new Id(id);
        this.sessionId = sessionId;
        this.quantity = quantity;
        this.ticketIds = ticketIds != null ? ticketIds.stream().map(Id::new).collect(Collectors.toList()) : new ArrayList<>();
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
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

    public List<String> getTicketIds() {
        return ticketIds.stream().map(Id::getValue).collect(Collectors.toList());
    }

    public void setTicketIds(List<String> ticketIds) {
        this.ticketIds = ticketIds != null ? ticketIds.stream().map(Id::new).collect(Collectors.toList()) : new ArrayList<>();
    }

    public void addTicket(String ticketId) {
        this.ticketIds.add(new Id(ticketId));
        this.quantity = this.ticketIds.size();
    }
}
