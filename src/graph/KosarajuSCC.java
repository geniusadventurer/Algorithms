package graph;

import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;

// 强连通分量搜索的Kosaraju算法，连通分量CC算法的延续
public class KosarajuSCC {
    private boolean[] marked;
    private int[] id; // id是以顶点为索引的数组，存储的值是标识符
    private int count; // count是CC的数目
    public void dfs(Digraph G, int v)
    {
        marked[v] = true;
        id[v] = count; // 访问到v，就把count值赋给id数组作为标志
        // 深度优先搜索
        for (int w: G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }
    // 循环图内每个顶点
    public KosarajuSCC(Digraph G)
    {
        marked = new boolean[G.V()];
        id = new int[G.V()]; // 分别初始化marked和id
        // 深度优先搜索：从0开始对每个s，若s未被访问，则从s开始做深度优先搜索
        DepthFirstOrder order = new DepthFirstOrder(G.reverse()); // 比起CC，加了这一行拓扑排序
        for (int s: order.reversePost()) // 比起CC，变了这一行
            if (!marked[s]) {
                dfs(G, s); // 从s点开始做深度优先搜索
                count++;
            }
    }
    public boolean stronglyConnected(int v, int w){
        return id[v] == id[w];
    }
    public int id(int v){
        return id[v];
    }
    public int count(){
        return count;
    }
}
