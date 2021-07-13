package graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// 广度优先搜索：目的是输出一个顶点到另一个顶点的最短路径
public class BFS {
    private boolean[] marked;
    // 记录w向下搜索到的所有邻接点
    private int[] edgeTo;
    private int s; //起点
    public BFS(Graph G, int s)
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }
    // 以v为起点的广度优先搜索。思想其实是先找节点再找与它相邻的且不重复的节点，把它排进queue里，因此用queue先进先出。
    private void bfs(Graph G, int s) {
        Queue<Integer> queue = new Queue<Integer>(); // 新建一个队列
        marked[s] = true; // 起点s已被搜索
        queue.enqueue(s); // 将s放入queue中
        // 只要队列不是空的
        while (!queue.isEmpty()) {
            // 弹出首位顶点
            int v = queue.dequeue();
            // 按照其邻接关系找到该顶点的所有邻接点，检查其是否被标记，w是被搜索的邻接点
            for (int w : G.adj(v)) // w是v的下一级相邻顶点
                if (!marked[w]) { // 当w还未被访问过时
                    edgeTo[w] = v; // 将v保存为最后一个与w相邻的顶点
                    marked[w] = true; // w被访问了
                    queue.enqueue(w); // 把w放进去
                }
        }
    }
    // 判断输入的两节点是否相连
    public boolean hasPathTo(int w)
    {
        return marked[w];
    }
    // 输出连通路径
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
        BFS d = new BFS(G, s);
        // 是否存在路径
        StdOut.print(d.hasPathTo(e));
        StdOut.print("\n");
        // 输出路径
        if (d.hasPathTo(e)) {
            StdOut.print(d.pathTo(e));
        }
    }
}
