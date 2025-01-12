package com.flight.server.domain.airline.service;

import com.flight.server.domain.airline.model.Airline;
import com.flight.server.domain.utils.service.GenericService;

public interface IAirlineService extends GenericService<Airline, String> {
    void checkAirlineActive(String airlineId);
}
