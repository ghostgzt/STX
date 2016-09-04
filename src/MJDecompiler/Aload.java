package MJDecompiler;

final class Aload extends Get {

    Aload(int i, int j, String s) {
        super(i, j);
        f = s;
    }

    public final int e() {
        return 2;
    }

    public final fo a(fo fo1) {
        bn bn1 = fo1.a(1);
        bn bn2 = fo1.a(0);
        String s;
        s = (s = bn1.b).length() > 1 ? s.substring(1) : "?";
        return fo1.b(2).a(new br(s, bn1, bn2));
    }

    final boolean a(Opcode ep1) {
        if (!(ep1 instanceof Astore)) {
            return false;
        } else {
            Astore cy1 = (Astore) ep1;
            return f.equals(cy1.f);
        }
    }

    final void a(Pstream printstream) {
        printstream.println("push []");
    }
    protected String f;
}
