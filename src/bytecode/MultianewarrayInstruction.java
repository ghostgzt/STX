package bytecode;

import classsys.Classsys;
import java.io.OutputStream;

public class MultianewarrayInstruction extends ImmediateShortInstruction {

    private int dimensions;

    public MultianewarrayInstruction(int opcode) {
        super(opcode);
    }

    
    public int getSize() {
        return super.getSize() + 1;
    }

    public int getDimensions() {
        return dimensions;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }

    
    public void read(ByteCodeInput in) {
        super.read(in);

        dimensions = in.readUnsignedByte();
    }

    
    public void write(ByteCodeOutput out) {
        super.write(out);

        out.writeByte(dimensions);
    }

    
    public void show(OutputStream o) {
        try {
            if (!ok(dimensions)) {
                System.out.println("MultianewarrayInstruction:" + dimensions + "  when code= " + getOpcodeVerbose());
            } else {
                o.write(("   " + offset + ":  " + getOpcodeVerbose() + " #" + dimensions + "   //  " + Classsys.cps[dimensions].toString() + "\r\n").getBytes("Utf-8"));
            }
        } catch (Exception ex) {
        }
    }
}
