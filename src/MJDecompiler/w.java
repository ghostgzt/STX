package MJDecompiler;

final class w extends bd {

    w(bd bd1, bd abd[], InvokeVirtual dc) {
        super(dc, abd);
        a(bd1);
        b(dc);
        a = bd1;
    }

    public final fo a(fo fo1) {
        return fo1.a(b());
    }

    public final bn b() {
        String s = a.toString();
        bn abn[] = new bn[super.i.length];
        for (int j = 0; j < super.i.length; j++) {
            abn[j] = super.i[j].b();
        }

        return new bu("A", s, abn);
    }

    public final int e() {
        return 0;
    }
    bd a;
}
