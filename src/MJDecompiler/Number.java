package MJDecompiler;

final class Number extends Opcode {

    Number(int i, int j, String s, String s1) {
        super(i, j);
        f = s;
        g = s1;
    }

    public final int f() {
        return 1;
    }

    public final fo a(fo fo1) {
        return fo1.a(new bg(f, g));
    }

    public final void a(String s) {
        if (s.equals("Z")) {
            if (g.equals("0")) {
                g = "false";
                return;
            }
            if (g.equals("1")) {
                g = "true";
                return;
            }
        } else if (s.equals("C")) {
            try {
                StringBuffer stringbuffer;
                (stringbuffer = new StringBuffer()).append('\'');
                int i;
                switch (i = Integer.parseInt(g)) {
                    case 0: // '\0'
                        stringbuffer.append("\\0");
                        break;

                    case 8: // '\b'
                        stringbuffer.append("\\b");
                        break;

                    case 9: // '\t'
                        stringbuffer.append("\\t");
                        break;

                    case 10: // '\n'
                        stringbuffer.append("\\n");
                        break;

                    case 12: // '\f'
                        stringbuffer.append("\\f");
                        break;

                    case 13: // '\r'
                        stringbuffer.append("\\r");
                        break;

                    case 39: // '\''
                        stringbuffer.append("\\'");
                        break;

                    case 92: // '\\'
                        stringbuffer.append("\\\\");
                        break;

                    default:
                        if (i < 32) {
                            String s1 = Integer.toString(i, 8);
                            while (s1.length() < 3) {
                                s1 = "0" + s1;
                            }
                            stringbuffer.append("\\" + s1);
                            break;
                        }
                        if (i < 127) {
                            stringbuffer.append((char) i);
                            break;
                        }
                        String s2 = Integer.toString(i, 16);
                        while (s2.length() < 4) {
                            s2 = "0" + s2;
                        }
                        stringbuffer.append("\\u" + s2);
                        break;
                }
                stringbuffer.append('\'');
                g = stringbuffer.toString();
                return;
            } catch (NumberFormatException _ex) {
                return;
            }
        }
    }

    final void a(Pstream printstream) {
        printstream.println("push " + g);
    }
    protected String f;
    protected String g;
}
