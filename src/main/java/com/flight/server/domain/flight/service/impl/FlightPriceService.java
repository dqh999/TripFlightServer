package com.flight.server.domain.flight.service.impl;

import com.flight.server.domain.flight.model.Flight;
import com.flight.server.domain.flight.service.IFlightPriceService;
import com.flight.server.domain.utils.valueObject.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FlightPriceService implements IFlightPriceService {
    @Override
    public Money calculateTotalFare(Flight flight,
                                      int childSeats, int adultSeats) {
        Money standardPrice = flight.getStandardPrice();

        Money totalPrice = new Money();

        Money childPrice = standardPrice.multiply(new BigDecimal(childSeats)).multiply(new BigDecimal("0.8"));
        totalPrice = totalPrice.add(childPrice);

        Money adultPrice = standardPrice.multiply(new BigDecimal(adultSeats));
        totalPrice = totalPrice.add(adultPrice);

        return totalPrice;
    }
}
