import java.util.*;

public class Graph {
    private int vertices;
    private List<List<Edge>> adjacencyList;
    private List<Edge> edgeList;
    
    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new ArrayList<>(vertices);
        edgeList = new ArrayList<>();
        
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }
    }
    
    public void addEdge(int from, int to, int weight) {
        Edge edge = new Edge(from, to, weight);
        adjacencyList.get(from).add(edge);
        adjacencyList.get(to).add(new Edge(to, from, weight));
        edgeList.add(edge);
    }
    
    public int getVertices() {
        return vertices;
    }
    
    public List<List<Edge>> getAdjacencyList() {
        return adjacencyList;
    }
    
    public List<Edge> getEdgeList() {
        return edgeList;
    }
    
    public int getEdgeCount() {
        return edgeList.size();
    }
    
    public List<Edge> getAllEdges() {
        return edgeList;
    }
}

