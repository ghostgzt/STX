package MJDecompiler;

import java.io.IOException;

import java.util.*;

final class MethodDescriptor {

    ClassFile cs;
    String descriptor;
    int c;
    int d;
    Vector e;
    boolean f;
    bz g;
    Hashtable h;

    MethodDescriptor(ClassFile cs, boolean isstatic, String des) {
        this.cs = cs;
        descriptor = des;
        c = isstatic ? 0 : 1;
        d = sysParama(des);
        e = new Vector();
        f = false;
        h = new Hashtable();
    }

    final void readLocal(ClazzInputStream in) throws IOException {
        int i = in.readUShort();
        for (int j = 0; j < i; j++) {
            LocalVariableTable ch1 = LocalVariableTable.a(cs, in);
            (a(ch1.index)).a(ch1);
        }

        f = true;
    }

    final cg a(int i) {
        if (i >= e.size()) {
            e.setSize(i + 1);
        }
        cg cg1;
        if ((cg1 = (cg) e.elementAt(i)) == null) {
            cg1 = new cg(i);
            e.setElementAt(cg1, i);
        }
        return cg1;
    }

    final LocalVariableTable a(int i, int j) {
        cg cg1;
        LocalVariableTable ch1;
        if ((ch1 = (cg1 = a(i)).a(j)) == null) {
            ch1 = new LocalVariableTable(i, "local" + ((i - c - d) + 1), "<unknownType>", cs);
            cg1.a(ch1);
        }
        return ch1;
    }

    final LocalVariableTable b(int i) {
        int j = c;
        for (int k = 0; k < i; k++) {
            j += a(j, 0).size();
        }

        return a(j, 0);
    }

    final Enumeration c(int i) {
        Vector vector = new Vector();
        for (int j = 0; j < e.size(); j++) {
            cg cg1;
            LocalVariableTable ch1;
            if ((cg1 = (cg) e.elementAt(j)) != null && (ch1 = cg1.a(i)) != null) {
                vector.addElement(ch1);
            }
        }

        return vector.elements();
    }

    final boolean a() {
        return !f;
    }

    private void d() {
        if (c > 0) {
            String s = "L" + cs.thisClass.next().toString() + ";";
            LocalVariableTable ch1 = new LocalVariableTable(0, "this", s, cs);
            a(0).a(ch1);
        }
    }

    private void e() {
        int i = c;
        int j;
        for (int k = 1; descriptor.charAt(k) != ')'; k = j + 1) {
            j = k;
            while (descriptor.charAt(j) == '[') {
                j++;
            }
            if (descriptor.charAt(j) == 'L') {
                while (descriptor.charAt(j) != ';') {
                    j++;
                }
            }
            String s = descriptor.substring(k, j + 1);
            LocalName bw1 = c(s);
            LocalVariableTable ch1 = new LocalVariableTable(i, bw1, s, cs);
            a(i).a(ch1);
            i += ch1.size();
        }

    }

    final void b() {
        if (!f) {
            d();
            e();
        }
    }

    final void a(int i, int j, String s, char c1) {
        if (i >= c + d) {

            (a(i)).a(j, s, c1);
            if (!f) {
                LocalVariableTable ch1 = a(i, j);
                if (c1 != 'S') {
                    s = az.a(ch1.type, s);
                }
                s.equals("<unknownType>");
                ch1.type = s;
            }
        }
    }

    final void a(int i, int j, String s) {
        a(i, j, s, 'L');
    }

    final void b(int i, int j, String s) {
        a(i, j, s, 'S');
    }

    final void a(bn bn1, int i) {
        if (!f && (bn1 instanceof cb)) {
            cb cb1 = (cb) bn1;
            a(cb1.d, i, "Y", 'X');
        }
    }

    final void b(bn bn1, int i) {
        if (!f && (bn1 instanceof cb)) {
            cb cb1 = (cb) bn1;
            a(cb1.d, i, "<temporary>", 'T');
        }
    }

    final void a(bz bz1) {
        g = bz1;
        for (int i = c + d; i < e.size(); i++) {
            cg cg1;
            if ((cg1 = (cg) e.elementAt(i)) == null) {
                continue;
            }
            if (f) {
                cg1.a(bz1, cs, this);
            } else {
                cg1.b(bz1, cs, this);
            }
        }

    }

    final int a(String s) {
        Integer integer;
        if ((integer = (Integer) h.get(s)) == null) {
            return 0;
        } else {
            return integer.intValue();
        }
    }

    final LocalName b(String s) {
        Integer integer;
        int i = (integer = (Integer) h.get(s)) == null ? 1 : integer.intValue() + 1;
        h.put(s, new Integer(i));
        if (s.equals("n")) {
            switch ((i - 1) % 3) {
                case 0: // '\0'
                    s = "i";
                    break;

                case 1: // '\001'
                    s = "j";
                    break;

                case 2: // '\002'
                    s = "k";
                    break;
            }
            return b(s);
        } else {
            return new LocalName(s, i, this);
        }
    }

    final LocalName c(String s) {
        StringBuffer stringbuffer = new StringBuffer();
        for (; s.startsWith("["); s = s.substring(1)) {
            stringbuffer.append("a");
        }

        if (s.equals("Z")) {
            stringbuffer.append("flag");
        } else if (s.equals("B")) {
            stringbuffer.append("b");
        } else if (s.equals("C")) {
            stringbuffer.append("ch");
        } else if (s.equals("S")) {
            stringbuffer.append("s");
        } else if (s.equals("I")) {
            stringbuffer.append("n");
        } else if (s.equals("J")) {
            stringbuffer.append("n");
        } else if (s.equals("F")) {
            stringbuffer.append("f");
        } else if (s.equals("D")) {
            stringbuffer.append("d");
        } else if (s.equals("W")) {
            stringbuffer.append("n");
        } else if (s.equals("Y")) {
            stringbuffer.append("n");
        } else if (s.startsWith("L")) {
            if ((s = s.replace('/', '.').substring(1, s.length() - 1)).endsWith("Exception") || s.endsWith("Error")) {
                stringbuffer.append("e");
            } else if (s.equals("java.awt.Graphics")) {
                stringbuffer.append("g");
            } else {
                char c1;
                c1 = Character.isUpperCase(c1 = (s = s.substring(s.lastIndexOf('.') + 1, s.length())).charAt(0)) ? Character.toLowerCase(c1) : Character.toUpperCase(c1);
                stringbuffer.append(c1);
                stringbuffer.append(s.substring(1));
            }
        } else {
            stringbuffer.append("local");
        }
        return b(stringbuffer.toString());
    }

    final void a(Pstream printstream, int i) {
        if (Decompiler.debug) {
            for (int j = 0; j < e.size(); j++) {
                cg cg1;
                (cg1 = (cg) e.elementAt(j)).a(printstream, i);
            }

        }
        if (g != null) {
            g.a(printstream, i, "method");
        }
    }

    private int sysParama(String s) {
        int i = 0;
        for (int j = 0; j < s.length() && s.charAt(j) != ')'; j++) {
            switch (s.charAt(j)) {
                case 40: // '('
                case 91: // '['
                    break;
                case 76: // 'L'
                    i++;
                    int k = ++j;
                    while (s.charAt(j) != ';') {
                        j++;
                    }
                    cs.b(s.substring(k, j));
                    break;

                case 68: // 'D'
                case 74: // 'J'
                    i += 2;
                    break;
                default:
                    i++;
                    break;
            }
        }

        String s1;
        if ((s1 = s.substring(s.lastIndexOf(')') + 1)).startsWith("L")) {
            cs.b(s1.substring(1, s1.length() - 1));
        }
        return i;
    }

    final String c() {
        return descriptor.substring(descriptor.lastIndexOf(')') + 1);
    }
}
