package MJDecompiler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public final class Decompiler {

    public static boolean debug;
    public static boolean showInfo;
    ClassFile clazz;
    String url;
    boolean trace;

    public Decompiler(String s, boolean debug, boolean show) {
        url = s;
        Decompiler.debug = debug;
        Decompiler.showInfo = show;
    }

    public void decompile(byte data[], boolean asm, OutputStream out) {
        if (!a(url, new ByteArrayInputStream(data))) {
            return;
        }
        decompile(asm, out);
        close();
    }

    public final boolean a(String s, InputStream inputstream) {
        try {
            ClazzInputStream in = new ClazzInputStream(inputstream);
            clazz = new ClassFile(in, s);
            in.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public final boolean decompile(boolean flag, OutputStream outputstream) {
        try {
            if (flag) {
                clazz.decompileMethod();
            }
            clazz.writeSource(outputstream);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public final void close() {
        clazz = null;
        debug = false;
        showInfo = false;
    }
}
