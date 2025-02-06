package com.airline.booking.application.cart.dataTransferObject;

import com.airline.booking.application.ticket.dataTransferObject.response.TicketBookResponse;

import java.util.List;

public class CartResponse {
    private String id;
    private Integer quantity;
    private List<TicketBookResponse> tickets;
}
