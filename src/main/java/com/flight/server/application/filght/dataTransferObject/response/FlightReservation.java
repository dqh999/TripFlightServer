package com.flight.server.application.filght.dataTransferObject.response;

import com.flight.server.domain.utils.valueObject.Money;

public class FlightReservation {
    private FlightResponse flight;
    private int childSeats = 0;
    private int adultSeats = 1;
    private Money pricePare;

    public FlightReservation() {}

    public FlightReservation(FlightResponse flight, int childSeats, int adultSeats, Money pricePare) {
        this.flight = flight;
        this.childSeats = childSeats;
        this.adultSeats = adultSeats;
        this.pricePare = pricePare;
    }

    public FlightResponse getFlight() {
        return flight;
    }

    public void setFlight(FlightResponse flight) {
        this.flight = flight;
    }

    public int getChildSeats() {
        return childSeats;
    }

    public void setChildSeats(int childSeats) {
        this.childSeats = childSeats;
    }

    public int getAdultSeats() {
        return adultSeats;
    }

    public void setAdultSeats(int adultSeats) {
        this.adultSeats = adultSeats;
    }

    public Money getPricePare() {
        return pricePare;
    }

    public void setPricePare(Money pricePare) {
        this.pricePare = pricePare;
    }
}
