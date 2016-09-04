package rar.vm;

import rar.unpack.RarCRC;
import rar.unpack.Raw;
import java.util.Vector;

public class RarVM extends BitInput {

    private static byte VM_CmdFlags[] = {
        6, 70, 70, 70, 41, 41, 69, 69, 9, 70,
        70, 70, 70, 41, 41, 41, 41, 41, 41, 1,
        1, 17, 16, 5, 70, 70, 70, 69, 0, 0,
        32, 64, 2, 2, 6, 6, 6, 102, 102, 0
    };
    private byte mem[];
    private int R[];
    private int flags, maxOpCount, codeSize, IP;

    public RarVM() {
        R = new int[8];
        maxOpCount = 0x17d7840;
        mem = null;
    }

    public void init() {
        if (mem == null) {
            mem = new byte[0x40004];
        }
    }

    private boolean isVMMem(byte mem[]) {
        return this.mem == mem;
    }

    private int getValue(boolean byteMode, byte mem[], int offset) {
        if (byteMode) {
            if (isVMMem(mem)) {
                return mem[offset];
            } else {
                return mem[offset] & 0xff;
            }
        }
        if (isVMMem(mem)) {
            return Raw.readIntLittleEndian(mem, offset);
        } else {
            return Raw.readIntBigEndian(mem, offset);
        }
    }

    private void setValue(boolean byteMode, byte mem[], int offset, int value) {
        if (byteMode) {
            if (isVMMem(mem)) {
                mem[offset] = (byte) value;
            } else {
                mem[offset] = (byte) (mem[offset] & 0 | (byte) (value & 0xff));
            }
        } else if (isVMMem(mem)) {
            mem[offset + 0] = (byte) value;
            mem[offset + 1] = (byte) (value >>> 8);
            mem[offset + 2] = (byte) (value >>> 16);
            mem[offset + 3] = (byte) (value >>> 24);
        } else {
            mem[offset + 3] = (byte) value;
            mem[offset + 2] = (byte) (value >>> 8);
            mem[offset + 1] = (byte) (value >>> 16);
            mem[offset + 0] = (byte) (value >>> 24);
        }
    }

    public void setLowEndianValue(byte mem[], int offset, int value) {
        Raw.writeIntLittleEndian(mem, offset, value);
    }

    public void setLowEndianValue(Vector mem, int offset, int value) {
        mem.setElementAt(new Byte((byte) (value & 0xff)), offset);
        mem.setElementAt(new Byte((byte) (value >>> 8 & 0xff)), offset + 1);
        mem.setElementAt(new Byte((byte) (value >>> 16 & 0xff)), offset + 2);
        mem.setElementAt(new Byte((byte) (value >>> 24 & 0xff)), offset + 3);
    }

    private int getOperand(VMPreparedOperand cmdOp) {
        int ret = 0;
        if (cmdOp.Type == 2) {
            int pos = cmdOp.offset + cmdOp.Base & 0x3ffff;
            ret = Raw.readIntLittleEndian(mem, pos);
        } else {
            int pos = cmdOp.offset;
            ret = Raw.readIntLittleEndian(mem, pos);
        }
        return ret;
    }

    public void execute(VMPreparedProgram prg) {
        int len = prg.getInitR().length;
        for (int i = 0; i < len; i++) {
            R[i] = prg.getInitR()[i];
        }

        long globalSize = Math.min(prg.getGlobalData().size(), 8192) & -1;
        if (globalSize != 0L) {
            for (int i = 0; i < globalSize; i++) {
                mem[0x3c000 + i] = ((Byte) prg.getGlobalData().elementAt(i)).byteValue();
            }

        }
        int staticSize = (int) (Math.min(prg.getStaticData().size(), 8192L - globalSize) & -1L);
        if (staticSize != 0) {
            for (int i = 0; i < staticSize; i++) {
                mem[0x3c000 + (int) globalSize + i] = ((Byte) prg.getStaticData().elementAt(i)).byteValue();
            }

        }
        R[7] = 0x40000;
        flags = 0;
        Vector preparedCode = prg.getAltCmd().size() != 0 ? prg.getAltCmd() : prg.getCmd();
        if (!ExecuteCode(preparedCode, prg.getCmdCount())) {
            ((VMPreparedCommand) preparedCode.elementAt(0)).setOpCode(22);
        }
        int newBlockPos = getValue(false, mem, 0x3c020) & 0x3ffff;
        int newBlockSize = getValue(false, mem, 0x3c01c) & 0x3ffff;
        if (newBlockPos + newBlockSize >= 0x40000) {
            newBlockPos = 0;
            newBlockSize = 0;
        }
        prg.setFilteredDataOffset(newBlockPos);
        prg.setFilteredDataSize(newBlockSize);
        prg.getGlobalData().removeAllElements();
        int dataSize = Math.min(getValue(false, mem, 0x3c030), 8128);
        if (dataSize != 0) {
            prg.getGlobalData().setSize(dataSize + 64);
            for (int i = 0; i < dataSize + 64; i++) {
                prg.getGlobalData().setElementAt(new Byte(mem[0x3c000 + i]), i);
            }

        }
    }

    public byte[] getMem() {
        return mem;
    }

    private boolean setIP(int ip) {
        if (ip >= codeSize) {
            return true;
        }
        if (--maxOpCount <= 0) {
            return false;
        } else {
            IP = ip;
            return true;
        }
    }

    private boolean ExecuteCode(Vector preparedCode, int cmdCount) {
        maxOpCount = 0x17d7840;
        codeSize = cmdCount;
        IP = 0;
        do {
            VMPreparedCommand cmd = (VMPreparedCommand) preparedCode.elementAt(IP);
            int op1 = getOperand(cmd.getOp1());
            int op2 = getOperand(cmd.getOp2());
            switch (cmd.getOpCode()) {
                case 39: // '\''
                default:
                    break;

                case 0: // '\0'
                {
                    setValue(cmd.isByteMode(), mem, op1, getValue(cmd.isByteMode(), mem, op2));
                    break;
                }

                case 40: // '('
                {
                    setValue(true, mem, op1, getValue(true, mem, op2));
                    break;
                }

                case 41: // ')'
                {
                    setValue(false, mem, op1, getValue(false, mem, op2));
                    break;
                }

                case 1: // '\001'
                {
                    int value1 = getValue(cmd.isByteMode(), mem, op1);
                    int result = value1 - getValue(cmd.isByteMode(), mem, op2);
                    if (result == 0) {
                        flags = 2;
                    } else {
                        flags = result > value1 ? 1 : 0 | result & 0x80000000;
                    }
                    break;
                }

                case 42: // '*'
                {
                    int value1 = getValue(true, mem, op1);
                    int result = value1 - getValue(true, mem, op2);
                    if (result == 0) {
                        flags = 2;
                    } else {
                        flags = result > value1 ? 1 : 0 | result & 0x80000000;
                    }
                    break;
                }

                case 43: // '+'
                {
                    int value1 = getValue(false, mem, op1);
                    int result = value1 - getValue(false, mem, op2);
                    if (result == 0) {
                        flags = 2;
                    } else {
                        flags = result > value1 ? 1 : 0 | result & 0x80000000;
                    }
                    break;
                }

                case 2: // '\002'
                {
                    int value1 = getValue(cmd.isByteMode(), mem, op1);
                    int result = (int) ((long) value1 + (long) getValue(cmd.isByteMode(), mem, op2) & -1L);
                    if (cmd.isByteMode()) {
                        result &= 0xff;
                        flags = result < value1 ? 1 : 0 | (result == 0 ? 2 : (result & 0x80) != 0 ? 0x80000000 : 0);
                    } else {
                        flags = result < value1 ? 1 : 0 | (result == 0 ? 2 : result & 0x80000000);
                    }
                    setValue(cmd.isByteMode(), mem, op1, result);
                    break;
                }

                case 44: // ','
                {
                    setValue(true, mem, op1, (int) ((long) getValue(true, mem, op1) & -1L + (long) getValue(true, mem, op2) & -1L));
                    break;
                }

                case 45: // '-'
                {
                    setValue(false, mem, op1, (int) ((long) getValue(false, mem, op1) & -1L + (long) getValue(false, mem, op2) & -1L));
                    break;
                }

                case 3: // '\003'
                {
                    int value1 = getValue(cmd.isByteMode(), mem, op1);
                    int result = (int) ((long) value1 & -1L - (long) getValue(cmd.isByteMode(), mem, op2) & -1L);
                    flags = result == 0 ? 2 : result > value1 ? 1 : 0 | result & 0x80000000;
                    setValue(cmd.isByteMode(), mem, op1, result);
                    break;
                }

                case 46: // '.'
                {
                    setValue(true, mem, op1, (int) ((long) getValue(true, mem, op1) & -1L - (long) getValue(true, mem, op2) & -1L));
                    break;
                }

                case 47: // '/'
                {
                    setValue(false, mem, op1, (int) ((long) getValue(false, mem, op1) & -1L - (long) getValue(false, mem, op2) & -1L));
                    break;
                }

                case 4: // '\004'
                {
                    if ((flags & 2) == 0) {
                        break;
                    }
                    setIP(getValue(false, mem, op1));
                    continue;
                }

                case 5: // '\005'
                {
                    if ((flags & 2) != 0) {
                        break;
                    }
                    setIP(getValue(false, mem, op1));
                    continue;
                }

                case 6: // '\006'
                {
                    int result = (int) ((long) getValue(cmd.isByteMode(), mem, op1) & 0L);
                    if (cmd.isByteMode()) {
                        result &= 0xff;
                    }
                    setValue(cmd.isByteMode(), mem, op1, result);
                    flags = result == 0 ? 2 : result & 0x80000000;
                    break;
                }

                case 48: // '0'
                {
                    setValue(true, mem, op1, (int) ((long) getValue(true, mem, op1) & 0L));
                    break;
                }

                case 49: // '1'
                {
                    setValue(false, mem, op1, (int) ((long) getValue(false, mem, op1) & 0L));
                    break;
                }

                case 7: // '\007'
                {
                    int result = (int) ((long) getValue(cmd.isByteMode(), mem, op1) & -2L);
                    setValue(cmd.isByteMode(), mem, op1, result);
                    flags = result == 0 ? 2 : result & 0x80000000;
                    break;
                }

                case 50: // '2'
                {
                    setValue(true, mem, op1, (int) ((long) getValue(true, mem, op1) & -2L));
                    break;
                }

                case 51: // '3'
                {
                    setValue(false, mem, op1, (int) ((long) getValue(false, mem, op1) & -2L));
                    break;
                }

                case 8: // '\b'
                {
                    setIP(getValue(false, mem, op1));
                    continue;
                }

                case 9: // '\t'
                {
                    int result = getValue(cmd.isByteMode(), mem, op1) ^ getValue(cmd.isByteMode(), mem, op2);
                    flags = result == 0 ? 2 : result & 0x80000000;
                    setValue(cmd.isByteMode(), mem, op1, result);
                    break;
                }

                case 10: // '\n'
                {
                    int result = getValue(cmd.isByteMode(), mem, op1) & getValue(cmd.isByteMode(), mem, op2);
                    flags = result == 0 ? 2 : result & 0x80000000;
                    setValue(cmd.isByteMode(), mem, op1, result);
                    break;
                }

                case 11: // '\013'
                {
                    int result = getValue(cmd.isByteMode(), mem, op1) | getValue(cmd.isByteMode(), mem, op2);
                    flags = result == 0 ? 2 : result & 0x80000000;
                    setValue(cmd.isByteMode(), mem, op1, result);
                    break;
                }

                case 12: // '\f'
                {
                    int result = getValue(cmd.isByteMode(), mem, op1) & getValue(cmd.isByteMode(), mem, op2);
                    flags = result == 0 ? 2 : result & 0x80000000;
                    break;
                }

                case 13: // '\r'
                {
                    if ((flags & 0x80000000) == 0) {
                        break;
                    }
                    setIP(getValue(false, mem, op1));
                    continue;
                }

                case 14: // '\016'
                {
                    if ((flags & 0x80000000) != 0) {
                        break;
                    }
                    setIP(getValue(false, mem, op1));
                    continue;
                }

                case 15: // '\017'
                {
                    if ((flags & 1) == 0) {
                        break;
                    }
                    setIP(getValue(false, mem, op1));
                    continue;
                }

                case 16: // '\020'
                {
                    if ((flags & (1 | 2)) == 0) {
                        break;
                    }
                    setIP(getValue(false, mem, op1));
                    continue;
                }

                case 17: // '\021'
                {
                    if ((flags & (1 | 2)) != 0) {
                        break;
                    }
                    setIP(getValue(false, mem, op1));
                    continue;
                }

                case 18: // '\022'
                {
                    if ((flags & 1) != 0) {
                        break;
                    }
                    setIP(getValue(false, mem, op1));
                    continue;
                }

                case 19: // '\023'
                {
                    R[7] -= 4;
                    setValue(false, mem, R[7] & 0x3ffff, getValue(false, mem, op1));
                    break;
                }

                case 20: // '\024'
                {
                    setValue(false, mem, op1, getValue(false, mem, R[7] & 0x3ffff));
                    R[7] += 4;
                    break;
                }

                case 21: // '\025'
                {
                    R[7] -= 4;
                    setValue(false, mem, R[7] & 0x3ffff, IP + 1);
                    setIP(getValue(false, mem, op1));
                    continue;
                }

                case 23: // '\027'
                {
                    setValue(cmd.isByteMode(), mem, op1, ~getValue(cmd.isByteMode(), mem, op1));
                    break;
                }

                case 24: // '\030'
                {
                    int value1 = getValue(cmd.isByteMode(), mem, op1);
                    int value2 = getValue(cmd.isByteMode(), mem, op2);
                    int result = value1 << value2;
                    flags = (result == 0 ? 2 : result & 0x80000000) | ((value1 << value2 - 1 & 0x80000000) != 0 ? 1 : 0);
                    setValue(cmd.isByteMode(), mem, op1, result);
                    break;
                }

                case 25: // '\031'
                {
                    int value1 = getValue(cmd.isByteMode(), mem, op1);
                    int value2 = getValue(cmd.isByteMode(), mem, op2);
                    int result = value1 >>> value2;
                    flags = (result == 0 ? 2 : result & 0x80000000) | value1 >>> value2 - 1 & 1;
                    setValue(cmd.isByteMode(), mem, op1, result);
                    break;
                }

                case 26: // '\032'
                {
                    int value1 = getValue(cmd.isByteMode(), mem, op1);
                    int value2 = getValue(cmd.isByteMode(), mem, op2);
                    int result = value1 >> value2;
                    flags = (result == 0 ? 2 : result & 0x80000000) | value1 >> value2 - 1 & 1;
                    setValue(cmd.isByteMode(), mem, op1, result);
                    break;
                }

                case 27: // '\033'
                {
                    int result = -getValue(cmd.isByteMode(), mem, op1);
                    flags = result == 0 ? 2 : 1 | result & 0x80000000;
                    setValue(cmd.isByteMode(), mem, op1, result);
                    break;
                }

                case 52: // '4'
                {
                    setValue(true, mem, op1, -getValue(true, mem, op1));
                    break;
                }

                case 53: // '5'
                {
                    setValue(false, mem, op1, -getValue(false, mem, op1));
                    break;
                }

                case 28: // '\034'
                {
                    int i = 0;
                    for (int SP = R[7] - 4; i < 8; SP -= 4) {
                        setValue(false, mem, SP & 0x3ffff, R[i]);
                        i++;
                    }

                    R[7] -= 32;
                    break;
                }

                case 29: // '\035'
                {
                    int i = 0;
                    for (int SP = R[7]; i < 8; SP += 4) {
                        R[7 - i] = getValue(false, mem, SP & 0x3ffff);
                        i++;
                    }

                    break;
                }

                case 30: // '\036'
                {
                    R[7] -= 4;
                    setValue(false, mem, R[7] & 0x3ffff, flags);
                    break;
                }

                case 31: // '\037'
                {
                    flags = getValue(false, mem, R[7] & 0x3ffff);
                    R[7] += 4;
                    break;
                }

                case 32: // ' '
                {
                    setValue(false, mem, op1, getValue(true, mem, op2));
                    break;
                }

                case 33: // '!'
                {
                    setValue(false, mem, op1, (byte) getValue(true, mem, op2));
                    break;
                }

                case 34: // '"'
                {
                    int value1 = getValue(cmd.isByteMode(), mem, op1);
                    setValue(cmd.isByteMode(), mem, op1, getValue(cmd.isByteMode(), mem, op2));
                    setValue(cmd.isByteMode(), mem, op2, value1);
                    break;
                }

                case 35: // '#'
                {
                    int result = (int) ((long) getValue(cmd.isByteMode(), mem, op1) & -1L * (long) getValue(cmd.isByteMode(), mem, op2) & -1L & -1L);
                    setValue(cmd.isByteMode(), mem, op1, result);
                    break;
                }

                case 36: // '$'
                {
                    int divider = getValue(cmd.isByteMode(), mem, op2);
                    if (divider != 0) {
                        int result = getValue(cmd.isByteMode(), mem, op1) / divider;
                        setValue(cmd.isByteMode(), mem, op1, result);
                    }
                    break;
                }

                case 37: // '%'
                {
                    int value1 = getValue(cmd.isByteMode(), mem, op1);
                    int FC = flags & 1;
                    int result = (int) ((long) value1 & -1L + (long) getValue(cmd.isByteMode(), mem, op2) & -1L + (long) FC & -1L);
                    if (cmd.isByteMode()) {
                        result &= 0xff;
                    }
                    flags = result < value1 || result == value1 && FC != 0 ? 1 : 0 | (result == 0 ? 2 : result & 0x80000000);
                    setValue(cmd.isByteMode(), mem, op1, result);
                    break;
                }

                case 38: // '&'
                {
                    int value1 = getValue(cmd.isByteMode(), mem, op1);
                    int FC = flags & 1;
                    int result = (int) ((long) value1 & -1L - (long) getValue(cmd.isByteMode(), mem, op2) & -1L - (long) FC & -1L);
                    if (cmd.isByteMode()) {
                        result &= 0xff;
                    }
                    flags = result > value1 || result == value1 && FC != 0 ? 1 : 0 | (result == 0 ? 2 : result & 0x80000000);
                    setValue(cmd.isByteMode(), mem, op1, result);
                    break;
                }

                case 22: // '\026'
                {
                    if (R[7] >= 0x40000) {
                        return true;
                    }
                    setIP(getValue(false, mem, R[7] & 0x3ffff));
                    R[7] += 4;
                    continue;
                }

                case 54: // '6'
                {
                    ExecuteStandardFilter(cmd.getOp1().Data);
                    break;
                }
            }
            IP++;
            maxOpCount--;
        } while (true);
    }

    public void prepare(byte code[], int codeSize, VMPreparedProgram prg) {
        InitBitInput();
        int cpLength = Math.min(32768, codeSize);
        for (int i = 0; i < cpLength; i++) {
            inBuf[i] |= code[i];
        }

        byte xorSum = 0;
        for (int i = 1; i < codeSize; i++) {
            xorSum ^= code[i];
        }

        faddbits(8);
        prg.setCmdCount(0);
        if (xorSum == code[0]) {
            int filterType = IsStandardFilter(code);
            if (filterType != 0) {
                VMPreparedCommand curCmd = new VMPreparedCommand();
                curCmd.setOpCode(54);
                curCmd.getOp1().Data = filterType;
                curCmd.getOp1().Type = 3;
                curCmd.getOp2().Type = 3;
                codeSize = 0;
                prg.getCmd().addElement(curCmd);
                prg.setCmdCount(prg.getCmdCount() + 1);
            }
            int dataFlag = getbits();
            faddbits(1);
            if ((dataFlag & 0x8000) != 0) {
                long dataSize = (long) ReadData(this) & 0L;
                for (int i = 0; inAddr < codeSize && i < dataSize; i++) {
                    prg.getStaticData().addElement(new Byte((byte) (getbits() >> 8)));
                    faddbits(8);
                }

            }
            while (inAddr < codeSize) {
                VMPreparedCommand curCmd = new VMPreparedCommand();
                int data = getbits();
                if ((data & 0x8000) == 0) {
                    curCmd.setOpCode(data >> 12);
                    faddbits(4);
                } else {
                    curCmd.setOpCode((data >> 10) - 24);
                    faddbits(6);
                }
                if ((VM_CmdFlags[curCmd.getOpCode()] & 4) != 0) {
                    curCmd.setByteMode(getbits() >> 15 == 1);
                    faddbits(1);
                } else {
                    curCmd.setByteMode(false);
                }
                curCmd.getOp1().Type = 3;
                curCmd.getOp2().Type = 3;
                int opNum = VM_CmdFlags[curCmd.getOpCode()] & 3;
                if (opNum > 0) {
                    decodeArg(curCmd.getOp1(), curCmd.isByteMode());
                    if (opNum == 2) {
                        decodeArg(curCmd.getOp2(), curCmd.isByteMode());
                    } else if (curCmd.getOp1().Type == 1 && (VM_CmdFlags[curCmd.getOpCode()] & 0x18) != 0) {
                        int distance = curCmd.getOp1().Data;
                        if (distance >= 256) {
                            distance -= 256;
                        } else {
                            if (distance >= 136) {
                                distance -= 264;
                            } else if (distance >= 16) {
                                distance -= 8;
                            } else if (distance >= 8) {
                                distance -= 16;
                            }
                            distance += prg.getCmdCount();
                        }
                        curCmd.getOp1().Data = distance;
                    }
                }
                prg.setCmdCount(prg.getCmdCount() + 1);
                prg.getCmd().addElement(curCmd);
            }
        }
        VMPreparedCommand curCmd = new VMPreparedCommand();
        curCmd.setOpCode(22);
        curCmd.getOp1().Type = 3;
        curCmd.getOp2().Type = 3;
        prg.getCmd().addElement(curCmd);
        prg.setCmdCount(prg.getCmdCount() + 1);
        if (codeSize != 0) {
            optimize(prg);
        }
    }

    private void decodeArg(VMPreparedOperand op, boolean byteMode) {
        int data = getbits();
        if ((data & 0x8000) != 0) {
            op.Type = 0;
            op.Data = data >> 12 & 7;
            op.offset = op.Data;
            faddbits(4);
        } else if ((data & 0xc000) == 0) {
            op.Type = 1;
            if (byteMode) {
                op.Data = data >> 6 & 0xff;
                faddbits(10);
            } else {
                faddbits(2);
                op.Data = ReadData(this);
            }
        } else {
            op.Type = 2;
            if ((data & 0x2000) == 0) {
                op.Data = data >> 10 & 7;
                op.offset = op.Data;
                op.Base = 0;
                faddbits(6);
            } else {
                if ((data & 0x1000) == 0) {
                    op.Data = data >> 9 & 7;
                    op.offset = op.Data;
                    faddbits(7);
                } else {
                    op.Data = 0;
                    faddbits(4);
                }
                op.Base = ReadData(this);
            }
        }
    }

    private void optimize(VMPreparedProgram prg) {
        Vector commands = prg.getCmd();
        int len = commands.size();
        for (int j = 0; j < len; j++) {
            VMPreparedCommand cmd = (VMPreparedCommand) commands.elementAt(j);
            switch (cmd.getOpCode()) {
                case 0: // '\0'
                    cmd.setOpCode(cmd.isByteMode() ? 40 : 41);
                    break;

                case 1: // '\001'
                    cmd.setOpCode(cmd.isByteMode() ? 42 : 43);
                    break;

                default:
                    if ((VM_CmdFlags[cmd.getOpCode()] & 0x40) == 0) {
                        break;
                    }
                    boolean flagsRequired = false;
                    int i = commands.indexOf(cmd) + 1;
                    do {
                        if (i >= commands.size()) {
                            break;
                        }
                        int flag = VM_CmdFlags[((VMPreparedCommand) commands.elementAt(i)).getOpCode()];
                        if ((flag & 0x38) != 0) {
                            flagsRequired = true;
                            break;
                        }
                        if ((flag & 0x40) != 0) {
                            break;
                        }
                        i++;
                    } while (true);
                    if (flagsRequired) {
                        break;
                    }
                    int op = 0;
                    switch (cmd.getOpCode()) {
                        case 2: // '\002'
                            op = 44;
                            break;
                        case 3: // '\003'
                            op = 46;
                            break;
                        case 6: // '\006'
                            op = 48;
                            break;
                        case 7: // '\007'
                            op = 50;
                            break;
                        case 27: // '\033'
                            op = 52;
                            break;
                    }
                    cmd.setOpCode(cmd.isByteMode() ? op : op + 1);
                    break;
            }
        }

    }

    public static int ReadData(BitInput rarVM) {
        int data = rarVM.getbits();
        switch (data & 0xc000) {
            case 0: // '\0'
                rarVM.faddbits(6);
                return data >> 10 & 0xf;

            case 16384:
                if ((data & 0x3c00) == 0) {
                    data = 0xffffff00 | data >> 2 & 0xff;
                    rarVM.faddbits(14);
                } else {
                    data = data >> 6 & 0xff;
                    rarVM.faddbits(10);
                }
                return data;

            case 32768:
                rarVM.faddbits(2);
                data = rarVM.getbits();
                rarVM.faddbits(16);
                return data;
        }
        rarVM.faddbits(2);
        data = rarVM.getbits() << 16;
        rarVM.faddbits(16);
        data |= rarVM.getbits();
        rarVM.faddbits(16);
        return data;
    }

    private int IsStandardFilter(byte code[]) {
        int stdList[][] = {
            {
                53, 0xad576887, 1
            }, {
                57, 0x3cd7e57e, 2
            }, {
                120, 0x3769893f, 3
            }, {
                29, 0xe06077d, 6
            }, {
                149, 0x1c2c5dc8, 4
            }, {
                216, 0xbc85e701, 5
            }, {
                40, 0x46b9c560, 7
            }
        };
        int CodeCRC = ~RarCRC.checkCrc(-1, code, 0, code.length);
        int len = stdList.length;
        for (int i = 0; i < len; i++) {
            if (stdList[i][1] == CodeCRC && stdList[i][0] == code.length) {
                return stdList[i][2];
            }
        }

        return 0;
    }

    private void ExecuteStandardFilter(int filterType) {
        label0:
        switch (filterType) {
            default:
                break;

            case 1: // '\001'
            case 2: // '\002'
            {
                int dataSize = R[4];
                long fileOffset = R[6] & -1;
                if (dataSize < 0x3c000) {
                    int fileSize = 0x1000000;
                    byte cmpByte2 = (byte) (filterType == 2 ? '\351' : 232);
                    int curPos = 0;
                    do {
                        if (curPos >= dataSize - 4) {
                            break;
                        }
                        byte curByte = mem[curPos++];
                        if (curByte == 232 || curByte == cmpByte2) {
                            long offset = (long) curPos + fileOffset;
                            long Addr = getValue(false, mem, curPos);
                            if ((Addr & 0xffffffff80000000L) != 0L) {
                                if ((Addr + offset & 0xffffffff80000000L) == 0L) {
                                    setValue(false, mem, curPos, (int) Addr + fileSize);
                                }
                            } else if ((Addr - (long) fileSize & 0xffffffff80000000L) != 0L) {
                                setValue(false, mem, curPos, (int) (Addr - offset));
                            }
                            curPos += 4;
                        }
                    } while (true);
                }
                break;
            }

            case 3: // '\003'
            {
                int dataSize = R[4];
                long fileOffset = R[6] & -1;
                if (dataSize >= 0x3c000) {
                    break;
                }
                int curPos = 0;
                byte Masks[] = {
                    4, 4, 6, 6, 0, 0, 7, 7, 4, 4,
                    0, 0, 4, 4, 0, 0
                };
                fileOffset >>>= 4;
                do {
                    if (curPos >= dataSize - 21) {
                        break label0;
                    }
                    int Byte = (mem[curPos] & 0x1f) - 16;
                    if (Byte >= 0) {
                        byte cmdMask = Masks[Byte];
                        if (cmdMask != 0) {
                            for (int i = 0; i <= 2; i++) {
                                if ((cmdMask & 1 << i) == 0) {
                                    continue;
                                }
                                int startPos = i * 41 + 5;
                                int opType = filterItanium_GetBits(curPos, startPos + 37, 4);
                                if (opType == 5) {
                                    int offset = filterItanium_GetBits(curPos, startPos + 13, 20);
                                    filterItanium_SetBits(curPos, (int) ((long) offset - fileOffset) & 0xfffff, startPos + 13, 20);
                                }
                            }

                        }
                    }
                    curPos += 16;
                    fileOffset++;
                } while (true);
            }

            case 6: // '\006'
            {
                int dataSize = R[4] & -1;
                int channels = R[0] & -1;
                int srcPos = 0;
                int border = dataSize * 2 & -1;
                setValue(false, mem, 0x3c020, dataSize);
                if (dataSize >= 0x1e000) {
                    break;
                }
                int curChannel = 0;
                do {
                    if (curChannel >= channels) {
                        break label0;
                    }
                    byte PrevByte = 0;
                    for (int destPos = dataSize + curChannel; destPos < border; destPos += channels) {
                        mem[destPos] = PrevByte -= mem[srcPos++];
                    }

                    curChannel++;
                } while (true);
            }

            case 4: // '\004'
            {
                int dataSize = R[4];
                int width = R[0] - 3;
                int posR = R[1];
                int channels = 3;
                int srcPos = 0;
                int destDataPos = dataSize;
                setValue(false, mem, 0x3c020, dataSize);
                if (dataSize >= 0x1e000 || posR < 0) {
                    break;
                }
                for (int curChannel = 0; curChannel < channels; curChannel++) {
                    long prevByte = 0L;
                    for (int i = curChannel; i < dataSize; i += channels) {
                        int upperPos = i - width;
                        long predicted;
                        if (upperPos >= 3) {
                            int upperDataPos = destDataPos + upperPos;
                            int upperByte = mem[upperDataPos] & 0xff;
                            int upperLeftByte = mem[upperDataPos - 3] & 0xff;
                            predicted = (prevByte + (long) upperByte) - (long) upperLeftByte;
                            int pa = Math.abs((int) (predicted - prevByte));
                            int pb = Math.abs((int) (predicted - (long) upperByte));
                            int pc = Math.abs((int) (predicted - (long) upperLeftByte));
                            if (pa <= pb && pa <= pc) {
                                predicted = prevByte;
                            } else if (pb <= pc) {
                                predicted = upperByte;
                            } else {
                                predicted = upperLeftByte;
                            }
                        } else {
                            predicted = prevByte;
                        }
                        prevByte = predicted - (long) mem[srcPos++] & 255L & 255L;
                        mem[destDataPos + i] = (byte) (int) (prevByte & 255L);
                    }

                }

                int i = posR;
                for (int border = dataSize - 2; i < border; i += 3) {
                    byte G = mem[destDataPos + i + 1];
                    mem[destDataPos + i] += G;
                    mem[destDataPos + i + 2] += G;
                }

                break;
            }

            case 5: // '\005'
            {
                int dataSize = R[4];
                int channels = R[0];
                int srcPos = 0;
                int destDataPos = dataSize;
                setValue(false, mem, 0x3c020, dataSize);
                if (dataSize >= 0x1e000) {
                    break;
                }
                int curChannel = 0;
                do {
                    if (curChannel >= channels) {
                        break label0;
                    }
                    long prevByte = 0L;
                    long prevDelta = 0L;
                    long Dif[] = new long[7];
                    int D1 = 0;
                    int D2 = 0;
                    int K1 = 0;
                    int K2 = 0;
                    int K3 = 0;
                    int i = curChannel;
                    for (int byteCount = 0; i < dataSize; byteCount++) {
                        int D3 = D2;
                        D2 = (int) prevDelta - D1;
                        D1 = (int) prevDelta;
                        long predicted = 8L * prevByte + (long) (K1 * D1) + (long) (K2 * D2) + (long) (K3 * D3);
                        predicted = predicted >>> 3 & 255L;
                        long curByte = mem[srcPos++] & 0xff;
                        predicted = predicted - curByte & -1L;
                        mem[destDataPos + i] = (byte) (int) predicted;
                        prevDelta = (byte) (int) (predicted - prevByte);
                        prevByte = predicted;
                        int D = (byte) (int) curByte << 3;
                        Dif[0] += Math.abs(D);
                        Dif[1] += Math.abs(D - D1);
                        Dif[2] += Math.abs(D + D1);
                        Dif[3] += Math.abs(D - D2);
                        Dif[4] += Math.abs(D + D2);
                        Dif[5] += Math.abs(D - D3);
                        Dif[6] += Math.abs(D + D3);
                        if ((byteCount & 0x1f) == 0) {
                            long minDif = Dif[0];
                            long numMinDif = 0L;
                            Dif[0] = 0L;
                            for (int j = 1; j < Dif.length; j++) {
                                if (Dif[j] < minDif) {
                                    minDif = Dif[j];
                                    numMinDif = j;
                                }
                                Dif[j] = 0L;
                            }

                            switch ((int) numMinDif) {
                                default:
                                    break;

                                case 1: // '\001'
                                    if (K1 >= -16) {
                                        K1--;
                                    }
                                    break;

                                case 2: // '\002'
                                    if (K1 < 16) {
                                        K1++;
                                    }
                                    break;

                                case 3: // '\003'
                                    if (K2 >= -16) {
                                        K2--;
                                    }
                                    break;

                                case 4: // '\004'
                                    if (K2 < 16) {
                                        K2++;
                                    }
                                    break;

                                case 5: // '\005'
                                    if (K3 >= -16) {
                                        K3--;
                                    }
                                    break;

                                case 6: // '\006'
                                    if (K3 < 16) {
                                        K3++;
                                    }
                                    break;
                            }
                        }
                        i += channels;
                    }

                    curChannel++;
                } while (true);
            }

            case 7: // '\007'
            {
                int dataSize = R[4];
                int srcPos = 0;
                int destPos = dataSize;
                if (dataSize >= 0x1e000) {
                    break;
                }
                while (srcPos < dataSize) {
                    byte curByte = mem[srcPos++];
                    if (curByte == 2 && (curByte = mem[srcPos++]) != 2) {
                        curByte -= 32;
                    }
                    mem[destPos++] = curByte;
                }
                setValue(false, mem, 0x3c01c, destPos - dataSize);
                setValue(false, mem, 0x3c020, dataSize);
                break;
            }
        }
    }

    private void filterItanium_SetBits(int curPos, int bitField, int bitPos, int bitCount) {
        int inaddr = bitPos / 8;
        int inbit = bitPos & 7;
        int andMask = -1 >>> 32 - bitCount;
        andMask = ~(andMask << inbit);
        bitField <<= inbit;
        for (int i = 0; i < 4; i++) {
            mem[curPos + inaddr + i] &= andMask;
            mem[curPos + inaddr + i] |= bitField;
            andMask = andMask >>> 8 | 0xff000000;
            bitField >>>= 8;
        }

    }

    private int filterItanium_GetBits(int curPos, int bitPos, int bitCount) {
        int inaddr = bitPos / 8;
        int inbit = bitPos & 7;
        int bitField = mem[curPos + inaddr++] & 0xff;
        bitField |= (mem[curPos + inaddr++] & 0xff) << 8;
        bitField |= (mem[curPos + inaddr++] & 0xff) << 16;
        bitField |= (mem[curPos + inaddr] & 0xff) << 24;
        bitField >>>= inbit;
        return bitField & -1 >>> 32 - bitCount;
    }

    public void setMemory(int pos, byte data[], int offset, int dataSize) {
        if (pos < 0x40000) {
            for (int i = 0; i < Math.min(data.length - offset, dataSize) && 0x40000 - pos >= i; i++) {
                mem[pos + i] = data[offset + i];
            }

        }
    }
}
