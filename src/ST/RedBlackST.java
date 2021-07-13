package ST;
import edu.princeton.cs.algs4.StdOut;

// 二叉树搜索。最重要的思想方法是迭代，返回以xx为根节点且做了某种操作的子树，因为沿着节点向下找的过程，看起来找的是节点，实际上找到的还是这个节点的子树。
// 其次需要理解，一棵二叉树由三个部分构成：左子节点，根节点，右子节点。
public class RedBlackST<Key extends Comparable<Key>, Value> {
    public Node root;
    private static final boolean red = true;
    private static final boolean black = true;
    public class Node{
        private Key key;
        private Value val;
        private Node left, right;
        private boolean color;
        private int N; // 以该结点为根的子树中的结点总数
        public Node(Key key, Value val, int N, boolean color){
            this.key = key; this.val = val; this.N = N; this.color = color;
        } // 创建一个Node对象用的
    }
    // 整体的size
    public int size(){ return size(root); }
    // 以x为根节点的树的size
    private int size(Node x){
        if (x == null) return 0;
        else return size(x.left) + size(x.right) + 1;
    }
    // 递归求解，找不到则返回null
    public Value get(Key key){
        return get(root, key);
    }
    private Value get(Node x, Key key){
        if (x == null) return null;
        else if (key.compareTo(x.key) == 0) return x.val;
        else if (key.compareTo(x.key) < 0) {return get(x.left, key);}
        else {return get(x.right, key);}
    }
    // 检验其连接是否为红
    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == red;
    }
    // 左旋，h节点为根节点，下同
    private Node rotateLeft(Node h){
        Node x = h.right;
        // 节点关系的维护，即根节点的转换
        h.right = x.left;
        x.left = h;
        // 颜色的维护
        x.color = h.color; // 根节点颜色的转换
        h.color = red; // h节点的颜色设为红色
        // 数目的维护
        x.N = h.N; // 新根节点x数目的更新
        h.N = size(h.left) + size(h.right) + 1; // h节点数目的更新
        return x; // 输入的是h，返回的却是x，这样就将其视为根节点
    }
    // 右旋
    private Node rotateRight(Node h){
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = red;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }
    // 若左右两个连接均为红色，则转换连接颜色
    private void flipColor(Node x){
        x.left.color = black;
        x.right.color = black;
        x.color = red;
    }
    // 同样用递归方法求解，特别注意递归函数是返回的是放进以x为根节点并放入key的树
    public void put(Key key, Value val){
        put(root, key, val);
    }
    private Node put(Node x, Key key, Value val){
        if (x == null) {
            Node n = new Node(key, val, 1, red);
            if (size(root) == 0) root = n;
            return n;
        } // 递归到最后没有键，就创建并返回这个新键
        int cmp = key.compareTo(x.key);
        if (cmp == 0) x.val = val; // 替换已有键值
        else if (cmp > 0) x.right = put(x.right, key, val); // x.right = x.right，但右边的x.right最后递归下去是包含新键的
        else x.left = put(x.left, key, val); // 同上
        // 遇到不合规则的键怎么办？平衡操作
        // 1.如果右键为红而左键不为红，则左旋使左键为红
        if (isRed(x.right) && !isRed(x.left)) rotateLeft(x);
        // 2.如果左键连续两个红，则右旋，然后会变成3，所以2必须在3前面
        if (isRed(x.left) && isRed(x.left.left)) rotateRight(x);
        // 3.如果左右键都是红，将红变成黑，上面的黑变红
        if (isRed(x.left) && isRed(x.right)) flipColor(x);
        x.N = size(x.left) + size(x.right) + 1; // 更新N
        return x; // 已有键，则在重新计算N后返回自己，保证已有关系不变
    }
    // 找最大键
    public Key max(){
        return max(root).key;
    }
    private Node max(Node x){
        if (x.right == null) return x;
        return max(x.right);
    }
    // 找最小键
    public Key min(){
        return min(root).key;
    }
    private Node min(Node x){
        if (x.left == null) return x;
        return min(x.left);
    }
    // 递归。递归函数为返回以x为根节点的树的小于等于key的最大键。
    public Key floor(Key key) {
        if (floor(root, key) == null) return null;
        return floor(root, key).key;
    }
    private Node floor(Node x, Key key){
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        else if (cmp < 0) return floor(x.left, key);
        else {
            if (floor(x.right, key) != null) return floor(x.right, key);
            else return x;
        }
    }
    // 递归。递归函数为返回以x为根节点的树的大于等于key的最小键。
    public Key ceiling(Key key) {
        if (ceiling(root, key) == null) return null;
        return ceiling(root, key).key;
    }
    private Node ceiling(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        else if (cmp > 0) return ceiling(x.right, key);
        else {
            if (ceiling(x.left, key) != null) return ceiling(x.left, key);
            else return x;
        }
    }
    // 找到第k大的键
    public Key select(int k){
        return select(root, k).key;
    }
    private Node select(Node x, int k){
        if (x == null) return null;
        int t = size(x.left) + 1; // t为左子树数目+1
        if (t == k) return x;
        else if (t < k) return select(x.right, k-t);
        else return select(x.left, k);
    }
    // 找到一个键的排名位次
    public int rank(Key key){
        return rank(root, key);
    }
    private int rank(Node x, Key key){
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(x.left, key);
        else if (cmp > 0) return 1 + size(x.left) + rank(x.right, key);
        else return size(x.left);
    }
    // 红黑树删除操作过于复杂，不看了。

    // 打印全树
    public void print(){
        print(root);
    }
    // 打印以x为根节点的树
    public void print(Node x){
        if (x.left != null) print(x.left);
        StdOut.print(x.key);
        if (x.right != null) print(x.right);
    }
    // 查找全树
    public void keys(){
        keys(root, min(), max());
    }
    // 查找lo和hi之间的键。但我不想输入x，因为这个操作很显然是对整个树而言的。因此再来个函数，默认执行root为第一个参数。
    public void keys(Key lo, Key hi){
        keys(root, lo, hi);
    }
    public void keys(Node x, Key lo, Key hi){
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (x.left != null) keys(x.left, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) StdOut.print(x.key);
        if (x.right != null) keys(x.right, lo, hi);
    }
    // main函数，输入样例
    public static void main(String[] args) {
        RedBlackST bst = new RedBlackST();
        String[] keys = {"S", "E", "X", "A", "R", "C", "H", "M"};
        int[] vals = {0, 1, 2, 3, 4, 5, 6, 7};
        for (int i = 0; i < keys.length; i++) bst.put(keys[i], vals[i]);
        bst.print();
        StdOut.print("\n");
        bst.keys("B", "Q");
    }
}
