package MJDecompiler;

class bn {

    bn(String s, int i) {
        b = s;
        c = i;
    }

    final String a() {
        return b;
    }

    final int size() {
      
        return b.equals("J")||b.equals("D") ? 2 : 1;
    }

    final String a(int i) {
        if (i > c) {
            return "(" + toString() + ")";
        } else {
            return toString();
        }
    }
    protected String b;
    protected int c;
}
