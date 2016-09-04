package rar.vm;

public class BitInput {
    protected int inAddr,inBit;
    protected byte inBuf[];

    public void InitBitInput() {
        inAddr =inBit = 0;
    }

    public void addbits(int Bits) {
        Bits += inBit;
        inAddr += Bits >> 3;
        inBit = Bits & 7;
    }

    public int getbits() {
        return ((inBuf[inAddr] & 0xff) << 16) + ((inBuf[inAddr + 1] & 0xff) << 8) + (inBuf[inAddr + 2] & 0xff) >>> 8 - inBit & 0xffff;
    }

    public BitInput() {
        inBuf = new byte[32768];
    }

    public void faddbits(int Bits) {
        addbits(Bits);
    }

    public boolean Overflow(int IncPtr) {
        return inAddr + IncPtr >= 32768;
    }

    public byte[] getInBuf() {
        return inBuf;
    }
}
