package unionfind;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class QuickUnionUF {
    // 相当于python的__init__部分，初始化一个id数组和一个数目，但没有值
    private int[] id;
    private int count;
    private int[] sz;
    // UF对象，参数N为数目。初始化，设数组的值与索引值相同。但此时，数组值代表的是节点的根节点。
    public QuickUnionUF(int N) {
        count = N;
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++){
            id[i] = i;
            sz[i] = 1;
        }
    }
    // 数目
    public int count(){
        return count;
    }
    // 查询：查询的是根节点。这里的本质是，如果一个节点是根节点，则一定有id[p]=p。否则就不是，需要通过循环回溯。
    private int root(int p){
        while(id[p] != p) {
            id[p] = id[id[p]];
            p = id[p];
        }
        return p;
    }
    // 判断两点是否相连
    public boolean connected(int p, int q){
        return root(p) == root(q);
    }
    // 连接两点。连接的是根节点。
    public void union(int p, int q){
        int proot = root(p);
        int qroot = root(q);
        if (sz[proot] < sz[qroot]) {
            id[proot] = qroot; // 直接改变根节点的值，因为根节点索引与数值相同。
            sz[qroot] += sz[proot]; // qroot作为根节点，更新sz数目。这里是将加权Union作为改进。
        }
        else {
            id[qroot] = proot;
            sz[proot] += sz[qroot];
        }
    }
    public static void main(String[] args){
        int N = StdIn.readInt();
        QuickUnionUF uf = new QuickUnionUF(N);
        while (!StdIn.isEmpty()){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                StdOut.println(p + " " + q);
            }
        }
    }
}

