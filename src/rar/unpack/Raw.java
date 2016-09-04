package rar.unpack;

public class Raw {

    public static final int readIntBigEndian(byte array[], int pos) {
        return ((array[pos] & 0xff) << 24) | ((array[pos] & 0xff) << 16) | ((array[pos] & 0xff) << 8) | (array[pos] & 0xff);

    }

    public static final short readShortLittleEndian(byte array[], int pos) {
        short result = 0;
        result += array[pos + 1] & 0xff;
        result <<= 8;
        result += array[pos] & 0xff;
        return result;
    }

    public static final int readIntLittleEndian(byte array[], int pos) {
        return (array[pos + 3] & 0xff) << 24 | (array[pos + 2] & 0xff) << 16 | (array[pos + 1] & 0xff) << 8 | array[pos] & 0xff;
    }

    public static final void writeShortLittleEndian(byte array[], int pos, short value) {
        array[pos + 1] = (byte) (value >>> 8);
        array[pos] = (byte) (value & 0xff);
    }

    public static final void writeIntLittleEndian(byte array[], int pos, int value) {
        array[pos + 3] = (byte) (value >>> 24);
        array[pos + 2] = (byte) (value >>> 16);
        array[pos + 1] = (byte) (value >>> 8);
        array[pos] = (byte) (value & 0xff);
    }
}
