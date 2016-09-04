package MJDecompiler;

final class Ret extends AbstractReturn {

    Ret(int i, int j, int k) {
        super(i, j);
        f = k;
    }

    public final void init(ClassFile bj, MethodDescriptor ar1) {
        g = ar1;
    }

    public final void a(fo fo1, CodeInfo ah1) {
        if (super.e == null) {
            g.a(f, super.offset, "<returnAddress>");
            super.a(fo1, ah1);
        }
    }

    public final fo a(fo fo1) {
        return fo1;
    }

    final void a(Pstream printstream) {
        printstream.println("endfinalize " + g.a(f, super.offset));
    }
    protected int f;
    protected MethodDescriptor g;
}
