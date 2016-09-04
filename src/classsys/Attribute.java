package classsys;

import java.io.IOException;
import java.io.OutputStream;

public class Attribute {

    String name;
    int length;
    int offset;

    Attribute(String n, int len, int off) {
        name = n;
        length = len;
        offset = off;
    }

    public void show(OutputStream o,int mode)throws IOException {
    }
}
