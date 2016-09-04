package classsys;

import bytecode.*;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

public class ByteCodeReader extends OpcodesUtil {

    public static String className;
    static boolean isstatic;
    static String[] arg;

    public static Document sys(Vector v, Vector paramas, ExceptionHander[] eh) throws Exception {
        int size = v.size();
        int opcode;
        Stack stack = new Stack();
        Stack T = new Stack();
        String ref;
        String index;
        String value;
        ConstantPool cp;
        LocalVariable locales[] = new LocalVariable[CodeAttribute.max_locals];
        isstatic = CodeAttribute.isstatic;
        arg = CodeAttribute.arg;
        if (paramas != null) {
            if (isstatic) {
                paramas.copyInto(locales);
            } else {
                for (int n = 0; n < paramas.size(); n++) {
                    locales[n + 1] = (LocalVariable) paramas.elementAt(n);
                }
            }
        }
        int start = 0;
        Document now = new Document();
        //     try {
        for (int i = 0; i < size; i++) {
            AbstractInstruction instruction = (AbstractInstruction) v.elementAt(i);
          //  System.err.print(i + ":  ");
         //   instruction.show(System.err);

            switch (opcode = instruction.getOpcode()) {

                case LCMP:
                case FCMPL:
                case FCMPG:
                case DCMPL:
                case DCMPG:
                    value = (String) stack.pop();
                    stack.push(stack.pop() + " > " + value);
                    T.pop();
                    T.pop();
                    T.push("I");
                    break;
                case IRETURN:
                case LRETURN:
                case FRETURN:
                case DRETURN:
                case ARETURN:
                    T.pop();
                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, "return " + stack.pop() + ";"));
                    break;
                case RETURN:
                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, "return;"));
                    break;
                case ARRAYLENGTH:
                    stack.push(stack.pop() + ".length");
                    T.pop();
                    T.push("I");
                    break;
                case ATHROW:
                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, "throw " + stack.peek()));
                    break;
                case MONITORENTER:
                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, stack.pop() + ".monitorenter;"));
                    T.pop();
                    break;
                case MONITOREXIT:
                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, stack.pop() + ".monitorexit;"));
                    T.pop();
                    break;
                //case BREAKPOINT:
                // case IMPDEP1:
                //  case IMPDEP2:
                //      break;

                case BIPUSH:
                case LDC:
                case ILOAD:
                case LLOAD:
                case FLOAD:
                case DLOAD:
                case ALOAD:
                case ISTORE:
                case LSTORE:
                case FSTORE:
                case DSTORE:
                case ASTORE:
                case RET:
                case NEWARRAY:
                    int im = ((ImmediateByteInstruction) instruction).getImmediateByte();

                    switch (opcode) {
                        case BIPUSH:
                            stack.push("" + im);
                            T.push("I");
                            break;
                        case LDC:
                            stack.push(Classsys.cps[im].Value());
                            T.push(Classsys.cps[im].type());
                            break;
                        case ILOAD:
                            stack.push(getlocal(im, instruction.offset, "I", locales));
                            T.push(locales[im].type);
                            break;
                        case LLOAD:
                            stack.push(getlocal(im, instruction.offset, "J", locales));
                            T.push("J");
                            break;
                        case FLOAD:
                            stack.push(getlocal(im, instruction.offset, "F", locales));
                            T.push("F");
                            break;
                        case DLOAD:
                            stack.push(getlocal(im, instruction.offset, "D", locales));
                            T.push("D");
                            break;
                        case ALOAD:
                            stack.push(getlocal(im, instruction.offset, "L", locales));
                            T.push("L");
                            break;
                        case ISTORE:
                            value = (String) stack.pop();
                            if (locales[im] != null && locales[im].type.equals("Z")) {
                                if (value.equals("1")) {
                                    value = "true";
                                } else if (value.equals("0")) {
                                    value = "false";
                                }
                            }
                            start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, getlocal(im, instruction.offset, "I", locales) + " = " + value + ";"));
                            if (!T.pop().equals("I")) {
                                debug("Error ISTORE type mismatch!");
                            }
                            break;
                        case LSTORE:
                            start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, getlocal(im, instruction.offset, "J", locales) + " = " + stack.pop() + ";"));
                            if (!T.pop().equals("J")) {
                                debug("Error LSTORE type mismatch!");
                            }
                            break;
                        case FSTORE:
                            start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, getlocal(im, instruction.offset, "F", locales) + " = " + stack.pop() + ";"));
                            if (!T.pop().equals("F")) {
                                debug("Error FSTORE type mismatch!");
                            }
                            break;
                        case DSTORE:
                            start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, getlocal(im, instruction.offset, "D", locales) + " = " + stack.pop() + ";"));
                            if (!T.pop().equals("D")) {
                                debug("Error DSTORE type mismatch!");
                            }
                            break;
                        case ASTORE:
                            if (stack.size() == 0) {
                                for (int k = eh.length - 1; k >= 0; k--) {
                                    if (eh[k].hand == instruction.offset) {
                                        start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, "}catch(" + getlocal(im, instruction.offset, "L", locales) + "){"));
                                        ((Line) now.lines.elementAt(now.find(eh[k].start))).addHead("try{\r\n");
                                        AbstractInstruction isd = (AbstractInstruction) v.elementAt(i - 1);
                                        if (isd.getOpcode() == GOTO) {
                                            now.addQuote(((BranchInstruction) isd).getBranchOffset() + isd.offset);
                                        }
                                        break;
                                    }
                                }
                            } else {
                                start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, getlocal(im, instruction.offset, "L", locales) + " = " + stack.pop() + ";"));
                                value = (String) T.pop();
                                if (!(value.startsWith("L") || value.startsWith("["))) {
                                    debug("Error ASTORE type mismatch!");
                                }
                            }
                            break;
                        case RET:
                            start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, "ret " + im + ";"));
                            break;
                        case NEWARRAY:

                            value = getArrayTypeVerbose(im);
                            stack.push("new " + value + "[" + stack.pop() + "]");
                            break;

                    }
                    break;

                case LDC_W:
                case LDC2_W:
                case GETSTATIC:
                case PUTSTATIC:
                case GETFIELD:
                case PUTFIELD:
                case INVOKEVIRTUAL:
                case INVOKESPECIAL:
                case INVOKESTATIC:
                case NEW:
                case ANEWARRAY:
                case CHECKCAST:
                case INSTANCEOF:
                case SIPUSH: // the only immediate short instruction that does
                    // not have an immediate constant pool reference

                    int is = ((ImmediateShortInstruction) instruction).getImmediateShort();
                    StringBuffer sb;
                    switch (opcode) {
                        case LDC_W:
                        case LDC2_W:
                            stack.push(Classsys.cps[is].Value());
                            T.push(Classsys.cps[is].type());
                            break;
                        case GETSTATIC:
                            stack.push(Classsys.cps[is].strVal1.replace('/', '.') + "." + Classsys.cps[is].Value());
                            T.push(Classsys.cps[is].type());
                            break;
                        case PUTSTATIC:
                            value = (String) stack.pop();
                            T.pop();
                            if (Classsys.cps[is].type().equals("Z")) {
                                if (value.equals("1")) {
                                    value = "true";
                                } else if (value.equals("0")) {
                                    value = "false";
                                }
                            }
                            start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, Classsys.cps[is].strVal1.replace('/', '.') + "." + Classsys.cps[is].Value() + " = " + value + ";"));
                            break;
                        case GETFIELD:
                            ref = (String) stack.pop();
                            T.pop();
                            if (ref.equals("L_0")) {
                                if (!isstatic) {
                                    ref = (Classsys.cps[is].strVal1.equals(className)) ? "this" : "super";
                                }
                            }
                            stack.push(ref + "." + Classsys.cps[is].Value());
                            T.push(Classsys.cps[is].type());
                            break;
                        case PUTFIELD:
                            value = (String) stack.pop();
                            T.pop();
                            if (Classsys.cps[is].type().equals("Z")) {
                                if (value.equals("1")) {
                                    value = "true";
                                } else if (value.equals("0")) {
                                    value = "false";
                                }
                            }
                            ref = (String) stack.pop();
                            T.pop();
                            if (ref.equals("L_0")) {
                                if (!isstatic) {
                                    ref = (Classsys.cps[is].strVal1.equals(className)) ? "this" : "super";
                                }
                            }
                            if (stack.size() == 0) {
                                start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, ref + "." + Classsys.cps[is].Value() + " = " + value + ";"));
                            } else {
                                stack.pop();
                                stack.push(ref + "." + Classsys.cps[is].Value() + " = " + value);
                            }
                            break;
                        case INVOKEVIRTUAL:
                        case INVOKESPECIAL:
                            sb = new StringBuffer(")");
                            cp = Classsys.cps[is];
                            value = Classsys.change(cp.strVal3);
                            String returntype = value.substring(0, value.indexOf('('));
                            value = value.substring(value.indexOf('(') + 1);
                            while (!value.startsWith(")")) {
                                int l = value.indexOf(',') + 1;
                                if (l == 0) {
                                    l = value.length() - 1;
                                }
                                index = value.substring(0, l);
                                value = value.substring(l);
                                ref = (String) stack.pop();
                                if (index.equals("boolean")) {
                                    if (ref.equals("0")) {
                                        ref = "false";
                                    } else if (ref.equals("1")) {
                                        ref = "true";
                                    }
                                } else if (ref.equals("L_0") && Method.methodAccess.indexOf("static") < 0) {
                                    ref = "this";
                                }

                                sb.insert(0, ref);
                                T.pop();
                                if (value.length() > 1) {
                                    sb.insert(0, ',');
                                }
                            }
                            sb.insert(0, '(');
                            if (!cp.strVal2.equals("<init>")) {
                                sb.insert(0, cp.strVal2).insert(0, ".");
                            }
                            ref = (String) stack.pop();
                            if (ref.equals("this") && !isstatic && !cp.strVal1.equals(className)) {
                                ref = "super";
                            }
                            sb.insert(0, ref);
                            T.pop();
                            if (!returntype.equals("void")) {
                                stack.push(sb.toString());
                                T.push(getreturntype(returntype));
                            } else {
                                if (stack.size() == 0) {
                                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, sb.toString() + ";"));
                                } else {
                                    stack.pop();
                                    stack.push(sb.toString());
                                }
                            }
                            break;
                        case INVOKESTATIC:
                            sb = new StringBuffer(")");
                            cp = Classsys.cps[is];
                            value = Classsys.change(cp.strVal3);
                            returntype = value.substring(0, value.indexOf('('));
                            value = value.substring(value.indexOf('(') + 1);
                            while (!value.startsWith(")")) {
                                sb.insert(0, stack.pop());
                                int l = value.indexOf(',');
                                if (l >= 0) {
                                    value = value.substring(l + 1);
                                    sb.insert(0, ',');
                                } else {
                                    break;
                                }
                            }
                            if (cp.strVal2.equals("<init>")) {
                                sb.insert(0, '(');
                            } else {
                                sb.insert(0, "(").insert(0, cp.strVal2).insert(0, ".");
                            }
                            sb.insert(0, cp.strVal1.replace('/', '.'));
                            if (!returntype.equals("void")) {
                                stack.push(sb.toString());
                                T.push(getreturntype(returntype));
                            } else {
                                start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, sb.toString() + ";"));
                            }
                            break;
                        case NEW:
                            stack.push("new " + Classsys.change3(Classsys.cps[is].strVal1));
                            T.push("L");
                            break;
                        case ANEWARRAY:
                            String type = Classsys.cps[is].strVal1;
                            type = type.startsWith("[") ? Classsys.change2(type) : Classsys.change3(type);
                            int l = type.indexOf('[');
                            if (l < 0) {
                                l = type.length();
                            }
                            StringBuffer sbf = new StringBuffer("new ");
                            sbf.append(type.substring(0, l)).append('[').append(stack.pop()).append(']').append(type.substring(l));
                            stack.push(sbf.toString());
                            T.push("L");
                            break;
                        case CHECKCAST:
                            stack.push("(" + Classsys.cps[is].strVal1 + ")" + stack.pop());
                            break;
                        case INSTANCEOF:
                            stack.push("(" + stack.pop() + " instanceof " + Classsys.cps[is].strVal1 + ")");
                            T.pop();
                            T.push("I");
                            break;
                        case SIPUSH: // the only immediate short instruction that does
                            stack.push("" + is);
                            T.push("I");
                            break;

                    }
                    break;

                case IFEQ:
                case IFNE:
                case IFLT:
                case IFGE:
                case IFGT:
                case IFLE:
                    int offset = ((BranchInstruction) instruction).getBranchOffset() + instruction.offset;
                    value = (String) stack.pop();
                    if (T.pop().equals("Z")) {
                        start = now.write(new GotoLine(start, instruction.offset + instruction.getSize(), i, opcode == IFEQ ? value : "!" + value, "", offset, 0));
                    } else // int gotoaddress = getopcode(v, i, offset);
                    // AbstractInstruction ai = (AbstractInstruction) v.elementAt(gotoaddress - 1);
                    {
                        start = now.write(new GotoLine(start, instruction.offset + instruction.getSize(), i, value, "0", offset, opcode));
                    }
                    //   now.addGoto(instruction.offset, offset);
                    break;
                case IF_ICMPEQ:
                case IF_ICMPNE:
                case IF_ICMPLT:
                case IF_ICMPGE:
                case IF_ICMPGT:
                case IF_ICMPLE:
                case IF_ACMPEQ:
                case IF_ACMPNE:
                    offset = ((BranchInstruction) instruction).getBranchOffset() + instruction.offset;
                    T.pop();
                    T.pop();
                    value = (String) stack.pop();
                    index = (String) stack.pop();
                    //  gotoaddress = getopcode(v, i, offset);
                    //   ai = (AbstractInstruction) v.elementAt(gotoaddress - 1);

                    start = now.write(new GotoLine(start, instruction.offset + instruction.getSize(), i, index, value, offset, opcode));
                    //  now.addGoto(instruction.offset, offset);
                    break;
                case GOTO:
                case JSR:
                case IFNULL:
                case IFNONNULL:
                    offset = ((BranchInstruction) instruction).getBranchOffset() + instruction.offset;
                    switch (opcode) {
                        case GOTO:
                            start = now.write(new GotoLine(start, instruction.offset + instruction.getSize(), i, null, null, offset, 0));
                            break;
                        case JSR:
                            stack.push("address: " + offset);
                            T.push("I");
                            break;
                        case IFNULL:
                        case IFNONNULL:
                            T.pop();
                            value = (String) stack.pop();
                            // int gotoaddress = getopcode(v, i, offset);
                            //  AbstractInstruction ai = (AbstractInstruction) v.elementAt(gotoaddress - 1);
                            // String gg = "if(";
                            //  if (((ai.getOpcode() == GOTO && ((BranchInstruction) ai).getBranchOffset() < 0) || (ai.getOpcode() == GOTO_W && ((ImmediateIntInstruction) ai).getImmediateInt() < 0))) {
                            //      gg = "while(";
                            //   }
                            start = now.write(new GotoLine(start, instruction.offset + instruction.getSize(), i, value, "null", offset, opcode));
                            //      now.addGoto(instruction.offset, offset);
                            break;
                    }
                    break;

                case GOTO_W:
                    //           start=now.write(start,instruction.offset,i,"goto " + ((ImmediateIntInstruction) instruction).getImmediateInt() + ";");
                    break;
                case JSR_W:
                    stack.push("address: " + ((ImmediateIntInstruction) instruction).getImmediateInt());
                    T.push("I");
                    break;
                case IINC:
                    IncrementInstruction ii = (IncrementInstruction) instruction;
                    ref = getlocal(ii.getImmediateByte(), instruction.offset, "I", locales);
                    if (ii.getIncrementConst() == 1) {
                        start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, ref + "++;"));
                    } else if (ii.getIncrementConst() == -1) {
                        start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, ref + "--;"));
                    } else {
                        start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, ref + " += " + ii.getIncrementConst() + ";"));
                    }
                    break;

                case TABLESWITCH:
                    TableSwitchInstruction ti = (TableSwitchInstruction) instruction;
                    T.pop();
                    int sta = start;
                    now.write(new Line(sta, 0, i, "switch(" + stack.pop() + "){"));
                    start = instruction.offset + instruction.getSize();
                    int startoff = ti.lowByte;
                    int endoff = ti.highByte;
                    int gotos[] = ti.jumpOffsets;
                    for (int k = startoff; k <= endoff; k++) {
                        now.write(new GotoLine(sta, sta + 1, i, "case " + k + " : ", "", gotos[k - startoff] + instruction.offset, 3));
                        sta += 2;
                    }
                    now.write(new GotoLine(sta, sta + 1, i, "default: ", "\r\n}", ti.defaultOffset + instruction.offset, 3));
                    break;

                case LOOKUPSWITCH:
                    LookupSwitchInstruction lsi = (LookupSwitchInstruction) instruction;
                    T.pop();
                    sta = start;
                    now.write(new Line(sta, 0, i, "switch(" + stack.pop() + "){"));
                    start = instruction.offset + instruction.getSize();
                    int cases[] = lsi.matchs;
                    gotos = lsi.offsets;
                    for (int k = 0; k < gotos.length; k++) {
                        now.write(new GotoLine(sta, sta + 1, i, "case " + cases[k] + " : ", "", gotos[k] + instruction.offset, 3));
                        sta += 2;
                    }
                    now.write(new GotoLine(sta, sta + 1, i, "default: ", "\r\n}", lsi.defaultOffset + instruction.offset, 3));

                    break;

                case INVOKEINTERFACE:
                    InvokeInterfaceInstruction iif = (InvokeInterfaceInstruction) instruction;
                    cp = Classsys.cps[iif.getImmediateShort()];
                    sb = new StringBuffer(")");
                    value = Classsys.change(cp.strVal3);
                    String returntype = value.substring(0, value.indexOf('('));
                    value = value.substring(value.indexOf('(') + 1);
                    while (!value.startsWith(")")) {
                        sb.insert(0, stack.pop());
                        int l = value.indexOf(',');
                        if (l >= 0) {
                            value = value.substring(l + 1);
                            sb.insert(0, ',');
                        } else {
                            break;
                        }
                    }
                    if (cp.strVal2.equals("<init>")) {
                        sb.insert(0, '(');
                    } else {
                        sb.insert(0, "(").insert(0, cp.strVal2).insert(0, ".");
                    }
                    sb.insert(0, stack.pop());
                    if (returntype.equals("void")) {
                        start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, sb.toString() + ";"));
                    } else {
                        stack.push(sb.toString());
                        T.push(getreturntype(returntype));
                    }
                    break;

                case MULTIANEWARRAY:
                    MultianewarrayInstruction mi = (MultianewarrayInstruction) instruction;
                    int length = mi.getDimensions();
                    String type = Classsys.change3(Classsys.cps[mi.getImmediateShort()].strVal1);
                    stack.push("new " + type + "{}");
                    T.push("L");

                    break;
                case WIDE:
                case NOP:
                    break;
                case ACONST_NULL:
                    stack.push("null");
                    T.push("L");
                    break;
                case ICONST_M1:
                case ICONST_0:
                case ICONST_1:
                case ICONST_2:
                case ICONST_3:
                case ICONST_4:
                case ICONST_5:
                    stack.push("" + (opcode - ICONST_0));
                    T.push("I");
                    break;
                case LCONST_0:
                case LCONST_1:
                    stack.push("" + (opcode - LCONST_0));
                    T.push("J");
                    break;
                case FCONST_0:
                case FCONST_1:
                case FCONST_2:
                    stack.push("" + (opcode - FCONST_0));
                    T.push("F");
                    break;
                case DCONST_0:
                case DCONST_1:
                    stack.push("" + (opcode - DCONST_0));
                    T.push("D");
                    break;
                case ILOAD_0:
                case ILOAD_1:
                case ILOAD_2:
                case ILOAD_3:
                    stack.push(getlocal(opcode - ILOAD_0, instruction.offset, "I", locales));
                    T.push(locales[opcode - ILOAD_0].type);
                    break;
                case LLOAD_0:
                case LLOAD_1:
                case LLOAD_2:
                case LLOAD_3:
                    stack.push(getlocal(opcode - LLOAD_0, instruction.offset, "J", locales));
                    T.push("J");
                    break;
                case FLOAD_0:
                case FLOAD_1:
                case FLOAD_2:
                case FLOAD_3:
                    stack.push(getlocal(opcode - FLOAD_0, instruction.offset, "F", locales));
                    T.push("F");
                    break;
                case DLOAD_0:
                case DLOAD_1:
                case DLOAD_2:
                case DLOAD_3:
                    stack.push(getlocal(opcode - DLOAD_0, instruction.offset, "D", locales));
                    T.push("D");
                    break;
                case ALOAD_0:
                case ALOAD_1:
                case ALOAD_2:
                case ALOAD_3:
                    stack.push(getlocal(opcode - ALOAD_0, instruction.offset, "L", locales));
                    T.push("L");
                    break;
                case IALOAD:
                case LALOAD:
                case FALOAD:
                case DALOAD:
                case AALOAD:
                case BALOAD:
                case CALOAD:
                case SALOAD:
                    T.push(getcompare(opcode));
                    index = (String) stack.pop();
                    ref = (String) stack.pop();
                    stack.push(ref + "[" + index + "]");
                    break;

                case ISTORE_0:
                case ISTORE_1:
                case ISTORE_2:
                case ISTORE_3:
                    value = (String) stack.pop();
                    getlocal(opcode - ISTORE_0, instruction.offset, "I", locales);
                    if (locales[opcode - ISTORE_0].type.equals("Z")) {
                        if (value.equals("1")) {
                            value = "true";
                        } else if (value.equals("0")) {
                            value = "false";
                        }
                    }
                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, getlocal(opcode - ISTORE_0, instruction.offset, "I", locales) + " = " + value + ";"));
                    if (!T.pop().equals("I")) {
                        debug("Error ISTORE type mismatch!");
                    }
                    break;
                case LSTORE_0:
                case LSTORE_1:
                case LSTORE_2:
                case LSTORE_3:
                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, getlocal(opcode - LSTORE_0, instruction.offset, "L", locales) + " = " + stack.pop() + ";"));
                    if (!T.pop().equals("J")) {
                        debug("Error LSTORE type mismatch!");
                    }
                    break;

                case FSTORE_0:
                case FSTORE_1:
                case FSTORE_2:
                case FSTORE_3:
                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, getlocal(opcode - FSTORE_0, instruction.offset, "F", locales) + " = " + stack.pop() + ";"));

                    if (!T.pop().equals("F")) {
                        debug("Error FSTORE type mismatch!");
                    }
                    break;
                case DSTORE_0:
                case DSTORE_1:
                case DSTORE_2:
                case DSTORE_3:
                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, getlocal(opcode - DSTORE_0, instruction.offset, "D", locales) + " = " + stack.pop() + ";"));
                    if (!T.pop().equals("D")) {
                        debug("Error DSTORE type mismatch!");
                    }
                    break;
                case ASTORE_0:
                case ASTORE_1:
                case ASTORE_2:
                case ASTORE_3:
                    if (stack.size() == 0) {
                        for (int k = eh.length - 1; k >= 0; k--) {
                            if (eh[k].hand == instruction.offset) {
                                start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, "}catch(" + getlocal(opcode - ASTORE_0, instruction.offset, "L", locales) + "){"));
                                ((Line) now.lines.elementAt(now.find(eh[k].start))).addHead("try{\r\n");
                                AbstractInstruction isd = (AbstractInstruction) v.elementAt(i - 1);
                                if (isd.getOpcode() == GOTO) {
                                    now.addQuote(((BranchInstruction) isd).getBranchOffset() + isd.offset);
                                }
                                break;
                            }
                        }
                    } else {
                        start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, getlocal(opcode - ASTORE_0, instruction.offset, "L", locales) + " = " + stack.pop() + ";"));
                        value = (String) T.pop();
                        if (!(value.startsWith("L") || value.startsWith("["))) {
                            debug("Error ASTORE type mismatch!" + value);
                        }
                    }
                    break;

                case IASTORE:
                case LASTORE:
                case FASTORE:
                case DASTORE:
                case AASTORE:
                case BASTORE:
                case CASTORE:
                case SASTORE:
                    value = (String) stack.pop();
                    index = (String) stack.pop();
                    ref = (String) stack.pop();
                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, ref + "[" + index + "] = " + value + ";"));
                    T.pop();
                    break;
                case POP:
                    if (stack.size() == 0) {
                      for (int k = eh.length - 1; k >= 0; k--) {
                            if (eh[k].hand == instruction.offset) {
                                start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, "}catch(" + "Exception ex" + "){"));
                                ((Line) now.lines.elementAt(now.find(eh[k].start))).addHead("try{\r\n");
                                AbstractInstruction isd = (AbstractInstruction) v.elementAt(i - 1);
                                if (isd.getOpcode() == GOTO) {
                                    now.addQuote(((BranchInstruction) isd).getBranchOffset() + isd.offset);
                                }
                                break;
                            }
                        }

                   } else {
                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, stack.pop() + ";"));
                    T.pop();
                    }
                    break;
                case POP2:
                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, stack.pop() + ";"));
                    T.pop();
                    start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, stack.pop() + ";"));
                    T.pop();
                    break;
                case DUP:
                    if (stack.size() == 0) {
                      for (int k = eh.length - 1; k >= 0; k--) {
                            if (eh[k].hand == instruction.offset) {
                                start = now.write(new Line(start, instruction.offset + instruction.getSize(), i, "}catch(" + "Exception ex" + "){"));
                                ((Line) now.lines.elementAt(now.find(eh[k].start))).addHead("try{\r\n");
                                AbstractInstruction isd = (AbstractInstruction) v.elementAt(i - 1);
                                if (isd.getOpcode() == GOTO) {
                                    now.addQuote(((BranchInstruction) isd).getBranchOffset() + isd.offset);
                                }
                                break;
                            }
                        }
                        stack.push("ex");
                        stack.push("ex");
                        T.push("L");
                        T.push("L");
                    } else {
                        stack.push(stack.peek());
                        T.push(T.peek());
                    }
                    break;
                case DUP_X1:
                    value = (String) stack.pop();
                    index = (String) stack.pop();
                    stack.push(value);
                    stack.push(index);
                    stack.push(value);
                    value = (String) T.pop();
                    index = (String) T.pop();
                    T.push(value);
                    T.push(index);
                    T.push(value);
                    break;
                case DUP_X2:      //todo 2
                    value = (String) stack.pop();
                    index = (String) stack.pop();
                    ref = (String) stack.pop();
                    stack.push(value);
                    stack.push(ref);
                    stack.push(index);
                    stack.push(value);
                    value = (String) T.pop();
                    index = (String) T.pop();
                    ref = (String) T.pop();
                    T.push(value);
                    T.push(ref);
                    T.push(index);
                    T.push(value);
                    break;
                case DUP2:        //todo 2
                    value = (String) stack.pop();
                    index = (String) stack.peek();
                    stack.push(value);
                    stack.push(index);
                    stack.push(value);
                    value = (String) T.pop();
                    index = (String) T.peek();
                    T.push(value);
                    T.push(index);
                    T.push(value);
                    break;
                case DUP2_X1:    //todo 2
                    value = (String) stack.pop();
                    index = (String) stack.pop();
                    ref = (String) stack.pop();
                    stack.push(index);
                    stack.push(value);
                    stack.push(ref);
                    stack.push(index);
                    stack.push(value);
                    value = (String) T.pop();
                    index = (String) T.pop();
                    ref = (String) T.pop();
                    T.push(index);
                    T.push(value);
                    T.push(ref);
                    T.push(index);
                    T.push(value);
                    break;
                case DUP2_X2:  //todo 4
                    value = (String) stack.pop();
                    index = (String) stack.pop();
                    ref = (String) stack.pop();
                    String temp = (String) stack.pop();
                    stack.push(index);
                    stack.push(value);
                    stack.push(temp);
                    stack.push(ref);
                    stack.push(index);
                    stack.push(value);
                    value = (String) T.pop();
                    index = (String) T.pop();
                    ref = (String) T.pop();
                    temp = (String) T.pop();
                    T.push(index);
                    T.push(value);
                    T.push(temp);
                    T.push(ref);
                    T.push(index);
                    T.push(value);
                    break;

                case SWAP:
                    value = (String) stack.pop();
                    index = (String) stack.pop();
                    stack.push(value);
                    stack.push(index);
                    value = (String) T.pop();
                    index = (String) T.pop();
                    T.push(value);
                    T.push(index);
                    break;
                case IADD:
                case LADD:
                case FADD:
                case DADD:
                    value = (String) stack.pop();
                    stack.push(stack.pop() + " + " + value);
                    T.pop();
                    break;
                case ISUB:
                case LSUB:
                case FSUB:
                case DSUB:
                    value = (String) stack.pop();
                    stack.push(stack.pop() + " - " + value);
                    T.pop();
                    break;
                case IMUL:
                case LMUL:
                case FMUL:
                case DMUL:
                    value = (String) stack.pop();
                    stack.push(stack.pop() + " * " + value);
                    T.pop();
                    break;

                case IDIV:
                case LDIV:
                case FDIV:
                case DDIV:
                    value = (String) stack.pop();
                    stack.push(stack.pop() + " / " + value);
                    T.pop();
                    break;

                case IREM:
                case LREM:
                case FREM:
                case DREM:
                    value = (String) stack.pop();
                    stack.push(stack.pop() + " % " + value);
                    T.pop();
                    break;

                case INEG:
                case LNEG:
                case FNEG:
                case DNEG:
                    stack.push("- " + stack.pop());
                    T.pop();
                    break;

                case ISHL:
                case LSHL:
                    value = (String) stack.pop();
                    stack.push(stack.pop() + " << " + value);
                    T.pop();
                    break;
                case ISHR:
                case LSHR:
                    value = (String) stack.pop();
                    stack.push(stack.pop() + " >> " + value);
                    T.pop();
                    break;
                case IUSHR:
                case LUSHR:
                    value = (String) stack.pop();
                    stack.push(stack.pop() + " >>> " + value);
                    T.pop();
                    break;
                case IAND:
                case LAND:
                    value = (String) stack.pop();
                    stack.push(stack.pop() + " & " + value);
                    T.pop();
                    break;
                case IOR:
                case LOR:
                    value = (String) stack.pop();
                    stack.push(stack.pop() + " | " + value);
                    T.pop();
                    break;
                case IXOR:
                case LXOR:
                    value = (String) stack.pop();
                    stack.push(stack.pop() + " ^ " + value);
                    T.pop();
                    break;
                case I2L:
                    stack.push("(long)" + stack.pop());
                    T.pop();
                    T.push("J");
                    break;
                case I2F:
                    stack.push("(float)" + stack.pop());
                    T.pop();
                    T.push("F");
                    break;
                case I2D:
                    stack.push("(double)" + stack.pop());
                    T.pop();
                    T.push("D");
                    break;
                case L2I:
                    stack.push("(int)" + stack.pop());
                    T.pop();
                    T.push("I");
                    break;
                case L2F:
                    stack.push("(float)" + stack.pop());
                    T.pop();
                    T.push("F");
                    break;
                case L2D:
                    stack.push("(double)" + stack.pop());
                    T.pop();
                    T.push("D");
                    break;
                case F2I:
                    stack.push("(int)" + stack.pop());
                    T.pop();
                    T.push("I");
                    break;
                case F2L:
                    stack.push("(long)" + stack.pop());
                    T.pop();
                    T.push("J");
                    break;
                case F2D:
                    stack.push("(double)" + stack.pop());
                    T.pop();
                    T.push("D");
                    break;
                case D2I:
                    stack.push("(int)" + stack.pop());
                    T.pop();
                    T.push("I");
                    break;
                case D2L:
                    stack.push("(long)" + stack.pop());
                    T.pop();
                    T.push("J");
                    break;
                case D2F:
                    stack.push("(float)" + stack.pop());
                    T.pop();
                    T.push("F");
                    break;
                case I2B:
                    stack.push("(byte)" + stack.pop());
                    T.pop();
                    T.push("B");
                    break;
                case I2C:
                    stack.push("(char)" + stack.pop());
                    T.pop();
                    T.push("C");
                    break;
                case I2S:
                    stack.push("(short)" + stack.pop());
                    T.pop();
                    T.push("S");
                    break;
            }

        }
        //  } catch (Exception e) {

        //    }
        System.err.println(" --------------------------------- ");
        return now;
        //    println("\r\n}");
    }

    public static Vector readByteCode(byte[] code) throws IOException {
        return readByteCode(code, null);
    }

    public static Vector readByteCode(byte[] code, AbstractInstruction[] prependInstructions) throws IOException {

        ByteCodeInput bcis = new ByteCodeInput(code);
        Vector instructions = new Vector();
        if (prependInstructions != null) {
            for (int i = 0; i < prependInstructions.length; i++) {
                instructions.addElement(prependInstructions[i]);
            }

        }

        boolean wide = false;
        AbstractInstruction currentInstruction;

        while (bcis.getBytesRead() < code.length) {
            currentInstruction = readNextInstruction(bcis, wide);
            wide = (currentInstruction.getOpcode() == WIDE);
            instructions.addElement(currentInstruction);
        }

        return instructions;
    }

    private static AbstractInstruction readNextInstruction(ByteCodeInput bcis, boolean wide) throws IOException {
        AbstractInstruction instruction;

        int opcode = bcis.readUnsignedByte();

        switch (opcode) {

            case WIDE:
            case NOP:
            case ACONST_NULL:
            case ICONST_M1:
            case ICONST_0:
            case ICONST_1:
            case ICONST_2:
            case ICONST_3:
            case ICONST_4:
            case ICONST_5:
            case LCONST_0:
            case LCONST_1:
            case FCONST_0:
            case FCONST_1:
            case FCONST_2:
            case DCONST_0:
            case DCONST_1:
            case ILOAD_0:
            case ILOAD_1:
            case ILOAD_2:
            case ILOAD_3:
            case LLOAD_0:
            case LLOAD_1:
            case LLOAD_2:
            case LLOAD_3:
            case FLOAD_0:
            case FLOAD_1:
            case FLOAD_2:
            case FLOAD_3:
            case DLOAD_0:
            case DLOAD_1:
            case DLOAD_2:
            case DLOAD_3:
            case ALOAD_0:
            case ALOAD_1:
            case ALOAD_2:
            case ALOAD_3:
            case IALOAD:
            case LALOAD:
            case FALOAD:
            case DALOAD:
            case AALOAD:
            case BALOAD:
            case CALOAD:
            case SALOAD:
            case ISTORE_0:
            case ISTORE_1:
            case ISTORE_2:
            case ISTORE_3:
            case LSTORE_0:
            case LSTORE_1:
            case LSTORE_2:
            case LSTORE_3:
            case FSTORE_0:
            case FSTORE_1:
            case FSTORE_2:
            case FSTORE_3:
            case DSTORE_0:
            case DSTORE_1:
            case DSTORE_2:
            case DSTORE_3:
            case ASTORE_0:
            case ASTORE_1:
            case ASTORE_2:
            case ASTORE_3:
            case IASTORE:
            case LASTORE:
            case FASTORE:
            case DASTORE:
            case AASTORE:
            case BASTORE:
            case CASTORE:
            case SASTORE:
            case POP:
            case POP2:
            case DUP:
            case DUP_X1:
            case DUP_X2:
            case DUP2:
            case DUP2_X1:
            case DUP2_X2:
            case SWAP:
            case IADD:
            case LADD:
            case FADD:
            case DADD:
            case ISUB:
            case LSUB:
            case FSUB:
            case DSUB:
            case IMUL:
            case LMUL:
            case FMUL:
            case DMUL:
            case IDIV:
            case LDIV:
            case FDIV:
            case DDIV:
            case IREM:
            case LREM:
            case FREM:
            case DREM:
            case INEG:
            case LNEG:
            case FNEG:
            case DNEG:
            case ISHL:
            case LSHL:
            case ISHR:
            case LSHR:
            case IUSHR:
            case LUSHR:
            case IAND:
            case LAND:
            case IOR:
            case LOR:
            case IXOR:
            case LXOR:
            case I2L:
            case I2F:
            case I2D:
            case L2I:
            case L2F:
            case L2D:
            case F2I:
            case F2L:
            case F2D:
            case D2I:
            case D2L:
            case D2F:
            case I2B:
            case I2C:
            case I2S:
            case LCMP:
            case FCMPL:
            case FCMPG:
            case DCMPL:
            case DCMPG:
            case IRETURN:
            case LRETURN:
            case FRETURN:
            case DRETURN:
            case ARETURN:
            case RETURN:
            case XXXUNUSEDXXX:
            case ARRAYLENGTH:
            case ATHROW:
            case MONITORENTER:
            case MONITOREXIT:
            case BREAKPOINT:
            case IMPDEP1:
            case IMPDEP2:

                instruction = new SimpleInstruction(opcode);
                break;

            case BIPUSH:
            case LDC:
            case ILOAD:
            case LLOAD:
            case FLOAD:
            case DLOAD:
            case ALOAD:
            case ISTORE:
            case LSTORE:
            case FSTORE:
            case DSTORE:
            case ASTORE:
            case RET:
            case NEWARRAY:

                instruction = new ImmediateByteInstruction(opcode, wide);
                break;

            case LDC_W:
            case LDC2_W:
            case GETSTATIC:
            case PUTSTATIC:
            case GETFIELD:
            case PUTFIELD:
            case INVOKEVIRTUAL:
            case INVOKESPECIAL:
            case INVOKESTATIC:
            case NEW:
            case ANEWARRAY:
            case CHECKCAST:
            case INSTANCEOF:
            case SIPUSH: // the only immediate short instruction that does
                // not have an immediate constant pool reference

                instruction = new ImmediateShortInstruction(opcode);
                break;

            case IFEQ:
            case IFNE:
            case IFLT:
            case IFGE:
            case IFGT:
            case IFLE:
            case IF_ICMPEQ:
            case IF_ICMPNE:
            case IF_ICMPLT:
            case IF_ICMPGE:
            case IF_ICMPGT:
            case IF_ICMPLE:
            case IF_ACMPEQ:
            case IF_ACMPNE:
            case GOTO:
            case JSR:
            case IFNULL:
            case IFNONNULL:

                instruction = new BranchInstruction(opcode);
                break;

            case GOTO_W:
            case JSR_W:

                instruction = new ImmediateIntInstruction(opcode);
                break;

            case IINC:

                instruction = new IncrementInstruction(opcode, wide);
                break;

            case TABLESWITCH:

                instruction = new TableSwitchInstruction(opcode);
                break;

            case LOOKUPSWITCH:

                instruction = new LookupSwitchInstruction(opcode);
                break;

            case INVOKEINTERFACE:

                instruction = new InvokeInterfaceInstruction(opcode);
                break;

            case MULTIANEWARRAY:

                instruction = new MultianewarrayInstruction(opcode);
                break;

            default:

                throw new IOException("invalid opcode 0x" + Integer.toHexString(opcode));
        }

        instruction.read(bcis);
        return instruction;
    }

    private static void debug(String s) throws IOException {
        System.out.println("debug: " + s);

    }

    static String getreturntype(String s) {

        if (s.equals("float")) {
            return "F";
        } else if (s.equals("int")) {
            return "I";
        } else if (s.equals("long") || s.equals("double") || s.equals("boolean") || s.equals("void")) {
            return s;
        }
// System.err.println(s + "----------------");

        return "L";
    }

    private static String getlocal(int index, int offset, String type, LocalVariable[] locales) {
        //   System.out.println("type=" + type + "  localeTypes=" + localeTypes[index] + "  index=" + index);
        offset += 2;
        if (locales[index] != null && matchType(locales[index].type, type) && locales[index].end >= offset && locales[index].start <= offset) {
            return locales[index].name;
        }

        Vector v = CodeAttribute.table;
        int size = v.size();
        LocalVariable ll;

        for (int i = 0; i < size; i++) {
            ll = (LocalVariable) v.elementAt(i);
            if (ll.index == index && ll.start <= offset && ll.end >= offset) {
                locales[index] = ll;
                if (index > (CodeAttribute.arg.length - (CodeAttribute.isstatic ? 1 : 0))) {
                    //   System.out.println(ll.index+"  "+ll.name);
                    return Classsys.change2(ll.type) + " " + ll.name;
                }

                return ll.name;
            }

        }
        locales[index] = new LocalVariable(offset, 10000, index, type + "_" + index, type);
        return type + " " + locales[index].name;
    }

    private static String getcompare(int opcode) {
        String ref = null;
        switch (opcode) {
            case IALOAD:
                ref = "I";
                break;

            case LALOAD:
                ref = "J";
                break;

            case FALOAD:
                ref = "F";
                break;

            case DALOAD:
                ref = "D";
                break;

            case AALOAD:
                ref = "L";
                break;

            case BALOAD:
                ref = "B";
                break;

            case CALOAD:
                ref = "C";
                break;

            case SALOAD:
                ref = "S";
        }

        return ref;
    }

    static String getnew(int sig) {
        String ref = null;
        switch (sig) {
            case IFNULL:
            case IFEQ:
            case IF_ICMPEQ:
            case IF_ACMPEQ:
                ref = "!=";
                break;
            case IFNONNULL:
            case IFNE:
            case IF_ICMPNE:
            case IF_ACMPNE:
                ref = "==";
                break;

            case IFLT:
            case IF_ICMPLT:
                ref = ">=";
                break;

            case IFGE:
            case IF_ICMPGE:
                ref = "<";
                break;

            case IFGT:
            case IF_ICMPGT:
                ref = "<=";
                break;

            case IFLE:
            case IF_ICMPLE:
                ref = ">";
                break;
            case 0:
                ref = "";
                break;
        }
        return ref;
    }

    static String getold(int sig) {
        String ref = null;
        switch (sig) {
            case IFNULL:
            case IFEQ:
            case IF_ICMPEQ:
            case IF_ACMPEQ:
                ref = "==";
                break;
            case IFNONNULL:
            case IFNE:
            case IF_ICMPNE:
            case IF_ACMPNE:
                ref = "!=";
                break;

            case IFLT:
            case IF_ICMPLT:
                ref = "<";
                break;

            case IFGE:
            case IF_ICMPGE:
                ref = ">=";
                break;

            case IFGT:
            case IF_ICMPGT:
                ref = ">";
                break;

            case IFLE:
            case IF_ICMPLE:
                ref = "<=";
                break;
            case 0:
                ref = "";
                break;
        }
        return ref;
    }

    private static boolean matchType(String t, String type) {
        //   System.out.println("match? " + t + " : " + type);
        boolean ss = true;
        if (t.equals("Z")) {
            // return
            ss = type.equals("I");
        }
        //  return
        ss = t.replace('[', 'L').startsWith(type);
        if (!ss) {
            System.err.println(t + "  not match  " + type);
        }
        return ss;
        //  return false;
    }
    /*
    private static int getopcode(Vector v, int now, int offset) {
    int size = v.size();
    AbstractInstruction s = (AbstractInstruction) v.elementAt(now);
    if (s.offset < offset) {
    while (now < size) {
    if ((s = (AbstractInstruction) v.elementAt(now)).offset == offset) {
    return now;
    }

    if (s.offset > offset) {
    return -1;
    }

    now++;
    }

    } else {
    while (now < size) {
    if ((s = (AbstractInstruction) v.elementAt(now)).offset == offset) {
    return now;
    }

    if (s.offset < offset) {
    return -1;
    }

    now--;
    }

    }
    return -1;
    }
     */
}
