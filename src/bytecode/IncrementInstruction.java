package bytecode;

import java.io.OutputStream;

public class IncrementInstruction extends ImmediateByteInstruction {

    private int incrementConst;

    public IncrementInstruction(int opcode, boolean wide) {
        super(opcode, wide);
    }

    public IncrementInstruction(int opcode, boolean wide, int immediateByte, int incrementConst) {
        super(opcode, wide, immediateByte);
        this.incrementConst = incrementConst;
    }

    public int getSize() {
        return super.getSize() + (wide ? 2 : 1);
    }

    public int getIncrementConst() {
        return incrementConst;
    }

    public void setIncrementConst(int incrementConst) {
        this.incrementConst = incrementConst;
    }

    public void read(ByteCodeInput in) {
        super.read(in);

        if (wide) {
            incrementConst = in.readShort();
        } else {
            incrementConst = in.readbyte();
        }
    }

    public void write(ByteCodeOutput out) {
        super.write(out);
        if (wide) {
            out.writeShort(incrementConst);
        } else {
            out.writeByte(incrementConst);
        }
    }

    public void show(OutputStream o) {
        try {
            o.write(("   " + offset + ":  " + getOpcodeVerbose() + " local "+getImmediateByte()+"iinc  by " + incrementConst + "\r\n").getBytes("Utf-8"));
        } catch (Exception ex) {
        }
    }
}
