package classsys;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;
import bytecode.AbstractInstruction;


public class CodeAttribute extends Attribute {

    static boolean isstatic;
    byte[] b;
    int max_stack;
    static int max_locals;
    int codelength;
    Method method;
    static String[] arg;
    static Vector table;

    CodeAttribute(String n, int len, int off, byte[] data, Method m) {
        super(n, len, off);
        b = new byte[len];
        System.arraycopy(data, off, b, 0, len);
        if (len > 8) {
            syscode();
        }
        method = m;
    }

    void syscode() {
        max_stack = readshort(0);
        max_locals = readshort(2);
        codelength = readshort(4) | readshort(6);
    }

    public void show(OutputStream o, int mode) throws IOException {
    //    try {
//        try {
//            o.write((" ms: " + max_stack + " ml: " + max_locals + " length: " + codelength + "\r\n").getBytes());
//        } catch (IOException ex) {
//        }
            byte[] temp = new byte[codelength];
            System.arraycopy(b, 8, temp, 0, codelength);
            int off = 8 + codelength;
            int exceptionNum = readshort(off);
            //    System.out.println("exceptionNum: "+exceptionNum);
            Vector localTable = new Vector();
            ExceptionHander[] eh = new ExceptionHander[exceptionNum];
            while (exceptionNum > 0) {
                exceptionNum--;
                //  try {
                //   o.write(("Exception  start: " + readshort(off += 2) + "  end: " + readshort(off += 2) + "  hander: " + readshort(off += 2) + "  " + Classsys.cps[readshort(off += 2)].toString() + "\r\n").getBytes());
                int index = readshort(off + 8);
                String ename = (index != 0) ? Classsys.change3(Classsys.cps[index].strVal1) : "Throwable";
                eh[exceptionNum] = new ExceptionHander(readshort(off + 2), readshort(off + 4), readshort(off + 6), ename);
                 o.write(("Exception  start: " + eh[exceptionNum].start + "  end: " + eh[exceptionNum].end + "  hander: " + eh[exceptionNum].hand + "  " + eh[exceptionNum].type + "\r\n").getBytes());
                off += 8;
                //   } catch (Exception ex) {
                //   }
            }
            exceptionNum = readshort(off += 2);
            int len = 0;
            while (exceptionNum > 0) {
                exceptionNum--;
                Attribute s = new Attribute(Classsys.cps[readshort(off += 2)].strVal1, len = readshort(off += 2) | readshort(off += 2), off + 2);
                off += len;
                //   try {
                //   o.write(("  CodeAttName: " + s.name + "  length: " + len + "\r\n").getBytes());
                if (s.name.equals("LocalVariableTable")) {
                    int k = readshort(s.offset);
                    for (int m = 0; m < k; m++) {
                        localTable.addElement(new LocalVariable(readshort(s.offset + 2 + 10 * m), readshort(s.offset + 4 + 10 * m), readshort(s.offset + 10 + 10 * m), Classsys.cps[readshort(s.offset + 6 + 10 * m)].strVal1, Classsys.cps[readshort(s.offset + 8 + 10 * m)].strVal1));
                    }
                } else {
                    System.out.println("skiped Attribute: " + s.name);
                }
                //     } catch (IOException ex) {
                //      }
            }

            String paramas = Classsys.change(method.descriptor);
            paramas = paramas.substring(paramas.indexOf('(') + 1);
            String[] args = getparamas(paramas);
            arg = args;
            StringBuffer sb = new StringBuffer("(");
            isstatic = (method.access & 8) > 0;
            int i = isstatic ? 0 : 1;
            int num = args.length;
            Vector parama = num > 0 ? new Vector() : null;
            int of = 0;
            int size = localTable.size();
            table = localTable;
            if (size > 0) {
                while (num > 0) {
                    for (int j = 0; j < size; j++) {
                        if (((LocalVariable) localTable.elementAt(j)).index == i) {
                            sb.append(args[of++]).append(' ').append(((LocalVariable) localTable.elementAt(j)).name).append(',');
                            parama.addElement(localTable.elementAt(j));
                            break;
                        }
                    }
                    num--;
                    i++;
                }
            } else {
                while (num > 0) {
                    String s = args[of].toLowerCase() + "_" + i;
                    parama.addElement(new LocalVariable(0, codelength, i++, s, getType(args[of])));
                    sb.append(args[of++]).append(' ').append(s).append(',');
                    num--;
                }
            }
            size = sb.length();
            if (size > 1) {
                sb.setCharAt(size - 1, ')');
            } else {
                sb.append(')');
            }
            sb.append("{\r\n");
            //   System.out.print(sb);
            try {
                o.write(sb.toString().getBytes());
            } catch (IOException ex) {
            }
            // try {
            Vector a = ByteCodeReader.readByteCode(temp);
            if (mode != 0) {
                for (int j = 0; j < a.size(); j++) {
                    ((AbstractInstruction) a.elementAt(j)).show(o);
                }
            }
            if (mode != 1) {
                Document doc;
            try {
                doc = ByteCodeReader.sys(a, parama, eh);
                 doc.show(o);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new IOException();
            }
               
            }

            //   } catch (Exception e) {
            //       e.printStackTrace();
            //   }
     //   } catch (Exception ex) {
   //         ex.printStackTrace();
          
    //    }
        //   } catch (Exception e) {
        //       e.printStackTrace();
        //   }
    }

    int readshort(int off) {
        return ((b[off++] & 0xFF) << 8) | (b[off] & 0xFF);
    }

    public static String[] getparamas(String paramas) {
        if (paramas.startsWith(")")) {
            return new String[0];
        } else {
            byte[] bytes = paramas.getBytes();
            Vector v = new Vector();
            int i = 0, start = 0;
            while (true) {
                if (bytes[i] == ',') {
                    v.addElement(new String(bytes, start, i - start));
                    start = i + 1;
                } else if (bytes[i] == ')') {
                    v.addElement(new String(bytes, start, i - start));
                    String[] s = new String[v.size()];
                    v.copyInto(s);
                    return s;
                }
                i++;
            }
        }
    }

    public static String getParamasName(String name, int index) {

        int l = name.indexOf('[');
        int len = name.length();
        if (l > 0) {
            name = name.substring(0, l);
            l = (len - l) / 2;
        }

        StringBuffer v = new StringBuffer();
        while (l-- > 0) {
            v.append('a');
        }
        if (name.equals("byte")) {
            v.append("b");
        } else if (name.equals("boolean")) {
            v.append("flag");
        } else if (name.equals("long")) {
            v.append("l");
        } else if (name.equals("char")) {
            v.append("ch");
        } else if (name.equals("short")) {
            v.append("s");
        } else if (name.equals("double")) {
            v.append("d");
        } else if (name.equals("float")) {
            v.append("f");
        } else if (name.equals("int")) {
            v.append("i");
        } else {
            v.append(name.toLowerCase());
        }
        return v.append('_').append(index).toString();
    }

    static String getType(String s) {
        String v;
        if (s.equals("byte")) {
            v = "B";
        } else if (s.equals("boolean")) {
            v = "Z";
        } else if (s.equals("long")) {
            v = "J";
        } else if (s.equals("char")) {
            v = "C";
        } else if (s.equals("short")) {
            v = "S";
        } else if (s.equals("double")) {
            v = "D";
        } else if (s.equals("float")) {
            v = "F";
        } else if (s.equals("int")) {
            v = "I";
        } else {
            v = "L" + s;
        }
        return v;
    }
}

