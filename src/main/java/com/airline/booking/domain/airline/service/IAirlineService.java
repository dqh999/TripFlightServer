package com.airline.booking.domain.airline.service;

import com.airline.booking.domain.airline.model.Airline;
import com.airline.booking.domain.utils.service.CURDService;

public interface IAirlineService extends CURDService<Airline, String> {
    void checkActive(String airlineId);
}
