package MJDecompiler;

import java.io.IOException;

final class CpDouble extends ConstantPool {

    CpDouble(ClazzInputStream bc1)throws IOException {
        a = bc1.readLong();
    }

    final void writeBytecode(ByteCodeOutput bf1)throws IOException {
        bf1.writeByte(6);
        bf1.writeLong(a);
    }

    final int size() {
        return 2;
    }

    final String type() {
        return "D";
    }

    public final String toString() {
        return String.valueOf(a);
    }

    final String a(ClassFile bj) {
        return "Double " + a;
    }
    long a;
}
