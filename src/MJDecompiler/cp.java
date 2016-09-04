package MJDecompiler;

final class cp extends db {

    final Operation a(Operation ai1, CodeInfo ah1) {
        if (!(ai1 instanceof If)) {
            return null;
        }
        IfGoto ct1 = (IfGoto) ai1;
        Operation ai2;
        if (!((ai2 = ah1.c(ai1)) instanceof bd)) {
            return null;
        }
        bd bd1;
        Operation ai3 = (bd1 = (bd) ai2).h;
        bd bd2 = bd1;

        if (ai3 instanceof Compare) {
            ((Compare) ai3).a(ct1.h);
        } else if (bd1.b().b.equals("Z")) {
            if (ct1.h.equals("==")) {
                bd2 = bd2.s();
            } else if (!ct1.h.equals("!=")) {
                return null;
            }
        } else {
            bd abd[];
            (abd = new bd[2])[0] = bd1;
            Number du1 = new Number(0, 0, "I", "0");
            bd abd1[] = new bd[0];
            abd[1] = new bd(du1, abd1);
            CaozuoTwo ev1 = new CaozuoTwo(ct1.index(), 0, "Z", ct1.h);
            bd2 = new bd(ev1, abd);
        }
        super.a++;
        aj abd = ( (aj) (new af(bd2, ct1.b(0), ct1.b(1)))).a(bd1);
        ah1.deleteOperation(bd1);
        ah1.deleteOperation(ct1);
        return ah1.setOperation((Operation) (Operation) abd);
    }
}
