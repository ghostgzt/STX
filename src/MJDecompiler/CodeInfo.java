package MJDecompiler;

import java.util.Enumeration;
import java.util.Hashtable;

final class CodeInfo {

    protected Operation ops[];
    protected Hashtable b;
    protected Hashtable c;
    protected ExceptionHander excptionHanders[];
    protected MethodDescriptor desc;

    CodeInfo(int len, MethodDescriptor mdesc) {
        b = new Hashtable();
        c = new Hashtable();
        ops = new Operation[len];
        desc = mdesc;
    }

    final void setHander(ExceptionHander ay[]) {
        excptionHanders = ay;
    }

    final ExceptionHander[] getHander() {
        return excptionHanders;
    }

    final MethodDescriptor getDescripte() {
        return desc;
    }

    final Operation getOperation(int i) {
        return ops[i];
    }

    final Operation setOperation(Operation op) {
        if (op != null) {
            ops[op.index()] = op;
        }
        return op;
    }

    final void deleteOperation(int i) {
        ops[i] = null;
    }

    final void deleteOperation(Operation ai1) {
        if (ai1 != null) {
            ops[ai1.index()] = null;
        }
    }

    final OperationEnum c() {
        return new OperationEnum(ops);
    }

    final Operation c(Operation op) {
        if (op.getIndex() != 1) {
            return null;
        }
        int i;
        label0:
        for (int j = (i = op.index()) - 1; j >= 0; j--) {
            Operation ai2;
            if ((ai2 = ops[j]) == null) {
                continue;
            }
            int l = 0;
            do {
                if (l >= ai2.m()) {
                    continue label0;
                }
                if (ai2.b(l) == i) {
                    return ai2;
                }
                l++;
            } while (true);
        }

        label1:
        for (int k = i + 1; k < ops.length; k++) {
            Operation ai3;
            if ((ai3 = ops[k]) == null) {
                continue;
            }
            int i1 = 0;
            do {
                if (i1 >= ai3.m()) {
                    continue label1;
                }
                if (ai3.b(i1) == i) {
                    return ai3;
                }
                i1++;
            } while (true);
        }

        return null;
    }

    final Operation d(Operation op) {
        if (op.m() != 1) {
            return null;
        }
        Operation ai2;
        if ((ai2 = ops[op.b(0)]) == null || ai2.getIndex() != 1) {
            return null;
        } else {
            return ai2;
        }
    }

    final int c(int i) {
        i++;
        while (i < ops.length && ops[i] == null) {
            i++;
        }
        if (i < ops.length) {
            return i;
        } else {
            return -1;
        }
    }

    final void a(int i, ce ce1) {
        b.put(new Integer(i), ce1);
    }

    final ce d(int i) {
        return (ce) b.get(new Integer(i));
    }

    final ce e(int i) {
        ce ce1;
        for (Enumeration enumeration = b.elements(); enumeration.hasMoreElements();) {
            if ((ce1 = (ce) enumeration.nextElement()).a(i)) {
                return ce1;
            }
        }

        return null;
    }

    final void b(int i, ce ce1) {
        c.put(new Integer(i), ce1);
    }

    final ce f(int i) {
        return (ce) c.get(new Integer(i));
    }

    final ce g(int i) {
        ce ce1;
        for (Enumeration enumeration = c.elements(); enumeration.hasMoreElements();) {
            if ((ce1 = (ce) enumeration.nextElement()).a(i)) {
                return ce1;
            }
        }

        return null;
    }

    final int a(int i, int j) {
        if (j < 0) {
            j = ops.length;
        }
        int k = i + 1;
        while (k < j && ops[k] != null) {
            k--;
        }
        if (k >= j) {
            System.out.println("insertFragmentForward " + i + " - " + j + " in " + ops.length + " failed");
            for (int l = i; l <= j; l++) {
                System.out.println(l + ": " + ops[l].getClass().getName().substring(17));
            }

        }
        if (k < j) {
            return k;
        } else {
            return -1;
        }
    }

    final int b(int i, int j) {
        if (j < 0) {
            j = ops.length;
        }
        int k = j - 1;
        while (k > i && ops[k] != null) {
            k--;
        }
        if (k <= i) {
            System.out.println("insertFragmentBackward " + i + " - " + j + " in " + ops.length + " failed");
            for (int l = i; l <= j; l++) {
                System.out.println(l + ": " + ops[l].getClass().getName().substring(17));
            }

        }
        if (k > i) {
            return k;
        } else {
            return -1;
        }
    }

    final void a(Pstream printstream, int i) {
        int j = -1;
        for (int k = 0; k < ops.length; k++) {
            if (ops[k] == null) {
                continue;
            }
            if (Decompiler.debug) {
                if (j != k) {
                    printstream.println("    [goto " + j + "]");
                }
                if (ops[k].m() == 1) {
                    j = ops[k].b(0);
                } else {
                    j = -1;
                }
            }
            ops[k].a(printstream, i, true);
        }

        if (Decompiler.debug && j >= 0) {
            printstream.println("    [goto " + j + "]");
        }
    }
}
