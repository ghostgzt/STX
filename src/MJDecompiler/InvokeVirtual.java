package MJDecompiler;

class InvokeVirtual extends Opcode {

    InvokeVirtual(int i, int j, int k) {
        super(i, j);
        f = k;
    }

    public final void init(ClassFile bj1, MethodDescriptor ar) {
        g = bj1;
        h = ar;
    }

    public final int e() {

        String s = g.getConstant(f).e();
        int i = 0;
        int j = 0;
        j++;
        for (char c = s.charAt(1); c != ')'; c = s.charAt(++j)) {
            if (c != '[') {
                i++;
            }
            if (c != 'L') {
                continue;
            }
            c = s.charAt(++j);
            while (c != ';') {
                c = s.charAt(++j);
            }
        }

        if (!b()) {
            i++;
        }
        return i;
    }

    public final int f() {
        return r().equals("V") ? 0 : 1;
    }

    private String r() {
        String s;
        return (s = (g.getConstant(f)).e()).substring(s.indexOf(')') + 1);
    }

    public final fo a(fo fo1) {
        Object obj = null;
        if (f() > 0) {
            int i;
            bn abn[] = new bn[i = b() ? e() : e() - 1];
            for (int j = 0; j < i; j++) {
                abn[j] = fo1.a(i - 1 - j);
            }

            ConstantPool bh1 = g.getConstant(f);
            bu bu1 = new bu(r(), bh1.name(), abn);
            if (b()) {
                obj = new bp(bh1.next(), bu1, g, h, super.offset);
            } else if (a(fo1.a(i))) {
                obj = new ca(bu1);
            } else {
                obj = new bt(fo1.a(i), bu1, h, super.offset);
            }
        }
        return fo1.b(e()).a((bn) (bn) obj);
    }

    final bn b(fo fo1) {
        if (f() > 0) {
            return null;
        }
        int i;
        bn abn[] = new bn[i = b() ? e() : e() - 1];
        for (int j = 0; j < i; j++) {
            abn[j] = fo1.a(i - 1 - j);
        }

        ConstantPool bh1;
        String s = (bh1 = g.getConstant(f)).name();
        boolean flag = a(fo1.a(i));
        if (d()) {
            if (b(fo1.a(i))) {
                s = "this";
            } else if (flag) {
                s = "super";
            }
            flag = false;
        }
        bu bu1 = new bu(r(), s, abn);
        if (b()) {
            return new bp(bh1.next(), bu1, g, h, super.offset);
        }
        if (flag) {
            return new ca(bu1);
        } else {
            return new bt(fo1.a(i), bu1, h, super.offset);
        }
    }

    boolean d() {
        return false;
    }

    boolean a(bn bn1) {
        return false;
    }

    boolean b(bn bn1) {
        return false;
    }

    boolean b() {
        return false;
    }

    final ConstantPool g() {
        return g.getConstant(f);
    }

    void a(Pstream printstream) {
        printstream.println("invoke " + g.getConstant(f));
    }

    public final void q() {
        f = g.a(f, "member");
    }
    protected int f;
    protected ClassFile g;
    protected MethodDescriptor h;
}
