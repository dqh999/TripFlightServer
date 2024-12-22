package com.railgo.domain.station.service.impl;

import com.railgo.domain.station.exception.StationExceptionCode;
import com.railgo.domain.station.model.Station;
import com.railgo.domain.station.model.StationRoute;
import com.railgo.domain.station.repository.StationRouteRepository;
import com.railgo.domain.station.service.IStationRouteService;
import com.railgo.domain.station.service.StationRouteFinder;
import com.railgo.domain.station.valueObject.Distance;
import com.railgo.domain.train.model.Train;
import com.railgo.domain.utils.exception.BusinessException;
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
    public List<StationRoute> getRoutesBetweenStations(Station startStation, Station endStation,
                                                       Train train) {
        List<StationRoute> allRoutes = stationRouteRepository.findAll();

        StationRouteFinder stationRouteFinder = new DFSRouteFinder(allRoutes);

        stationRouteFinder.buildGraph();

        List<StationRoute> path = stationRouteFinder.findPath(train, startStation.getId(), endStation.getId());

        if (path.isEmpty()) {
            throw new BusinessException(StationExceptionCode.STATION_ROUTE_NOT_FOUND);
        }
        return path;
    }

    @Override
    public Double getTravelTime(Station startStation, Station endStation,
                                Train train) {
        Double trainSpeedLimit = train.getSpeedLimit();
        Double distanceKm = 0.0;

        if (stationRouteRepository.existDirectRouteBetweenStations(startStation.getId(), endStation.getId())) {
            distanceKm = getDirectRouteDistance(startStation, endStation);
            return distanceKm / trainSpeedLimit;

        };

        distanceKm = getRouteDistance(startStation, endStation, train);
        return distanceKm / trainSpeedLimit;
    }

    private Double getDirectRouteDistance(Station startStation, Station endStation) {
        StationRoute route = stationRouteRepository.findRouteBetweenStations(startStation.getId(), endStation.getId())
                .orElseThrow(() -> new BusinessException(StationExceptionCode.STATION_NOT_FOUND));
        return route.getDistanceKm();
    }

    private Double getRouteDistance(Station startStation, Station endStation, Train train) {
        Double distanceKm = 0.0;

        List<StationRoute> allRoutes = stationRouteRepository.findAll();

        StationRouteFinder stationRouteFinder = new DFSRouteFinder(allRoutes);
        stationRouteFinder.buildGraph();

        List<StationRoute> path = stationRouteFinder.findPath(train, startStation.getId(), endStation.getId());
        for (StationRoute route : path) {
            distanceKm += route.getDistanceKm();
        }
        return distanceKm;
    }

    @Override
    public Double getDistanceBetweenStations(Station startStation, Station endStation) {
        if (stationRouteRepository.existDirectRouteBetweenStations(startStation.getId(), endStation.getId())) {
            return getDirectRouteDistance(startStation, endStation);
        }
        return getRouteDistance(startStation, endStation, null);
    }
}
