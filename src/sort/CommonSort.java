package sort;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class CommonSort {
    // 选择排序
    public static void SelectionSort(Comparable[] a){
        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i; j < a.length; j++) if (less(a[j], a[min])) min = j;
            exch(a, i, min);
        }
    }
    public static void InsertionSort(Comparable[] a){
        for (int i = 0; i < a.length; i++) {
            for (int j = i; j > 0; j--)
                if (less(a[j], a[j-1])) exch(a, j, j-1);
                else break; // 如果经交换后检测到大于了，就不用再去检测前面的了，因为前面已经是递增序列。
        }
    }
    public static void ShellSort(Comparable[] a){
        int h = 1;
        while (3 * h + 1 < a.length) h = 3 * h + 1;
        while (h > 0) {
            for (int j = a.length - 1; j >= h; j--)
                if (less(a[j], a[j-h])) exch(a, j, j-h);
                else break; // 如果经交换后检测到大于了，就不用再去检测前面的了，因为前面已经是递增序列。
            h = h / 3;
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
    public static void show(Comparable[] a)
    {
        for (int i = 0; i < a.length; i++) StdOut.print(a[i] + " ");
        StdOut.println();
    }
    public static boolean isSorted(Comparable[] a)
    {
        for (int i = 1; i < a.length; i++)
        {if (less(a[i], a[i-1])) return false;}
        return true;
    }
    public static void main(String[] args){
        String[] a = StdIn.readAllStrings();
        SelectionSort(a);
        show(a);
        InsertionSort(a);
        assert isSorted(a); // 确认a已被排序
        show(a);
    }
}
