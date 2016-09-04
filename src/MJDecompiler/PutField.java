package MJDecompiler;

final class PutField extends Put {

    PutField(int j, int k, int l, boolean flag) {
        super(j, k);
        f = l;
        g = flag;
    }

    public final void init(ClassFile bj1, MethodDescriptor ar) {
        h = bj1;
        i = ar;
    }

    public final int e() {
        return g ? 1 : 2;
    }

    public final fo a(fo fo1) {
        return fo1.b(g ? 1 : 2);
    }

    final void a(Pstream printstream) {
        printstream.println("pop " + h.getConstant(f));
    }

    final FildInfo b() {
        ConstantPool bh1 = h.getConstant(f);
        FildInfo ax1;
        if ((ax1 = h.a(bh1)) != null && ax1.d()) {
            return ax1;
        } else {
            return null;
        }
    }

    final bn a(bd abd[]) {
        ConstantPool bh1 = h.getConstant(f);
        bg bg1 = new bg(bh1.type(), bh1.name());
        if (g) {
            return new bp(bh1.next(), bg1, h, i, super.offset);
        } else {
            return new bt(abd[0].b(), bg1, i, super.offset);
        }
    }

    public final void q() {
        f = h.a(f, "member");
    }
    protected int f;
    protected boolean g;
    protected ClassFile h;
    protected MethodDescriptor i;
}
