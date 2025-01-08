package com.airline.domain.airplane.repository;

import com.airline.domain.airplane.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface FlightScheduleRepository {
    void save(Flight s);

    Optional<Flight> findById(String id);

    Page<Flight> findAllSchedules(String departureAirportId, String arrivalAirportId,
                                  LocalDateTime startDate, LocalDateTime endDate,
                                  Pageable pageable);
}
