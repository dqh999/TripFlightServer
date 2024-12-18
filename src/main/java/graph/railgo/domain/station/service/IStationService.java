package graph.railgo.domain.station.service;

import graph.railgo.domain.station.model.Station;

public interface IStationService {
    Station addStation(Station station);
    Station getStation(String id);
}
