package MJDecompiler;

final class MonitorExit extends Opcode {

    MonitorExit(int i, int j) {
        super(i, j);
    }

    public final int e() {
        return 1;
    }

    public final fo a(fo fo1) {
        return fo1.a;
    }

    final void a(Pstream printstream) {
        printstream.println("unlock");
    }
}
