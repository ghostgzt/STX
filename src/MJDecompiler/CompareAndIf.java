package MJDecompiler;

final class CompareAndIf extends IfGoto {

    CompareAndIf(int i, int j, String s, String s1, int k) {
        super(i, j, s1, k);
    }

    public final int e() {
        return 2;
    }

    public final void a(fo fo1, CodeInfo ah1) {
        if (super.e == null) {
            if (super.h.equals("==") || super.h.equals("!=")) {
                String s = fo1.a(0).b;
                String s1 = fo1.a(1).b;
                if (!s.equals("Z") && s1.equals("Z")) {
                    super.g.a(fo1.a(1), super.offset);
                } else if (s.equals("Z") && !s1.equals("Z")) {
                    super.g.a(fo1.a(0), super.offset);
                }
            } else {
                super.g.a(fo1.a(0), super.offset);
                super.g.a(fo1.a(1), super.offset);
            }
            super.a(fo1, ah1);
        }
    }

    public final fo a(fo fo1) {
        return fo1.b(2);
    }

    final void a(Pstream printstream) {
        printstream.println("compare and if " + super.h + " goto " + super.i);
    }
    protected String f;
}
