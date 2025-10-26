import java.util.*;

public class KruskalMST {
    private int vertices;
    private List<Edge> edges;
    private int operations;
    private long executionTime;
    private int totalCost;
    private List<Edge> mstEdges;
    
    public KruskalMST(Graph graph) {
        this.vertices = graph.getVertices();
        this.edges = graph.getEdgeList();
        this.operations = 0;
    }
    
    public MSTResult calculate() {
        long startTime = System.nanoTime();
        operations = 0;
        mstEdges = new ArrayList<>();
        totalCost = 0;
        
        List<Edge> sortedEdges = new ArrayList<>(edges);
        Collections.sort(sortedEdges, Comparator.comparingInt(e -> e.weight));
        operations += sortedEdges.size() * (int)Math.log(sortedEdges.size());
        
        UnionFind uf = new UnionFind(vertices);
        
        for (Edge edge : sortedEdges) {
            operations++;
            
            if (uf.find(edge.from) != uf.find(edge.to)) {
                uf.union(edge.from, edge.to);
                mstEdges.add(edge);
                totalCost += edge.weight;
                operations += 2;
                
                if (mstEdges.size() == vertices - 1) {
                    break;
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

class UnionFind {
    private int[] parent;
    private int[] rank;
    private int operations;
    
    public UnionFind(int size) {
        parent = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
        operations = 0;
    }
    
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
            operations++;
        }
        operations++;
        return parent[x];
    }
    
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        
        if (rootX == rootY) {
            operations++;
            return;
        }
        
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
        operations += 3;
    }
}


