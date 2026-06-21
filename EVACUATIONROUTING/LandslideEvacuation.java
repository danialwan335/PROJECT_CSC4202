
import java.util.*;

public class LandslideEvacuation {
    static final String[] NODE_NAMES = {
            "Hospital Kuala Kubu Bharu", // 0 SOURCE
            "Pekan KKB Town Junction", // 1
            "Simpang Empat Batang Kali Fork", // 2
            "Sungai Chilling Forest Reserve", // 3
            "Ladang Ulu Bernam (Plantation)", // 4
            "Pos Gertak (Orang Asli Settlement)", // 5
            "Hulu Bernam Highlands Crossroad", // 6
            "Kampung Sungai Lui" // 7 DESTINATION
    };

    static class Graph {
        int V;
        List<int[]>[] adj;

        @SuppressWarnings("unchecked")
        Graph(int vertices) {
            this.V = vertices;
            adj = new ArrayList[vertices];
            for (int i = 0; i < vertices; i++)
                adj[i] = new ArrayList<>();
        }

        void addEdge(int u, int v, int weight) {
            adj[u].add(new int[] { v, weight });
            adj[v].add(new int[] { u, weight });
        }
    }

    static int[] dist;
    static int[] prev;

    // Dijkstra's Algorithm with min-priority queue
    static void dijkstra(Graph g, int src) {
        dist = new int[g.V];
        prev = new int[g.V];
        boolean[] visited = new boolean[g.V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[src] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.offer(new int[] { 0, src });

        while (!pq.isEmpty()) {
            int[] top = pq.poll();
            int u = top[1];
            if (visited[u])
                continue;
            visited[u] = true;

            for (int[] edge : g.adj[u]) {
                int v = edge[0], w = edge[1];
                if (!visited[v] && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                    prev[v] = u;
                    pq.offer(new int[] { dist[v], v });
                }
            }
        }
    }

    static List<Integer> getPath(int dest) {
        LinkedList<Integer> path = new LinkedList<>();
        for (int cur = dest; cur != -1; cur = prev[cur])
            path.addFirst(cur);
        return path;
    }

    public static void main(String[] args) {
        Graph g1 = new Graph(8);
        g1.addEdge(0, 1, 8);
        g1.addEdge(1, 2, 10);
        g1.addEdge(1, 3, 18);
        g1.addEdge(2, 4, 20);
        g1.addEdge(2, 5, 36);
        g1.addEdge(3, 5, 17);
        g1.addEdge(4, 6, 10);
        g1.addEdge(5, 7, 12);
        g1.addEdge(6, 7, 14);
        // Direct 0→7 highway is BLOCKED — omitted

        dijkstra(g1, 0);

        System.out.println("Shortest Travel Time: " + dist[7] + " minutes");
        System.out.println("Optimal Route: " + getPath(7));
    }
}
