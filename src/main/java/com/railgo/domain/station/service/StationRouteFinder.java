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

            if (!stationGraph.containsKey(stationAId)) {
                List<StationRoute> newRoutes = new ArrayList<>();
                newRoutes.add(route);
                stationGraph.put(stationAId, new ArrayList<>(newRoutes));
            } else {
               stationGraph.get(stationAId).add(route);
            };
            String stationBId = route.getStationB().getId();
            if (!stationGraph.containsKey(stationBId)) {
                List<StationRoute> newRoutes = new ArrayList<>();
                newRoutes.add(route.reverseStation());
                stationGraph.put(stationBId, new ArrayList<>(newRoutes));
            } else {
                stationGraph.get(stationBId).add(route.reverseStation());
            }
        });
    }
    public Map<String, List<StationRoute>> getStationGraph() {
        return this.stationGraph;
    }
    public abstract List<StationRoute> findPath(Train train, String from, String to);
}
