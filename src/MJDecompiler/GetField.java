package MJDecompiler;

final class GetField extends Get {

    GetField(int j, int k, int l, boolean flag) {
        super(j, k);
        f = l;
        g = flag;
    }

    public final void init(ClassFile bj1, MethodDescriptor ar) {
        cs = bj1;
        desc = ar;
    }

    public final int e() {
        return g ? 0 : 1;
    }

    public final fo a(fo fo1) {
        ConstantPool bh1 = cs.getConstant(f);
        bg bg1 = new bg(bh1.type(), bh1.name());
        Object obj;
        if (g) {
            obj = new bp(bh1.next(), bg1, cs, desc, super.offset);
        } else {
            obj = new bt(fo1.b, bg1, desc, super.offset);
        }
        return fo1.b(g ? 0 : 1).a((bn) (bn) obj);
    }

    final boolean a(Opcode ep1) {
        if (!(ep1 instanceof PutField)) {
            return false;
        }
        PutField ck1 = (PutField) ep1;
        return f == ck1.f && g == ck1.g;
    }

    final void a(Pstream printstream) {
        printstream.println("push " + cs.getConstant(f));
    }

    public final void q() {
        f = cs.a(f, "member");
    }
    protected int f;
    protected boolean g;
    protected ClassFile cs;
    protected MethodDescriptor desc;
}
