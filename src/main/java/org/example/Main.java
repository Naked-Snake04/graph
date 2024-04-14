package org.example;

import org.example.graph.Graph;
import org.example.graph.Vertex;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        FileReader reader = new FileReader("src/main/resources/Taxicab_64.txt");
        Scanner scanner = new Scanner(reader);

        int n; // количество вершин
        n = Integer.parseInt(scanner.nextLine().replaceAll("\\D+", ""));

        List<Vertex> vertices = new ArrayList<>(); // создаём множество вершин
        int verticesNum = 1; // номер вершины (по тз начинается с 1)
        while (scanner.hasNextLine()) {
            String[] coordinates = scanner.nextLine().replaceAll("\\D+", " ").split("\\D+");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            Vertex vertex = new Vertex(verticesNum, x, y);
            vertices.add(vertex); // добавляем во множество вершин
            verticesNum++;
        }
        scanner.close();

        Graph graph = new Graph(n);
        graph.connectVertices(graph, vertices); // Соединяем все вершины со всеми
        graph.printGraph();
    }
}