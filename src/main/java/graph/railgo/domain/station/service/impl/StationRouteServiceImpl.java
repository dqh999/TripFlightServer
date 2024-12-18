package graph.railgo.domain.station.service.impl;

import graph.railgo.domain.station.model.StationRoute;
import graph.railgo.domain.station.repository.StationRouteRepository;
import graph.railgo.domain.station.service.IStationRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationRouteServiceImpl implements IStationRouteService {
    private final StationRouteRepository stationRouteRepository;
    @Autowired
    public StationRouteServiceImpl(StationRouteRepository stationRouteRepository) {
        this.stationRouteRepository = stationRouteRepository;
    }

    @Override
    public StationRoute addRoute(StationRoute route) {
        stationRouteRepository.save(route);
        return route;
    }

    @Override
    public List<StationRoute> getRoutesBetweenStations(String startStationId, String endStationId) {
        return List.of();
    }
}
