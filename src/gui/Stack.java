package gui;

public class Stack {

    int[] s = new int[100];
    int top = 0;

    void sort() {
        boolean over = true;
        for (int i = 0; i < top && over; i++) {
            over = false;
            for (int j = 1; j < top - i; j++) {
                if (s[j - 1] > s[j]) {
                    s[s.length - 1] = s[j];
                    s[j] = s[j - 1];
                    s[j - 1] = s[s.length - 1];
                    over = true;
                }
            }
        }
    }

  public void push(int d) {
        int l = s.length;
        if (top + 2 > l) {
            int[] t = new int[50 + l];
            System.arraycopy(s, 0, t, 0, l);
            s = t;
        }
        s[top++] = d;
    }

  public  int pop() {
        if (top > 0) {
            return s[--top];
        } else {
            return 0;
        }
    }

    int pek() {
        return s[top - 1];
    }

    boolean haveElement() {
        return top > 0;
    }

    public void clear() {
        top = 0;
    }
}
