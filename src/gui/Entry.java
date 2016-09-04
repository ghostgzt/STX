package gui;

public abstract class Entry {

    public int dostime, size, compressedSize, offset, crc;
    public short method;
    public String name;
    public int flag;

    public abstract boolean isDirectory();
}
