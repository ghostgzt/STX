package MJDecompiler;

class CaozuoTwo extends Cal {

    CaozuoTwo(int i1, int j1, String s, String s1) {
        super(i1, j1, s, s1);
    }

    public final void init(ClassFile bj, MethodDescriptor ar1) {
        h = ar1;
    }

    public final int e() {
        return 2;
    }

    private int d() {
        if (super.g.equals("||")) {
            return 1;
        }
        if (super.g.equals("&&")) {
            return 2;
        }
        if (super.g.equals("|")) {
            return 3;
        }
        if (super.g.equals("^")) {
            return 4;
        }
        if (super.g.equals("&")) {
            return 5;
        }
        if (super.g.equals("==")) {
            return 6;
        }
        if (super.g.equals("!=")) {
            return 7;
        }
        if (super.g.equals("<>")) {
            return 8;
        }
        if (super.g.equals("<")) {
            return 9;
        }
        if (super.g.equals("<=")) {
            return 10;
        }
        if (super.g.equals(">")) {
            return 11;
        }
        if (super.g.equals(">=")) {
            return 12;
        }
        if (super.g.equals("<<")) {
            return 13;
        }
        if (super.g.equals(">>")) {
            return 14;
        }
        if (super.g.equals(">>>")) {
            return 15;
        }
        if (super.g.equals("+")) {
            return 16;
        }
        if (super.g.equals("-")) {
            return 17;
        }
        if (super.g.equals("*")) {
            return 18;
        }
        if (super.g.equals("/")) {
            return 19;
        }
        return super.g.equals("%") ? 20 : 0;
    }

    public final void a(fo fo1, CodeInfo ah1) {
        if (super.e == null) {
            switch (l[d()]) {
                default:
                    break;

                case 0: // '\0'
                    h.a(fo1.a(0), super.offset);
                    h.a(fo1.a(1), super.offset);
                    break;

                case 1: // '\001'
                    String s = fo1.a(0).b;
                    String s1 = fo1.a(1).b;
                    if (!s.equals("Z") && s1.equals("Z")) {
                        h.a(fo1.a(1), super.offset);
                        break;
                    }
                    if (s.equals("Z") && !s1.equals("Z")) {
                        h.a(fo1.a(0), super.offset);
                    }
                    break;
            }
            super.a(fo1, ah1);
        }
    }

    public final fo a(fo fo1) {
        bn bn1 = fo1.a(1);
        bn bn2 = fo1.a(0);
        int i1 = d();
        byte byte0 = i[i1];
        byte byte1 = j[i1];
        String s;
        if ((s = k[i1]).length() == 0) {
            s = super.f;
        }
        if (byte1 == 0) {
            return fo1.b(2).a(new bs(s, bn1, bn2, super.g, byte0));
        } else {
            return fo1.b(2).a(new cv(s, bn1, bn2, super.g, byte0));
        }
    }

    final void a(Pstream printstream) {
        printstream.println("" + super.g);
    }
    protected MethodDescriptor h;
    private static final byte i[] = {
        0, 13, 14, 15, 16, 17, 18, 18, 19, 19,
        19, 19, 19, 20, 20, 20, 21, 21, 22, 22,
        22
    };
    private static final byte j[] = {
        1, 0, 0, 1, 1, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
        1
    };
    private static final String k[] = {
        "", "Z", "Z", "", "", "", "Z", "Z", "Z", "Z",
        "Z", "Z", "Z", "", "", "", "", "", "", "",
        ""
    };
    private static final byte l[] = {
        0, 2, 2, 1, 1, 1, 2, 2, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0
    };
}
