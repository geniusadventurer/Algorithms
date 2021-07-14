package graph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Edge;

import java.util.Comparator;

// Kruskal算法的最小生成树。每次都找所有边里的最小边，把它们连起来。如果已经相连了就不再连接，直接跳过。再把该边加到最小生成树里。简洁明了！
public class KruskalMST {
    private Queue<Edge> mst;
    public KruskalMST(EdgeWeightedGraph G){
        mst = new Queue<edu.princeton.cs.algs4.Edge>();
        MinPQ<Edge> pq = new MinPQ<Edge>((Comparator<Edge>) G.edges());
        UF uf = new UF(G.V());
        while (!pq.isEmpty() && mst.size() < G.V()-1){
            Edge min = pq.delMin();
            int v = min.either();
            int w = min.other(v);
            if (uf.connected(v, w)) continue;
            uf.union(v, w);
            mst.enqueue(min);
        }
    }
}
