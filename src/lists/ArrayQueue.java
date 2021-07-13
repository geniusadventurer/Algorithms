package lists;

// 循环表
public class ArrayQueue<Item> {
    private Item[] s; // 数组整体，s.length表示整体长度
    private int head = 0;
    private int tail = 0;
    public ArrayQueue(int cap){
        s = (Item[]) new Object[cap];
    }
    public void enqueue(Item item){
        if (tail == s.length && head != 0) tail = 0;
        if (size() == s.length) resize(s.length * 2);
        s[tail++] = item;
    }
    public Item dequeue(){
        Item item = s[head];
        if (size() < s.length / 4) resize(s.length / 2);
        s[head++] = null;
        if (head == s.length) head = 0;
        return item;
    }
    public boolean isEmpty() {
        return (head == tail && s[head] == null);
    }
    public int size(){
        if (isEmpty()) return 0;
        else if (head >= tail) return head - tail + 1;
        else return s.length + tail - head + 1;
    }
    public void resize(int cap){
        Item[] c = (Item[]) new Object[cap];
        int p = head;
        for (int i = 0; i < size(); i++) {
            if (p == s.length) p = 0;
            c[i] = s[p++];
        }
        s = c;
        head = 0;
        tail = size() - 1;
    }
}
