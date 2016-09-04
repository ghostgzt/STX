package MJDecompiler;

abstract class Get extends Opcode {

    Get(int i, int j) {
        super(i, j);
    }

    public final int f() {
        return 1;
    }

    abstract boolean a(Opcode ep1);
}
