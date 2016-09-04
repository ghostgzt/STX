package MJDecompiler;

final class eo extends db {

    final Operation a(Operation ai1, CodeInfo ah1) {
        boolean flag = false;
        boolean flag1 = false;
        Object obj = null;
        Object obj1 = ai1;
        Object obj2 = ai1;
        if (!(ai1 instanceof Iinc)) {
            return null;
        }
        Iinc fp1 = (Iinc) ai1;
        Operation ai2;
        if ((ai2 = ah1.c(ai1)) instanceof bd) {
            ai2 = ((bd) ai2).h;
        }
        Load m1;
        if ((ai2 instanceof Load) && (m1 = (Load) ai2).a(fp1)) {
            flag = true;
            obj1 = m1;
        }
        if (!flag) {
            Operation ai3;
            if ((ai3 = ah1.d(ai1)) instanceof bd) {
                ai3 = ((bd) ai3).h;
            }
            Load m2;
            if ((ai3 instanceof Load) && (m2 = (Load) ai3).a(fp1)) {
                flag = true;
                flag1 = true;
                obj2 = m2;
            }
        }
        int i = fp1.g;
        boolean flag2 = false;
        String s = null;
        switch (i) {
            case 1: // '\001'
                s = "++";
                flag2 = true;
                break;

            case -1:
                s = "--";
                flag2 = true;
                break;

            case 0: // '\0'
            default:
                if (i < 0) {
                    s = "-=";
                    i = -i;
                } else {
                    s = "+=";
                }
                break;
        }
        super.a++;
        bn bn1 = fp1.a((bd[]) null);
        Object obj3 = null;
        if (!flag2) {
            Number du1 = new Number(0, 0, "I", String.valueOf(i));
            bd abd[] = new bd[0];
            bd bd1 = new bd(du1, abd);
            obj3 = new g(bn1, (Operation) (Operation) obj2, s, bd1);
        } else if (flag1) {
            obj3 = new u(bn1, (Operation) (Operation) obj2, s);
        } else {
            obj3 = new b(bn1, (Operation) (Operation) obj2, s);
        }
        Object obj4 = null;
        if (flag) {
            obj4 = obj3;
        } else {
            obj4 = new ao((bd) (bd) obj3);
        }
        ((aj) (aj) obj4).a((Operation) (Operation) obj1).b((Operation) (Operation) obj2);
        ah1.deleteOperation((Operation) (Operation) obj1);
        ah1.deleteOperation((Operation) (Operation) obj2);
        return ah1.setOperation((Operation) (Operation) obj4);
    }
}
