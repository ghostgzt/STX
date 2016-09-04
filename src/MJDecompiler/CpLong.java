package MJDecompiler;

import java.io.IOException;

final class CpLong extends ConstantPool {

    CpLong(ClazzInputStream bc1)throws IOException {
        a = bc1.readLong();
    }

    final void writeBytecode(ByteCodeOutput bf1)throws IOException {
        bf1.writeByte(5);
        bf1.writeLong(a);
    }

    final int size() {
        return 2;
    }

    final String type() {
        return "J";
    }

    public final String toString() {
        return String.valueOf(a);
    }

    final String a(ClassFile bj) {
        return "Long " + a;
    }
    long a;
}
