package com.railgo.domain.station.service.impl;

import com.railgo.domain.station.model.StationRoute;
import com.railgo.domain.station.service.StationRouteFinder;
import com.railgo.domain.train.model.Train;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DFSRouteFinder extends StationRouteFinder {

    public DFSRouteFinder(List<StationRoute> routes) {
        super(routes);
    }

    @Override
    public List<StationRoute> findPath(Train train, String from, String to) {
        Map<String, List<StationRoute>> graph = this.getStationGraph();

        Set<String> visited = new HashSet<>();

        Map<String, StationRoute> previousRoute = new HashMap<>();

        dfsRecursive(graph, from, to, visited, previousRoute);

        return buildPath(previousRoute, from, to);
    }

    private void dfsRecursive(Map<String, List<StationRoute>> graph,
                              String from, String to,
                              Set<String> visited,
                              Map<String, StationRoute> previousRoute) {
        visited.add(from);

        for (StationRoute route : graph.get(from)) {

            String stationBId = route.getStationB().getId();

            if (stationBId.equals(to)) {
                previousRoute.put(stationBId, route);
                return;
            }

            if (!visited.contains(stationBId)) {
                previousRoute.put(stationBId, route);
                dfsRecursive(graph, stationBId, to, visited, previousRoute);
            }
        }
    }

    private List<StationRoute> buildPath(Map<String, StationRoute> previousRoute, String from, String to) {
        List<StationRoute> path = new ArrayList<>();

        String current = to;

        while (current != null && !current.equals(from)) {
            StationRoute route = previousRoute.get(current);
            if (route != null) {
                path.add(route);
                current = route.getStationA().getId();
                continue;
            }
            break;
        }
        Collections.reverse(path);
        return path;
    }
}
