package gui;

public class dstack {

    double[] s = new double[30];
    int top;

    dstack() {
        top = 0;
    }

    void push(double d) {
        int l = s.length;
        if (top + 2 > l) {
            double[] t = new double[30 + l];
            System.arraycopy(s, 0, t, 0, l);
            s = t;
        }
        s[top++] = d;
    }

    double pop() {
        if (top > 0) {
            return s[--top];
        } else {
            return 0;
        }
    }

    double pek() {
        if (top > 0) {
            return s[top - 1];
        } else {
            return 0;
        }
    }

    boolean empt() {
        return top <= 0;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer("");
        while (!empt()) {
            sb.append(pop());
        }
        return sb.toString();

    }
}
