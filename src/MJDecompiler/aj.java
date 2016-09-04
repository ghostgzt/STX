package MJDecompiler;

abstract class aj implements Operation {

    aj() {
        b = 0;
        c = -1;
        d = -1;
    }

    final aj a(Operation ai1) {
        b = ai1.getIndex();
        c = ai1.index();
        return this;
    }

    aj b(Operation ai1) {
        switch (ai1.m()) {
            case 0: // '\0'
                d = -1;
                break;

            case 1: // '\001'
                d = ai1.b(0);
                break;

            default:
                throw new IllegalArgumentException("StructuredFragment has split tail");
        }
        return this;
    }

    public void addIndex() {
        b++;
    }

    public void minusIndex() {
        b--;
    }

    public final int getIndex() {
        return b;
    }

    public final int index() {
        return c;
    }

    public int l() {
        return index();
    }

    public int a() {
        return l();
    }

    public void a(bz bz) {
    }

    public int m() {
        return d < 0 ? 0 : 1;
    }

    public int b(int i1) {
        return d;
    }

    public void a(int i1, int j1) {
        d = j1;
    }

    public final void init(ClassFile bj, MethodDescriptor ar) {
    }

    public final void a(fo fo1, CodeInfo ah) {
    }

    public void c() {
    }

    public final fo n() {
        return new fo();
    }

    public final int size() {
        return 0;
    }

    final aj c(int i1) {
        c = i1;
        return this;
    }

    boolean a(ce ce, int i1) {
        return false;
    }

    static final String d(int i1) {
        StringBuffer stringbuffer = new StringBuffer(4 * i1);
        for (int j1 = 0; j1 < i1; j1++) {
            stringbuffer.append("    ");
        }

        return stringbuffer.toString();
    }

    public void a(Pstream printstream, int i1) {
        printstream.println(d(i1) + getClass().getName());
    }

    public int p() {
        return 1;
    }

    public final void a(Pstream printstream, int i1, boolean flag) {
        if (Decompiler.debug) {
            printstream.println(d(i1 - 2) + l() + "-" + a() + ":" + getIndex() + ">" + "\t\t\t\t\t\t\t\t" + getClass().getName().substring(6));
        }
        if (!flag && p() <= 1) {
            flag = true;
        }
        if (!flag) {
            printstream.println(d(i1 - 1) + "{");
        }
        a(printstream, i1);
        if (!flag) {
            printstream.println(d(i1 - 1) + "}");
        }
    }

    protected final String a(String s, int i1) {
        StringBuffer stringbuffer = new StringBuffer();
        if (s.charAt(0) == '(') {
            int j1 = 1;
            do {
                if (i1 < 0) {
                    break;
                }
                char c1 = s.charAt(j1++);
                if (i1 == 0) {
                    stringbuffer.append(c1);
                }
                switch (c1) {
                    case 41: // ')'
                        i1 = -1;
                        break;

                    case 76: // 'L'
                        char c2;
                        do {
                            c2 = s.charAt(j1++);
                            if (i1 == 0) {
                                stringbuffer.append(c2);
                            }
                        } while (c2 != ';');
                        i1--;
                        break;

                    default:
                        i1--;
                        break;

                    case 91: // '['
                        break;
                }
            } while (true);
        }
        return stringbuffer.toString();
    }

    public final void q() {
    }

    public abstract int e();

    public abstract int f();

    public abstract fo a(fo fo1);
    protected int b;
    protected int c;
    protected int d;
}
