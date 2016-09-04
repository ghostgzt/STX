package MJDecompiler;

final class Return extends AbstractReturn {

    Return(int i, int j, String s) {
        super(i, j);
        f = s;
    }

    public final int e() {
        return f.equals("V") ? 0 : 1;
    }

    public final fo a(fo fo1) {
        if (f.equals("V")) {
            return fo1;
        } else {
            return fo1.a;
        }
    }

    final void a(Pstream printstream) {
        if (f.equals("V")) {
            printstream.println("return void");
            return;
        } else {
            printstream.println("return");
            return;
        }
    }
    protected String f;
}
