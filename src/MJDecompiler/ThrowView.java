package MJDecompiler;

final class ThrowView extends ao {

    ThrowView(bd bd) {
        super(bd);
    }

    public final void a(Pstream printstream, int i) {
        printstream.println(aj.d(i) + "throw " + this + ";");
    }
}
