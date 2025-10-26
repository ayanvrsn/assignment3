public class Edge {
    public int from;
    public int to;
    public int weight;
    
    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
    
    @Override
    public String toString() {
        return "(" + from + "-" + to + ": " + weight + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        return from == edge.from && to == edge.to && weight == edge.weight;
    }
    
    @Override
    public int hashCode() {
        int result = from;
        result = 31 * result + to;
        result = 31 * result + weight;
        return result;
    }
}


