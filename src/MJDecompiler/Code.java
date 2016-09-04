package MJDecompiler;

final class Code {

    ClassFile a;
    int maxstack;
    int maxlocal;
    int length;
    //   byte codeData[];
    ExceptionHander f[];
    MethodDescriptor desc;
    CodeInfo info;

    Code(ClazzInputStream in, ClassFile cf, MethodDescriptor mdesc) throws Exception, by {
        a = cf;
        desc = mdesc;
        maxstack = in.readUShort();
        maxlocal = in.readUShort();
        length = in.readInt();
        //  in.mark(length);
        //  codeData = new byte[length];
        //  in.readFully(codeData);
        //  in.reset();
        info = new CodeInfo(length, mdesc);
        Operation op;
        for (int k = 0; k < length; k += op.size()) {
            (op = info.setOperation(Opcode.readNext(in, k))).init(cf, mdesc);
        }

        int l = in.readUShort();
        f = new ExceptionHander[l];
        for (int i1 = 0; i1 < l; i1++) {
            f[i1] = new ExceptionHander(in, cf);
        }
        info.setHander(f);
        l = in.readUShort();
        for (int k = 0; k < l; k++) {
            String s = cf.getConstant(in).toString();
            int len = in.readInt();
            if (s.equals("LineNumberTable")) {
                in.skip(len);
                System.err.println("skip code attribute LineNumberTable");
                continue;
            }
            if (s.equals("LocalVariableTable")) {
                mdesc.readLocal(in);
            } else {
                System.err.println("Ignoring code attribute " + s);
                in.skip(len);
            }
        }

    }

    final void a() {
        OperationEnum x1 = info.c();
        while (x1.haveMoreElement()) {
            x1.NextElement().q();
        }
        for (int k = 0; k < f.length; k++) {
            f[k].a(a);
        }

    }

    final void b() {
        info.getOperation(0).addIndex();
        for (OperationEnum en = info.c(); en.haveMoreElement();) {
            Operation op = en.NextElement();
            int l = 0;
            while (l < op.m()) {
                info.getOperation(op.b(l)).addIndex();
                l++;
            }
        }

        for (int k = 0; k < f.length; k++) {
            info.getOperation(f[k].hander).addIndex();
            info.getOperation(f[k].start).addIndex();
            info.getOperation(f[k].end).addIndex();
        }

    }

    final void c() {
        fo fo1 = new fo();
        info.getOperation(0).a(fo1, info);
        for (int k = 0; k < f.length; k++) {
            String s = f[k].a();
            fo fo2 = fo1.a(new bg(s, "e"));
            info.getOperation(f[k].hander).a(fo2, info);
        }

    }

    final int a(db adb[], boolean flag) {

        int k;
        do {
            k = 0;
            for (OperationEnum x1 = info.c(); x1.haveMoreElement();) {
                Operation ai1 = x1.NextElement();
                int l = 0;
                while (l < adb.length) {
                    Operation ai2;
                    if ((ai2 = adb[l].a(ai1, info)) != null) {
                        ai1 = ai2;
                        k++;
                        if (Decompiler.debug) {
                            System.out.println("Applied " + adb[l].getClass().getName());
                        } else if (Decompiler.showInfo) {
                            System.out.print(".");
                            System.out.flush();
                        }
                        if (flag) {
                            x1.a(null);
                        } else {
                            x1.a(ai2);
                        }
                    }
                    l++;
                }
            }

        } while (k != 0);
        return k;
    }

    final void d() {
        bz bz1 = new bz(null, 0, 0x7fffffff);
        OperationEnum x1 = info.c();
        while (x1.haveMoreElement()) {
            x1.NextElement().a(bz1);
        }
        desc.a(bz1);
    }

    final void e() {
        OperationEnum x1 = info.c();
        while (x1.haveMoreElement()) {
            x1.NextElement().c();
        }
    }

    final String f() {
        int k = 0;
        for (OperationEnum x1 = info.c(); x1.haveMoreElement();) {
            x1.NextElement();
            k++;
        }

        if (k > 1) {
            return "Flow analysis could not complete";
        } else {
            return null;
        }
    }

    final String decompile() {
        b();
        c();
        a(i, false);
        a(j, true);
        d();
        e();
        return f();
    }

    final void a(Pstream printstream) {
        desc.a(printstream, 2);
        info.a(printstream, 2);
        if (Decompiler.debug) {
            for (int k = 0; k < f.length; k++) {
                ExceptionHander y1 = f[k];
                printstream.println("// handle " + y1.b() + " in " + y1.start + "-" + y1.end + " at " + y1.hander);
            }
        }
 }
    db i[] = {
        new di(), new dp(), new ee(), new cm(), new cn(), new dn(), new dx(), new dj(), new dk(), new dl(),
        new eo(), new el(), new eh(), new dd(), new em(), new fl(), new fk(), new ek(), new co(), new cp(),
        new cz(), new dm(), new dod()
    };
    db j[] = {
        new ej(), new fc(), new fd(), new ff(), new fj(), new fi(), new cq(), new fe(), new ef(), new dv(),
        new fh(), new en(), new er(), new ei(), new fg()
    };
}
