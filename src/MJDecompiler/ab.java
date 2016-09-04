package MJDecompiler;


import java.util.Vector;

final class ab extends al {

    ab(ao ao1, bd bd1, ao ao2, am am1, ce ce, ce ce1) {
        super(bd1, am1, ce, ce1);
        i = ao1;
        j = ao2;
    }

    public final int l() {
        if (i != null) {
            return i.l();
        }
        if (super.e != null) {
            return super.e.l();
        } else {
            return index();
        }
    }

    public final void a(bz bz1) {
        k = new bz(bz1, l(), a());
        super.a(k);
    }

    public final void c() {
        if (i != null) {
            i.c();
        }
        if (j != null) {
            j.c();
        }
        super.c();
    }

    public final void a(Pstream printstream, int i1) {
        b();
        b(printstream, i1);
        if (k != null) {
            k.a(printstream, i1, "for");
        }
        printstream.print(aj.d(i1) + "for (");
        if (i != null) {
            printstream.print(i);
        }
        printstream.print("; " + super.a + "; ");
        if (j != null) {
            printstream.print(j);
        }
        printstream.print(")");
        if (super.e != null) {
            printstream.println();
            super.e.a(printstream, i1 + 1, false);
            return;
        } else {
            printstream.println(" /* null body */ ;");
            return;
        }
    }

    final void b() {
        if (i != null) {
            Vector vector = k.c;
            for (int i1 = 0; i1 < vector.size();) {
                cg cg1;
                int j1 = (cg1 = (cg) vector.elementAt(i1)).a();
                bd bd1;
                if (i.a() == j1 - 1 && ((bd1 = i.a) instanceof g)) {
                    LocalVariableTable ch = cg1.a(j1);
                    bd bd2 = bd1.a(0);
                    f f1;
                    (f1 = new f(ch, bd2)).a(i);
                    f1.b(i);
                    i = f1;
                    cg1 = null;
                }
                if (cg1 == null) {
                    vector.removeElementAt(i1);
                } else {
                    i1++;
                }
            }

        }
        if (k.c.size() > 0) {
            System.err.println("Declarators could not be incorporated in for statement");
        }
    }
    ao i;
    ao j;
    bz k;
}
