package MJDecompiler;

import java.io.*;
import java.util.*;

public final class ClassFile {

    ConstantPool cps[];
    Access access;
    ConstantPool thisClass;
    String pack;
    ConstantPool superClass;
    ConstantPool interfaces[];
    FildInfo filds[];
    MethodInfo methods[];
    ConstantPool sourceFile;
    Vector imports;
    Hashtable type;
    Hashtable o;
    Hashtable p;
    Hashtable q;
    Hashtable r;
    Hashtable s;
    Hashtable t;
    String u[];
    int v[];

    public ClassFile(ClazzInputStream in, String s1) throws Exception, by {
        imports = new Vector();
        type = new Hashtable();
        o = new Hashtable();
        p = new Hashtable();
        if (in.readInt() != 0xcafebabe) {
            throw new by("Not a valid Java class file");
        }
        in.readUShort();
        in.readUShort();
        int num = in.readUShort();
        cps = new ConstantPool[num];
        for (int j = 1; j < num; j += cps[j].size()) {
            cps[j] = ConstantPool.read(in, this);
        }

        access = new Access(in);
        thisClass = cps[in.readUShort()];
        String s2;
        int k1;
        if ((k1 = (s2 = thisClass.toString()).lastIndexOf('.')) >= 0) {
            pack = s2.substring(0, k1);
            useClass(pack);
        }
        useClass("java.lang");
        a();
        superClass = cps[in.readUShort()];
        num = in.readUShort();
        interfaces = new ConstantPool[num];
        for (int i2 = 0; i2 < num; i2++) {
            interfaces[i2] = cps[in.readUShort()];
        }

        num = in.readUShort();
        filds = new FildInfo[num];
        for (int k2 = 0; k2 < num; k2++) {
            filds[k2] = new FildInfo(in, this);
        }

        num = in.readUShort();
        methods = new MethodInfo[num];
        for (int i3 = 0; i3 < num; i3++) {
            methods[i3] = new MethodInfo(in, this);
        }

        num = in.readUShort();
        for (int k3 = 0; k3 < num; k3++) {
            String s3 = cps[in.readUShort()].toString();
            int l3 = in.readInt();
            if (s3.equals("SourceFile")) {
                sourceFile = cps[in.readUShort()];
            } else {
                System.err.println("Ignoring class attribute " + s3);
                in.skip(l3);
            }
        }

    }

    final ConstantPool getConstant(int i1) {
        return cps[i1];
    }

    final ConstantPool getConstant(ClazzInputStream in) throws IOException {
        return getConstant(in.readUShort());
    }

    final void useClass(String s1) {
        for (int i1 = 0; i1 < imports.size(); i1++) {
            if (s1.equals((String) imports.elementAt(i1))) {
                return;
            }
        }

        imports.addElement(s1);
    }

    final void b(String s1) {
        if ((s1 = s1.replace('/', '.')).lastIndexOf('.') < 0) {
            String s2;
            if ((s2 = (String) p.get(s1)) != null && !s2.equals("<dup>")) {
                o.remove(s2);
            }
            p.put(s1, "<dup>");
            return;
        }
        if ((s1 = c(s1)).lastIndexOf('.') >= 0) {
            String s3 = s1.substring(s1.lastIndexOf('.') + 1);
            String s4;
            if ((s4 = (String) p.get(s3)) != null) {
                if (!s4.equals("<dup>")) {
                    o.remove(s4);
                    p.put(s3, "<dup>");
                    return;
                }
            } else {
                o.put(s1, s3);
                p.put(s3, s1);
            }
        }
    }

    final void settoClass(int i) {
        type.put(new Integer(i), "C");
    }

    final void c(int i) {
        type.put(new Integer(i), "T");
    }

    final void a() {
        Enumeration enumeration = type.keys();
        while (enumeration.hasMoreElements()) {
            Integer integer = (Integer) enumeration.nextElement();
            String s1 = (String) type.get(integer);
            String s2 = getConstant(integer.intValue()).toString();
            if (s1.equals("C")) {
                b(s2);
            } else if (s2.startsWith("L")) {
                b(s2.substring(1, s2.length() - 1));
            }
        }
    }

    final String c(String s1) {
        int i1;
        if ((i1 = (s1 = s1.replace('/', '.')).lastIndexOf('.')) < 0) {
            return s1;
        }
        String s2 = s1.substring(0, i1);
        String s3 = s1.substring(i1 + 1);
        for (int j1 = 0; j1 < imports.size(); j1++) {
            if (s2.equals((String) imports.elementAt(j1))) {
                return s3;
            }
        }

        if ((s3 = (String) o.get(s1)) != null) {
            return s3;
        } else {
            return s1;
        }
    }

    final ConstantPool b() {
        return thisClass;
    }

    final FildInfo a(ConstantPool bh1) {
        if (bh1.next() != thisClass) {
            return null;
        }
        String s1 = bh1.name();
        String s2 = bh1.type();
        for (int i1 = 0; i1 < filds.length; i1++) {
            if (filds[i1].b().equals(s1) && filds[i1].c().equals(s2)) {
                return filds[i1];
            }
        }

        return null;
    }

    public final void decompileMethod() {
        try {
            for (int i = 0; i < methods.length; i++) {
                methods[i].decompile();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private void writeImport(Pstream printstream) {
        boolean flag = false;
        Vector vector = new Vector();
        Enumeration enumeration = o.keys();
        String classpath;
        while (enumeration.hasMoreElements()) {
            classpath = (String) enumeration.nextElement();
            int i1 = 0;
            while (i1 < vector.size() && classpath.compareTo((String) vector.elementAt(i1)) >= 0) {
                i1++;
            }
            if (i1 < vector.size()) {
                vector.insertElementAt(classpath, i1);
            } else {
                vector.addElement(classpath);
            }
        }
        String s2 = "";
        int j1 = 0;
        for (int k1 = 0; k1 < vector.size(); k1++) {
            String s3;
            String s6;
            if ((s6 = (s3 = (String) vector.elementAt(k1)).substring(0, s3.lastIndexOf('.'))).equals(s2)) {
                if (++j1 <= 2) {
                    continue;
                }
                if (j1 == 3) {
                    useClass(s6);
                    for (int j2 = 0; j2 < 2; j2++) {
                        vector.removeElementAt(k1 - 2);
                    }

                    k1 -= 2;
                }
                vector.removeElementAt(k1);
                k1--;
            } else {
                s2 = s6;
                j1 = 1;
            }
        }

        for (int l1 = 0; l1 < imports.size(); l1++) {
            String s4 = (String) imports.elementAt(l1);
            if ((pack == null || !s4.equals(pack)) && !s4.equals("java.lang")) {
                printstream.println("import " + s4 + ".*;");
                flag = true;
            }
        }

        for (int i2 = 0; i2 < vector.size(); i2++) {
            String s5;
            if (!(s5 = (String) vector.elementAt(i2)).startsWith("[L")) {
                printstream.println("import " + s5 + ";");
            }
            flag = true;
        }

        if (flag) {
            printstream.println();
        }
    }

    final String d(String s1) {
        String s2 = null;
        if (s1.indexOf('/') >= 0) {
            s2 = s1.substring(0, s1.lastIndexOf('/'));
            s1 = s1.substring(s1.lastIndexOf('/') + 1, s1.length());
        }
        if ((pack == null && s2 == null || pack != null && s2 != null && s2.replace('/', '.').equals(pack)) && !q.containsKey(s1)) {
            String s3;
            if ((s3 = (String) s.get(s1)) == null) {
                s3 = String.valueOf(s.size());
                s.put(s1, s3);
            }
            s1 = s3;
        }
        if (s2 != null) {
            s1 = s2 + '/' + s1;
        }
        return s1;
    }

    final String e(String s1) {
        if (!r.containsKey(s1)) {
            String s2;
            if ((s2 = (String) t.get(s1)) == null) {
                s2 = String.valueOf(t.size());
                t.put(s1, s2);
            }
            s1 = s2;
        }
        return s1;
    }

    final String f(String s1) {
        StringBuffer stringbuffer = new StringBuffer();
        for (int i1 = 0; i1 < s1.length(); i1++) {
            if (s1.charAt(i1) == 'L') {
                int j1 = i1 + 1;
                while (s1.charAt(j1) != ';') {
                    j1++;
                }
                stringbuffer.append("L" + d(s1.substring(i1 + 1, j1)) + ";");
                i1 = j1;
            } else {
                stringbuffer.append(s1.charAt(i1));
            }
        }

        return stringbuffer.toString();
    }

    final boolean g(String s1) {
        int i1;
        if ((i1 = (s1 = s1.replace('/', '.')).lastIndexOf('.')) < 0) {
            return pack != null;
        }
        if (pack == null) {
            return true;
        }
        return !pack.equals(s1.substring(0, i1));
    }

    final void a(ConstantPool cp, String type) {
        int i1 = 1;
        while (i1 < cps.length && cps[i1] != cp) {
            i1 += cps[i1].size();
        }
        if (a(i1, type) != i1) {
            System.err.println("Conflicting usage of constant " + cp.toString());
        }
    }

    final int a(int i1, String s1) {
        if (u[i1] == null) {
            u[i1] = s1;
        }
        if (u[i1].equals(s1)) {
            return i1;
        }
        if (v[i1] != 0) {
            return a(v[i1], s1);
        }
        int j1 = cps.length;
        ConstantPool abh[] = cps;
        cps = new ConstantPool[j1 + 1];
        for (int k1 = 0; k1 < j1; k1++) {
            cps[k1] = abh[k1];
        }

        String as[] = u;
        u = new String[j1 + 1];
        for (int l1 = 0; l1 < j1; l1++) {
            u[l1] = as[l1];
        }

        int ai[] = v;
        v = new int[j1 + 1];
        for (int i2 = 0; i2 < j1; i2++) {
            v[i2] = ai[i2];
        }

        cps[j1] = ((CpUtf) cps[i1]).g();
        u[j1] = s1;
        v[i1] = j1;
        return j1;
    }

    public final void a(Hashtable hashtable, Hashtable hashtable1, Hashtable hashtable2, Hashtable hashtable3) {
        q = hashtable;
        r = hashtable1;
        s = hashtable2;
        t = hashtable3;
        u = new String[cps.length];
        v = new int[cps.length];
        a(thisClass, "class");
        a(superClass, "class");
        a(h("SourceFile"), "attribute");
        int i1 = filds.length;
        for (int j1 = 0; j1 < i1; j1++) {
            filds[j1].a();
        }

        int k1 = methods.length;
        for (int l1 = 0; l1 < k1; l1++) {
            methods[l1].a();
        }

        for (int i2 = 1; i2 < cps.length; i2 += cps[i2].size()) {
            cps[i2].f();
        }

        for (int j2 = 1; j2 < cps.length; j2 += cps[j2].size()) {
            cps[j2].a(this, u[j2]);
        }

        sourceFile = null;
    }

    final void a(ByteCodeOutput bf1, ConstantPool bh1) throws IOException {
        for (int i1 = 1; i1 < cps.length; i1 += cps[i1].size()) {
            if (cps[i1] == bh1) {
                bf1.writeUshort(i1);
                return;
            }
        }

        throw new IOException("Constant not found");
    }

    final ConstantPool h(String s1) {
        for (int i1 = 1; i1 < cps.length; i1 += cps[i1].size()) {
            if ((cps[i1] instanceof CpUtf) && cps[i1].toString().equals(s1)) {
                return cps[i1];
            }
        }

        return null;
    }

    public final void writeSource(OutputStream outputstream) {
        Pstream printstream = new Pstream(outputstream);
        if (pack != null) {
            printstream.println("package " + pack + ";\r\n");
        }
        writeImport(printstream);
        printstream.print(access.toString());
        if (!access.isInterface()) {
            printstream.print("class ");
        }
        printstream.print(thisClass.toString());
        String s1;
        if (!(s1 = superClass.toString()).equals("Object")) {
            printstream.print(" extends " + s1);
        }
        if (interfaces.length > 0) {
            printstream.print(" implements ");
            for (int i1 = 0; i1 < interfaces.length; i1++) {
                if (i1 > 0) {
                    printstream.print(", ");
                }
                printstream.print(interfaces[i1].toString());
            }

        }
        printstream.println("{\r\n");
        for (int j = 0; j < filds.length; j++) {
            filds[j].writeSource(printstream);
        }

        if (filds.length > 0 && methods.length > 0) {
            printstream.println();
        }
        for (int k1 = 0; k1 < methods.length; k1++) {
            methods[k1].writeSource(printstream);
            if (k1 < methods.length - 1 && !methods[k1].empty()) {
                printstream.println();
            }
        }

        printstream.println();
        printstream.println("}");
        printstream.close();
    }
}
