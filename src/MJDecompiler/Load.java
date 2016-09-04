package MJDecompiler;

final class Load extends Get {

    Load(int i, int j, String s, int k) {
        super(i, j);
        f = s;
        g = k;
    }

    public final void init(ClassFile bj, MethodDescriptor ar1) {
        h = ar1;
    }

    public final void a(fo fo1, CodeInfo ah1) {
        if (super.e == null) {
            h.a(g, super.offset, f);
            super.a(fo1, ah1);
        }
    }

    public final fo a(fo fo1) {
        return fo1.a(new cb(h, g, super.offset));
    }

    final boolean a(Opcode ep1) {
        if (ep1 instanceof Iinc) {
            Iinc fp1 = (Iinc) ep1;
            return g == fp1.f;
        }
        if (!(ep1 instanceof Store)) {
            return false;
        }
        Store fa1 = (Store) ep1;
        return g == fa1.g;
    }

    final void a(Pstream printstream) {
        printstream.println("push " + h.a(g, super.offset));
    }
    protected String f;
    protected int g;
    protected MethodDescriptor h;
}
