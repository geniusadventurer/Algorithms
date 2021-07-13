package graph;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;

// 有向环问题，用深度优先搜索：找到一条环路后，输出该环路的所有顶点。
public class DirectedCycle {
    private boolean[] marked;
    private int[] edgeTo;
    private Stack<Integer> cycle; // 栈
    private boolean[] onStack; // 保存递归调用期间栈上的所有顶点
    public DirectedCycle (Digraph G){
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];
        for (int i = 0; i < G.V(); i++){
            if (!marked[i]) dfs(G, i);
        }
    }
    private void dfs(Digraph G, int v)
    {
        marked[v] = true;
        // 但为什么要加一个onStack呢？按道理只要我被标记过就可以了？我想真正的原因是，marked只负责是否被标记，onStack是实时变动的，相当于是一个排除用的布尔型数组。
        // 如果一条支线上没有找到环，marked还可以是true，但onStack则必须要改回false。
        onStack[v] = true;
        // 按照其邻接关系依次寻找，找到一个后往下搜索做递归
        for (int w: G.adj(v)){
            // 若有环了 返回
            if (this.hasCycle()) return;
            // 若无环，且w没有被访问过，递归执行深度优先搜索
            else if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
            // 若无环，且访问到了w且w没被排除，说明重复访问，则找到了一个环，此时依次将各个顶点放进去。
            else if (onStack[w]){
                cycle = new Stack<Integer>();
                // edgeTo[x]找的是x的前一个点，所以是从v开始往前追溯到所有点，直到找到w为止，最后把w和v都放进去，完成循环
                for (int x = v; x != w; x = edgeTo[x]){
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }
        // 找不到，则使得onStack为false，排除v点在环中的可能性。
        onStack[v] = false;
    }
    public boolean hasCycle(){
        return cycle != null;
    }
    public Iterable<Integer> cycle(){
        return cycle;
    }
}
