package MJDecompiler;

class al extends am {

    al(bd bd1, am am1, ce ce1, ce ce2) {
        a = bd1;
        e = am1;
        f = ce1;
        g = ce2;
    }

    public int l() {
        if (e != null) {
            return e.l();
        } else {
            return index();
        }
    }

    public final int a() {
        return a.a() + 1;
    }

    public void a(bz bz1) {
        if (e != null) {
            e.a(bz1);
        }
    }

    public void c() {
        a = a.a("Z");
        a.c();
        if (e != null) {
            e.c();
        }
    }

    public int p() {
        if (e == null) {
            return 0;
        } else {
            return e.p();
        }
    }

    final boolean a(ce ce1, int i) {
        if (e != null) {
            return e.a(ce1, i + 1);
        } else {
            return false;
        }
    }

    final void b(Pstream printstream, int i) {
        boolean flag = false;
        if (f != null && a(f, 0)) {
            flag = true;
        }
        if (g != null && a(g, 0)) {
            flag = true;
        }
        if (flag) {
            h++;
            if (f != null) {
                f.c(h);
            }
            if (g != null) {
                g.c(h);
            }
            printstream.println(aj.d(i - 1) + "loop" + h + ":");
        }
    }
    bd a;
    am e;
    ce f;
    ce g;
    static int h;
}
