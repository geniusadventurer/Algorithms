package graph;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Graph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private final int V;
    private int E;
    private Bag<Integer>[] adj;
    // 初始化Graph，并建立连接
    public Graph(In in) {
        if (in == null) throw new IllegalArgumentException("argument is null");
        try {
            this.V = in.readInt(); // 输入的第一个值是V
            if (V < 0) throw new IllegalArgumentException("number of vertices in a Graph must be non-negative");
            adj = (Bag<Integer>[]) new Bag[V];
            for (int v = 0; v < V; v++) {
                adj[v] = new Bag<Integer>();
            } // 为每个顶点建立起空背包
            int E = in.readInt(); // 输入的第二个值是E
            if (E < 0) throw new IllegalArgumentException("number of edges in a Graph must be non-negative");
            for (int i = 0; i < E; i++) {
                int v = in.readInt();
                int w = in.readInt();
                validateVertex(v);
                validateVertex(w);
                addEdge(v, w);
            } // 依次读取相连的两顶点并建立起联系
        }
        catch (NoSuchElementException e) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", e);
        }
    }
    // 建立一个规模为v的空图
    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices must be non-negative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }
    // 基本属性
    // 顶点和边的数目
    public int V() {
        return V;
    }
    public int E() {
        return E;
    }
    // 判断有效性的语句
    private void validateVertex(int v) {
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
    // 添加边
    public void addEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        E++; // 更新E值
        adj[v].add(w);
        adj[w].add(v); // 注意双向添加！
    }
    // 返回这个点的邻接表
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }
    // 返回该点的度数
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }
    // 以字符串形式输出所有邻接表
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (int w : adj[v]) {
                s.append(w + " ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        StdOut.println(G);
    }
}