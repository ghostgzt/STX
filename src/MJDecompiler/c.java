package MJDecompiler;

final class c extends al {

    c(bd bd, am am1, ce ce, ce ce1) {
        super(bd, am1, ce, ce1);
    }

    public final void a(Pstream printstream, int i) {
        b(printstream, i);
        printstream.print(aj.d(i) + "while (" + super.a + ")");
        if (super.e != null) {
            printstream.println();
            super.e.a(printstream, i + 1, false);
            return;
        } else {
            printstream.println(" /* null body */ ;");
            return;
        }
    }
}
