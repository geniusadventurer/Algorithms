package lists;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

// 用链表实现栈。实现时，出栈入栈都是从第一个位置出入。
// 注意，还应有对空栈和栈溢出的异常提示。
public class LinkedListStack {
    // 创建Node对象
    private class Node{
        String item;
        Node next;
    }
    // 初始化第一个Node
    private Node first = null;
    // 放入元素“item”
    public void push(String item){
        Node newfirst = new Node();
        newfirst.next = first;
        first = newfirst;
        newfirst.item = item;
    }
    // 返回最后一个入栈的元素并从栈中删除它
    public String pop(){
        String item = first.item;
        first = first.next;
        return item;
    }
    // 检查栈是否为空，返回布尔值
    public boolean isEmpty(){
        return first == null;
    }
    public static void main(String[] args){
        LinkedListStack stack = new LinkedListStack();
        while (!StdIn.isEmpty()){
            String s = StdIn.readString();
            if (s.equals("-"))
                StdOut.print(stack.pop());
            else
                stack.push(s);
        }
    }
}
