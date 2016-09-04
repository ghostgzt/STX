package MJDecompiler;

import java.io.IOException;
import java.io.OutputStream;

public class Pstream {

    OutputStream out;

    Pstream(OutputStream o) {
        out = o;
    }

    void println(String s) {
        try {
            out.write(s.getBytes());
            println();
        } catch (IOException ex) {
        }
    }

    void println() {
        try {
            out.write("\r\n".getBytes());
        } catch (IOException ex) {
        }
    }

    void print(String s) {
        try {
            out.write(s.getBytes());
        } catch (IOException ex) {
        }
    }

    void close() {
        try {
            out.close();
        } catch (IOException ex) {
           
        }
    }

    void print(Object o) {
        try {
            out.write(o.toString().getBytes());
        } catch (IOException ex) {
        }
    }
}
