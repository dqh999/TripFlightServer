package com.airline.booking.domain.airline.service;

import com.airline.booking.domain.airline.model.Airline;
import com.airline.booking.domain.utils.service.GenericService;

public interface IAirlineService extends GenericService<Airline, String> {
    void checkAirlineActive(String airlineId);
}
