package com.airline.domain.airport.service;

import com.airline.domain.airport.model.Airport;
import org.springframework.data.domain.Page;

public interface IAirportService {
    Airport addAirport(Airport airline);
    Airport getAirport(String id);
    Page<Airport> search(String keyword, int page, int size);
}
