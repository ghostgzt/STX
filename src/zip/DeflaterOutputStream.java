package zip;

import io.BufDataOutputStream;
import java.io.OutputStream;
import java.io.IOException;

public class DeflaterOutputStream extends OutputStream {

    protected byte buf[];
    private int size;
    protected Deflater def;
    protected BufDataOutputStream out;

    protected void deflate() throws IOException {
        int len;
        while (!def.needsInput() && (len = def.deflate(buf, 0, size)) > 0) {
            out.write(buf, 0, len);
        }
    }

    public DeflaterOutputStream(BufDataOutputStream out, Deflater defl, int bufsize) {
        this.out = out;
        buf = new byte[bufsize];
        size = bufsize;
        def = defl;
    }

    public void flush() throws IOException {
        def.flush();
        deflate();
     }

    public void finish() throws Exception {
        def.finish();
        int len;
        while (!def.finished() && (len = def.deflate(buf, 0, size)) > 0) {
            out.write(buf, 0, len);
        }
     }

    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public void close() {
        try {
            finish();
            out.close();
        } catch (Exception ex) {
        }
    }

    public void write(byte buf[], int off, int len) throws IOException {
        def.setInput(buf, off, len);
        deflate();
    }

    public void write(int b) throws IOException {
        write(new byte[]{(byte) b}, 0, 1);
    }
}
