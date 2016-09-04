package MJDecompiler;

abstract class Switch extends Opcode {

    Switch(int j, int k) {
        super(j, k);
    }

    public final int m() {
        return 1 + i.length;
    }

    public final int b(int j) {
        if (j == 0) {
            return h;
        } else {
            return i[j - 1];
        }
    }

    public final void a(int j, int k) {
        if (j == 0) {
            h = k;
            return;
        } else {
            i[j - 1] = k;
            return;
        }
    }

    public final int e() {
        return 1;
    }

    public final fo a(fo fo1) {
        return fo1.a;
    }

    final void a(Pstream printstream) {
        printstream.println("switch");
        for (int j = 0; j < i.length; j++) {
            printstream.println("case " + a(j + 1) + ": goto " + i[j]);
        }

        printstream.println("default: goto " + h);
    }

    abstract int a(int j);
    protected int h;
    protected int i[];
}
