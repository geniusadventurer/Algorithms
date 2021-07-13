package graph;
import edu.princeton.cs.algs4.*;

// 有向图的深度优先搜索，核心思想是递归
public class DirectedDFS{
    // 不用记录点被数的次数：因为有向图。只记录这个点是否被访问。
    private boolean[] marked;
    // 接受一个顶点
    public DirectedDFS(DiGraph G, int s)
    {
        marked = new boolean[G.V()];
        dfs(G, s);
    }
    // 以v为起点的深度优先搜索
    private void dfs(DiGraph G, int v)
    {
        marked[v] = true;
        // 按照其邻接关系依次寻找，找到一个后往下搜索做递归
        for (int w: G.adj(v))
            if (!marked[w]) {
                dfs(G, w);
            }
    }
    // 判断输入的两节点是否相连
    public boolean marked(int w)
    {
        return marked[w];
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        DiGraph G = new DiGraph(in.readInt());
        G.E = in.readInt();
        while(in.hasNextLine())
            G.addEdge(in.readInt(), in.readInt());
        int p = StdIn.readInt();
        DirectedDFS reachable = new DirectedDFS(G, p);
        // 下面这种方法是输出一个Iterable对象（例如Bag）的常规操作！
        for (int i: G.adj(p))
            StdOut.print(i + " ");
    }
}