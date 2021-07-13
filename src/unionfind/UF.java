package unionfind;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class UF {
    // 相当于python的__init__部分，初始化一个id数组和一个数目，但没有值
    private int[] id;
    private int count;
    // UF对象，参数N为数目。初始化，设数组的值与索引值相同。
    public UF(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++)
            id[i] = i;
    }
    // 数目
    public int count(){
        return count;
    }
    // 查询
    public int find(int p){
        return id[p];
    }
    // 判断两点是否相连
    public boolean connected(int p, int q){
        return find(p) == find(q);
    }
    // 连接两点。注意第4行的pid不能用id[p]，因为它也会改变，造成后续比较的错误
    public void union(int p, int q){
        int pid = find(p);
        int qid = find(q);
        for (int i = 0; i < count; i++)
            if(id[i] == pid) id[i] = qid;
    }
    public static void main(String[] args){
        int N = StdIn.readInt();
        UF uf = new UF(N);
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


