package bytecode;

import java.io.OutputStream;

public class TableSwitchInstruction extends PaddedInstruction {

    public int defaultOffset;
    public int lowByte, highByte;
    public int[] jumpOffsets;

    public TableSwitchInstruction(int opcode) {
        super(opcode);
    }

    
    public int getSize() {
        return super.getSize() + 12 + 4 * jumpOffsets.length;
    }

    
    public void read(ByteCodeInput in) {
        super.read(in);
        defaultOffset = in.readInt();
        lowByte = in.readInt();
        highByte = in.readInt();
        int numberOfOffsets = highByte - lowByte + 1;
        jumpOffsets = new int[numberOfOffsets];
        for (int i = 0; i < numberOfOffsets; i++) {
            jumpOffsets[i] = in.readInt();
        }

    }

    
    public void write(ByteCodeOutput out) {
        super.write(out);
        out.writeInt(defaultOffset);
        out.writeInt(lowByte);
        out.writeInt(highByte);
        int numberOfOffsets = jumpOffsets.length;
        for (int i = 0; i < numberOfOffsets; i++) {
            out.writeInt(jumpOffsets[i]);
        }
    }

    public void show(OutputStream o) {
        try {
            o.write(("   " + offset + ": " + getOpcodeVerbose() + "\r\n").getBytes());
            o.write((" default: " + (defaultOffset + offset) + "\r\n").getBytes());
            o.write((" caseNum: " + jumpOffsets.length + "\r\n").getBytes());
            for (int i = lowByte; i <= highByte; i++) {
                o.write(("  case  " + i + ": goto " + (jumpOffsets[i - lowByte] + offset) + "\r\n").getBytes());
            }
        } catch (Exception ex) {
        }
    }
}
