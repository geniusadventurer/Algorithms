package graph;

import edu.princeton.cs.algs4.Bag;

public class DiGraph {
    public int V;
    public int E;
    private Bag<Integer>[] adj;
    public DiGraph(int V){
        this.V = V;
        // adj：邻接表
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++)
            adj[v] = new Bag<Integer>();
    }
    public int V(){
        return V;
    }
    public int E(){
        return E;
    }
    // 添加边，因为是单向图，所以只添加一个边
    public void addEdge(int v, int w){
        adj[v].add(w);
    }
    public Iterable<Integer> adj(int v){
        return adj[v];
    }
    // 输出一张反向图
    public DiGraph reverse(){
        DiGraph R = new DiGraph(V);
        for (int v = 0; v < V; v++){
            for (int w: adj(v))
                R.addEdge(w, v);
        }
        return R;
    }
}
