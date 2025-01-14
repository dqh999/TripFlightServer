package com.airline.booking.domain.airline.repository;

import com.airline.booking.domain.airline.model.Airline;

import java.util.Optional;

public interface AirlineRepository {
    Airline save(Airline a);
    Optional<Airline> findById(String id);
    boolean existsById(String id);
}
