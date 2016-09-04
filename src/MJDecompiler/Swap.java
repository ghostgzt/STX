package MJDecompiler;

final class Swap extends Opcode {

    Swap(int i, int j) {
        super(i, j);
    }

    public final int e() {
        return 2;
    }

    public final int f() {
        return 2;
    }

    public final fo a(fo fo1) {
        bn bn = fo1.a(0);
        bn bn1 = fo1.a(1);
        return fo1.b(2).a(bn).a(bn1);
    }

    final void a(Pstream printstream) {
        printstream.println("swap");
    }
}
