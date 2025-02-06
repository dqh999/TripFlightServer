package com.airline.booking.domain.cart.model;

import com.airline.booking.domain.utils.valueObject.Id;

import java.util.List;

public class Cart {
    private Id id;
    private Id userId;
    private String ipAddress;
    private Integer quantity;
    private List<Id> ticketId;
}
