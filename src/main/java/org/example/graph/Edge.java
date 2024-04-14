package org.example.graph;

/**
 * Класс рёбер
 */
public class Edge {
    private int weight; // вес
    private Vertex source; // начальная вершина
    private Vertex destination; // конечная вершина

    Edge() {

    }

    /**
     * Конструктор вершины
     * @param source - начальная вершина
     * @param destination - конечная вершина
     * @param weight - вес
     */
    Edge(Vertex source, Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Vertex getSource() {
        return source;
    }

    public void setSource(Vertex source) {
        this.source = source;
    }

    public Vertex getDestination() {
        return destination;
    }

    public void setDestination(Vertex destination) {
        this.destination = destination;
    }
}
