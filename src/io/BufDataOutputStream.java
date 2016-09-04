package io;

import java.io.IOException;
import java.io.OutputStream;

public class BufDataOutputStream extends OutputStream {

    OutputStream out;
    byte[] bytes;
    private int bufoff;
    private int offset;

    public BufDataOutputStream(OutputStream output) {
        out = output;
        bytes = new byte[MemorySet.OUTBUFSIZE];
        bufoff = 0;
        offset = 0;
    }

    public byte[] getBuf() {
        return bytes;
    }

    public boolean resetTo(int off) {
        int l = offset - off;
        if (l <= bufoff) {
            bufoff -= l;
            offset = off;
            return true;
        }
        return false;
    }

    public int getoffse() {
        return offset;
    }

    public void skipto(int off) {
        bufoff += off - offset;
        offset = off;
    }

    public void flush() throws IOException {
       if (bufoff > 0) {
            out.write(bytes, 0, bufoff);
            bufoff = 0;
        }
    }

    public void close() throws IOException {
        flush();
        bytes = null;
        out.close();
    }

    public void write(byte[] addr, int off, int len) throws IOException {

        if (len < bytes.length - bufoff) {
            System.arraycopy(addr, off, bytes, bufoff, len);
            bufoff += len;
        } else {
            flush();
            out.write(addr, off, len);
        }
        offset += len;
    }

    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public void writeInt(int l) throws IOException {
        if (bytes.length < bufoff + 4) {
            flush();
        }
        offset += 4;
        bytes[bufoff++] = (byte) (l >> 24);
        bytes[bufoff++] = (byte) ((l & 0xff0000) >> 16);
        bytes[bufoff++] = (byte) ((l & 0xff00) >> 8);
        bytes[bufoff++] = (byte) (l & 0xff);
    }

    public void writeLshort(int l) throws IOException {
        if (bytes.length < bufoff + 2) {
            flush();
        }
        offset += 2;
        bytes[bufoff++] = (byte) (l & 0xff);
        bytes[bufoff++] = (byte) ((l & 0xff00) >> 8);
    }

    public void writeLint(int l) throws IOException {
        if (bytes.length < bufoff + 4) {
            flush();
        }
        offset += 4;
        bytes[bufoff++] = (byte) (l & 0xff);
        bytes[bufoff++] = (byte) ((l & 0xff00) >> 8);
        bytes[bufoff++] = (byte) ((l & 0xff0000) >> 16);
        bytes[bufoff++] = (byte) (l >> 24);
    }

    public void writeUnsignedByte(int l) throws IOException {
        if (bytes.length < bufoff + 1) {
            flush();
        }
        offset++;
        bytes[bufoff++] = (byte) (l & 0xff);
    }

    public void write(int b) throws IOException {
        if (bytes.length < bufoff + 1) {
            flush();
        }
        offset++;
        bytes[bufoff++] = (byte) b;
    }
}
