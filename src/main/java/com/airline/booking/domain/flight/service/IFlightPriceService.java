package com.airline.booking.domain.flight.service;

import com.airline.booking.domain.flight.model.Flight;
import com.airline.booking.domain.utils.valueObject.Money;

public interface IFlightPriceService {
    Money calculateTotalFare(
            Flight flight,
            int childSeats, int adultSeats
    );
}
