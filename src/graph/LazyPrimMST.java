package graph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Edge;

import java.io.File;

// 最小生成树
public class LazyPrimMST {
    // 初始空间
    private boolean[] marked; // 被标记的点
    private Queue<Edge> mst; // 最小生成树的边
    private MinPQ<edu.princeton.cs.algs4.Edge> pq; // 横切边与失效的边
    // 构造函数
    public LazyPrimMST(EdgeWeightedGraph G){
        pq = new MinPQ<edu.princeton.cs.algs4.Edge>();
        marked = new boolean[G.V()];
        mst = new Queue<edu.princeton.cs.algs4.Edge>();
        visit(G, 0); // 假定G是连通的，将所有与0和为标记顶点相连的边加入pq
        while (!pq.isEmpty()){
            Edge e = pq.delMin(); // 找到最小的边
            int v = e.either(); // 拿出两个顶点
            int w = e.other(v);
            if (marked[v] && marked[w]) continue; // 如果两个顶点都被访问过，则该边无效，跳过
            if (!marked[v]) visit(G, v);
            if (!marked[w]) visit(G, w); // 总有一个顶点要再往下找，把待找的边放进去。两个顶点先后向下访问。
        }
    }
    // 标记顶点v，且将所有与v和未标记顶点相连的边加入pq
    private void visit(EdgeWeightedGraph G, int v){
        marked[v] = true;
        for (edu.princeton.cs.algs4.Edge e: G.adj(v)){
            if (!marked[e.other(v)]){
                pq.insert(e);
            }
        }
    }
    // 所有边
    public Iterable<edu.princeton.cs.algs4.Edge> edges(){
        return mst;
    }
    // 所有权重
    public double weight(){
        double weight = 0.0;
        for (edu.princeton.cs.algs4.Edge e: mst){
            weight += e.weight();
        }
        return weight;
    }
    public static void main(String[] args) {
        In in = new In(new File("F:/Coding/algs4-data/tinyEWG.txt"));
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        LazyPrimMST mst = new LazyPrimMST(G);
        for (edu.princeton.cs.algs4.Edge e: mst.edges()){
            StdOut.println(e);
        }
        StdOut.println(mst.weight());
    }
}
