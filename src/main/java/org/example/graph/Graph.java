package org.example.graph;

import java.util.*;

/**
 * Класс Граф
 */
public class Graph {
    private int verticesNum; // количество вершин
    private List<Integer>[] adj; // смежные вершины
    private List<Edge> edges; // рёбра


    public Graph(int verticesNum) {
        this.verticesNum = verticesNum;
        adj = new ArrayList[verticesNum];
        for (int i = 0; i < verticesNum; i++) {
            adj[i] = new ArrayList<>();
        }
        edges = new ArrayList<>();
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

        // Если множество достижимых вершин не включает все, или диаметр больше заданного, возвращаем false
        for (int i = 0; i < verticesNum; i++) {
            if (!visited[i])
                return false;
        }
        return true;
    }

    public void reverseDeleteMST(int diameter) {
        edges.sort(Comparator.comparingInt(Edge::getU));
        List<Edge> resultEdges = new ArrayList<>();
        int mstWeight = 0;
        for (Edge edge : edges) {
            int u = edge.getU();
            int v = edge.getV();
            int w = edge.getW();

            adj[u].remove((Integer) v);
            adj[v].remove((Integer) u);
            if (!isConnected()) {
                adj[u].add(v);
                adj[v].add(u);
                resultEdges.add(new Edge(u, v, w));
                mstWeight += w;
                System.out.println("e " + (u+1) + " " + (v+1));
            }
        }
        System.out.println(mstWeight);
    }
}
