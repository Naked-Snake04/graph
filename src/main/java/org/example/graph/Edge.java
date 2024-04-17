package org.example.graph;

/**
 * Класс рёбер
 */
class Edge implements Comparable<Edge> {
    int u, v, w;
    Edge(int u, int v, int w)
    {
        this.u = u;
        this.w = w;
        this.v = v;
    }
    public int compareTo(Edge other)
    {
        return (this.w - other.w);
    }
}

