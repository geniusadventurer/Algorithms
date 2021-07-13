package lists;

// 用数组实现栈，涉及栈的长度的动态调整。入栈出栈均放在最后一个位置。
// 注意，出栈时，返回最后一个值后，要将最后一个值设为null，以启用java内置的垃圾回收机制。
public class ArrayStack {
    private String[] s;
    private int N = 0;
    public ArrayStack(int cap){
        s = new String[cap];
    }
    public void push(String item){
        if (s.length == N) resize(2 * N);
        s[N++] = item;
    }
    public void resize(int cap){
        String[] c = new String[cap];
        for (int i = 0; i<N; i++) c[i] = s[i];
        s = c;
    }
    public String pop(){
        String item = s[--N];
        s[N] = null;
        if (N>0 && N == s.length / 4) resize(s.length / 2);
        return item;
    }
    public boolean isEmpty(){
        return N == 0;
    }
}
