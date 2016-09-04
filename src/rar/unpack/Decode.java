package rar.unpack;

public class Decode {

    int maxNum;
    protected int decodeNum[];
    final int decodeLen[] = new int[16], decodePos[] = new int[16];

    public Decode(int num) {
        decodeNum = new int[num];
    }

    public Decode() {
        this(2);
    }
}
