package com.airline.infrastructure.persistence.airplane.repository;

import com.airline.infrastructure.persistence.airplane.model.PlaneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightEntityRepository extends JpaRepository<PlaneEntity,String> {
}
