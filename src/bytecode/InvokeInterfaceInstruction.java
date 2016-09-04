package bytecode;

public class InvokeInterfaceInstruction extends ImmediateShortInstruction {

    private int count;

    public InvokeInterfaceInstruction(int opcode) {
        super(opcode);
    }

    public InvokeInterfaceInstruction(int opcode, int immediateShort, int count) {
        super(opcode, immediateShort);
        this.count = count;
    }

    
    public int getSize() {
        return super.getSize() + 2;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    
    public void read(ByteCodeInput in) {
        super.read(in);
        count = in.readUnsignedByte();
        in.readByte();
    }

    
    public void write(ByteCodeOutput out) {
        super.write(out);
        out.writeByte(count);
        out.writeByte(0);
    }
}
