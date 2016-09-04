package MJDecompiler;

final class GoTo extends GotoBase {

    GoTo(int i, int j, int k) {
        super(i, j, k);
    }

    public final int b(int i) {
        return super.i;
    }

    public final void a(int i, int j) {
        super.i = j;
    }

    public final fo a(fo fo) {
        return fo;
    }

    final void a(Pstream printstream) {
        printstream.println("goto " + super.i);
    }
}
