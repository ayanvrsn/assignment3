import java.io.*;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MSTExperiment {
    
    public static void main(String[] args) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            
            String inputJson = readFile("input.json");
            InputData inputData = gson.fromJson(inputJson, InputData.class);
            
            List<ResultData> results = new ArrayList<>();
            
            for (GraphData graphData : inputData.graphs) {
                Graph graph = buildGraph(graphData);
                
                PrimMST prim = new PrimMST(graph);
                MSTResult primResult = prim.calculate();
                
                KruskalMST kruskal = new KruskalMST(graph);
                MSTResult kruskalResult = kruskal.calculate();
                
                ResultData result = new ResultData();
                result.graphName = graphData.name;
                result.vertices = graph.getVertices();
                result.edges = graph.getEdgeCount();
                
                result.prim = new AlgorithmResult();
                result.prim.mstEdges = convertEdges(primResult.edges);
                result.prim.totalCost = primResult.totalCost;
                result.prim.operations = primResult.operations;
                result.prim.executionTime = primResult.executionTime;
                
                result.kruskal = new AlgorithmResult();
                result.kruskal.mstEdges = convertEdges(kruskalResult.edges);
                result.kruskal.totalCost = kruskalResult.totalCost;
                result.kruskal.operations = kruskalResult.operations;
                result.kruskal.executionTime = kruskalResult.executionTime;
                
                results.add(result);
                
                System.out.println("\n=== " + graphData.name + " ===");
                System.out.println("Vertices: " + result.vertices + ", Edges: " + result.edges);
                System.out.println("Prim's MST Cost: " + primResult.totalCost);
                System.out.println("Kruskal's MST Cost: " + kruskalResult.totalCost);
                System.out.println("Match: " + (primResult.totalCost == kruskalResult.totalCost));
                System.out.println("Prim time: " + primResult.executionTime + "ms, ops: " + primResult.operations);
                System.out.println("Kruskal time: " + kruskalResult.executionTime + "ms, ops: " + kruskalResult.operations);
            }
            
            OutputData output = new OutputData();
            output.results = results;
            
            String outputJson = gson.toJson(output);
            writeFile("output.json", outputJson);
            
            System.out.println("\nResults written to output.json");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static Graph buildGraph(GraphData graphData) {
        Graph graph = new Graph(graphData.vertices);
        for (EdgeData edge : graphData.edges) {
            graph.addEdge(edge.from, edge.to, edge.weight);
        }
        return graph;
    }
    
    private static List<String> convertEdges(List<Edge> edges) {
        List<String> result = new ArrayList<>();
        for (Edge edge : edges) {
            result.add(edge.from + "-" + edge.to);
        }
        return result;
    }
    
    private static String readFile(String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }
    
    private static void writeFile(String filename, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(content);
        }
    }
    
    static class InputData {
        List<GraphData> graphs;
    }
    
    static class GraphData {
        String name;
        int vertices;
        List<EdgeData> edges;
    }
    
    static class EdgeData {
        int from;
        int to;
        int weight;
    }
    
    static class OutputData {
        List<ResultData> results;
    }
    
    static class ResultData {
        String graphName;
        int vertices;
        int edges;
        AlgorithmResult prim;
        AlgorithmResult kruskal;
    }
    
    static class AlgorithmResult {
        List<String> mstEdges;
        int totalCost;
        int operations;
        long executionTime;
    }
}


