package graph;
import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;

// 拓扑排序。借用digraph，记录有向图深度优先搜索的访问顺序或完成搜索顺序即可。
public class Topological {
    private Iterable<Integer> order;
    public Topological(Digraph G){
        DirectedCycle cycleOrder = new DirectedCycle(G);
        if (!cycleOrder.hasCycle()){
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost(); // 这里可以改成dfs.post()或dfs.pre()，输出的就不是拓扑排序了。
        }
    }
    public Iterable<Integer> order(){
        return order;
    }
    public boolean isDAG(){
        return order != null;
    }
}
