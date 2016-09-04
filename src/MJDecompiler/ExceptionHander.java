package MJDecompiler;

import java.io.IOException;

class ExceptionHander {

    int start;
    int end;
    int hander;
    ConstantPool exception;

    ExceptionHander(int i, int j, int k) {
        start = j;
        end = k;
        hander = i;
    }

    ExceptionHander(ClazzInputStream in, ClassFile cs) throws IOException {
        start = in.readUShort();
        end = in.readUShort();
        hander = in.readUShort();
        exception = cs.getConstant(in);
    }

    final void writeBytecode(ByteCodeOutput out, ClassFile cs) throws IOException {
        out.writeUshort(start);
        out.writeUshort(end);
        out.writeUshort(hander);
        if (exception == null) {
            out.writeUshort(0);
            return;
        } else {
            cs.a(out, exception);
            return;
        }
    }

    final void a(ClassFile cs) {
        if (exception != null) {
            cs.a(exception, "class");
        }
    }

    String a() {
        if (exception == null) {
            return "<anyException>";
        } else {
            return "L" + exception.next().toString() + ";";
        }
    }

    String b() {
        if (exception == null) {
            return "<anyException>";
        } else {
            return exception.toString();
        }
    }
}
