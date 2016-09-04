package classsys;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

public class ImportHandler {

    Hashtable imports;
    String className;
    String pkg;

    public void WriteHeader(OutputStream writer) throws IOException {
        if (pkg.length() != 0) {
            writer.write(("package " + pkg + ";\r\n\r\n").getBytes());
        }
        Enumeration iter = imports.keys();
        String thispkg = pkg + ".*;";
        String[] is = new String[imports.size()];
        int i = 0;
        while (iter.hasMoreElements()) {
            is[i++] = (String) iter.nextElement();
        }
        for (int j = i - 1; j > 0; j--) {
            for (int l = 0; l < j; l++) {
                if (is[l].length() > is[l + 1].length()) {
                    String temp = is[l];
                    is[l] = is[l + 1];
                    is[l + 1] = temp;
                }
            }
        }
        for (int j = 0; j < i; j++) {
            if (!is[j].equals("java.lang.*;") && !is[j].equals(thispkg)) {
                writer.write(("import " + is[j] + "\r\n").getBytes());
            }
        }
        writer.write("\r\n".getBytes());
    }

    public void init(String className) {
        imports = new Hashtable();
        className = className.replace('/', '.');
        pkg = pkg(className);
        this.className = className;
    }

    public String pkg(String s) {
        int l = s.lastIndexOf('.');
        return l > 0 ? s.substring(0, l) : "";
    }

    public void useClass(String clazz) {
        if (clazz.startsWith("[")) {
            return;
        } else if (clazz.startsWith("L")) {
            clazz=clazz.substring(1);
        }
        clazz = clazz.replace('/', '.');
        imports.put(pkg(clazz) + ".*;", "1");
    }
//   public String getClassString(ClassInfo clazz) {
//        String n = clazz.getName();
//        if (imports.containsKey(pkg(n)+".*;")) {
//            n = n.substring(n.lastIndexOf('.') + 1);
//        }
//        return n;
//    }
}


