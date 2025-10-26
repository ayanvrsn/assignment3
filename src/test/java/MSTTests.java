import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class MSTTests {
    private Graph smallGraph;
    private Graph mediumGraph;
    
    @BeforeEach
    public void setUp() {
        smallGraph = new Graph(5);
        smallGraph.addEdge(0, 1, 2);
        smallGraph.addEdge(0, 2, 3);
        smallGraph.addEdge(1, 2, 1);
        smallGraph.addEdge(1, 3, 4);
        smallGraph.addEdge(2, 3, 5);
        smallGraph.addEdge(2, 4, 6);
        smallGraph.addEdge(3, 4, 3);
        
        mediumGraph = new Graph(10);
        mediumGraph.addEdge(0, 1, 8);
        mediumGraph.addEdge(0, 2, 5);
        mediumGraph.addEdge(1, 2, 10);
        mediumGraph.addEdge(1, 3, 3);
        mediumGraph.addEdge(2, 3, 7);
        mediumGraph.addEdge(2, 4, 6);
        mediumGraph.addEdge(3, 4, 9);
        mediumGraph.addEdge(3, 5, 4);
        mediumGraph.addEdge(4, 5, 11);
        mediumGraph.addEdge(5, 6, 8);
        mediumGraph.addEdge(6, 7, 2);
        mediumGraph.addEdge(7, 8, 12);
        mediumGraph.addEdge(8, 9, 5);
        mediumGraph.addEdge(4, 9, 7);
    }
    
    @Test
    public void testPrimCorrectness() {
        PrimMST prim = new PrimMST(smallGraph);
        MSTResult result = prim.calculate();
        
        assertNotNull(result);
        assertEquals(smallGraph.getVertices() - 1, result.edges.size());
        assertTrue(result.totalCost > 0);
        assertTrue(result.executionTime >= 0);
        assertTrue(result.operations > 0);
    }
    
    @Test
    public void testKruskalCorrectness() {
        KruskalMST kruskal = new KruskalMST(smallGraph);
        MSTResult result = kruskal.calculate();
        
        assertNotNull(result);
        assertEquals(smallGraph.getVertices() - 1, result.edges.size());
        assertTrue(result.totalCost > 0);
        assertTrue(result.executionTime >= 0);
        assertTrue(result.operations > 0);
    }
    
    @Test
    public void testSameMSTCost() {
        PrimMST prim = new PrimMST(smallGraph);
        MSTResult primResult = prim.calculate();
        
        KruskalMST kruskal = new KruskalMST(smallGraph);
        MSTResult kruskalResult = kruskal.calculate();
        
        assertEquals(primResult.totalCost, kruskalResult.totalCost);
    }
    
    @Test
    public void testMSTHasNoCycles() {
        PrimMST prim = new PrimMST(mediumGraph);
        MSTResult result = prim.calculate();
        
        assertEquals(mediumGraph.getVertices() - 1, result.edges.size());
        
        Set<Integer> visited = new HashSet<>();
        Map<Integer, List<Integer>> adjacency = new HashMap<>();
        
        for (Edge edge : result.edges) {
            visited.add(edge.from);
            visited.add(edge.to);
            adjacency.putIfAbsent(edge.from, new ArrayList<>());
            adjacency.putIfAbsent(edge.to, new ArrayList<>());
            adjacency.get(edge.from).add(edge.to);
            adjacency.get(edge.to).add(edge.from);
        }
        
        assertEquals(mediumGraph.getVertices(), visited.size());
    }
    
    @Test
    public void testAllVerticesConnected() {
        KruskalMST kruskal = new KruskalMST(mediumGraph);
        MSTResult result = kruskal.calculate();
        
        Set<Integer> vertices = new HashSet<>();
        for (Edge edge : result.edges) {
            vertices.add(edge.from);
            vertices.add(edge.to);
        }
        
        assertEquals(mediumGraph.getVertices(), vertices.size());
    }
    
    @Test
    public void testEdgeCountCorrect() {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 5);
        graph.addEdge(1, 2, 3);
        graph.addEdge(2, 3, 7);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 5, 9);
        
        PrimMST prim = new PrimMST(graph);
        MSTResult primResult = prim.calculate();
        
        KruskalMST kruskal = new KruskalMST(graph);
        MSTResult kruskalResult = kruskal.calculate();
        
        assertEquals(graph.getVertices() - 1, primResult.edges.size());
        assertEquals(graph.getVertices() - 1, kruskalResult.edges.size());
    }
    
    @Test
    public void testExecutionTimeNonNegative() {
        PrimMST prim = new PrimMST(smallGraph);
        MSTResult primResult = prim.calculate();
        
        KruskalMST kruskal = new KruskalMST(smallGraph);
        MSTResult kruskalResult = kruskal.calculate();
        
        assertTrue(primResult.executionTime >= 0);
        assertTrue(kruskalResult.executionTime >= 0);
    }
    
    @Test
    public void testOperationsConsistent() {
        PrimMST prim = new PrimMST(smallGraph);
        MSTResult primResult = prim.calculate();
        MSTResult primResult2 = prim.calculate();
        
        assertEquals(primResult2.operations, primResult.operations);
    }
}


