package rar.ppm;

import rar.unpack.Raw;





public class State extends Pointer {

    public State(byte mem[]) {
        super(mem);
    }

    public State init(byte mem[]) {
        this.mem = mem;
        pos = 0;
        return this;
    }

    public int getSymbol() {
        return mem[pos] & 0xff;
    }

    public void setSymbol(int symbol) {
        mem[pos] = (byte) symbol;
    }

    public int getFreq() {
        return mem[pos + 1] & 0xff;
    }

    public void setFreq(int freq) {
        mem[pos + 1] = (byte) freq;
    }

    public void incFreq(int dFreq) {
        mem[pos + 1] += dFreq;
    }

    public int getSuccessor() {
        return Raw.readIntLittleEndian(mem, pos + 2);
    }

    public void setSuccessor(PPMContext successor) {
        setSuccessor(successor.getAddress());
    }

    public void setSuccessor(int successor) {
        Raw.writeIntLittleEndian(mem, pos + 2, successor);
    }

    public void setValues(StateRef state) {
        setSymbol(state.getSymbol());
        setFreq(state.getFreq());
        setSuccessor(state.getSuccessor());
    }

    public void setValues(State ptr) {
        System.arraycopy(ptr.mem, ptr.pos, mem, pos, 6);
    }

    public State decAddress() {
        setAddress(pos - 6);
        return this;
    }

    public State incAddress() {
        setAddress(pos + 6);
        return this;
    }

    public static void ppmdSwap(State ptr1, State ptr2) {
        byte mem1[] = ptr1.mem;
        byte mem2[] = ptr2.mem;
        int i = 0;
        int pos1 = ptr1.pos;
        for (int pos2 = ptr2.pos; i < 6; pos2++) {
            byte temp = mem1[pos1];
            mem1[pos1] = mem2[pos2];
            mem2[pos2] = temp;
            i++;
            pos1++;
        }

    }
}
