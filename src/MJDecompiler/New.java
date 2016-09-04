package MJDecompiler;

final class New extends Opcode {

    New(int i, int j, int k) {
        super(i, j);
        f = k;
    }

    public final void init(ClassFile bj1, MethodDescriptor ar) {
        g = bj1;
    }

    public final int f() {
        return 1;
    }

    public final fo a(fo fo1) {
        String s = g.getConstant(f).next().toString();
        String s1 = "new " + g.c(s);
        String s2 = "L" + s + ";";
        return fo1.a(new bg(s2, s1));
    }

    final void a(Pstream printstream) {
        printstream.println("new " + g.getConstant(f));
    }

    public final void q() {
        f = g.a(f, "class");
    }
    protected int f;
    protected ClassFile g;
}
