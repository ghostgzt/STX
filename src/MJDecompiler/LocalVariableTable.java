package MJDecompiler;

import java.io.IOException;

final class LocalVariableTable {

    int index;
    Object name;
    String type;
    int start;
    int end;
    ClassFile cs;

    LocalVariableTable(int index, Object name, String type, int j, int k, ClassFile cf) {
        start = j;
        end = k;
        cs = cf;
        this.name = name;
        this.type = type;
        this.index = index;
    }

    static final LocalVariableTable a(ClassFile cs, ClazzInputStream in) throws IOException {
        int start;
        int end = (start = in.readUShort()) + in.readUShort();
        String name = cs.getConstant(in).toString();
        String type = cs.getConstant(in).toString();
        int index = in.readUShort();
        return new LocalVariableTable(index, name, type, start, end, cs);
    }

    LocalVariableTable(int i, Object obj, String s, ClassFile bj1) {
        this(i, obj, s, 0, 0x7fffffff, bj1);
    }

    final boolean a() {
        return end == 0x7fffffff;
    }

    final Object b() {
        return name;
    }

    final void a(MethodDescriptor desc) {
        if (name == null) {
            name = desc.c(type);
        }
    }

    final String type() {
        return type;
    }

    final int size() {

        return type.equals("D") || type.equals("J") ? 2 : 1;
    }

    final int index() {
        return index;
    }

    public final String toString() {
        return name.toString();
    }

    final az f() {
        if (type.startsWith("<")) {
            return null;
        } else {
            return new az(name.toString(), type, cs);
        }
    }

    static final String a(int i) {
        StringBuffer stringbuffer = new StringBuffer(4 * i);
        for (int j = 0; j < i; j++) {
            stringbuffer.append("    ");
        }

        return stringbuffer.toString();
    }

    final void a(Pstream printstream, int i) {
        az az1;
        if ((az1 = f()) != null) {
            printstream.print(a(i) + az1 + ";");
            if (Decompiler.debug && !a()) {
                printstream.print("\t// slot " + index + ", scope " + start + "-" + end);
            }
            printstream.println();
        }
    }

    final boolean b(int i) {
        return start <= i;
    }
}
