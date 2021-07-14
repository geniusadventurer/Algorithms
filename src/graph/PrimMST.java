package graph;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.Edge;

import java.io.File;

public class PrimMST {
    private Edge[] minEdge; // 每个点与最小生成树连接的最短边，它们会被放进pq
    private IndexMinPQ<Double> pq; // 有效横切边（候选边）名单，动态更新，把里面最小的加进来。还是没理解pq的意思啊！
    private boolean[] marked; // 被标记的点，最小生成树的点
    private double[] minWeight; // 入选最小边的权重，动态更新
    public PrimMST(EdgeWeightedGraph G){
        minEdge = new Edge[G.V()];
        pq = new IndexMinPQ<Double>(G.V());
        marked = new boolean[G.V()];
        minWeight = new double[G.V()];
        for (int i = 0; i < G.V(); i++){
            minWeight[i] = Double.POSITIVE_INFINITY;
        }
        minWeight[0] = 0.0;
        pq.insert(0, 0.0); // 初始化
        while (!pq.isEmpty()){
            choose(G, pq.delMin()); // 把minWeight[i]的各个值放在一起找最小值，把有效横切边pq中最小的那一个选出来，选进minEdge、minWeight、pq
        }
    }
    // 选中点v进入最小生成树，并更新数据
    private void choose(EdgeWeightedGraph G, int v){
        marked[v] = true;
        for (Edge e: G.adj(v)){
            int w = e.other(v);
            if (marked[w]) continue;
            else {
                if (e.weight() < minWeight[w]) {
                    minWeight[w] = e.weight();
                    minEdge[w] = e; // 更新每个点连到最小生成树的最小权重和对应边
                    // 下列If和else是在队列pq里同步更新minweight，以便于找出最小的。pq的作用是找汇总各个点到最小生成树的最小值，再找最小的进入最小生成树。
                    // 原理上，只要在minWeight这样一个double[]里找到最小的，其实就可以了！使用pq在这里，应该是因为java提供的double[]数据结构没直接实现找最小值，
                    // 或者用pq找，速度更快？
                    if (pq.contains(w)) pq.changeKey(w, e.weight());
                    else pq.insert(w, e.weight());
                }
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(new File("F:/Coding/algs4-data/tinyEWG.txt"));
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);
        PrimMST mst = new PrimMST(G);
    }
}
