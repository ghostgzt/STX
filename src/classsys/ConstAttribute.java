package classsys;

import java.io.IOException;
import java.io.OutputStream;

public class ConstAttribute extends Attribute {

    String value;

    ConstAttribute(String n, int len, int off, String value) {
        super(n, len, off);
        this.value = value;
    }

    public void show(OutputStream o,int mode) {
        try {
            o.write((" = " + value).getBytes("Utf-8"));
        } catch (IOException ex) {
        }
    }
}
