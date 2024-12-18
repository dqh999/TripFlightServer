package railgo.graph;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo đồ thị có kiểu dữ liệu Integer cho các nút và trọng số Integer
        WeightedGraph<Integer, Integer> graph = new WeightedGraph<>();

        // Thêm các cạnh vào đồ thị
        graph.addEdge(1, 2, 10);  // Ga 1 -> Ga 2, trọng số là 10
        graph.addEdge(2, 3, 5);   // Ga 2 -> Ga 3, trọng số là 5
        graph.addEdge(2, 5, 4);   // Ga 2 -> Ga 5, trọng số là 4
        graph.addEdge(3, 4, 7);   // Ga 3 -> Ga 4, trọng số là 7
        graph.addEdge(4, 6, 2);   // Ga 4 -> Ga 6, trọng số là 2
        graph.addEdge(5, 6, 1);   // Ga 5 -> Ga 6, trọng số là 1
        graph.addEdge(1, 5, 6);   // Ga 1 -> Ga 5, trọng số là 6

        // Tìm tất cả các đường đi từ Ga 1 đến Ga 6
        List<List<Integer>> allPaths = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        List<Integer> currentPath = new ArrayList<>();
        graph.findAllPaths(1, 6, visited, currentPath, allPaths);

        // In tất cả các đường đi từ Ga 1 đến Ga 6
        System.out.println("Tất cả các đường đi từ Ga 1 đến Ga 6:");
        for (List<Integer> path : allPaths) {
            System.out.println(path);
        }

        // Kiểm tra đường đi từ Ga 2 đến Ga 4
        List<List<Integer>> pathFrom2To4 = new ArrayList<>();
        visited.clear(); // Reset visited set
        currentPath.clear(); // Reset current path
        graph.findAllPaths(2, 4, visited, currentPath, pathFrom2To4);

        System.out.println("\nTất cả các đường đi từ Ga 2 đến Ga 4:");
        for (List<Integer> path : pathFrom2To4) {
            System.out.println(path);
        }
    }
}
