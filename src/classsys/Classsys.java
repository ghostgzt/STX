package classsys;

import io.FileSys;
import java.io.*;

public class Classsys implements Runnable {

    String f;
    int items[];
    public static byte[] b;
    String[] strings;
    int header, maxStringLength;
    public static ConstantPool[] cps;
    static final int ACC_PUBLIC = 1;
    static final int ACC_PRIVATE = 2;
    static final int ACC_PROTECTED = 4;
    static final int ACC_STATIC = 8;
    static final int ACC_FINAL = 16;
    static final int ACC_SUPER = 32;
    static final int ACC_SYNCHRONIZED = 32;
    static final int ACC_VOLATILE = 64;
    static final int ACC_TRANSIENT = 128;
    static final int ACC_NATIVE = 256;
    static final int ACC_INTERFACE = 512;
    static final int ACC_ABSTRACT = 1024;
    static final int ACC_STRICT = 2048;
    static final int CLASS = 7;
    static final int FIELD = 9;
    static final int METH = 10;
    static final int IMETH = 11;
    public static final int STR = 8;
    public static final int INT = 3;
    public static final int FLOAT = 4;
    static final int LONG = 5;
    static final int DOUBLE = 6;
    static final int NAME_TYPE = 12;
    static final int UTF8 = 1;
    static final int TYPE_NORMAL = 13;
    static final int TYPE_UNINIT = 14;
    static final int TYPE_MERGED = 15;
    private OutputStream o;
    public static String classname;
    ImportHandler imports;

    public Classsys(String file) {
        f = file;
    }

    public Classsys(DataOutputStream out) {
        o = out;
    }

    public static String getAccess(int i) {
        StringBuffer sb = new StringBuffer();
        if ((i & ACC_PUBLIC) > 0) {
            sb.append("public ");
        }
        if ((i & ACC_PRIVATE) > 0) {
            sb.append("private ");
        }
        if ((i & ACC_PROTECTED) > 0) {
            sb.append("protected ");
        }
        if ((i & ACC_STATIC) > 0) {
            sb.append("static ");
        }
        if ((i & ACC_FINAL) > 0) {
            sb.append("final ");
        }

        if ((i & ACC_VOLATILE) > 0) {
            sb.append("volatile ");
        }

        if ((i & ACC_TRANSIENT) > 0) {
            sb.append("transient ");
        }
        if ((i & ACC_NATIVE) > 0) {
            sb.append("native ");
        }

        if ((i & ACC_ABSTRACT) > 0) {
            sb.append("abstract ");
        }
        if ((i & ACC_STRICT) > 0) {
            sb.append("strict ");
        }
        if ((i & ACC_INTERFACE) > 0) {
            sb.append("interface ");
        }

        return sb.toString();
    }

    public int readUnsignedShort(final int index) {

        return ((b[index] & 0xFF) << 8) | (b[index + 1] & 0xFF);
    }

    public void readclass(int mode) {

        int i = 0;
        int off = 0;
        if (this.readInt(0) != 0xcafebabe) {
            println("非合法class文件");
            return;
        }
        imports = new ImportHandler();
        try {
            int n = readUnsignedShort(off + 8);
            items = new int[n];
            strings = new String[n];
            cps = new ConstantPool[n];
            int max = 0;
            int index = off + 10;
            for (i = 1; i < n; ++i) {
                items[i] = index + 1;
                cps[i] = new ConstantPool(i);
                int size;
                switch (b[index]) {
                    case INT:
                        cps[i].set(readInt(index + 1));
                        size = 5;
                        break;
                    case FIELD:
                    case METH:
                    case IMETH:
                        size = 5;
                        break;
                    case FLOAT:
                        cps[i].set((float) readInt(index + 1));
                        size = 5;
                        break;
                    case NAME_TYPE:
                        size = 5;
                        break;
                    case LONG:
                        cps[i].set(readLong(index + 1));
                        size = 9;
                        ++i;
                        break;
                    case DOUBLE:
                        cps[i].set((double) readLong(index + 1));
                        size = 9;
                        ++i;
                        break;
                    case UTF8:
                        size = 3 + readUnsignedShort(index + 1);
                        if (size > max) {
                            max = size;
                        }
                        cps[i].set(UTF8, this.readUTF(index + 3, size - 3, new char[max]), null, null);
                        break;
                    //   case CLASS:
                    //   case STR:
                    default:
                        size = 3;
                        break;
                }
                index += size;
            }
            header = index;
            maxStringLength = max;
            for (int h = 1; h < n; h++) {
                switch (b[items[h] - 1]) {
                    case NAME_TYPE:
                        cps[h].set(NAME_TYPE, cps[readUnsignedShort(items[h])].strVal1, cps[readUnsignedShort(items[h] + 2)].strVal1, null);
                        break;
                    case LONG:
                    case DOUBLE:
                        h++;
                        break;
                    case CLASS:
                        cps[h].set(CLASS, cps[readUnsignedShort(items[h])].strVal1, null, null);
                        break;
                    case STR:
                        cps[h].set(STR, cps[readUnsignedShort(items[h])].strVal1, null, null);
                        break;
                }

            }

            classname = cps[readUnsignedShort(header + 2)].strVal1;
            ByteCodeReader.className = classname;
            imports.init(classname);

            for (int h = 1; h < n; h++) {

                switch (b[items[h] - 1]) {

                    case CLASS:
                        // cps[h].set(CLASS, cps[readUnsignedShort(items[h])].strVal1, null, null);
                        //    println(h + ": class:  " + cps[h].strVal1);
                        //     break;
                        imports.useClass(cps[h].strVal1);
                        break;
                    case NAME_TYPE:
                    //    cps[h].set(NAME_TYPE, cps[readUnsignedShort(items[h])].strVal1, cps[readUnsignedShort(items[h] + 2)].strVal1, null);
                    //   println(h + ": name_type:  " + cps[h].strVal1 + "  :  " + cps[h].strVal2);
                    //      break;
                    case UTF8:
                    //   println(h + ": utf8:  " + cps[h].strVal1);
                    //    break;

                    case STR:
                    //     println(h + ": String:  " + cps[h].strVal1);
                    //     break;
                    case INT:
                        //      println(h+": " +cps[h].toString());// ": int:  " + cps[h].intVal);
                        break;
                    case LONG:
                    //    println(h + ": long:  " + cps[h].longVal);
                    //     ++h;
                    //      break;
                    case DOUBLE:
                        //      println(h+": " + cps[h].toString());//": double:  " + cps[h].longVal);
                        ++h;
                        break;

                    case FIELD:
                        cps[h].set(FIELD, cps[readUnsignedShort(items[h])].strVal1, cps[readUnsignedShort(items[h] + 2)].strVal1, cps[readUnsignedShort(items[h] + 2)].strVal2);
                        //    println(h+": " +cps[h].toString()); //": Field:  " + cps[readUnsignedShort(items[h])].strVal1 + "  :  " + cps[readUnsignedShort(items[h] + 2)].strVal1 + "  :  " + cps[readUnsignedShort(items[h] + 2)].strVal2);
                        break;
                    case METH:
                        cps[h].set(METH, cps[readUnsignedShort(items[h])].strVal1, cps[readUnsignedShort(items[h] + 2)].strVal1, cps[readUnsignedShort(items[h] + 2)].strVal2);
                        //      println(h+": " + cps[h].toString());//": Meth:  " + cps[readUnsignedShort(items[h])].strVal1 + "  :  " + cps[readUnsignedShort(items[h] + 2)].strVal1 + "  :  " + cps[readUnsignedShort(items[h] + 2)].strVal2);
                        break;
                    case IMETH:
                        cps[h].set(IMETH, cps[readUnsignedShort(items[h])].strVal1, cps[readUnsignedShort(items[h] + 2)].strVal1, cps[readUnsignedShort(items[h] + 2)].strVal2);
                        //     println(h+": " +cps[h].toString());// ": iMeth:  " + cps[readUnsignedShort(items[h])].strVal1 + "  :  " + cps[readUnsignedShort(items[h] + 2)].strVal1 + "  :  " + cps[readUnsignedShort(items[h] + 2)].strVal2);
                        break;

                }

            }
            imports.WriteHeader(o);
            print(getAccess(readUnsignedShort(header)));
            if ((readUnsignedShort(header) & ACC_INTERFACE) == 0) {
                print("class ");
            }
            print((classname = classname.substring(classname.lastIndexOf('/') + 1)));
            String superc = change3(cps[readUnsignedShort(header + 4)].strVal1);
            if (!superc.equals("Object")) {
                print(" extends ");
                print(superc);
            }
            String[] inter = getInterfaces();
            if (inter.length > 0) {
                print(" implements ");
                for (int h = 0; h < inter.length - 1; h++) {
                    print(change3(inter[h]) + ", ");
                }
                println(change3(inter[inter.length - 1]) + "{");
            } else {
                println("{");
            }

            off = header + 8 + inter.length * 2;
            int len = 0;
            n = readUnsignedShort(off);
            Field[] fields = new Field[n];
            int m = 0;
            for (i = 0; i < n; i++) {
                fields[i] = new Field(readUnsignedShort(off += 2), cps[readUnsignedShort(off += 2)].strVal1, cps[readUnsignedShort(off += 2)].strVal1);
                m = readUnsignedShort(off += 2);
                if (m > 0) {
                    String name;
                    Attribute[] a = new Attribute[m];
                    for (int j = 0; j < m; j++) {
                        name = cps[readUnsignedShort(off += 2)].strVal1;
                        if (name.equals("ConstantValue")) {
                            a[j] = new ConstAttribute(name, len = readInt(off += 2), off + 4, cps[readUnsignedShort(off + 4)].Value());
                        } else {
                            a[j] = new Attribute(name, len = readInt(off += 2), off + 4);
                        }
                        off += len + 2;
                    }
                    fields[i].setAttributes(a);
                }
                fields[i].show(o, i);
            }
            n = readUnsignedShort(off += 2);

            Method[] methods = new Method[n];
            for (i = 0; i < n; i++) {
                methods[i] = new Method(readUnsignedShort(off += 2), cps[readUnsignedShort(off += 2)].strVal1, cps[readUnsignedShort(off += 2)].strVal1);
                m = readUnsignedShort(off += 2);
                if (m > 0) {
                    CodeAttribute[] a = new CodeAttribute[m];
                    for (int j = 0; j < m; j++) {
                        a[j] = new CodeAttribute(cps[readUnsignedShort(off += 2)].strVal1, len = readInt(off += 2), off + 4, b, methods[i]);
                        off += len + 2;
                    }
                    methods[i].setAttributes(a);
                }
                methods[i].show(o, mode);
            }
            n = readUnsignedShort(off += 2);
            println("\r\n }");
            Attribute[] a = new Attribute[n];
            for (int j = 0; j < n; j++) {
                a[j] = new Attribute(cps[readUnsignedShort(off += 2)].strVal1, len = readInt(off += 2), off);
                off += len + 2;
                if (a[j].name.equals("SourceFile")) {
                    println("// SourceFile: " + cps[readUnsignedShort(a[j].offset + 4)].strVal1);
                } else {
                    println("  Attname" + a[j].name + "  length: " + len);
                }
            }
            println("// ClassSysEnd TotalLenth= " + b.length + "  SysLength= " + (off + 2));
        } catch (Exception ex) {
            System.out.println("offset=" + off);
            // ex.printStackTrace();
            return;

        } finally {
            try {
                if (f != null && o != null) {
                    o.close();
                }
            } catch (IOException ex) {
            }
        }

        // System.out.println("-----------------------------------------");
    }

    public String[] getInterfaces() {
        int index = header + 6;
        int n = readUnsignedShort(index);
        String[] interfaces = new String[n];
        if (n > 0) {
            char[] buf = new char[maxStringLength];
            for (int i = 0; i < n; ++i) {
                index += 2;
                interfaces[i] = readClass(index, buf);
            }
        }
        return interfaces;
    }

    public String getSuperName() {
        return cps[readUnsignedShort(header + 4)].strVal1;
    }

    public String getClassName() {
        return cps[readUnsignedShort(header + 2)].strVal1;
    }

    public String readClass(final int index, final char[] buf) {
        return readUTF8(items[readUnsignedShort(index)], buf);
    }

    public String readUTF8(int index, final char[] buf) {
        int item = readUnsignedShort(index);
        String s = strings[item];
        if (s != null) {
            return s;
        }
        index = items[item];
        return strings[item] = readUTF(index + 2, readUnsignedShort(index), buf);
    }

    private String readUTF(int index, final int utfLen, final char[] buf) {
        try {
            /*   int endIndex = index + utfLen;
            int strLen = 0;
            int c, d, e;
            while (index < endIndex) {
            c = b[index++] & 0xFF;
            switch (c >> 4) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            // 0xxxxxxx
            buf[strLen++] = (char) c;
            break;
            case 12:
            case 13:
            // 110x xxxx 10xx xxxx
            d = b[index++];
            buf[strLen++] = (char) (((c & 0x1F) << 6) | (d & 0x3F));
            break;
            default:
            // 1110 xxxx 10xx xxxx 10xx xxxx
            d = b[index++];
            e = b[index++];
            buf[strLen++] = (char) (((c & 0x0F) << 12) | ((d & 0x3F) << 6) | (e & 0x3F));
            break;
            }
            }
            return new String(buf, 0, strLen);*/
            return new String(b, index, utfLen, "Utf-8");
        } catch (Exception ex) {
            return new String(b, index, utfLen);
        }
    }

    public long readLong(final int index) {
        long l1 = readInt(index);
        long l0 = readInt(index + 4) & 0xFFFFFFFFL;
        return (l1 << 32) | l0;
    }

    public int readInt(final int index) {

        return ((b[index] & 0xFF) << 24) | ((b[index + 1] & 0xFF) << 16) | ((b[index + 2] & 0xFF) << 8) | (b[index + 3] & 0xFF);
    }

    public void run() {
        long time = System.currentTimeMillis();
        b = FileSys.getdata(f, 0, 0);
        if (b == null || b.length < 10) {
            return;
        }
        o = FileSys.getOutputStream(f + ".chen.java");
        o = new ByteArrayOutputStream();
        readclass(0);
        o = FileSys.getOutputStream(f + ".jj.java");
        readclass(1);
        chen.c.show.show("信息", "分析" + f + "所花时间为：" + (System.currentTimeMillis() - time));
        //  javax.swing.JOptionPane.showMessageDialog(null, "信息\r\n分析" + f + "所花时间为：" + (System.currentTimeMillis() - time));
    }

    private void println(String s) {
        try {
            o.write((s + "\r\n").getBytes("Utf-8"));
        } catch (Exception ex) {
        }
    }

    private void print(String s) {
        try {
            o.write(s.getBytes("Utf-8"));
        } catch (Exception ex) {
        }
    }

    public static String change(String s) {
        //   System.out.println(s);
        if (s == null || s.length() < 1) {
            return null;
        }
        byte[] data = s.getBytes();
        byte[] temp = new byte[data.length - 3];
        String ts;
        StringBuffer sb = new StringBuffer("");
        if (data[0] != '(') {
            return s;
        }
        sb.append("(");
        int off = 0;
        int m = 0, l = 0;
        int n = s.lastIndexOf(')');
        while (off < n) {

            switch (data[++off]) {
                case 0x5b:
                    m++;
                    continue;
                case 'B':
                    sb.append("byte");
                    break;
                case 'I':
                    sb.append("int");
                    break;
                case 'C':
                    sb.append("char");
                    break;
                case 'D':
                    sb.append("double");
                    break;
                case 'S':
                    sb.append("short");
                    break;
                case 'J':
                    sb.append("long");
                    break;
                case 'F':
                    sb.append("float");
                    break;
                case 'Z':
                    sb.append("boolean");
                    break;
                case 'L':
                    l = 0;
                    while (data[++off] != ';') {
                        temp[l++] = data[off];
                    }
                    ts = new String(temp, 0, l);
                    if (ts.startsWith("java")) {
                        ts = ts.substring(ts.lastIndexOf('/') + 1);
                    }
                    sb.append(ts);
                    break;
                case ')':
                    sb.append(")");
                    continue;
            }

            while (m > 0) {
                sb.append("[]");
                m--;
            }

            if (data[off + 1] == ')') {
                sb.append(")");
                break;
            } else {
                sb.append(",");
            }
        }
        //  System.out.println(change2(s.substring(n+ 1)) + sb.toString());
        return change2(s.substring(n + 1)) + sb.toString().replace('/', '.');
    }

    public static String change2(String s) {

        StringBuffer sb = new StringBuffer();
        int m = 0;
        while (s.charAt(m) == 0x5b) {
            m++;
        }
        switch (s.charAt(m)) {
            case 'V':
                return "void";
            case 'B':
                sb.append("byte");
                break;
            case 'I':
                sb.append("int");
                break;
            case 'C':
                sb.append("char");
                break;
            case 'D':
                sb.append("double");
                break;
            case 'S':
                sb.append("short");
                break;
            case 'J':
                sb.append("long");
                break;
            case 'F':
                sb.append("float");
                break;
            case 'Z':
                sb.append("boolean");
                break;
            case 'L':
                sb.append(s.substring(m + 1, s.length() - 1));
        }
        while (m-- > 0) {
            sb.append("[]");
        }
        String ss = sb.toString().replace('/', '.');

        if (ss.startsWith("java")) {
            return ss.substring(ss.lastIndexOf('.') + 1);
        } else {
            return ss;
        }

    }

    public static String change3(String s) {
        String ss = s.replace('/', '.');

        if (ss.startsWith("java")) {
            return ss.substring(ss.lastIndexOf('.') + 1);
        } else {
            return ss;
        }
    }

    public static void close() {
        b = null;
    }
}
