package BinaryHeapSort;

import edu.princeton.cs.algs4.StdOut;

// 用二叉堆实现
// 二叉堆索引的性质意味着可以用单个列表直接操作，变动仅涉及索引值
// 用一个列表形式上模拟一棵树，非常高明！
// 二叉堆的性质：父节点一定大于等于其子节点；子节点的索引值/2=其父节点的索引值
// 第一个位置留空
public class BinaryHeapSort<Key extends Comparable<Key>>{
    private int N = 0;
    private Key[] pq;
    public BinaryHeapSort(int maxN){
        pq = (Key[]) new Comparable[maxN+1];
    }
    public void insert(Key v){
        pq[++N] = v; // 这里随着一个元素的插入，就实现了N的记录
        swim(N);
    }
    // 删除最大值：最大值一定在pq[1]位置，将它提取到另一个变量后，将其与最后一个值交换位置，然后把新的pq[1]放在新的位置
    public Key delMax(){
        Key max = pq[1];
        exch(1, N--);
        sink(1);
        pq[N+1] = null; // 清除原来的N位置（因为N减过一次）数据
        return max;
    }
    public boolean isEmpty(){
        return N == 0;
    }
    public Key max(){
        return pq[1];
    }
    public int size(){
        return N;
    }
    // 节点上浮，主要用于添加节点
    private void swim(int k){
        while (k > 1 && less(k/2, k)){
            exch(k/2, k);
            k = k/2;
        }
    }
    // 节点下沉，主要用于删除最大节点后将小节点放下去。上浮下浮的灵魂都在这个循环，保证能一次性操作完
    // 下沉和上浮的目的是修改顺序使堆有序，而非强行挪动位置。
    private void sink(int k){
        // 循环条件：含有子节点
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(j, j+1)) j++; // 找到较小的节点，不加等于号意味着若该式成立则存在右子节点，若该式不成立则只存在一个子节点
            if (less(k, j)) exch(k, j);
            else break;
            k = j;
        }
    }
    // 利用sink实现堆排序：每次找最大的元素，交换到最后一个，再使被交换上去的元素归位，重复
    private void sort(){
        while(N > 1){
            exch(1, N--);
            sink(1);
        }
    }
    public boolean less(int v, int w){
        return pq[v].compareTo(pq[w]) < 0;
    }
    public void exch(int i, int j){
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }
    public void show(){
        for (int i = 1; i < pq.length; i++) StdOut.print(pq[i] + " ");
        StdOut.println();
    }
    public static void main(String[] args){
        BinaryHeapSort eg = new BinaryHeapSort(11);
        String[] st = {"S","O","R","T","E","X","A","M","P","L","E"};
        for (int i = 0; i < st.length; i++) eg.insert(st[i]);
        eg.show();
        eg.sort();
        eg.show();
    }
}
