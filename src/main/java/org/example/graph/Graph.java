package org.example.graph;

import java.util.*;

/**
 * Класс Граф
 */
public class Graph {
    private final int verticesNum; // количество вершин
    private final List<Integer>[] adj; // смежные вершины
    private final List<Edge> edges; // рёбра

    public Graph(int verticesNum) {
        this.verticesNum = verticesNum;
        adj = new ArrayList[verticesNum];
        for (int i = 0; i < verticesNum; i++) {
            adj[i] = new ArrayList<Integer>();
        }
        edges = new ArrayList<Edge>();
    }

    public void addEdge(int u, int v, int w) {
        adj[u].add(v);
        adj[v].add(u);
        edges.add(new Edge(u, v, w));
    }

    private void DFS(int v, boolean[] visited) {
        // помечаем текущую вершину пройденной
        visited[v] = true;

        // рекурсивно проходим для всех вершин смежных с текущим
        for (int i : adj[v]) {
            if (!visited[i])
                DFS(i, visited);
        }
    }

    // проверка на связность графа
    private boolean isConnected() {
        boolean[] visited = new boolean[verticesNum];

        // Находим все доступные вершины начиная с первой
        DFS(0, visited);
        // Если множество достижимых вершин включает все, возвращаем true
        for (int i = 1; i < verticesNum; i++) {
            if (!visited[i])
                return false;
        }
        return true;
    }

    public void reverseDeleteMST(int diameter) {
        Collections.sort(edges);
        int mstWeight = 0;
        for (int i = edges.size() - 1; i >= 0; i--) {
//            System.out.println(i);
            int u = edges.get(i).u;
            int v = edges.get(i).v;

            adj[u].remove((Integer) v);
            adj[v].remove((Integer) u);

            if (!isConnected()) {
                adj[u].add(v);
                adj[v].add(u);
                System.out.println("(" + (u + 1) + ", " + (v + 1)
                        + ")");
                mstWeight += edges.get(i).w;
            }
        }
        System.out.println(mstWeight);
    }
}
