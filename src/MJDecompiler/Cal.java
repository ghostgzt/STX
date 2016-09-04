package MJDecompiler;

abstract class Cal extends Opcode {

    Cal(int i, int j, String s, String s1) {
        super(i, j);
        f = s;
        g = s1;
    }

    public final int f() {
        return 1;
    }

    final String b() {
        return g;
    }
    protected String f;
    protected String g;
}
