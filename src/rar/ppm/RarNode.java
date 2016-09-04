package rar.ppm;

import rar.unpack.Raw;



public class RarNode extends Pointer {

    private int next;
    public static final int size = 4;

    public RarNode(byte mem[]) {
        super(mem);
    }

    public int getNext() {
        if (mem != null) {
            next = Raw.readIntLittleEndian(mem, pos);
        }
        return next;
    }

    public void setNext(RarNode next) {
        setNext(next.getAddress());
    }

    public void setNext(int next) {
        this.next = next;
        if (mem != null) {
            Raw.writeIntLittleEndian(mem, pos, next);
        }
    }
}
