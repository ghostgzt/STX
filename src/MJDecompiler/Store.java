package MJDecompiler;

final class Store extends Put {

    Store(int i, int j, String s, int k) {
        super(i, j);
        g = k;
    }

    public final void init(ClassFile bj, MethodDescriptor ar1) {
        h = ar1;
    }

    public final int e() {
        return 1;
    }

    public final void a(fo fo1, CodeInfo ah1) {
        if (super.e == null) {
            h.b(g, super.offset + super.size, fo1.b.b);
            super.a(fo1, ah1);
        }
    }

    public final fo a(fo fo1) {
        return fo1.a;
    }

    final void a(Pstream printstream) {
        printstream.println("pop " + h.a(g, super.offset + super.size));
    }

    final bn a(bd abd[]) {
        return new cb(h, g, super.offset + super.size);
    }
    protected String f;
    protected int g;
    protected MethodDescriptor h;
}
