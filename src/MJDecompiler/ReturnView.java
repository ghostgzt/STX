package MJDecompiler;



final class ReturnView extends ao {

    ReturnView(bd bd1, String s) {
        super(bd1);
        e = s;
    }

    public final void c() {
        super.a = super.a.a(e);
        super.a.c();
    }

    public final void a(Pstream printstream, int i) {
        printstream.println(aj.d(i) + "return " + this + ";");
    }
    String e;
}
