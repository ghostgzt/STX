package MJDecompiler;

final class di extends db {

    final Operation a(Operation ai1, CodeInfo ah1) {
        if (!(ai1 instanceof InvokeVirtual)) {
            return null;
        }
        if (ai1.f() > 0) {
            return null;
        }
        InvokeVirtual dc1;
        if (!(dc1 = (InvokeVirtual) ai1).d()) {
            return null;
        }
        int i;
        bd abd[] = new bd[i = ai1.e() - 1];
        Operation ai2 = ai1;
        for (int j = 0; j < i; j++) {
            if (!((ai2 = ah1.c(ai2)) instanceof bd)) {
                return null;
            }
            abd[i - j - 1] = (bd) ai2;
        }

        Dup dq1 = null;
        if ((ai2 = ah1.c(ai2)) instanceof Dup) {
            if ((dq1 = (Dup) ai2).f != 0) {
                return null;
            }
            ai2 = ah1.c(ai2);
        }
        if (!(ai2 instanceof bd)) {
            return null;
        }
        bd bd1;
        Operation ai3;
        if (!((ai3 = (bd1 = (bd) ai2).h) instanceof New)) {
            return null;
        }
        super.a++;
        w w1 = new w(bd1, abd, dc1);
        Object obj;
        if (dq1 == null) {
            obj = new ao(w1);
        } else {
            obj = w1;
        }
        ah1.deleteOperation(bd1);
        ah1.deleteOperation(dq1);
        for (int k = 0; k < i; k++) {
            ah1.deleteOperation(abd[k]);
        }

        ah1.deleteOperation(dc1);
        return ah1.setOperation((Operation) (Operation) obj);
    }
}
