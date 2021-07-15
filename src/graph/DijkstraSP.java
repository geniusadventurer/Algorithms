package graph;

import edu.princeton.cs.algs4.*;

import java.io.File;

// Dijkstra最短路径算法，其实与Prim算法是十分接近的！
public class DijkstraSP {
    // 构造函数
    private DirectedEdge[] finalEdge; // finalEdge[w]：树中连接w及其前一个节点的边（即s到w的最短路径的最后一条边）
    private double[] distTo; // distTo[v]，从s到v的已知最短路径的长度
    private IndexMinPQ<Double> pq; // 优先队列pq：edgeTo[]的复刻，找最小值用的
    // 从s出发的最短路径
    public DijkstraSP(EdgeWeightedDigraph G, int s){
        finalEdge = new DirectedEdge[G.V()];
        distTo = new double[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        finalEdge[s] = null;
        distTo[s] = 0.0;
        for (int i = 0; i < G.V(); i++){
            if (i != s) {
                distTo[i] = Double.POSITIVE_INFINITY;
            }
        } // 初始化
        pq.insert(s, 0.0);
        while (!pq.isEmpty()){
            relax(G, pq.delMin());
        } // Dijkstra的核心：每次放松edgeTo里最小的那条边。
    }
    // 从s到v的距离，初始化为无穷大。
    public double distTo (int v){
        return distTo[v];
    }
    // 是否存在从s到v的路径
    public boolean hasPathTo (int v){
        return (distTo(v) < Double.POSITIVE_INFINITY);
    }
    // 对v的每条邻接边e的放松。放松算法是最短路径算法的理论基础。
    // 把relax想象成一条橡皮筋，拉到weight最短的位置就是放松。
    private void relax (EdgeWeightedDigraph G, int v){
        for (DirectedEdge e: G.adj(v)){
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight()){
                distTo[w] = distTo[v] + e.weight();
                finalEdge[w] = e;
                if (pq.contains(w)) pq.changeKey(w, distTo[w]);
                else pq.insert(w, distTo[w]);
                // 将e边的终点w添加到优先队列
            }
        }
    }
    // 输出s到v的路径
    Iterable<DirectedEdge> pathTo(int v){
        if (!hasPathTo(v)) return null;
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = finalEdge[v]; e != null; e = finalEdge[e.from()])
            path.push(e);
        return path;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph G;
        G = new EdgeWeightedDigraph(new In(new File("F:/Coding/algs4-data/tinyEWD.txt")));
        int s = StdIn.readInt();
        DijkstraSP sp = new DijkstraSP(G, s);
        for (int t = 0; t < G.V(); t++){
            StdOut.print(s + " to " + t);
            StdOut.printf(" (%4.2f): ", sp.distTo(t));
            if (sp.hasPathTo(t)) {
                for (DirectedEdge e: sp.pathTo(t))
                    StdOut.print(e + "  ");
            }
            StdOut.println();
        }
    }
}
