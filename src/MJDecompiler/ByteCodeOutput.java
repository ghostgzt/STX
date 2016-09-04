package MJDecompiler;

import java.io.*;

public final class ByteCodeOutput extends DataOutputStream {

    public ByteCodeOutput(OutputStream outputstream) {
        super(outputstream);
    }

    public final void writeUbyte(int i)throws IOException {
        if (i > 127) {
            i -= 256;
        }
        writeByte(i);
    }

    public final void writeUshort(int i)throws IOException {
        if (i > 32767) {
            i -= 0x10000;
        }
        writeShort(i);
    }
}
