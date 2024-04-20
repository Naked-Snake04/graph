package org.example.graph;

import java.io.FileWriter;
import java.io.IOException;
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
        // Если множество достижимых вершин включает все, возвращаем true
        for (int i = 1; i < verticesNum; i++) {
            if (!visited[i])
                return false;
        }
        return true;
    }

    public void reverseDeleteMST(int diameter) {
        Collections.sort(edges);
        List<Edge> resultEdges = new ArrayList<>();
        List<Edge> finalEdges = new ArrayList<>();
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

        // убираем ребра превышающие диаметр
        resultEdges.sort(Comparator.comparingInt(Edge::getU));
        LinkedList<Integer> list = new LinkedList<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < resultEdges.size(); i++) {
            int u = resultEdges.get(i).getU();
            list.add(u);
            indexes.add(resultEdges.indexOf(resultEdges.get(i)));
            int finalI = i;
            var ref = new Object() {
                int v = resultEdges.get(finalI).getV();
            };
            list.add(ref.v);
            finalEdges.add(new Edge(u, ref.v, 0));
            for (int j = i + 1; j < resultEdges.size(); j++) {
                if (resultEdges.get(j).getU() == ref.v) {
                    list.add(resultEdges.get(j).getV());
                    indexes.add(resultEdges.indexOf(resultEdges.get(j)));
                    ref.v = resultEdges.get(j).getV();
                    finalEdges.add(new Edge(resultEdges.get(j).getU(), ref.v, 0));
                } else if (resultEdges.get(j).getV() == ref.v) {
                    list.add(resultEdges.get(j).getU());
                    indexes.add(resultEdges.indexOf(resultEdges.get(j)));
                    finalEdges.add(new Edge( resultEdges.get(j).getU(), ref.v, 0));
                    ref.v = resultEdges.get(j).getU();
                }
            }
            indexes.sort(Collections.reverseOrder());
            if (list.size() > diameter) {
                while (list.size() != diameter) {
                    mstWeight -= resultEdges.get(indexes.getFirst()).getW();
                    list.remove(list.getLast());
                    if (finalEdges.size() > diameter)
                        finalEdges.remove(finalEdges.getLast());
                    if (indexes.size() > diameter)
                        indexes.remove(indexes.getFirst());
                }
            }
            indexes.sort(Integer::compareTo);
            list.clear();
            indexes.clear();
        }
        finalEdges = removeDuplicates(finalEdges);

        try {
            FileWriter writer = new FileWriter("src/main/resources/result.txt");
            writer.write("c Вес дерева = " + mstWeight + ", диаметр = " + diameter + "\n");
            writer.write("p edge " + countVertices(finalEdges) + " " + finalEdges.size() + "\n");
            finalEdges.forEach(edge -> {
                try {
                    writer.write("e " + (edge.getU() + 1) + " " + (edge.getV() + 1) + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer countVertices (List<Edge> list) {
        HashSet<Integer> vertexSet = new HashSet<>();

        list.forEach(element -> {
            vertexSet.add(element.getV());
            vertexSet.add(element.getU());
        });

        return vertexSet.size();
    }

    public static ArrayList<Edge> removeDuplicates(List<Edge> list) {
        HashSet<List<Integer>> uniqueSet = new HashSet<>();
        ArrayList<Edge> newList = new ArrayList<>();

        for (Edge element : list) {
            if (uniqueSet.add(List.of(element.getU(), element.getV()))) {
                newList.add(element);
            }
        }

        return newList;
    }

}
