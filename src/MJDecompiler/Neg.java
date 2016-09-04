package MJDecompiler;

final class Neg extends Cal {

    Neg(int i, int j, String s, String s1) {
        super(i, j, s, s1);
    }

    public final int e() {
        return 1;
    }

    public final fo a(fo fo1) {
        bn bn = fo1.b;
        return fo1.a.a(new cw(super.f, bn, super.g, 24));
    }

    final void a(Pstream printstream) {
        printstream.println("unary " + super.g);
    }
}
