package MJDecompiler;

final class NewArray extends Opcode {

    NewArray(int k, int l, int i1) {
        super(k, l);
        f = i1;
        h = 1;
        i = false;
    }

    NewArray(int k, int l, String s) {
        super(k, l);
        g = s;
        h = 1;
        i = true;
    }

    NewArray(int k, int l, int i1, int j1) {
        super(k, l);
        f = i1;
        h = j1;
        i = true;
    }

    public final void init(ClassFile bj1, MethodDescriptor ar) {
        j = bj1;
    }

    public final int e() {
        return h;
    }

    public final int f() {
        return 1;
    }

    public final fo a(fo fo1) {
        if (g == null) {
            g = j.getConstant(f).next().toString();
            if (!i) {
                if (g.startsWith("[")) {
                    g = "[" + g;
                } else {
                    g = "[L" + g + ";";
                }
                i = true;
            }
        }
        int k = 0;
        String s;
        for (s = g; s.charAt(0) == '['; s = s.substring(1)) {
            k++;
        }

        if (s.startsWith("L")) {
            s = j.c(s.substring(1, s.length() - 1));
        } else if (s.equals("B")) {
            s = "byte";
        } else if (s.equals("C")) {
            s = "char";
        } else if (s.equals("D")) {
            s = "double";
        } else if (s.equals("F")) {
            s = "float";
        } else if (s.equals("I")) {
            s = "int";
        } else if (s.equals("J")) {
            s = "long";
        } else if (s.equals("S")) {
            s = "short";
        } else if (s.equals("Z")) {
            s = "boolean";
        }
        String s1 = "new " + s;
        for (int l = h; l > 0; l--) {
            s1 = s1 + "[" + fo1.a(l - 1) + "]";
        }

        for (int i1 = h; i1 < k; i1++) {
            s1 = s1 + "[]";
        }

        return fo1.b(h).a(new bg(g, s1));
    }

    final void a(Pstream printstream) {
        printstream.print("new ");
        if (g == null) {
            printstream.print(j.getConstant(f));
        } else {
            printstream.print(g);
        }
        for (int k = 0; k < h; k++) {
            printstream.print("[]");
        }

        printstream.println();
    }

    public final void q() {
        f = j.a(f, "class");
    }
    protected int f;
    protected String g;
    protected int h;
    protected boolean i;
    protected ClassFile j;
}
