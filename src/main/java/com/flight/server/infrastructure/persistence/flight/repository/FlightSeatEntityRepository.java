package com.flight.server.infrastructure.persistence.flight.repository;

import com.flight.server.infrastructure.persistence.flight.model.FlightSeatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightSeatEntityRepository extends JpaRepository<FlightSeatEntity, Integer> {
}
