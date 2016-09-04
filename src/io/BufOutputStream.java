package io;

import java.io.OutputStream;

public class BufOutputStream extends OutputStream {

    protected byte[] buf;
    protected int offset;
    protected int bufsize;

    public BufOutputStream() {
        this(1024);
    }

    public byte[] getbuf() {
        return buf;
    }

    public int size() {
        return offset;
    }

    public void reset(int newsize) {
        offset = 0;
        bufsize = newsize;
        buf = new byte[newsize];
    }

    public void close() {
        buf = null;
        System.gc();
    }

    public BufOutputStream(int size) {
        buf = new byte[size];
        offset = 0;
        bufsize = size;
    }

    public void flush() {
    }

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) {
        if (bufsize <= offset + len) {
            add(len + bufsize / 2);
        }
        System.arraycopy(b, off, buf, offset, len);
        offset += len;

    }

    private void add(int size) {
        byte[] temp = new byte[bufsize + size];
        System.arraycopy(buf, 0, temp, 0, offset);
        buf = temp;
        bufsize += size;
        System.gc();

    }

    public void write(int b) {
         if (bufsize <= offset) {
            add(1024 + bufsize / 2);
        }
        buf[offset++] = (byte) b;
    }
}
