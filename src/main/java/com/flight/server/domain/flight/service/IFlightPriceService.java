package com.flight.server.domain.flight.service;

import com.flight.server.domain.flight.model.Flight;
import com.flight.server.domain.utils.valueObject.Money;

public interface IFlightPriceService {
    Money calculateTotalFare(
            Flight flight,
            int childSeats, int adultSeats
    );
}
