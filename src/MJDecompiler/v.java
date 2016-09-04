package MJDecompiler;

final class v extends bd {

    v(bd bd1, bd bd2, bd bd3) {
        a = bd1.s();
        bd abd[] = {
            bd3, bd2
        };
        super.i = abd;
    }

    public final int a() {
        if (super.i[0].a() > super.i[1].a()) {
            return super.i[0].a();
        } else {
            return super.i[1].a();
        }
    }

    public final fo a(fo fo1) {
        return fo1.a(b());
    }

    public final bn b() {
        bn bn1 = a.b();
        bn bn2 = super.i[0].b();
        bn bn3 = super.i[1].b();
        return new bq(bn2.b, bn1, bn2, bn3, "?", ":", 12);
    }

    final boolean a(bn bn1) {
        if (super.a(bn1)) {
            return true;
        } else {
            return a.a(bn1);
        }
    }

    public final void c() {
        a = a.a("Z");
        a.c();
        super.c();
    }

    final bd a(String s) {
        super.i[0] = super.i[0].a(s);
        super.i[1] = super.i[1].a(s);
        if (s.equals("Z")) {
            String s1 = super.i[0].toString();
            String s2 = super.i[1].toString();
            if (s1.equals("true") && s2.equals("false")) {
                return a;
            }
            if (s1.equals("false") && s2.equals("true")) {
                return a.s();
            }
        }
        return this;
    }
    bd a;
}
