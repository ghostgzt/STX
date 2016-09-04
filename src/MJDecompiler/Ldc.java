package MJDecompiler;

final class Ldc extends Opcode {

    Ldc(int i, int j, int k) {
        super(i, j);
        f = k;
    }

    public final void init(ClassFile bj1, MethodDescriptor ar) {
        cs = bj1;
    }

    public final int f() {
        return 1;
    }

    public final fo a(fo fo1) {
        ConstantPool bh1 = cs.getConstant(f);
        return fo1.a(new bg(bh1.type(), bh1.toString()));
    }

    final void a(Pstream printstream) {
        printstream.println("push " + cs.getConstant(f));
    }

    public final void q() {
        f = cs.a(f, "literal");
    }
    protected int f;
    protected ClassFile cs;
}
