package com.railgo.domain.station.repository;

import com.railgo.domain.station.model.StationRoute;

import java.util.List;
import java.util.Optional;

public interface StationRouteRepository {
    void save(StationRoute r);

    boolean existDirectRouteBetweenStations(String stationAId, String stationBId);
    List<StationRoute> findAll();
    Optional<StationRoute> findRouteBetweenStations(String stationAId, String stationBId);
    StationRoute findById(String id);
}
