package MJDecompiler;

final class fo {

    fo() {
        a = null;
        b = null;
    }

    fo(fo fo1, bn bn1) {
        a = fo1;
        b = bn1;
    }

    final bn a() {
        return b;
    }

    final bn a(int i) {
        if (i == 0) {
            return b;
        } else {
            return a.a(i - 1);
        }
    }

    final fo a(bn bn1) {
        if (bn1 == null) {
            return this;
        } else {
            return new fo(this, bn1);
        }
    }

    final fo b() {
        return a;
    }

    final fo b(int i) {
        if (i == 0) {
            return this;
        } else {
            return a.b(i - 1);
        }
    }

    final int c() {
        if (b == null) {
            return 0;
        } else {
            return a.c() + 1;
        }
    }

    public final String c(int i) {
        String s = "";
        if (b != null) {
            if (i == 0) {
                s = "...";
            } else if ((s = a.c(i - 1)).length() > 0) {
                s = s + ", " + b;
            } else {
                s = b.toString();
            }
        }
        return s;
    }

    public final String toString() {
        return c(3);
    }
    protected fo a;
    protected bn b;
}
