package rar.ppm;

public class Pointer {

    protected byte mem[];
    protected int pos;

    public Pointer(byte mem[]) {
        this.mem = mem;
    }

    public int getAddress() {
        return pos;
    }

    public void setAddress(int pos) {
        this.pos = pos;
    }
}
