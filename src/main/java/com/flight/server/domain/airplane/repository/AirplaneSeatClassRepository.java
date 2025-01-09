package com.flight.server.domain.airplane.repository;

import com.flight.server.domain.airplane.model.AirplaneSeatClass;

import java.util.Optional;

public interface AirplaneSeatClassRepository {
    AirplaneSeatClass save(AirplaneSeatClass t);
    Optional<AirplaneSeatClass> findById(String id);
}
