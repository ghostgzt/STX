package MJDecompiler;

import java.io.IOException;

final class CpInt extends ConstantPool {

    int value;
    String type;

    CpInt(ClazzInputStream bc1) throws IOException {
        value = bc1.readInt();
        type = "I";
    }

    final void writeBytecode(ByteCodeOutput bf1) throws IOException {
        bf1.writeByte(3);
        bf1.writeInt(value);
    }

    final String type() {
        return type;
    }

    public final String toString() {
        if (type.equals("Z")) {
            if (value != 0) {
                return "true";
            } else {
                return "false";
            }
        }
        if (type.equals("C")) {
            StringBuffer stringbuffer;
            (stringbuffer = new StringBuffer()).append('\'');
            switch (value) {
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
                    if (value < 32) {
                        String s = Integer.toString(value, 8);
                        while (s.length() < 3) {
                            s = "0" + s;
                        }
                        stringbuffer.append("\\" + s);
                        break;
                    }
                    if (value < 127) {
                        stringbuffer.append((char) value);
                        break;
                    }
                    String s1 = Integer.toString(value, 16);
                    while (s1.length() < 4) {
                        s1 = "0" + s1;
                    }
                    stringbuffer.append("\\u" + s1);
                    break;
            }
            stringbuffer.append('\'');
            return stringbuffer.toString();
        } else {
            return String.valueOf(value);
        }
    }

    final String a(ClassFile bj) {
        return "Integer " + value;
    }
}
