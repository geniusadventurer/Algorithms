package lists;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.WeakHashMap;

public class LinkedListQueue {
    // 创建Node对象
    private class Node{
        String item;
        Node next;
    }
    // 初始化第一个Node
    private Node first = null;
    private Node last = null;
    private int N = 0;
    // enqueue进入链表末端
    public void enqueue(String item){
        Node newlast = new Node();
        newlast.item = item;
        last.next = newlast;
        last = newlast;
        if (isEmpty()) first = last; // 第一个指针为空即列表为空时，让第一个和最后一个指针相同，使得first不再为空。
        N++;
    }
    // dequeue离开链表前端，和stack的pop一致
    public String dequeue(){
        String item = first.item;
        first = first.next;
        if (isEmpty()) last = null; // 若dequeue后队列为空，要把last指针更新为空，否则last仍然指向之前那个已经被dequeue掉的值。
        N--;
        return item;
    }
    public int size(){
        return N;
    }
    public boolean isEmpty(){
        return first == null;
    }
    public static void main(String[] args){
        LinkedListQueue q = new LinkedListQueue();
        while (!StdIn.isEmpty()){
            String item = StdIn.readString();
            if (!item.equals("-")) q.enqueue(item);
            else if (!q.isEmpty()) StdOut.print(q.dequeue() + " ");
        }
    }
}
