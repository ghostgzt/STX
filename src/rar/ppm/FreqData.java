package rar.ppm;

import rar.unpack.Raw;



public class FreqData extends Pointer {

    public static final int size = 6;

    public FreqData(byte mem[]) {
        super(mem);
    }

    public FreqData init(byte mem[]) {
        this.mem = mem;
        pos = 0;
        return this;
    }

    public int getSummFreq() {
        return Raw.readShortLittleEndian(mem, pos) & 0xffff;
    }

    public void setSummFreq(int summFreq) {
        Raw.writeShortLittleEndian(mem, pos, (short) summFreq);
    }

    public void incSummFreq(int dSummFreq) {
        int c = (mem[pos] & 0xff) + (dSummFreq & 0xff) >>> 8;
        mem[pos] += dSummFreq & 0xff;
        if (c > 0 || (dSummFreq & 0xff00) != 0) {
            mem[pos + 1] += (dSummFreq >>> 8 & 0xff) + c;
        }
    }

    public int getStats() {
        return Raw.readIntLittleEndian(mem, pos + 2);
    }

    public void setStats(State state) {
        setStats(state.getAddress());
    }

    public void setStats(int state) {
        Raw.writeIntLittleEndian(mem, pos + 2, state);
    }
}
