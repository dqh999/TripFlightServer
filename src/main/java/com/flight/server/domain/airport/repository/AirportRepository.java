package com.flight.server.domain.airport.repository;

import com.flight.server.domain.airport.model.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AirportRepository {
    Airport save(Airport s);
    Optional<Airport> findById(String id);
    Page<Airport> search(String keyword, Pageable pageable);
}
