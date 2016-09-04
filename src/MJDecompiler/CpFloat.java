package MJDecompiler;

import java.io.IOException;

final class CpFloat extends ConstantPool {

    CpFloat(ClazzInputStream bc1) throws IOException {
        a = bc1.readInt();
    }

    final void writeBytecode(ByteCodeOutput bf1)
            throws IOException {
        bf1.writeByte(4);
        bf1.writeInt(a);
    }

    final String type() {
        return "F";
    }

    public final String toString() {
        return a + "";
    }

    final String a(ClassFile bj) {
        return "Float " + a;
    }
    int a;
}
