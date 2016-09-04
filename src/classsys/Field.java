package classsys;

import java.io.IOException;
import java.io.OutputStream;

public class Field {

    int access;
    String name;
    String descriptor;
    int attributesCount;
    Attribute[] attributes;

    Field(int flag, String n, String d) {
        access = flag;
        name = n;
        descriptor = d;
    }

    void show(OutputStream o, int j) throws IOException {
        o.write(("  " + Classsys.getAccess(access) + Classsys.change2(descriptor) + "  " + name).getBytes());
        for (int i = 0; i < attributesCount; i++) {
            // o.write(("  Attribute_name:  "+attributes[i].name+"\r\n").getBytes());
            attributes[i].show(o,0);
        }
        o.write(";\r\n".getBytes());
    }

    void setAttributes(Attribute[] a) {
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
