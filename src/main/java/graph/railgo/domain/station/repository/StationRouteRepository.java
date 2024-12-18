package graph.railgo.domain.station.repository;

import graph.railgo.domain.station.model.StationRoute;

public interface StationRouteRepository {
    void save(StationRoute r);
    StationRoute findById(String id);
}
