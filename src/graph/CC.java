package graph;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

// CC：连通分量
public class CC {
    private boolean[] marked;
    private int[] id; // id是以顶点为索引的数组，存储的值是标识符
    private int count; // count是CC的数目
    public void dfs(Graph G, int v)
    {
        marked[v] = true;
        id[v] = count; // 访问到v，就把count值赋给id数组作为标志
        // 深度优先搜索
        for (int w: G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }
    // 循环图内每个顶点
    public CC(Graph G)
    {
        marked = new boolean[G.V()];
        id = new int[G.V()]; // 分别初始化marked和id
        // 深度优先搜索：从0开始对每个s，若s未被访问，则从s开始做深度优先搜索
        for (int s = 0; s < G.V(); s++)
            if (!marked[s]) {
                dfs(G, s); // 从s点开始做深度优先搜索
                count++;
            }
    }
    public boolean connected(int v, int w){
        return id[v] == id[w];
    }
    public int id(int v){
        return id[v];
    }
    public int count(){
        return count;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        CC cc = new CC(G);
        // 是否存在路径
        StdOut.print(cc.count() + " connected components");
        StdOut.print("\n");
        // 输出连通分量。本来也用两层循环做了输出，但显然可以优化：设置背包，放进每个顶点对应的id对应的背包里，再依次输出背包即可。
        Bag<Integer>[] components;
        components = (Bag<Integer>[]) new Bag[cc.count()];
        for (int i = 0; i < cc.count(); i++)
            components[i] = new Bag<Integer>();
        for (int j = 0; j < G.V(); j++)
            components[cc.id(j)].add(j);
        for (int i = 0; i < cc.count(); i++)
        {
            for (int j: components[i])
                StdOut.print(j + " ");
            StdOut.print("\n");
        }
    }
}