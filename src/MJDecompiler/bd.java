package MJDecompiler;

class bd extends aj {

    bd() {
    }

    bd(Operation ai1, bd abd[]) {
        a(((Operation) (abd.length > 0 ? ((Operation) (abd[0])) : ai1)));
        b(ai1);
        h = ai1;
        i = abd;
    }

    public int e() {
        return i.length;
    }

    public final int f() {
        return -1;
    }

    public int a() {
        return h.a();
    }

    public fo a(fo fo1) {
        for (int j = 0; j < i.length; j++) {
            fo1 = i[j].a(fo1);
        }

        return h.a(fo1);
    }

    public bn b() {
        return a(new fo()).b;
    }

    public final String toString() {
        return b().toString();
    }

    final Operation r() {
        return h;
    }

    bd a(int j) {
        return i[j];
    }

    void a(int j, bd bd1) {
        i[j] = bd1;
    }

    public void c() {
        if (h instanceof InvokeVirtual) {
            InvokeVirtual dc1;
            ConstantPool bh1;
            String s1 = (bh1 = (dc1 = (InvokeVirtual) h).g()).e();
            int k;
            for (int l = k = dc1.b() ? 0 : 1; l < i.length; l++) {
                i[l] = i[l].a(a(s1, l - k));
            }

        }
        for (int j = 0; j < i.length; j++) {
            i[j].c();
        }

    }

    bd a(String s1) {
        Number du1;
        if (h instanceof Number) {
            (du1 = (Number) h).a(s1);
        }
        return this;
    }

    final bd s() {
        String s1;
        String s2;
        if (h instanceof Cal) {
            if ((s2 = ((Cal) h).g).equals("==")) {
                s1 = "!=";
            } else if (s2.equals("!=")) {
                s1 = "==";
            } else if (s2.equals("<")) {
                s1 = ">=";
            } else if (s2.equals(">")) {
                s1 = "<=";
            } else if (s2.equals("<=")) {
                s1 = ">";
            } else if (s2.equals(">=")) {
                s1 = "<";
            } else if (s2.equals("&&")) {
                s1 = "||";
            } else if (s2.equals("||")) {
                s1 = "&&";
            } else if (s2.equals("!")) {
                s1 = null;
            } else {
                s1 = "!";
            }
        } else {
            s1 = "!";
        }
        if (s1 == null) {
            return i[0];
        }
        if (s1.equals("!")) {
            Neg dw1 = new Neg(index(), 0, "Z", "!");
            bd abd[] = {
                this
            };
            return new bd(dw1, abd);
        }
        h = new CaozuoTwo(0, 0, "Z", s1);
        if (s1.equals("&&") || s1.equals("||")) {
            for (int j = 0; j < i.length; j++) {
                i[j] = i[j].s();
            }

        }
        return this;
    }

    bn d() {
        return null;
    }

    boolean a(bn bn1) {
        if (toString().equals(bn1.toString())) {
            return true;
        }
        for (int j = 0; j < i.length; j++) {
            if (i[j].a(bn1)) {
                return true;
            }
        }

        return false;
    }

    public final void a(Pstream printstream, int j) {
        printstream.println(aj.d(j) + "expression " + this);
    }
    Operation h;
    bd i[];
}
