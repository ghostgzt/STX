
package bytecode;

import classsys.Classsys;
import classsys.OpcodesUtil;
import java.io.OutputStream;


public class ImmediateByteInstruction extends AbstractInstruction {

    protected boolean wide;
    private int immediateByte;

    public ImmediateByteInstruction(int opcode, boolean wide) {
        super(opcode);
        this.wide = wide;
    }

    public ImmediateByteInstruction(int opcode, boolean wide, int immediateByte) {
        this(opcode, wide);
        this.immediateByte = immediateByte;
    }

    
    public int getSize() {
        return super.getSize() + (wide ? 2 : 1);
    }

  
    public int getImmediateByte() {
        return immediateByte;
    }

    
    public void setImmediateByte(int immediateByte) {
        this.immediateByte = immediateByte;
    }

    public boolean isWide() {
        return wide;
    }

   
    public void setWide(boolean wide) {
        this.wide = wide;
    }

    
    public void read(ByteCodeInput in) {
        super.read(in);

        if (wide) {
            immediateByte = in.readUnsignedShort();
        } else {
            immediateByte = in.readUnsignedByte();
        }
    }

    
    public void write(ByteCodeOutput out)  {
        super.write(out);

        if (wide) {
            out.writeShort(immediateByte);
        } else {
            out.writeByte(immediateByte);
        }
    }

    public void show(OutputStream o) {
        try {
            if (getOpcode() !=  OpcodesUtil.LDC&&getOpcode() != OpcodesUtil.NEWARRAY) {
                o.write(("   " + offset + ":  " + getOpcodeVerbose() + "  " + immediateByte + "\r\n").getBytes());
            }// else if (!ok(immediateByte)) {
           //     System.out.println("ImmediateByteInstruction:" + immediateByte + "  when code= " + getOpcodeVerbose());
            else if(getOpcode()== OpcodesUtil.NEWARRAY){
            o.write(("   " + offset + ":  " + getOpcodeVerbose() + "  " + OpcodesUtil.getArrayTypeVerbose(immediateByte) + "\r\n").getBytes());
            }
            else {
                o.write(("   " + offset + ":  " + getOpcodeVerbose() + " #" + immediateByte + " //  " + Classsys.cps[immediateByte].toString() + "\r\n").getBytes("Utf-8"));
            }
        } catch (Exception ex) {
           
        }
    }
}
