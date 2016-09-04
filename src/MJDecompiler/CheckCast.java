package MJDecompiler;

final class CheckCast extends Opcode {

    CheckCast(int i, int j, int k) {
        super(i, j);
        f = k;
    }

    public final void init(ClassFile bj1, MethodDescriptor ar) {
        g = bj1;
    }

    public final int e() {
        return 1;
    }

    public final int f() {
        return 1;
    }

    public final fo a(fo fo1) {
        String s = g.getConstant(f).next().toString();
        String s1 = "(" + g.c(s) + ")";
        String s2 = "L" + s + ";";
        bn bn = fo1.b;
        return fo1.a.a(new cw(s2, bn, s1, 23));
    }

    final void a(Pstream printstream) {
        printstream.println("cast to " + g.getConstant(f));
    }

    public final void q() {
        f = g.a(f, "class");
    }
    protected int f;
    protected ClassFile g;
}
