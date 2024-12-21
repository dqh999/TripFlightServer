package com.railgo.domain.station.repository;

import com.railgo.domain.station.model.Station;

import java.util.Optional;

public interface StationRepository {
    void save(Station s);
    Optional<Station> findById(String id);
}
