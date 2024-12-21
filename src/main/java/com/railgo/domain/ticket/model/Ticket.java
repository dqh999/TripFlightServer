package com.railgo.domain.ticket.model;

import com.railgo.domain.account.model.User;
import com.railgo.domain.station.model.Station;
import com.railgo.domain.utils.valueObject.Id;
import com.railgo.domain.utils.valueObject.Money;

import java.math.BigDecimal;

public class Ticket {
    private Id id;
    private User user;
    private Station startStation;
    private Station endStation;
    private Id returnTicketId;
    private Money totalPrice;
    private Id discountId;

    public Ticket() {
        this.id = new Id();
    }

    public Ticket(String id, User user, Station startStation, Station endStation, Id returnTicketId, Money totalPrice, Id discountId) {
        this.id = new Id(id);
        this.user = user;
        this.startStation = startStation;
        this.endStation = endStation;
        this.returnTicketId = returnTicketId;
        this.totalPrice = totalPrice;
        this.discountId = discountId;
    }

    public String getId() {
        return id.getValue();
    }

    public void setId(String id) {
        this.id = new Id(id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public void setEndStation(Station endStation) {
        this.endStation = endStation;
    }

    public Id getReturnTicketId() {
        return returnTicketId;
    }

    public void setReturnTicketId(String returnTicketId) {
        this.returnTicketId = new Id(returnTicketId);
    }

    public BigDecimal getTotalPrice() {
        return totalPrice.getValue();
    }

    public void setTotalPrice(BigDecimal totalPrice, String currency) {
        this.totalPrice = new Money(totalPrice, currency);
    }

    public String getDiscountId() {
        return discountId.getValue();
    }

    public void setDiscountId(String discountId) {
        this.discountId = new Id(discountId);
    }
}
