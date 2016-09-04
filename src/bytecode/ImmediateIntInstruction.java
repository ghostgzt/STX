

package bytecode;


import java.io.OutputStream;


public class ImmediateIntInstruction extends AbstractInstruction {

    private int immediateInt;
   
    public ImmediateIntInstruction(int opcode) {
        super(opcode); 
    }

   
    public ImmediateIntInstruction(int opcode, int immediateInt) {
        super(opcode); 
        this.immediateInt = immediateInt;
    }
    
    
    public int getSize() {
        return super.getSize() + 4;
    }

    public int getImmediateInt() {
        return immediateInt;
    }

    public void setImmediateInt(int immediateInt) {
        this.immediateInt = immediateInt;
    }
    
    
    public void read(ByteCodeInput in) {
        super.read(in);

        immediateInt = in.readInt();
    }

    
    public void write(ByteCodeOutput out){
        super.write(out);

        out.writeInt(immediateInt);
    }

    public void show(OutputStream o) {
       try {
            o.write((offset+": "+getOpcodeVerbose()+" goto "+(offset+immediateInt)+"\r\n\r\n").getBytes("Utf-8"));
        } catch (Exception ex) {
           
        }
    }
    
}
