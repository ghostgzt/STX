package bytecode;

import classsys.Classsys;
import classsys.OpcodesUtil;
import java.io.OutputStream;

public abstract class AbstractInstruction {

    public int offset;
    private int opcode;

    protected AbstractInstruction(int opcode) {
        this.opcode = opcode;
    }

    public int getSize() {
        return 1;
    }

    public int getOpcode() {
        return opcode;
    }

    public String getOpcodeVerbose() {
        return OpcodesUtil.getVerbose(opcode);
    }

    public void read(ByteCodeInput in) {
        offset = in.getBytesRead() - 1;
    }

    public void write(ByteCodeOutput out) {
        out.writeByte(opcode);
    }

    public abstract void show(OutputStream o);

    boolean ok(int k) {
        return k > 0 && (k < Classsys.cps.length) && Classsys.cps[k] != null;
    }
}
