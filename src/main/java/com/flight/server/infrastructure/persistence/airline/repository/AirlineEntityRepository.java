package com.flight.server.infrastructure.persistence.airline.repository;

import com.flight.server.infrastructure.persistence.airline.model.AirlineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineEntityRepository extends JpaRepository<AirlineEntity, String> {
}
