package graph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SymbolGraph {
    private ST<String, Integer> st; // 符号名——索引
    private String[] keys; // 索引——符号名
    private Graph G; // 图
    public SymbolGraph(String stream, String sp){
        st = new ST<String, Integer>();
        In in = new In(stream);
        // 第一步：构建以字符为键，size为值的键值对st
        while (in.hasNextLine()){
            String[] a = in.readLine().split(sp);
            for (int i = 0; i < a.length; i++)
                if (!st.contains(a[i]))
                    st.put(a[i], st.size());
        }
        // 第二步：构建以数值为索引，字符为对应值的字符串数组，建立反向映射关系。和数字表的区别主要就是在，映射关系是键值对，而不是列表索引和值。
        keys = new String[st.size()];
        for (String name: st.keys())
            keys[st.get(name)] = name;
        // 第三步：构造图，在Graph内添加边。
        G = new Graph(st.size());
        in = new In(stream);
        while (in.hasNextLine()){
            String[] a = in.readLine().split(sp);
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++)
                G.addEdge(v, st.get(a[i]));
        }
    }
    public boolean contains(String key){
        return st.contains(key);
    }
    public int index(String key){
        return st.get(key);
    }
    public String name(int v){
        return keys[v];
    }
    public Graph G(){
        return G;
    }
    public static void main(String[] args) {
        String filename = args[0];
        String delim = args[1];
        SymbolGraph sg = new SymbolGraph(filename, delim);
        Graph G = sg.G();
        while (StdIn.hasNextLine()){
            String source = StdIn.readLine(); // 输入出发地机场
            for (int w: G.adj(sg.index(source)))
                StdOut.println("    " + sg.name(w));
        }
    }
}
