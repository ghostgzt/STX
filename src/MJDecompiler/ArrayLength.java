package MJDecompiler;

final class ArrayLength extends Opcode {

    ArrayLength(int i, int j) {
        super(i, j);
    }

    public final int e() {
        return 1;
    }

    public final int f() {
        return 1;
    }

    public final fo a(fo fo1) {
        bn bn = fo1.b;
        return fo1.a.a(new bo("I", bn, ".length", 26));
    }

    final void a(Pstream printstream) {
        printstream.println(".length");
    }
}
