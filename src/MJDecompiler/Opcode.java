package MJDecompiler;

import java.io.IOException;

abstract class Opcode implements Operation {

    public abstract fo a(fo fo1);
    protected int offset;
    protected int size;
    protected int end;
    protected int index;
    protected fo e;

    Opcode(int off, int len) {
        offset = off;
        size = len;
        end = off + len;
        index = 0;
    }

    public void init(ClassFile bj, MethodDescriptor ar) {
    }

    public int size() {
        return size;
    }

    public final int index() {
        return offset;
    }

    public final int l() {
        return offset;
    }

    public final int a() {
        return (offset + size) - 1;
    }

    public final void a(bz bz) {
    }

    public final void addIndex() {
        index++;
    }

    public final void minusIndex() {
        index--;
    }

    public final int getIndex() {
        return index;
    }

    public int m() {
        return 1;
    }

    public int b(int i1) {
        return end;
    }

    public void a(int i1, int j1) {
        end = j1;
    }

    public void a(fo fo1, CodeInfo ah1) {
        if (e == null) {
            e = fo1;
            fo fo2 = a(fo1);
            for (int i1 = 0; i1 < m(); i1++) {
                ah1.getOperation(b(i1)).a(fo2, ah1);
            }
        }
    }

    public final void c() {
    }

    public final fo n() {
        return e;
    }

    public int e() {
        return 0;
    }

    public int f() {
        return 0;
    }

    public final int p() {
        return 1;
    }

    public final void a(Pstream printstream, int i1, boolean flag) {
        if (Decompiler.debug) {
            for (int j1 = 0; j1 < i1 - 2; j1++) {
                printstream.print("    ");
            }
            printstream.println(l() + "-" + a() + ":" + getIndex() + ">");
        }
        for (int k1 = 0; k1 < i1; k1++) {
            printstream.print("    ");
        }
        a(printstream);
    }

    abstract void a(Pstream printstream);

    public void q() {
    }

    static final Operation readNext(ClazzInputStream in, int off) throws IOException, by {
        switch (in.readUByte()) {
            case 0:
                return new Nop(off, 1);
            case 1:
                return new Number(off, 1, "A", "null");
            case 2:
                return new Number(off, 1, "B", "-1");
            case 3:
                return new Number(off, 1, "Z", "0");
            case 4:
                return new Number(off, 1, "Z", "1");
            case 5:
                return new Number(off, 1, "B", "2");
            case 6:
                return new Number(off, 1, "B", "3");
            case 7:
                return new Number(off, 1, "B", "4");
            case 8:
                return new Number(off, 1, "B", "5");
            case 9:
                return new Number(off, 1, "J", "0L");
            case 10:
                return new Number(off, 1, "J", "1L");
            case 11:
                return new Number(off, 1, "F", "0.0F");
            case 12:
                return new Number(off, 1, "F", "1.0F");
            case 13:
                return new Number(off, 1, "F", "2.0F");
            case 14:
                return new Number(off, 1, "D", "0.0");
            case 15:
                return new Number(off, 1, "D", "1.0");
            case 16:
                return new Number(off, 2, "B", String.valueOf(in.readByte()));
            case 17:
                return new Number(off, 3, "S", String.valueOf(in.readShort()));
            case 18:
                return new Ldc(off, 2, in.readUByte());
            case 19:
            case 20:
                return new Ldc(off, 3, in.readUShort());
            case 21:
                return new Load(off, 2, "W", in.readUByte());
            case 22:
                return new Load(off, 2, "J", in.readUByte());
            case 23:
                return new Load(off, 2, "F", in.readUByte());
            case 24:
                return new Load(off, 2, "D", in.readUByte());
            case 25:
                return new Load(off, 2, "A", in.readUByte());
            case 26:
                return new Load(off, 1, "W", 0);
            case 27:
                return new Load(off, 1, "W", 1);
            case 28:
                return new Load(off, 1, "W", 2);
            case 29:
                return new Load(off, 1, "W", 3);
            case 30:
                return new Load(off, 1, "J", 0);
            case 31:
                return new Load(off, 1, "J", 1);
            case 32:
                return new Load(off, 1, "J", 2);
            case 33:
                return new Load(off, 1, "J", 3);
            case 34:
                return new Load(off, 1, "F", 0);
            case 35:
                return new Load(off, 1, "F", 1);
            case 36:
                return new Load(off, 1, "F", 2);
            case 37:
                return new Load(off, 1, "F", 3);
            case 38:
                return new Load(off, 1, "D", 0);
            case 39:
                return new Load(off, 1, "D", 1);
            case 40:
                return new Load(off, 1, "D", 2);
            case 41:
                return new Load(off, 1, "D", 3);
            case 42:
                return new Load(off, 1, "A", 0);
            case 43:
                return new Load(off, 1, "A", 1);
            case 44:
                return new Load(off, 1, "A", 2);
            case 45:
                return new Load(off, 1, "A", 3);
            case 46:
                return new Aload(off, 1, "I");
            case 47:
                return new Aload(off, 1, "J");
            case 48:
                return new Aload(off, 1, "F");
            case 49:
                return new Aload(off, 1, "D");
            case 50:
                return new Aload(off, 1, "A");
            case 51:
                return new Aload(off, 1, "B");
            case 52:
                return new Aload(off, 1, "C");
            case 53:
                return new Aload(off, 1, "S");
            case 54:
                return new Store(off, 2, "W", in.readUByte());
            case 55:
                return new Store(off, 2, "J", in.readUByte());
            case 56:
                return new Store(off, 2, "F", in.readUByte());
            case 57:
                return new Store(off, 2, "D", in.readUByte());
            case 58:
                return new Store(off, 2, "A", in.readUByte());
            case 59:
                return new Store(off, 1, "W", 0);
            case 60:
                return new Store(off, 1, "W", 1);
            case 61:
                return new Store(off, 1, "W", 2);
            case 62:
                return new Store(off, 1, "W", 3);
            case 63:
                return new Store(off, 1, "J", 0);
            case 64:
                return new Store(off, 1, "J", 1);
            case 65:
                return new Store(off, 1, "J", 2);
            case 66:
                return new Store(off, 1, "J", 3);
            case 67:
                return new Store(off, 1, "F", 0);
            case 68:
                return new Store(off, 1, "F", 1);
            case 69:
                return new Store(off, 1, "F", 2);
            case 70:
                return new Store(off, 1, "F", 3);
            case 71:
                return new Store(off, 1, "D", 0);
            case 72:
                return new Store(off, 1, "D", 1);
            case 73:
                return new Store(off, 1, "D", 2);
            case 74:
                return new Store(off, 1, "D", 3);
            case 75:
                return new Store(off, 1, "A", 0);
            case 76:
                return new Store(off, 1, "A", 1);
            case 77:
                return new Store(off, 1, "A", 2);
            case 78:
                return new Store(off, 1, "A", 3);
            case 79:
                return new Astore(off, 1, "I");
            case 80:
                return new Astore(off, 1, "J");
            case 81:
                return new Astore(off, 1, "F");
            case 82:
                return new Astore(off, 1, "D");
            case 83:
                return new Astore(off, 1, "A");
            case 84:
                return new Astore(off, 1, "B");
            case 85:
                return new Astore(off, 1, "C");
            case 86:
                return new Astore(off, 1, "S");
            case 87:
                return new Pop(off, 1);
            case 89:
                return new Dup(off, 1, 0);
            case 90:
                return new Dup(off, 1, 1);
            case 91:
                return new Dup(off, 1, 2);
            case 92:
                return new Dup2(off, 1, 0);
            case 93:
                return new Dup2(off, 1, 1);
            case 94:
                return new Dup2(off, 1, 2);
            case 95:
                return new Swap(off, 1);
            case 96:
                return new CaozuoTwo(off, 1, "I", "+");
            case 97:
                return new CaozuoTwo(off, 1, "J", "+");
            case 98:
                return new CaozuoTwo(off, 1, "F", "+");
            case 99:
                return new CaozuoTwo(off, 1, "D", "+");
            case 100:
                return new CaozuoTwo(off, 1, "I", "-");
            case 101:
                return new CaozuoTwo(off, 1, "J", "-");
            case 102:
                return new CaozuoTwo(off, 1, "F", "-");
            case 103:
                return new CaozuoTwo(off, 1, "D", "-");
            case 104:
                return new CaozuoTwo(off, 1, "I", "*");
            case 105:
                return new CaozuoTwo(off, 1, "J", "*");
            case 106:
                return new CaozuoTwo(off, 1, "F", "*");
            case 107:
                return new CaozuoTwo(off, 1, "D", "*");
            case 108:
                return new CaozuoTwo(off, 1, "I", "/");
            case 109:
                return new CaozuoTwo(off, 1, "J", "/");
            case 110:
                return new CaozuoTwo(off, 1, "F", "/");
            case 111:
                return new CaozuoTwo(off, 1, "D", "/");
            case 112:
                return new CaozuoTwo(off, 1, "I", "%");
            case 113:
                return new CaozuoTwo(off, 1, "J", "%");
            case 114:
                return new CaozuoTwo(off, 1, "F", "%");
            case 115:
                return new CaozuoTwo(off, 1, "D", "%");
            case 116:
                return new Neg(off, 1, "I", "-");
            case 117:
                return new Neg(off, 1, "J", "-");
            case 118:
                return new Neg(off, 1, "F", "-");
            case 119:
                return new Neg(off, 1, "D", "-");
            case 120:
                return new CaozuoTwo(off, 1, "I", "<<");
            case 121:
                return new CaozuoTwo(off, 1, "J", "<<");
            case 122:
                return new CaozuoTwo(off, 1, "I", ">>");
            case 123:
                return new CaozuoTwo(off, 1, "J", ">>");
            case 124:
                return new CaozuoTwo(off, 1, "I", ">>>");
            case 125:
                return new CaozuoTwo(off, 1, "J", ">>>");
            case 126:
                return new CaozuoTwo(off, 1, "I", "&");
            case 127:
                return new CaozuoTwo(off, 1, "J", "&");
            case 128:
                return new CaozuoTwo(off, 1, "I", "|");
            case 129:
                return new CaozuoTwo(off, 1, "J", "|");
            case 130:
                return new CaozuoTwo(off, 1, "I", "^");
            case 131:
                return new CaozuoTwo(off, 1, "J", "^");
            case 132:
                return new Iinc(off, 3, in.readUByte(), in.readByte());
            case 133:
                return new Convert(off, 1, "W", "J");
            case 134:
                return new Convert(off, 1, "W", "F");
            case 135:
                return new Convert(off, 1, "W", "D");
            case 136:
                return new Convert(off, 1, "J", "I");
            case 137:
                return new Convert(off, 1, "J", "F");
            case 138:
                return new Convert(off, 1, "J", "D");
            case 139:
                return new Convert(off, 1, "F", "I");
            case 140:
                return new Convert(off, 1, "F", "J");
            case 141:
                return new Convert(off, 1, "F", "D");
            case 142:
                return new Convert(off, 1, "D", "I");
            case 143:
                return new Convert(off, 1, "D", "J");
            case 144:
                return new Convert(off, 1, "D", "F");
            case 145:
                return new Convert(off, 1, "W", "B");
            case 146:
                return new Convert(off, 1, "W", "C");
            case 147:
                return new Convert(off, 1, "W", "S");
            case 148:
                return new Compare(off, 1, "J");
            case 149:
            case 150:
                return new Compare(off, 1, "F");
            case 151:
            case 152:
                return new Compare(off, 1, "D");
            case 153:
                return new If(off, 3, "==", in.readShort());
            case 154:
                return new If(off, 3, "!=", in.readShort());
            case 155:
                return new If(off, 3, "<", in.readShort());
            case 156:
                return new If(off, 3, ">=", in.readShort());
            case 157:
                return new If(off, 3, ">", in.readShort());
            case 158:
                return new If(off, 3, "<=", in.readShort());
            case 159:
                return new CompareAndIf(off, 3, "W", "==", in.readShort());
            case 160:
                return new CompareAndIf(off, 3, "W", "!=", in.readShort());
            case 161:
                return new CompareAndIf(off, 3, "W", "<", in.readShort());
            case 162:
                return new CompareAndIf(off, 3, "W", ">=", in.readShort());
            case 163:
                return new CompareAndIf(off, 3, "W", ">", in.readShort());
            case 164:
                return new CompareAndIf(off, 3, "W", "<=", in.readShort());
            case 165:
                return new CompareAndIf(off, 3, "A", "==", in.readShort());
            case 166:
                return new CompareAndIf(off, 3, "A", "!=", in.readShort());
            case 167:
                return new GoTo(off, 3, in.readShort());
            case 168:
                return new Jsr(off, 3, in.readShort());
            case 169:
                return new Ret(off, 2, in.readUByte());
            case 170:
                int i5 = -off - 1 & 3;
                in.skip(i5);
                return new TableSwitch(off, i5 + 1, in);
            case 171:
                int j5 = -off - 1 & 3;
                in.skip(j5);
                return new LookupSwitch(off, j5 + 1, in);
            case 172:
                return new Return(off, 1, "W");
            case 173:
                return new Return(off, 1, "J");
            case 174:
                return new Return(off, 1, "F");
            case 175:
                return new Return(off, 1, "D");
            case 176:
                return new Return(off, 1, "A");
            case 177:
                return new Return(off, 1, "V");
            case 178:
                return new GetField(off, 3, in.readUShort(), true);
            case 179:
                return new PutField(off, 3, in.readUShort(), true);
            case 180:
                return new GetField(off, 3, in.readUShort(), false);
            case 181:
                return new PutField(off, 3, in.readUShort(), false);
            case 182:

                return new InvokeVirtual(off, 3, in.readUShort());
            case 183:
                return new InvokeSpecial(off, 3, in.readUShort());
            case 184:
                return new InvokeStatic(off, 3, in.readUShort());
            case 185:
                int j7 = in.readUShort();
                in.readByte();
                in.readUByte();
                return new InvokeVirtual(off, 5, j7);
            case 186:
                return new Unused(off, 1);
            case 187:
                return new New(off, 3, in.readUShort());
            case 188:
                String s;
                switch (in.readUByte()) {
                    case 1:
                        s = "[[?";
                        break;
                    case 4:
                        s = "[Z";
                        break;
                    case 5:
                        s = "[C";
                        break;
                    case 6:
                        s = "[F";
                        break;
                    case 7:
                        s = "[D";
                        break;
                    case 8:
                        s = "[B";
                        break;
                    case 9:
                        s = "[S";
                        break;
                    case 10:
                        s = "[I";
                        break;
                    case 11:
                        s = "[J";
                        break;
                    case 2:
                    default:
                        throw new by("Invalid newarray operand");
                }
                return new NewArray(off, 2, s);
            case 189:
                return new NewArray(off, 3, in.readUShort());
            case 190:
                return new ArrayLength(off, 1);
            case 191:
                return new Throw(off, 1);
            case 192:
                return new CheckCast(off, 3, in.readUShort());
            case 193:
                return new InstanceOf(off, 3, in.readUShort());
            case 194:
                return new MonitorEnter(off, 1);
            case 195:
                return new MonitorExit(off, 1);
            case 196:
                switch (in.readUByte()) {
                    case 21:
                        return new Load(off, 4, "W", in.readUShort());
                    case 22:
                        return new Load(off, 4, "J", in.readUShort());
                    case 23:
                        return new Load(off, 4, "F", in.readUShort());
                    case 24:
                        return new Load(off, 4, "D", in.readUShort());
                    case 25:
                        return new Load(off, 4, "A", in.readUShort());
                    case 54:
                        return new Store(off, 4, "W", in.readUShort());
                    case 55:
                        return new Store(off, 4, "J", in.readUShort());
                    case 56:
                        return new Store(off, 4, "F", in.readUShort());
                    case 57:
                        return new Store(off, 4, "D", in.readUShort());
                    case 58:
                        return new Store(off, 4, "A", in.readUShort());
                    case 132:
                        return new Iinc(off, 6, in.readUShort(), in.readShort());
                    case 169:
                        return new Ret(off, 4, in.readUShort());
                }
                throw new by("Invalid wide opcode");
            case 197:
                return new NewArray(off, 4, in.readUShort(), in.readUByte());
            case 198:
                return new IfNull(off, 3, "==", in.readShort());
            case 199:
                return new IfNull(off, 3, "!=", in.readShort());
            case 200:
                return new GoTo(off, 5, in.readInt());
            case 201:
                return new Jsr(off, 5, in.readInt());
            case 202:
                return new Nop(off, 1);
        }
        throw new by("Invalid opcode");
    }
}


