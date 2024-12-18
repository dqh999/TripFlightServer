package graph.railgo.domain.station.repository;

import graph.railgo.domain.station.model.Station;

public interface StationRepository {
    void save(Station s);
    Station findById(String id);
}
