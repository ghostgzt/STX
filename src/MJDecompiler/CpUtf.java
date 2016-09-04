package MJDecompiler;

import java.io.IOException;

final class CpUtf extends ConstantPool {

    CpUtf(ClazzInputStream bc1) throws Exception {

        byte abyte0[] = new byte[bc1.readUShort()];
        bc1.readFully(abyte0);
        a = new String(abyte0);
    }

    final void writeBytecode(ByteCodeOutput bf1) throws IOException {
        bf1.writeByte(1);
        bf1.writeUshort(a.length());
        for (int it = 0; it < a.length(); it++) {
            bf1.writeByte(a.charAt(it));
        }

    }

    final void a(ClassFile bj1, String s) {
        if (s == null) {
            a = "";
            return;
        }
        if (s.equals("class")) {
            a = bj1.d(a);
            return;
        }
        if (s.equals("internal member")) {
            a = bj1.e(a);
            return;
        }
        if (s.equals("signature")) {
            a = bj1.f(a);
        }
    }

    final CpUtf g() {
        return null;
    }

    public final String toString() {
        return a;
    }

    final String a(ClassFile bj1) {
        return "AsciiZ '" + a + "'";
    }
    String a;
}
