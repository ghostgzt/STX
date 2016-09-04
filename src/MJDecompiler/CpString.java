package MJDecompiler;

import java.io.IOException;

final class CpString extends ConstantPool {

    CpString(ClazzInputStream bc1, ClassFile bj1) throws IOException {
        a = bj1;
        b = bc1.readUShort();
    }

    final void writeBytecode(ByteCodeOutput bf1)throws IOException {
        bf1.writeByte(8);
        bf1.writeUshort(b);
    }

    final void f() {
        b = a.a(b, "literal");
    }

    final String type() {
        return "Ljava/lang/String;";
    }

    public final String toString() {
        StringBuffer stringbuffer;
        (stringbuffer = new StringBuffer()).append('"');
        String s = a.getConstant(b).toString();
        int i = 0;
        do {
            if (i >= s.length()) {
                break;
            }
            int j;
            if ((j = s.charAt(i++)) >= '\200') {
                if (j < 224) {
                    j = (j = (j &= 0x1f) << 6) | s.charAt(i++) & 0x3f;
                } else {
                    j = (j = (j = (j = (j &= 0xf) << 6) | s.charAt(i++) & 0x3f) << 6) | s.charAt(i++) & 0x3f;
                }
            }
            switch (j) {
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

                case 34: // '"'
                    stringbuffer.append("\\\"");
                    break;

                case 92: // '\\'
                    stringbuffer.append("\\\\");
                    break;

                default:
                    if (j < 32) {
                        String s1;
                        for (s1 = Integer.toString(j, 8); s1.length() < 3; s1 = "0" + s1);
                        stringbuffer.append("\\" + s1);
                    } else if (j < 127) {
                        stringbuffer.append((char) j);
                    } else {
                        String s2;
                        for (s2 = Integer.toString(j, 16); s2.length() < 4; s2 = "0" + s2);
                        stringbuffer.append("\\u" + s2);
                    }
                    break;
            }
        } while (true);
        stringbuffer.append('"');
        return stringbuffer.toString();
    }

    final String a(ClassFile bj1) {
        return "String(" + b + ":" + bj1.getConstant(b).a(bj1) + ")";
    }
    ClassFile a;
    int b;
}
