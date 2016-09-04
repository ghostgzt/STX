package MJDecompiler;

import java.io.IOException;

final class CpNameAndType extends ConstantPool {

    CpNameAndType(ClazzInputStream bc1, ClassFile bj1) throws IOException {
        a = bj1;
        b = bc1.readUShort();
        o = bc1.readUShort();
        bj1.c(o);
    }

    final void writeBytecode(ByteCodeOutput bf1)throws IOException {
        bf1.writeByte(12);
        bf1.writeUshort(b);
        bf1.writeUshort(o);
    }

    final void f() {
        o = a.a(o, "signature");
    }

    final void a(int i) {
        if (a.g(a.getConstant(i).next().toString())) {
            b = a.a(b, "external member");
            return;
        } else {
            b = a.a(b, "internal member");
            return;
        }
    }

    final void a(ClassFile bj1, String s) {
        a(s, "none");
    }

    final String name() {
        return a.getConstant(b).toString();
    }

    final String type() {
        return a.getConstant(o).toString();
    }

    final String a(ClassFile bj1) {
        return "NameAndType(" + b + ":" + bj1.getConstant(b).a(bj1) + ", " + o + ":" + bj1.getConstant(o).a(bj1) + ")";
    }
    ClassFile a;
    int b;
    int o;
}
