package MJDecompiler;

abstract class IfGoto extends GotoBase {

    IfGoto(int i, int j, String s, int k) {
        super(i, j, k);
        h = s;
    }

    public final void init(ClassFile bj, MethodDescriptor ar) {
        g = ar;
    }

    public final int m() {
        return 2;
    }

    public final int b(int i) {
        if (i == 0) {
            return super.b(i);
        } else {
            return super.i;
        }
    }

    public final void a(int i, int j) {
        if (i == 0) {
            super.a(i, j);
            return;
        } else {
            super.i = j;
            return;
        }
    }

    final String b() {
        return h;
    }
    protected MethodDescriptor g;
    protected String h;
}
