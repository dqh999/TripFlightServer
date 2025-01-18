package com.airline.booking.domain.airport.service;

import com.airline.booking.domain.airport.model.Airport;
import com.airline.booking.domain.utils.service.CURDService;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface IAirportService extends CURDService<Airport, String> {
    boolean isAirportAvailableAtTime(String airportCode, LocalDateTime time);
    Page<Airport> search(String keyword, int page, int size);
}
