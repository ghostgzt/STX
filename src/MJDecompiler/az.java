
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
package MJDecompiler;

// Referenced classes of package MJDecompiler:
//            ar, bj, by, ch, 
//            bh
final class az {

    az(String s, String s1, ClassFile bj1) {
        a = bj1;
        c = s;
        d = s1;
    }

    az(ConstantPool bh, ConstantPool bh1, ClassFile bj1, MethodDescriptor ar1) {
        this(bh.toString(), bh1.toString(), bj1);
        b = ar1;
    }

    az(ConstantPool bh, ConstantPool bh1, ClassFile bj1) {
        this(bh, bh1, bj1, null);
    }

    final String a(String s)
            throws by {
        String s1 = null;
        StringBuffer stringbuffer = new StringBuffer();
        String s2 = "";
        switch (d.charAt(e++)) {
            case 66: // 'B'
                s1 = "byte";
                break;

            case 67: // 'C'
                s1 = "char";
                break;

            case 68: // 'D'
                s1 = "double";
                break;

            case 70: // 'F'
                s1 = "float";
                break;

            case 73: // 'I'
                s1 = "int";
                break;

            case 74: // 'J'
                s1 = "long";
                break;

            case 83: // 'S'
                s1 = "short";
                break;

            case 86: // 'V'
                s1 = "void";
                break;

            case 87: // 'W'
                s1 = "int?";
                break;

            case 89: // 'Y'
                s1 = "int?";
                break;

            case 90: // 'Z'
                s1 = "boolean";
                break;

            case 76: // 'L'
                int i;
                for (i = e; d.charAt(i) != ';'; i++);
                s1 = d.substring(e, i);
                s1 = a.c(s1);
                e = i + 1;
                break;

            case 91: // '['
                e--;
                do {
                    int j;
                    for (j = ++e; d.charAt(j) >= '0' && d.charAt(j) <= '9'; j++);
                    s2 = s2 + "[" + d.substring(e, j) + "]";
                    e = j;
                } while (d.charAt(e) == '[');
                s1 = a(null);
                break;

            case 40: // '('
                stringbuffer.append("(");
                int k = 0;
                String s3;
                for (; d.charAt(e) != ')'; stringbuffer.append(a(s3))) {
                    if (stringbuffer.length() > 1) {
                        stringbuffer.append(", ");
                    }
                    s3 = null;
                    if (b != null) {
                        s3 = b.b(k++).toString();
                    }
                }

                stringbuffer.append(")");
                e++;
                s1 = a(null);
                break;

            case 60: // '<'
                s1 = "<internalType>";
                break;

            case 41: // ')'
            case 42: // '*'
            case 43: // '+'
            case 44: // ','
            case 45: // '-'
            case 46: // '.'
            case 47: // '/'
            case 48: // '0'
            case 49: // '1'
            case 50: // '2'
            case 51: // '3'
            case 52: // '4'
            case 53: // '5'
            case 54: // '6'
            case 55: // '7'
            case 56: // '8'
            case 57: // '9'
            case 58: // ':'
            case 59: // ';'
            case 61: // '='
            case 62: // '>'
            case 63: // '?'
            case 64: // '@'
            case 65: // 'A'
            case 69: // 'E'
            case 71: // 'G'
            case 72: // 'H'
            case 75: // 'K'
            case 77: // 'M'
            case 78: // 'N'
            case 79: // 'O'
            case 80: // 'P'
            case 81: // 'Q'
            case 82: // 'R'
            case 84: // 'T'
            case 85: // 'U'
            case 88: // 'X'
            default:
                throw new by("Invalid signature " + d);
        }
        if (s != null) {
            if (s.equals("<init>")) {
                s1 = a.thisClass.toString() + stringbuffer;
            } else if (s.equals("<clinit>")) {
                s1 = "";
            } else if (stringbuffer.length() > 0) {
                s1 = s1 + s2 + " " + s + stringbuffer;
            } else {
                s1 = s1 + " " + s + s2;
            }
        } else {
            s1 = s1 + s2;
        }
        return s1;
    }

    public final String toString() {
        e = 0;
        try {
            return a(c);
        } catch (by ex) {
            return "<badSignature>";
        }
    }

    static final String a(String s, String s1) {
        String s2 = s;
        String s3 = s1;
        int i;
        for (i = 0; s2.charAt(0) == '['; i++) {
            s2 = s2.substring(1);
        }

        int j;
        for (j = 0; s3.charAt(0) == '['; j++) {
            s3 = s3.substring(1);
        }

        if (s2.equals("A") && j > 0) {
            return s1;
        }
        if (s3.equals("A") && i > 0) {
            return s;
        }
        if (i != j) {
            return "<incompatibleDims>";
        }
        if (s2.equals(s3)) {
            return s;
        }
        if (s2.startsWith("<")) {
            return s1;
        }
        if (s3.startsWith("<")) {
            return s;
        }
        int k = -1;
        for (int l = 0; k < 0 && l < f.length; l++) {
            if (f[l].equals(s2)) {
                k = l;
            }
        }

        int i1 = -1;
        for (int j1 = 0; i1 < 0 && j1 < f.length; j1++) {
            if (f[j1].equals(s3)) {
                i1 = j1;
            }
        }

        if (k < i1) {
            int k1 = k;
            k = i1;
            i1 = k1;
        }
        if (i1 >= 0) {
            if (i > 0) {
                return "<incompatibleTypes>";
            }
            int l1;
            String s4 = (l1 = g[k][i1]) < 0 ? "<incompatibleTypes>" : f[l1];
            for (int i2 = 0; i2 < i; i2++) {
                s4 = "[" + s4;
            }

            return s4;
        }
        if (k >= 0) {
            return "<incompatibleTypes>";
        }
        if (!s2.equals("A") && !s2.startsWith("L")) {
            return "<incompatibleTypes>";
        }
        if (!s3.equals("A") && !s3.startsWith("L")) {
            return "<incompatibleTypes>";
        }
        if (s2.equals("A")) {
            return s1;
        }
        if (s3.equals("A")) {
            return s;
        } else {
            return "Ljava/lang/Object;";
        }
    }
    ClassFile a;
    MethodDescriptor b;
    String c;
    String d;
    int e;
    private static final String f[] = {
        "W", "Z", "Y", "B", "C", "S", "I", "J", "F", "D"
    };
    private static final int g[][] = {
        {
            0
        }, {
            1, 1
        }, {
            2, 2, 2
        }, {
            3, 3, 3, 3
        }, {
            4, 4, 4, 4, 4
        }, {
            5, 5, 5, 5, 6, 5
        }, {
            6, 6, 6, 6, 6, 6, 6
        }, {
            7, 7, 7, 7, 7, 7, 7, 7
        }, {
            8, 8, 8, 8, 8, 8, 8, 8, 8
        }, {
            9, 9, 9, 9, 9, 9, 9, 9, 9, 9
        }
    };
}
