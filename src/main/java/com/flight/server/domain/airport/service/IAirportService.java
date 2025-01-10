package com.flight.server.domain.airport.service;

import com.flight.server.domain.airport.model.Airport;
import com.flight.server.domain.utils.service.GenericService;
import org.springframework.data.domain.Page;

public interface IAirportService extends GenericService<Airport, String> {
    Page<Airport> search(String keyword, int page, int size);
}
