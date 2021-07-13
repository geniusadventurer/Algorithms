package sort;

import edu.princeton.cs.algs4.StdOut;

public class MergeSort {
    // 辅助数组aux是全局变量，从头到尾的辅助数组只有它一个，函数的参数都用这一个数组
    private static Comparable[] aux1;
    private static Comparable[] aux2;
    public static void merge(Comparable[] a, Comparable[] aux, int lo, int hi, int mid){
        for (int i = lo; i < hi + 1; i++)
            aux[i] = a[i];
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k < hi + 1; k++){
            if (i > mid) { a[k] = aux[j++]; }
            else if (j > hi) { a[k] = aux[i++]; }
            else if (less(aux[i],aux[j]))  { a[k] = aux[i++]; }
            else { a[k] = aux[j++]; }
        }
        assert isSorted(a);
    }
    // 只会被调用一次，用于分配空间，激活sort的递归过程
    public static void Mergesort_TopDown(Comparable[] a){
        aux1 = new Comparable[a.length];
        sort(a, 0, a.length-1);
    }
    public static void sort(Comparable[] a, int lo, int hi){
        if (lo != hi){
            sort(a, lo, (lo + hi) / 2);
            sort(a, (lo + hi) / 2 + 1, hi);
        }
        merge(a, aux1, lo, hi, (lo + hi) / 2);
    }
    public static void Mergesort_DownTop(Comparable[] a){
        aux2 = new Comparable[a.length];
        // sz是归并数目/2，如第一次归并2个数，则sz设为1。这一点的理解很重要。
        for (int sz = 1; sz < a.length; sz = sz * 2){
            for (int i = 0; i < a.length - sz; i += 2 * sz){
                merge(a, aux2, i, Math.min(i + 2 * sz - 1, a.length - 1), i + sz - 1);
            }
        }
    }
    public static boolean less(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }
    public static boolean isSorted(Comparable[] a)
    {
        for (int i = 1; i < a.length; i++)
        {if (less(a[i], a[i-1])) return false;}
        return true;
    }
    public static void show(Comparable[] a){
        for (int i = 0; i < a.length; i++) {StdOut.print(a[i]);}
        StdOut.print("\n");
    }
    public static void main(String[] args){
        String[] exp1 = new String[]{"S", "T", "R", "I", "N", "G"};
        Mergesort_TopDown(exp1);
        assert isSorted(exp1);
        show(exp1);
        String[] exp2 = new String[]{"S", "T", "R", "I", "N", "G"};
        Mergesort_DownTop(exp2);
        assert isSorted(exp2);
        show(exp2);
    }
}