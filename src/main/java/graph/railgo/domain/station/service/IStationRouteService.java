package graph.railgo.domain.station.service;

import graph.railgo.domain.station.model.StationRoute;

import java.util.List;

public interface IStationRouteService {
    StationRoute addRoute(StationRoute route);
    List<StationRoute> getRoutesBetweenStations(String startStationId, String endStationId);
}
