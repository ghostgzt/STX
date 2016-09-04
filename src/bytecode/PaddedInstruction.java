
package bytecode;

public abstract class PaddedInstruction extends AbstractInstruction {

  
    public PaddedInstruction(int opcode) {
        super(opcode); 
    }

    public int getPaddedSize(int offset) {
        return getSize() + paddingBytes(offset + 1);
    }

    
    public void read(ByteCodeInput in) {
        super.read(in);
        
        int bytesToRead = paddingBytes(in.getBytesRead());
        for (int i = 0; i < bytesToRead; i++) {
            in.readByte();
        }
    }

    
    public void write(ByteCodeOutput out){
        super.write(out);
        
        int bytesToWrite = paddingBytes(out.getBytesWritten());
        for (int i = 0; i < bytesToWrite; i++) {
            out.writeByte(0);
        }
    }
    
    private int paddingBytes(int bytesCount) {
        
        int bytesToPad = 4 - bytesCount % 4;
        return (bytesToPad == 4) ? 0 : bytesToPad;
    }


}
