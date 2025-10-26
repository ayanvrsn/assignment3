import java.util.*;

public class PrimMST {
    private int vertices;
    private List<List<Edge>> graph;
    private int operations;
    private long executionTime;
    private int totalCost;
    private List<Edge> mstEdges;
    
    public PrimMST(Graph graph) {
        this.vertices = graph.getVertices();
        this.graph = graph.getAdjacencyList();
        this.operations = 0;
    }
    
    public MSTResult calculate() {
        long startTime = System.nanoTime();
        operations = 0;
        mstEdges = new ArrayList<>();
        totalCost = 0;
        
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(
            Comparator.comparingInt(e -> e.weight)
        );
        boolean[] visited = new boolean[vertices];
        
        // Start with vertex 0
        visited[0] = true;
        operations++;
        
        for (Edge edge : graph.get(0)) {
            priorityQueue.offer(edge);
            operations++;
        }
        
        while (!priorityQueue.isEmpty() && mstEdges.size() < vertices - 1) {
            operations++; // comparison check
            
            Edge minEdge = priorityQueue.poll();
            operations++;
            
            int nextVertex = -1;
            if (visited[minEdge.from] && !visited[minEdge.to]) {
                nextVertex = minEdge.to;
            } else if (visited[minEdge.to] && !visited[minEdge.from]) {
                nextVertex = minEdge.from;
            }
            
            if (nextVertex != -1) {
                visited[nextVertex] = true;
                operations++;
                
                mstEdges.add(minEdge);
                totalCost += minEdge.weight;
                
                for (Edge edge : graph.get(nextVertex)) {
                    if (!visited[edge.to]) {
                        priorityQueue.offer(edge);
                        operations++;
                    }
                }
            }
        }
        
        long endTime = System.nanoTime();
        executionTime = (endTime - startTime) / 1_000_000;
        
        return new MSTResult(mstEdges, totalCost, operations, executionTime);
    }
    
    public int getOperations() {
        return operations;
    }
    
    public long getExecutionTime() {
        return executionTime;
    }
    
    public int getTotalCost() {
        return totalCost;
    }
    
    public List<Edge> getMstEdges() {
        return mstEdges;
    }
}

