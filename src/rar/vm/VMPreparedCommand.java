package rar.vm;

public class VMPreparedCommand {

    private int OpCode;
    private boolean ByteMode;
    private VMPreparedOperand Op1, Op2;

    public VMPreparedCommand() {
        Op1 = new VMPreparedOperand();
        Op2 = new VMPreparedOperand();
    }

    public boolean isByteMode() {
        return ByteMode;
    }

    public void setByteMode(boolean byteMode) {
        ByteMode = byteMode;
    }

    public VMPreparedOperand getOp1() {
        return Op1;
    }

    public VMPreparedOperand getOp2() {
        return Op2;
    }

    public int getOpCode() {
        return OpCode;
    }

    public void setOpCode(int opCode) {
        OpCode = opCode;
    }
}
