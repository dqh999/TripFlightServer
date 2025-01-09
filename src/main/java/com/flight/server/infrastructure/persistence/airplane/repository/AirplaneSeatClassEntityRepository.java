package com.flight.server.infrastructure.persistence.airplane.repository;

import com.flight.server.infrastructure.persistence.airplane.model.AirplaneSeatClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplaneSeatClassEntityRepository extends JpaRepository<AirplaneSeatClassEntity, String> {
}
