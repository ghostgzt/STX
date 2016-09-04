package bytecode;

import java.io.OutputStream;

public class BranchInstruction extends AbstractInstruction {

    private int branchOffset;

    public BranchInstruction(int opcode) {
        super(opcode);
    }

    public BranchInstruction(int opcode, int branchOffset) {
        super(opcode);
        this.branchOffset = branchOffset;
    }

    
    public int getSize() {
        return super.getSize() + 2;
    }

    public int getBranchOffset() {
        return branchOffset;
    }

    public void setBranchOffset(int branchOffset) {
        this.branchOffset = branchOffset;
    }

    
    public void read(ByteCodeInput in) {
        super.read(in);

        branchOffset = in.readShort();
    }

    
    public void write(ByteCodeOutput out) {
        super.write(out);

        out.writeShort(branchOffset);
    }

    public void show(OutputStream o) {
        try {
            o.write(("   " + offset + ":  " + getOpcodeVerbose() + " goto " + (offset + branchOffset) + "\r\n\r\n").getBytes());
        } catch (Exception ex) {
        }
    }
}
