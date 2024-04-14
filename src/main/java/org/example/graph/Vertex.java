package org.example.graph;

/**
 * Класс вершин
 */
public class Vertex {
    int vertNumber;
    int x;
    int y;

    public Vertex(int vertNumber, int x, int y) {
        this.vertNumber = vertNumber;
        this.x = x;
        this.y = y;
    }

    public int getVertNumber() {
        return vertNumber;
    }

    public void setVertNumber(int vertNumber) {
        this.vertNumber = vertNumber;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
