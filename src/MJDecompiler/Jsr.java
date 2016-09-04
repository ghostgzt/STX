package MJDecompiler;

final class Jsr extends GotoBase {

    Jsr(int i, int j, int k) {
        super(i, j, k);
    }

    public final fo a(fo fo1) {
        return fo1.a(new bg("<returnAddress>", "@"));
    }

    public final int m() {
        return super.i < 0 ? 1 : 2;
    }

    public final int b(int i) {
        if (i == 0) {
            return super.b(i);
        } else {
            return super.i;
        }
    }

    public final void a(int i, int j) {
        if (i == 0) {
            super.a(i, j);
            return;
        } else {
            super.i = j;
            return;
        }
    }

    final void a(Pstream printstream) {
        printstream.println("finalize " + super.i);
    }
}
