package rar.unpack;

import java.io.*;

public class ComprDataIO {

    private int unpPackedSize;
    public InputStream inputStream;
    private OutputStream outputStream;
    private FileHeader subHead;
    private boolean oldFormat;
    private int unpFileCRC, packedCRC;

    public void init(OutputStream outputStream, FileHeader hd, boolean old, InputStream in) {
        this.outputStream = outputStream;
        unpPackedSize = 0;
        unpFileCRC = old ? 0 : -1;
        unpPackedSize = hd.compressedSize;
        inputStream = in;
        subHead = hd;
        packedCRC = -1;
        oldFormat = old;
    }

    public int unpRead(byte addr[], int offset, int count) throws IOException {

        if (unpPackedSize == 0 || count <= 0) {
            return 0;
        }
        int readSize = Math.min(count, unpPackedSize);
        int retCode = inputStream.read(addr, offset, readSize);
        if ((subHead.flag & 2) != 0) {
            packedCRC = RarCRC.checkCrc(packedCRC, addr, offset, retCode);
        }
        unpPackedSize -= retCode;
        if (unpPackedSize == 0 && (subHead.flag & 2) != 0) {
            return -1;
        }

        return retCode;
    }

    public void unpWrite(byte addr[], int offset, int count) throws IOException {
        if (outputStream != null) {
            outputStream.write(addr, offset, count);
        }
        if (oldFormat) {
            unpFileCRC = RarCRC.checkOldCrc((short) (int) unpFileCRC, addr, count);
        } else {
            unpFileCRC = RarCRC.checkCrc((int) unpFileCRC, addr, offset, count);
        }

    }

    public int getPackedCRC() {
        return packedCRC;
    }

    public int getUnpFileCRC() {
        return unpFileCRC;
    }
}
