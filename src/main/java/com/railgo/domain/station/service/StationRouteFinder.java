package com.railgo.domain.station.service;

import com.railgo.domain.station.model.StationRoute;
import com.railgo.domain.train.model.Train;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class StationRouteFinder {
    private final List<StationRoute> routes;
    private final Map<String, List<StationRoute>> stationGraph = new HashMap<>();

    public StationRouteFinder(List<StationRoute> routes) {
        this.routes = routes;
    }

    public void buildGraph() {
        routes.forEach(route -> {
            String stationAId = route.getStationA().getId();

            addRouteToGraph(stationAId, route);

            String stationBId = route.getStationB().getId();
            StationRoute reversedRoute = route.reverseStation();

            addRouteToGraph(stationBId, reversedRoute);
        });
    }

    private void addRouteToGraph(String stationId, StationRoute route) {
        if (!stationGraph.containsKey(stationId)) {
            List<StationRoute> newRoutes = new ArrayList<>();
            newRoutes.add(route);
            stationGraph.put(stationId, newRoutes);
            return;
        }
        if (!stationGraph.get(stationId).contains(route)) {
            stationGraph.get(stationId).add(route);
        }
    }

    public Map<String, List<StationRoute>> getStationGraph() {
        return this.stationGraph;
    }

    public abstract List<StationRoute> findPath(Train train, String from, String to);
}
