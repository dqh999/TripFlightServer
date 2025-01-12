package com.flight.server.domain.airport.service;

import com.flight.server.domain.airport.model.Airport;
import com.flight.server.domain.utils.service.GenericService;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface IAirportService extends GenericService<Airport, String> {
    boolean isAirportAvailableAtTime(String airportCode, LocalDateTime time);
    Page<Airport> search(String keyword, int page, int size);
}
