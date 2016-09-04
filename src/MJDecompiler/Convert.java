package MJDecompiler;

final class Convert extends Opcode {

    Convert(int i, int j, String s, String s1) {
        super(i, j);
        f = s;
        g = s1;
        h = false;
    }

    public final int e() {
        return 1;
    }

    public final int f() {
        return 1;
    }

    public final fo a(fo fo1) {
        if (h) {
            return fo1;
        }
        String s = null;
        if (g.equals("B")) {
            s = "(byte)";
        } else if (g.equals("C")) {
            s = "(char)";
        } else if (g.equals("D")) {
            s = "(double)";
        } else if (g.equals("F")) {
            s = "(float)";
        } else if (g.equals("I")) {
            s = "(int)";
        } else if (g.equals("J")) {
            s = "(long)";
        } else if (g.equals("S")) {
            s = "(short)";
        } else if (g.equals("W")) {
            s = "(int)";
        }
        bn bn1 = fo1.b;
        return fo1.a.a(new cw(g, bn1, s, 23));
    }

    private int a(String s) {
        if (s.equals("B")) {
            return 1;
        }
        if (s.equals("C")) {
            return 2;
        }
        if (s.equals("D")) {
            return 6;
        }
        if (s.equals("F")) {
            return 5;
        }
        if (s.equals("I")) {
            return 3;
        }
        if (s.equals("J")) {
            return 4;
        }
        if (s.equals("S")) {
            return 2;
        }
        return s.equals("W") ? 3 : 3;
    }

    final boolean b() {
        bn bn1;
        if (f.equals("W") && super.e != null && (bn1 = super.e.b) != null) {
            return a(bn1.b) >= a(g);
        }
        return a(f) >= a(g);
    }

    final boolean d() {
        return h;
    }

    final void g() {
        h = true;
    }

    final void a(Pstream printstream) {
        printstream.println("convert " + f + " to " + g);
    }
    protected String f;
    protected String g;
    boolean h;
}
