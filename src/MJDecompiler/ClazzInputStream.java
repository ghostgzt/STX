package MJDecompiler;

import java.io.*;

public final class ClazzInputStream extends DataInputStream {

    public ClazzInputStream(InputStream inputstream) {
        super(inputstream);
    }

    public final int readUByte()throws IOException {
        int i;
        if ((i = readByte()) < 0) {
            i += 256;
        }
        return i;
    }

    public final int readUShort()throws IOException {
        int i;
        if ((i = readShort()) < 0) {
            i += 0x10000;
        }
        return i;
    }
}
