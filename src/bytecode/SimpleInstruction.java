package bytecode;

import java.io.OutputStream;

public class SimpleInstruction extends AbstractInstruction {

    public SimpleInstruction(int opcode) {
        super(opcode);
    }

    public void show(OutputStream o) {
        try {
            o.write(("   " + offset + ":  " + getOpcodeVerbose() + "\r\n").getBytes());
        } catch (Exception ex) {
        }
    }
}
