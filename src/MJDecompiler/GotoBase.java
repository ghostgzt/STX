package MJDecompiler;

abstract class GotoBase extends Opcode {

    GotoBase(int j, int k, int l) {
        super(j, k);
        i = j + l;
    }
    protected int i;
}
