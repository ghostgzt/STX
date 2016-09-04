package bytecode;

import java.io.OutputStream;

public class LookupSwitchInstruction extends PaddedInstruction {

    public int defaultOffset;
    public int matchs[], offsets[];

    public LookupSwitchInstruction(int opcode) {
        super(opcode);
    }

    public int getSize() {
        return super.getSize() + 8 + 8 * matchs.length;
    }

    public int getDefaultOffset() {
        return defaultOffset;
    }

    public void setDefaultOffset(int defaultOffset) {
        this.defaultOffset = defaultOffset;
    }

    public void read(ByteCodeInput in) {
        super.read(in);
        defaultOffset = in.readInt();
        int numberOfPairs = in.readInt();
        matchs = new int[numberOfPairs];
        offsets = new int[numberOfPairs];
        for (int i = 0; i < numberOfPairs; i++) {
            matchs[i] = in.readInt();
            offsets[i] = in.readInt();
        }
    }

    public void write(ByteCodeOutput out) {
        super.write(out);

        out.writeInt(defaultOffset);

        int numberOfPairs = matchs.length;
        out.writeInt(numberOfPairs);
        for (int i = 0; i < numberOfPairs; i++) {
            out.writeInt(matchs[i]);
            out.writeInt(offsets[i]);
        }
    }

    public void show(OutputStream o) {
        try {
            o.write(("   " + offset + ": " + getOpcodeVerbose() + "\r\n").getBytes());
            o.write((" default: " + (defaultOffset + offset) + "\r\n").getBytes());
            o.write((" caseNum: " + matchs.length + "\r\n").getBytes());
            for (int i = 0; i < matchs.length; i++) {
                o.write(("  case  " + matchs[i] + ":  goto " + (offsets[i] + offset) + "\r\n").getBytes());
            }
        } catch (Exception ex) {
        }
    }
}
