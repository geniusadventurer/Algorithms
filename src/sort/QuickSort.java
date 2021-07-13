package sort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class QuickSort {
    public static void Quicksort(Comparable[] a){
        StdRandom.shuffle(a);
        sort(a, 0, a.length-1);
    }
    public static int partition(Comparable[] a, int lo, int hi){
        int k = lo;
        lo = lo + 1;
        while (lo <= hi) {
            if (less(a[lo], a[k])) lo++;
            else if (less(a[k], a[hi])) hi--;
            else exch(a, lo, hi);
        }
        if (k < lo-1) exch(a, k, lo-1); // 避免索引溢出，设置一个判断语句
        return lo-1; // 返回切分位置索引，lo-1是因为循环里加过1
    }
    public static Comparable findkth(Comparable[] a, int k){
        StdRandom.shuffle(a);
        k = k - 1; // 转换为索引，因此输入k的最小值为1
        int lo = 0;
        int hi = a.length - 1;
        while(lo <= hi){
            int v = partition(a, lo, hi); // 最终目标是使得切分点v的数值等于k，k比v大，把左界放在切分点右边再找，k比v小，把右界放在切分点左边再找，递归以不断缩小范围
            if (k>v) lo = v + 1;
            else if (k<v) hi = v - 1;
            else break;
        }
        return a[k];
    }
    public static void sort(Comparable[] a, int lo, int hi){
        int p = partition(a, lo, hi);
        if (lo < hi) {
            sort(a, lo, p - 1); // p位置的元素已经到了正确的位置，不参与排序了。分别对除p以外的左半部分和右半部分排序。
            sort(a, p + 1, hi);
        }
    }
    public static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }
    public static void exch(Comparable[] a, int i, int j){
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
    public static boolean isSorted(Comparable[] a)
    {
        for (int i = 1; i < a.length; i++)
        {if (less(a[i], a[i-1])) return false;}
        return true;
    }
    public static void show(Comparable[] a){
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i]);}
        StdOut.print("\n");
    }
    public static void main(String[] args){
        String[] exp1 = new String[]{"S", "T", "R", "I", "N", "G"};
        StdOut.println(findkth(exp1, 4));
        Quicksort(exp1);
        assert isSorted(exp1);
        show(exp1);
    }
}
