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

    public int getDiameter() {
        int diameter = 0;

        // Перебираем все вершины для нахождения максимального пути от каждой вершины
        for (int i = 0; i < verticesNum; i++) {
            for (int j = i + 1; j < verticesNum; j++) {
                int dist = findShortestPath(i, j);
                if (dist > diameter) {
                    diameter = dist;
                }
            }
        }
        return diameter;
    }

    private int findShortestPath(int source, int destination) {
        // Initialize distances from source to all other vertices as INFINITE
        int[] dist = new int[verticesNum];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0; // Distance from source to itself is always 0

        // Create a priority queue to store vertices that are being preprocessed.
        PriorityQueue<Integer> pq = new PriorityQueue<>(verticesNum, Comparator.comparingInt(o -> dist[o]));
        pq.add(source);

        while (!pq.isEmpty()) {
            int u = pq.poll();

            // Get all adjacent vertices of a vertex
            for (int neighbor : adj[u]) {
                // If there is a shorter path to neighbor through u
                if (dist[u] + 1 < dist[neighbor]) {
                    // Remove neighbor from pq if it is already there
                    pq.remove(neighbor);
                    // Updating distance of neighbor
                    dist[neighbor] = dist[u] + 1;
                    pq.add(neighbor);
                }
            }
        }
        return dist[destination];
    }

    public void reverseDeleteMST(int diameter) {
        Collections.sort(edges);
        List<Edge> resultEdges = new ArrayList<>();
        int mstWeight = 0;
        for (int i = edges.size() - 1; i >= 0; i--) {
            int u = edges.get(i).getU();
            int v = edges.get(i).getV();

            adj[u].remove((Integer) v);
            adj[v].remove((Integer) u);
            if (!isConnected()) {
                adj[u].add(v);
                adj[v].add(u);
                resultEdges.add(new Edge(u, v, edges.get(i).getW()));
                mstWeight += edges.get(i).getW();
            }
        }
        deleteEdgesUntilDiameter(diameter);
    }

    public void deleteEdgesUntilDiameter(int targetDiameter) {
        int mstWeight = 0;
        int currentDiameter = getDiameter();
        HashSet<Integer> vertex = new HashSet<>();
        int countEdges = 0;
        while (currentDiameter > targetDiameter) {
            // Сортируем рёбра в порядке убывания их весов
            Collections.sort(edges);

            // Удаляем рёбра, начиная с самых тяжелых
            for (int i = edges.size() - 1; i >= 0; i--) {
                Edge edgeToRemove = edges.get(i);
                int u = edgeToRemove.getU();
                int v = edgeToRemove.getV();

                // Удаляем ребро из списка смежности
                adj[u].remove((Integer) v);
                adj[v].remove((Integer) u);

                // Если после удаления ребра граф остается связным и его диаметр уменьшается, добавляем ребро в результат
                if (isConnected() && getDiameter() < currentDiameter) {
                    currentDiameter = getDiameter();
                    mstWeight += edges.get(i).getW();
                    countEdges++;
                    vertex.add(u);
                    vertex.add(v);
                    System.out.println("e " + (u+1) + " " + (v+1));
                    break; // Переходим к следующей итерации
                } else {
                    // Если удаление ребра приводит к разрыву связности или не уменьшает диаметр, восстанавливаем ребро
                    adj[u].add(v);
                    adj[v].add(u);
                }
            }
        }
        System.out.println(vertex.size() + " " + countEdges);
        System.out.println(mstWeight);
    }
}
