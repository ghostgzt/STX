package MJDecompiler;

final class LocalName {

    LocalName(String s, int i, MethodDescriptor ar1) {
        a = s;
        b = i;
        c = ar1;
    }

    public final String toString() {
        if (b == 1 && c.a(a) == 1) {
            return a;
        } else {
            return a + b;
        }
    }
    String a;
    int b;
    MethodDescriptor c;
}
