package com.railgo.domain.station.service;

import com.railgo.domain.station.model.Station;
import com.railgo.domain.station.model.StationRoute;
import com.railgo.domain.train.model.Train;

import java.util.List;

public interface IStationRouteService {
    StationRoute addRoute(StationRoute route);
    List<StationRoute> getRoutesBetweenStations(Station startStationId, Station endStationId,
                                                Train train);
    Double getTravelTime(Station startStationId, Station endStationId, Train train);
}
