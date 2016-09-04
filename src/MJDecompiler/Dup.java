package MJDecompiler;

class Dup extends Opcode {

    Dup(int i, int j, int k) {
        super(i, j);
        f = k;
        g = 1;
    }

    public final int e() {
        return f + g;
    }

    public final int f() {
        return f + 2 * g;
    }

    final int b() {
        return f;
    }

    public final fo a(fo fo1) {
        fo fo2 = fo1;
        bn abn[] = new bn[g];
        int i;
        int j;
        for (i = j = 0; j < g; i++) {
            abn[i] = fo2.b;
            j += abn[i].size();
            fo2 = fo2.a;
        }

        bn abn1[] = new bn[f];
        int k;
        int l;
        for (k = l = 0; l < f; k++) {
            abn1[k] = fo2.b;
            l += abn1[k].size();
            fo2 = fo2.a;
        }

        for (int i1 = i; i1 > 0;) {
            fo2 = fo2.a(abn[--i1]);
        }

        for (int j1 = k; j1 > 0;) {
            fo2 = fo2.a(abn1[--j1]);
        }

        for (int k1 = i; k1 > 0;) {
            fo2 = fo2.a(abn[--k1]);
        }

        return fo2;
    }

    final void a(Pstream printstream) {
        printstream.println("dup " + g + " over " + f);
    }
    protected int f;
    protected int g;
}
