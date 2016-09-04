package MJDecompiler;

final class Nop extends Opcode {

    Nop(int i, int j) {
        super(i, j);
    }

    public final fo a(fo fo) {
        return fo;
    }

    final void a(Pstream printstream) {
        printstream.println("nop");
    }
}
