package MJDecompiler;

final class Astore extends Put {

    Astore(int i, int j, String s) {
        super(i, j);
        f = s;
    }

    public final int e() {
        return 3;
    }

    public final fo a(fo fo1) {
        return fo1.b(3);
    }

    final void a(Pstream printstream) {
        printstream.println("pop []");
    }

    final bn a(bd abd[]) {
        bn bn = abd[0].b();
        bn bn1 = abd[1].b();
        return new br(f, bn, bn1);
    }
    protected String f;
}
