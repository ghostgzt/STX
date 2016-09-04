package MJDecompiler;

final class IfNull extends IfGoto {

    IfNull(int i, int j, String s, int k) {
        super(i, j, s, k);
    }

    public final int e() {
        return 1;
    }

    public final fo a(fo fo1) {
        return fo1.a;
    }

    final void a(Pstream printstream) {
        printstream.println("if " + super.h + " null goto " + super.i);
    }
}
