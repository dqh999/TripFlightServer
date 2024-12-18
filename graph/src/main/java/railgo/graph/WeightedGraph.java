package railgo.graph;

import java.util.*;

public class WeightedGraph<K, V> {
    private Map<K, List<Map.Entry<K, V>>> graph;

    public WeightedGraph() {
        graph = new HashMap<>();
    }

    public void addEdge(K start, K end, V weight) {
        graph.putIfAbsent(start, new ArrayList<>());
        graph.get(start).add(new AbstractMap.SimpleEntry<>(end, weight));
    }

    public List<Map.Entry<K, V>> getEdges(K stationId) {
        return graph.getOrDefault(stationId, Collections.emptyList());
    }

    public void findAllPaths(K start, K end, Set<K> visited, List<K> currentPath, List<List<K>> allPaths) {
        if (start.equals(end)) {
            allPaths.add(new ArrayList<>(currentPath));
            return;
        }

        visited.add(start);
        currentPath.add(start);

        for (Map.Entry<K, V> neighbor : graph.get(start)) {
            K neighborId = neighbor.getKey();
            if (!visited.contains(neighborId)) {
                findAllPaths(neighborId, end, visited, currentPath, allPaths);
            }
        }

        currentPath.remove(currentPath.size() - 1);
        visited.remove(start);
    }

    public Map<K, List<Map.Entry<K, V>>> getGraph() {
        return graph;
    }
}
