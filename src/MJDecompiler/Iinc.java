package MJDecompiler;

final class Iinc extends Opcode {

    Iinc(int i, int j, int k, int l) {
        super(i, j);
        f = k;
        g = l;
    }

    public final void init(ClassFile bj, MethodDescriptor ar1) {
        h = ar1;
    }

    public final void a(fo fo1, CodeInfo ah1) {
        if (super.e == null) {
            h.a(f, super.offset, "I");
            h.b(f, super.offset + super.size, "I");
            super.a(fo1, ah1);
        }
    }

    public final fo a(fo fo1) {
        return fo1;
    }

    final int b() {
        return g;
    }

    final bn a(bd abd[]) {
        return new cb(h, f, super.offset + super.size);
    }

    final void a(Pstream printstream) {
        if (g >= 0) {
            printstream.println("increment " + h.a(f, super.offset) + " by " + g);
            return;
        } else {
            printstream.println("decrement " + h.a(f, super.offset) + " by " + -g);
            return;
        }
    }
    protected int f;
    protected int g;
    protected MethodDescriptor h;
}
