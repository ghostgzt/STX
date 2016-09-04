package MJDecompiler;

import java.io.IOException;

final class MethodInfo {

    ClassFile clazz;
    Access access;
    ConstantPool name;
    ConstantPool descriptor;
    Code code;
    MethodDescriptor head;
    ConstantPool exceptions[];

    MethodInfo(ClazzInputStream in, ClassFile cz) throws Exception {
        clazz = cz;
        access = new Access(in);
        name = cz.getConstant(in);
        descriptor = cz.getConstant(in);
        head = new MethodDescriptor(cz, access.isStatic(), descriptor.toString());
        int i = in.readUShort();
        for (int j = 0; j < i; j++) {
            String s = cz.getConstant(in).toString();
            int k = in.readInt();
            if (s.equals("Code")) {
                code = new Code(in, cz, head);
                continue;
            }
            if (s.equals("Exceptions")) {
                int l = in.readUShort();
                exceptions = new ConstantPool[l];
                for (int i1 = 0; i1 < l; i1++) {
                    exceptions[i1] = cz.getConstant(in);
                }

            } else {
                System.out.println("Ignoring method attribute " + s);
                in.skip(k);
            }
        }

        if (head.a()) {
            head.b();
        }
    }

    final void a() {
        clazz.a(name, "internal member");
        clazz.a(descriptor, "signature");
        if (code != null) {
            clazz.a(clazz.h("Code"), "attribute");
            code.a();
        }
        if (exceptions != null) {
            clazz.a(clazz.h("Exceptions"), "attribute");
            for (int i = 0; i < exceptions.length; i++) {
                clazz.a(exceptions[i], "class");
            }

        }
    }

    final void decompile() {
        if (code != null) {
            if (Decompiler.showInfo) {
                System.out.println(" Method " + name);
            }
            String s = code.decompile();
            if (s != null) {
                System.out.println("Method " + name + ": " + s);
            }
        }
    }

    final boolean empty() {
        return code == null;
    }

    final void writeSource(Pstream printstream) {
        az az1 = new az(name, descriptor, clazz, head);
        printstream.print("    ");
        printstream.print(access);
        printstream.print(az1);
        if (exceptions != null) {
            printstream.println();
            printstream.print("        throws ");
            for (int i = 0; i < exceptions.length; i++) {
                if (i != 0) {
                    printstream.print(", ");
                }
                printstream.print(exceptions[i]);
            }

        }
        if (code == null) {
            printstream.println(";");
            return;
        } else {
            printstream.println();
            printstream.println("    {");
            code.a(printstream);
            printstream.println("    }");
            return;
        }
    }
}
