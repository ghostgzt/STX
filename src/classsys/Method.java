package classsys;

import java.io.IOException;
import java.io.OutputStream;

public class Method {

    int access;
    String name;
    String descriptor;
    int attributesCount;
    Attribute[] attributes;
    public static String methodAccess;

    Method(int flag, String n, String d) {
        access = flag;
        name = n;
        descriptor = d;

    }

    void show(OutputStream o, int mode) throws IOException {
        String s = Classsys.change(descriptor);
        String n = name;
        if (n.equals("<init>")) {
            n = Classsys.change3(Classsys.classname);
            s = s.substring(s.indexOf('('));
        } else if (n.equals("<clinit>")) {
            n = "";
            s = s.substring(s.indexOf('('));
        }
        methodAccess = Classsys.getAccess(access);
        String methodname = "\r\n  " + Classsys.getAccess(access) + s.substring(0, s.indexOf('(')) + " " + n;
     //   try {
            //System.out.print(methodname);
            o.write(methodname.getBytes());
            //   int off = 0;
            for (int i = 0; i < attributesCount; i++) {
                //     off = attributes[i].offset;
                //     o.write(("//  " + attributes[i].name + "  offset: " + off + "  length: " + attributes[i].length + "\r\n").getBytes());
                if (attributes[i].name.equals("Code")) {
                      ((CodeAttribute) attributes[i]).show(o, mode);
                }
            }
            if ((access & 1024) > 0) {
                String paramas = Classsys.change(descriptor);
                paramas = paramas.substring(paramas.indexOf('(') + 1);
                String[] args = CodeAttribute.getparamas(paramas);
                StringBuffer sb = new StringBuffer("(");
                int i = 0;
                for (; i < args.length - 1; i++) {
                    sb.append(args[i]).append(' ').append(CodeAttribute.getParamasName(args[i], i)).append(", ");
                }
                sb.append(args[i]).append(' ').append(CodeAttribute.getParamasName(args[i], i)).append(");\r\n");
                o.write(sb.toString().getBytes());
            } else //   System.out.println("   }");
            {
                o.write("   }\r\n".getBytes());
            }
      //  } catch (Throwable a) {
    //        a.printStackTrace();

     //   }
    }

    void setAttributes(CodeAttribute[] a) {
        attributes = a;
        attributesCount = a.length;
    }

    String getname() {
        return name;
    }

    String getdescriptor() {
        return descriptor;
    }

    Attribute[] getattributes() {
        return attributes;
    }
}
