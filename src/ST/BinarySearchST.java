package ST;

import edu.princeton.cs.algs4.Queue;

// 类似于字典
// BinarySearch是有序表
public class BinarySearchST<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] vals;
    private int N = 0; // 定义N为数组长度
    public BinarySearchST(int capacity){
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }
    public int size() { return N; }
    // [lo,hi]闭区间内的键的数量
    public int size(Key lo, Key hi){
        if (hi.compareTo(lo) < 0) return 0;
        else if (contains(hi)) return rank(hi)-rank(lo)+1;
        else return rank(hi) - rank(lo);
        // 这里的关键点是rank的定义是小于，因此不用看lo，只看hi这个点，如果存在于列表中则要+1以恢复长度
    }
    public boolean isEmpty(){
        return N == 0;
    }
    public Value get(Key key){
        if (isEmpty()) return null;
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return vals[i];
        else return null;
    }
    public boolean contains(Key key){
        if (get(key) != null) return true;
        else return false;
    }
    // rank方法：小于key的键的数量。它可帮我们快速定位一个键的所在位置（或其最应该进入的位置）
    public int rank(Key key){
        int lo = 0;
        int hi = size() - 1;
        while(lo<=hi){
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) hi = mid - 1; // 若要查找的键小于中值则把搜索范围降低一半
            if (cmp > 0) lo = mid + 1; // 若要查找的键大于中值则把搜索范围增加一半
            else return mid;
        }
        return lo;
    }
    public void put(Key key, Value val){
        int i = rank(key);
        if (i<N && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        } // 如果键已经存在，则直接替换值
        for (int j = N; j > i; j--){
            keys[j] = keys[j-1];
            vals[i] = val;
            N++;
        } // 否则，从高到低，向后移动比它大的键，直至rank(key)位置。
        if (size() == keys.length) resize(keys.length * 2);
        if (size() < keys.length / 4) resize(keys.length / 2);
        // 动态调整数组大小
    }
    public void resize(int cap){
        Key[] new_keys = (Key[]) new Object[cap];
        Value[] new_vals = (Value[]) new Object[cap];
        for (int i = 0; i < size(); i++) {
            new_keys[i] = keys[i];
            new_vals[i] = vals[i];
        }
        keys = new_keys;
        vals = new_vals;
    }
    public Key min(){
        return keys[0];
    }
    public Key max(){
        return keys[N-1];
    }
    public Key select(int k){
        return keys[k];
    }
    public void deleteMin(){
        delete(min());
    }
    public void deleteMax(){
        delete(max());
    }
    // 寻找>=key的最小元素
    public Key ceiling(Key key){
        int i = rank(key);
        return keys[i];
    }
    // 寻找<=key的最大元素
    public Key floor(Key key){
        int i = rank(key);
        if (keys[i].compareTo(key) == 0) return keys[i];
        else return keys[i-1];
    }
    // 删除一个key和它对应的键值
    public void delete(Key key){
        if (contains(key)) {
            int i = rank(key);
            for (int j = i; j < N - 1; j++){
                keys[j] = keys[j+1];
                vals[j] = vals[j+1];
            }
            keys[N-1] = null;
            vals[N-1] = null;
            N--;
        } // 从右向左移动后，把最后一个位置清空。
    }
    // 列出表中所有键的集合
    public Iterable<Key> keys(){
        return keys(min(), max());
    }
    // 列出[lo,hi]之间的所有键
    public Iterable<Key> keys(Key lo, Key hi){
        Queue<Key> q = new Queue<Key>();
        for (int i = rank(lo); i < rank(hi); i++){
            q.enqueue(keys[i]);
        }
        if (contains(hi)) {
            q.enqueue(keys[rank(hi)]);
        }
        return q;
    }
    public static void main(String[] args){

    }
}
