package com.flight.server.domain.flight.service.impl;

import com.flight.server.domain.flight.model.Flight;
import com.flight.server.domain.flight.repository.FlightRepository;
import com.flight.server.domain.flight.service.IFlightService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FlightServiceImpl implements IFlightService {
    private final FlightRepository flightRepository;

    public FlightServiceImpl(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    public Flight create(Flight flight) {
        return null;
    }

    @Override
    public Flight update(Flight flight) {
        return null;
    }

    @Override
    public Flight getById(String id) {
        return null;
    }

    @Override
    public Page<Flight> getFlights(
            String departureAirportId, String arrivalAirportId,
            LocalDateTime departureTime,
            int pageNumber, int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();
        return flightRepository.findFlights(departureAirportId, arrivalAirportId, startDate, endDate, pageable);
    }

    @Override
    public void delete(Flight flight) {

    }

}
