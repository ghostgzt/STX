package MJDecompiler;

final class Unused extends Opcode {

    Unused(int i, int j) {
        super(i, j);
    }

    public final int e() {
        return 1;
    }

    public final int f() {
        return 1;
    }

    public final fo a(fo fo1) {
        String s = "new(" + fo1.b.toString() + ")";
        return fo1.a.a(new bg("A", s));
    }

    final void a(Pstream printstream) {
        printstream.println("new \"...\"");
    }
}
