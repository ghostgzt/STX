package MJDecompiler;

import java.io.IOException;

abstract class CpField extends ConstantPool {

    CpField(ClazzInputStream bc1, ClassFile bj1) throws IOException {
        a = bj1;
        b = bc1.readUShort();
        o = bc1.readUShort();
    }

    final void f() {
        a.a(b, "class");
        ((CpNameAndType) a.getConstant(o)).a(b);
    }

    final void a(ClassFile bj1, String s) {
        a(s, "member");
    }

    final ConstantPool next() {
        return a.getConstant(b);
    }

    final String name() {
        return a.getConstant(o).name();
    }

    public final String toString() {
        return name();
    }
    ClassFile a;
    int b;
    int o;
}
