package com.airline.domain.airplane.repository;

import com.airline.domain.airplane.model.Airplane;

import java.util.Optional;

public interface FlightRepository {
    void save(Airplane t);
    Optional<Airplane> findById(String id);
}
