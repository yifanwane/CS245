//Yifan Wan
//11/20/2023
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[][] Example1 = {{0, 1}, {1, 2}, {1, 3}, {1, 4}, {4, 5}, {3, 5}, {5, 6}};
        int[] sol1 = findCutEdge(Example1);
        System.out.println("The first solution is");
        System.out.println(Arrays.toString(sol1));

        int[][] Example2 = {{0, 1}, {1, 2}, {1, 3}, {2, 3}, {4, 5}, {3, 5}, {5, 6}};
        int[] sol2 = findCutEdge(Example2);
        System.out.println("The second solution is");
        System.out.println(Arrays.toString(sol2));

        int[][] Example3 = {{0, 1}, {1, 2}, {1, 3}, {2, 6}, {4, 5}, {3, 5}, {5, 6}};
        int[] sol3 = findCutEdge(Example3);
        System.out.println("The third solution is");
        System.out.println(Arrays.toString(sol3));
    }

    public static int[] findCutEdge(int[][] edges) {
        int n = edges.length + 1;
        DisjointSets disjointSets = new DisjointSets(n);

        for (int[] edge : edges) {
            int parentX = disjointSets.Find(edge[0]);
            int parentY = disjointSets.Find(edge[1]);

            // Check if adding the edge creates a cycle
            if (parentX == parentY) {
                return edge; // This edge completes a cycle
            }

            disjointSets.Union(parentX, parentY);
        }

        return new int[0]; // No edge found that creates a cycle
    }

    // Disjoint Sets implementation (Union-Find)
    private static class DisjointSets {
        private int n;
        private int[] parent;

        public DisjointSets(int n) {
            this.n = n;
            parent = new int[n];
            Arrays.fill(parent, -1);
        }

        public int Find(int k) {
            if (parent[k] < 0) {
                return k;
            }
            parent[k] = Find(parent[k]); // Path compression
            return parent[k];
        }

        public void Union(int a, int b) {
            int rootA = Find(a);
            int rootB = Find(b);

            if (rootA != rootB) {
                if (parent[rootA] < parent[rootB]) {
                    parent[rootA] += parent[rootB];
                    parent[rootB] = rootA;
                } else {
                    parent[rootB] += parent[rootA];
                    parent[rootA] = rootB;
                }
            }
        }
    }
}
