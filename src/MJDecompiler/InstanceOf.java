package MJDecompiler;

final class InstanceOf extends Opcode {

    InstanceOf(int i, int j, int k) {
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
        String s = g.getConstant(f).name();
        bg bg1 = new bg("?", s);
        bn bn = fo1.b;
        return fo1.a.a(new bs("Z", bn, bg1, "instanceof", 19));
    }

    final void a(Pstream printstream) {
        printstream.println("instanceof " + g.getConstant(f));
    }

    public final void q() {
        f = g.a(f, "class");
    }
    protected int f;
    protected ClassFile g;
}
