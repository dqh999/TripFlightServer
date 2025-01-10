package com.flight.server.domain.flight.repository;

import com.flight.server.domain.flight.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FlightRepository {
    Flight save(Flight s);
    boolean existByCode(String code);
    Optional<Flight> findById(String id);

    Page<Flight> findFlights(
            String departureAirportId, String arrivalAirportId,
            Integer totalSeats,
            LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable
    );
}
