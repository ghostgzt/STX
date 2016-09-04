package rar.unpack;

import gui.Entry;

public class FileHeader extends Entry {

    public byte unpVersion;
    public short headerSize;

    
    /*
    public int[] getTime() {
    int[] time = new int[6];
    time[5] = 2 * (dostime & 0x1f);
    time[4] = (dostime >> 5) & 0x3f;
    time[3] = (dostime >> 11) & 0x1f;
    time[2] = (dostime >> 16) & 0x1f;
    time[1] = (dostime >> 21) & 0xf;
    time[0] = (dostime >> 25) + 1980;
    return time;
    }
     */

    public String toString() {
        return name;
    }

    public boolean isSolid() {
        return (flag & 0x10) != 0;
    }

    public boolean isDirectory() {
        return (flag & 0xe0) == 224;
    }
}
