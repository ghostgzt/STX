package MJDecompiler;

abstract class Put extends Opcode {

    Put(int i, int j) {
        super(i, j);
    }

    FildInfo b() {
        return null;
    }

    abstract bn a(bd abd[]);
}
