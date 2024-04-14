package org.example.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * Класс Граф
 */
public class Graph {
    int verticesNum;
    LinkedList<Edge> [] adjacencyVertices;

    public Graph(int verticesNum) {
        this.verticesNum = verticesNum;
        adjacencyVertices = new LinkedList[verticesNum];
        for (int i = 0; i < verticesNum; i++) {
            adjacencyVertices[i] = new LinkedList<>();
        }
    }

    public int getVerticesNum() {
        return verticesNum;
    }

    public void setVerticesNum(int verticesNum) {
        this.verticesNum = verticesNum;
    }

    public void addEdge(Vertex source, Vertex destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencyVertices[source.getVertNumber() - 1].add(edge);
    }

    public void printGraph() {
        for (int i = 0; i < verticesNum ; i++) {
            LinkedList<Edge> list = adjacencyVertices[i];
            for (Edge edge : list) {
                System.out.println("vertex-" + (i + 1) + " is connected to " +
                        edge.getDestination().getVertNumber() + " with weight " + edge.getWeight());
            }
        }
    }

    public void connectVertices(Graph graph, List<Vertex> vertices) {
        vertices.forEach(vertex -> {
            ListIterator<Vertex> vertexIterator = vertices.listIterator();
            while (vertexIterator.hasNext()) {
                Vertex vertexNext = vertexIterator.next();
                int vertexIndex = vertexIterator.nextIndex();
                if (vertexIndex == vertex.getVertNumber()) continue;
                int weight = Math.abs(vertex.getX() - vertexNext.getX()) + Math.abs(vertex.getY() - vertexNext.getY());
                graph.addEdge(vertex, vertexNext, weight);
            }
        });
    }
}
