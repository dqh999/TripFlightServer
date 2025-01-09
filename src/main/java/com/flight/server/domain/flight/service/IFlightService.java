package com.flight.server.domain.flight.service;

import com.flight.server.domain.flight.model.Flight;
import com.flight.server.domain.utils.service.CURDService;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface IFlightService extends CURDService<Flight, String> {
    Page<Flight> getFlights(String departureAirportId, String arrivalAirportId,
                               LocalDateTime departureTime,
                               int pageNumber, int pageSize);
}
