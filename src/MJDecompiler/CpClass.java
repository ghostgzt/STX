package MJDecompiler;

import java.io.IOException;

final class CpClass extends ConstantPool {

    CpClass(ClazzInputStream in, ClassFile cs) throws IOException {
        this.cs = cs;
        b = in.readUShort();
        cs.settoClass(b);
    }

    final void writeBytecode(ByteCodeOutput bf1)throws IOException {
        bf1.writeByte(7);
        bf1.writeUshort(b);
    }

    final void f() {
        b = cs.a(b, "class");
    }

    final void a(ClassFile bj1, String s) {
        a(s, "class");
    }

    final ConstantPool next() {
        return cs.getConstant(b);
    }

    final String name() {
        return cs.c(next().toString());
    }

    public final String toString() {
        return name();
    }

    final String a(ClassFile bj1) {
        return "Class(" + b + ":" + bj1.getConstant(b).a(bj1) + ")";
    }
    ClassFile cs;
    int b;
}
