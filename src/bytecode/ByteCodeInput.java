package bytecode;

public class ByteCodeInput {

    byte[] codes;
    int readed;

    public ByteCodeInput(byte[] code) {
        codes = code;
        readed = 0;
    }

    public int getBytesRead() {
        return readed;
    }

    public int readUnsignedByte() {
        return codes[readed++] & 0xff;
    }

    public int readInt() {
        return ((codes[readed++] & 0xff) << 24) | ((codes[readed++] & 0xff) << 16) | ((codes[readed++] & 0xff) << 8) | (codes[readed++] & 0xff);
    }

    public int readbyte() {
        return codes[readed++];
    }

    public void readByte() {
        readed++;
    }

    public int readShort() {
        return ((codes[readed++]) << 8) | (codes[readed++] & 0xff);
    }

    public int readUnsignedShort() {
        return ((codes[readed++] & 0xFF) << 8) | (codes[readed++] & 0xFF);
    }

    
}
