package MJDecompiler;

final class If extends IfGoto {

    If(int i, int j, String s, int k) {
        super(i, j, s, k);
    }

    public final int e() {
        return 1;
    }

    public final void a(fo fo1, CodeInfo ah1) {
        if (super.e == null) {
            if (!super.h.equals("==") && !super.h.equals("!=")) {
                super.g.a(fo1.b, super.offset);
            }
            super.a(fo1, ah1);
        }
    }

    public final fo a(fo fo1) {
        return fo1.a;
    }

    final void a(Pstream printstream) {
        printstream.println("if " + super.h + " goto " + super.i);
    }
}
