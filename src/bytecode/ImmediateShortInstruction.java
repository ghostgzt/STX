package bytecode;

import classsys.Classsys;
import classsys.OpcodesUtil;
import java.io.OutputStream;

public class ImmediateShortInstruction extends AbstractInstruction {

    private int immediateShort;

    
    public int getSize() {
        return super.getSize() + 2;
    }

    public ImmediateShortInstruction(int opcode) {
        super(opcode);
    }

    public ImmediateShortInstruction(int opcode, int immediateShort) {
        super(opcode);
        this.immediateShort = immediateShort;
    }

    public int getImmediateShort() {
        return immediateShort;
    }

    public void setImmediateShort(int immediateShort) {
        this.immediateShort = immediateShort;
    }

    
    public void read(ByteCodeInput in) {
        super.read(in);

        immediateShort = in.readUnsignedShort();
    }

    
    public void write(ByteCodeOutput out) {
        super.write(out);

        out.writeShort(immediateShort);
    }

    public void show(OutputStream o) {
        try {
            if (getOpcode() == OpcodesUtil.SIPUSH) {
                o.write(("   " + offset + ": " + getOpcodeVerbose() + "  " + immediateShort + "\r\n").getBytes());
            } //else if (!ok(immediateShort)) {
            //   System.out.println("ImmediateShortInstruction:" + immediateShort + "  when code= " + getOpcodeVerbose());
            // }
            else {
                o.write(("   " + offset + ":  " + getOpcodeVerbose() + " #" + immediateShort + "   //  " + Classsys.cps[immediateShort].toString() + "\r\n").getBytes("Utf-8"));
            }
        } catch (Exception ex) {
        }
    }
}
