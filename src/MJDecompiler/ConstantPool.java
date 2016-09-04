package MJDecompiler;

import java.io.IOException;

abstract class ConstantPool {

    int size() {
        return 1;
    }

    ConstantPool next() {
        return null;
    }

    String name() {
        return "name?";
    }

    String type() {
        return "type?";
    }

    String e() {
        return "sig?";
    }

    void a(String s) {
    }

    static final ConstantPool read(ClazzInputStream in, ClassFile cs) throws Exception, by {

        switch (in.readByte()) {
            case 1: // '\001'
                return new CpUtf(in);

            case 2: // '\002'
                return new bk(in);

            case 3: // '\003'
                return new CpInt(in);

            case 4: // '\004'
                return new CpFloat(in);

            case 5: // '\005'
                return new CpLong(in);

            case 6: // '\006'
                return new CpDouble(in);

            case 7: // '\007'
                return new CpClass(in, cs);

            case 8: // '\b'
                return new CpString(in, cs);

            case 9: // '\t'
                return new as(in, cs);

            case 10: // '\n'
                return new cd(in, cs);

            case 11: // '\013'
                return new be(in, cs);

            case 12: // '\f'
                return new CpNameAndType(in, cs);
        }
        return null;
    }

    abstract void writeBytecode(ByteCodeOutput bf) throws IOException;

    void f() {
    }

    void a(ClassFile bj1, String s) {
        a(s, "literal");
    }

    final void a(String s, String s1) {
        if (s != null && !s.equals(s1)) {
            System.err.println(getClass().getName() + " used as " + s + " instead of " + s1);
        }
    }

    abstract String a(ClassFile bj1);
}
