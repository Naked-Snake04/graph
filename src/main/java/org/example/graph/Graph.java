package org.example.graph;

import java.util.*;

/**
 * Класс Граф
 */
public class Graph {
    private int verticesNum; // количество вершин
    private List<List<Integer>> adjacencyList; // смежные вершины
    private List<Edge> edges; // рёбра


    public Graph(int verticesNum) {
        this.verticesNum = verticesNum;
        adjacencyList = new ArrayList<>(verticesNum);
        for (int i = 0; i < verticesNum; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        edges = new ArrayList<>();
    }

    public void addEdge(int u, int v, int w) {
        edges.add(new Edge(u, v, w));
        edges.add(new Edge(v, u, w));
    }

    private Edge findEdge(int u, int v) {
        List<Edge> findEdges = edges.stream().filter(edge -> edge.getU() == u && edge.getV() == v).toList();
        return findEdges.getFirst();
    }

    public void findThreeEdgesTree() {
        List<Edge> result = new ArrayList<>();
        for (int i = 0; i < verticesNum; i += 2) {
            int v = (i + 1) % verticesNum;

            Edge edge1 = findEdge(i, v);
            Edge edge2 = findEdge(i, (i + 2) % verticesNum);
            Edge edge3 = findEdge(v, (v + 2) % verticesNum);
            result.add(edge1);
            result.add(edge2);
            result.add(edge3);
        }
        var ref = new Object() {
            int totalWeight = 0;
            int maxWeight = Integer.MIN_VALUE;
            int countEdges = 0;
            int countVertex = 0;
        };
        HashSet<Integer> usedVertices = new HashSet<>();
        result.forEach(edge -> {
            System.out.println("e " + (edge.getU() + 1) + " " + (edge.getV() + 1));
            ref.countEdges++;
            if (usedVertices.add(edge.getU()))
                ref.countVertex++;
            ref.totalWeight += edge.getW();
            if (edge.getW() > ref.maxWeight) {
                ref.maxWeight = edge.getW();
            }
        });

        System.out.println("c Вес кубического подграфа = " + ref.totalWeight
                + ", самое длинное ребро = " + ref.maxWeight);
        System.out.println("p edge " + ref.countVertex + " " + ref.countEdges);
    }
}
