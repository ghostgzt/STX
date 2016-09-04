package MJDecompiler;

final class bu extends bn {

    bu(String s, String s1, bn abn[]) {
        super(s, 90);
        a = s1;
        d = abn;
    }

    public final String toString() {
        String s = a + "(";
        for (int i = 0; i < d.length; i++) {
            if (i > 0) {
                s = s + ", ";
            }
            s = s + d[i];
        }

        return s = s + ")";
    }
    String a;
    bn d[];
}
