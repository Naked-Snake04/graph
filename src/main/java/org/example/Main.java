package org.example;

import org.example.graph.Graph;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        FileReader reader = new FileReader("src/main/resources/Taxicab_4096.txt");
        Scanner scanner = new Scanner(reader);

        int n = 0; // количество вершин
        n = Integer.parseInt(scanner.nextLine().replaceAll("\\D+", ""));
        int diameter = n / 16;
        Map<Integer, List<Integer>> vertices = new HashMap<>();
        int numVert = 0;
        while (scanner.hasNextLine()) {
            String[] coordinates = scanner.nextLine().replaceAll("\\D+", " ").split("\\D+");
            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);
            vertices.putIfAbsent(numVert, Arrays.asList(x, y));
            numVert++;
        }

        scanner.close();

        Graph graph = new Graph(n);

        vertices.forEach((key, value) -> {
            for (int i = key + 1; i < vertices.size(); i++) {
                int weight = Math.abs(vertices.get(key).getFirst() - vertices.get(i).getFirst()) +
                        Math.abs(vertices.get(key).getLast() - vertices.get(i).getLast());
                graph.addEdge(key, i, weight);
            }
        });

        graph.findThreeEdgesTree();
    }
}