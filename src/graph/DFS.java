package graph;
import edu.princeton.cs.algs4.*;

// 深度优先搜索，核心思想是递归
public class DFS{
    private boolean[] marked;
    private int count;
    // 记录w向下搜索的最后一个邻接点
    private int[] edgeTo;
    private int s;
    private Queue<Integer> pre;
    private Queue<Integer> post;
    private Stack<Integer> reversePost;
    public DFS(Graph G, int s)
    {
        pre = new Queue<Integer>();
        post = new Queue<Integer>();
        reversePost = new Stack<Integer>();
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }
    // 以v为起点的深度优先搜索
    private void dfs(Graph G, int v)
    {
        pre.enqueue(v);
        marked[v] = true;
        count++;
        // 按照其邻接关系依次寻找，找到一个后往下搜索做递归
        for (int w: G.adj(v))
        {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
        post.enqueue(v);
        reversePost.push(v);
    }
    // 判断输入的两节点是否相连
    public boolean hasPathTo(int w)
    {
        return marked[w];
    }
    public int count() {
        return count;
    }
    // 输出连通路径：由于是深度优先，所以会一直向深处搜索，因此寻找edgeto数组里最新的那个数组，然后按值又去找它索引到的值，输出整个栈即可。
    public Iterable<Integer> pathTo(int e){
        if (!hasPathTo(e)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = e; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        int s = StdIn.readInt();
        int e = StdIn.readInt();
        DFS d = new DFS(G, s);
        // 是否存在路径
        StdOut.print(d.hasPathTo(e));
        StdOut.print("\n");
        // 输出路径
        if (d.hasPathTo(e)) {
            StdOut.print(d.pathTo(e));
        }
    }
    public Iterable<Integer> pre(){
        return pre;
    }
    public Iterable<Integer> post(){
        return post;
    }
    public Iterable<Integer> reversePost(){
        return reversePost;
    }
}