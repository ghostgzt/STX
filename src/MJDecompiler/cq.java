package MJDecompiler;

final class cq extends db {

    final int a(eg aeg[], int i, int j) {
        int k;
        if (aeg[i].a == j) {
            k = 0x7ffffffe;
        } else if (aeg[i].b == -1) {
            k = aeg[i].a;
        } else if (aeg[i].b == j) {
            k = aeg[i].a;
        } else if (aeg[i].d >= 0) {
            k = a(aeg, aeg[i].d, j) - 1;
        } else {
            k = -1;
        }
        if (k >= 0 && i == 0) {
            k = 0x7fffffff;
        }
        return k;
    }

    final Operation a(Operation ai1, CodeInfo ah1) {
        Operation ai2;
        if (!((ai2 = ai1) instanceof Switch)) {
            return null;
        }
        Switch ci1 = (Switch) ai2;
        if (!((ai2 = ah1.c(ai1)) instanceof bd)) {
            return null;
        }
        bd bd1 = (bd) ai2;
        eg aeg[] = new eg[ci1.m()];
        int i = 0;
        for (int j = 0; j < aeg.length; j++) {
            int l = ci1.b(j);
            int l1;
            for (l1 = 0; l1 < i && aeg[l1].a != l; l1++);
            if (l1 == i) {
                aeg[i++] = new eg(l);
            }
        }

        int k = -1;
        for (int i1 = 0; k < 0 && i1 < 2; i1++) {
            for (int i2 = 0; i2 < i; i2++) {
                Operation ai4;
                if (!((ai4 = ah1.getOperation(aeg[i2].a)) instanceof am)) {
                    return null;
                }
                int j3 = -1;
                switch (ai4.m()) {
                    case 0: // '\0'
                        aeg[i2].b = -1;
                        break;

                    case 1: // '\001'
                        aeg[i2].b = ai4.b(0);
                        int k3;
                        for (k3 = 0; k3 < i && aeg[k3].a != aeg[i2].b; k3++);
                        if (k3 < i) {
                            aeg[i2].d = k3;
                            aeg[k3].c++;
                            if (aeg[k3].c > 1) {
                                j3 = aeg[k3].a;
                            }
                        } else if (i1 > 0) {
                            j3 = aeg[i2].b;
                        }
                        break;

                    default:
                        j3 = aeg[i2].a;
                        break;
                }
                if (j3 < 0) {
                    continue;
                }
                if (k < 0) {
                    k = j3;
                    continue;
                }
                if (j3 != k) {
                    return null;
                }
            }

        }

        for (int j1 = 0; j1 < i; j1++) {
            aeg[j1].e = a(aeg, j1, k);
            if (aeg[j1].e < 0) {
                return null;
            }
        }

        super.a++;
        for (int k1 = 0; k1 < i - 1; k1++) {
            int j2 = k1;
            for (int k2 = k1 + 1; k2 < i; k2++) {
                if (aeg[k2].e < aeg[j2].e) {
                    j2 = k2;
                }
            }

            if (j2 != k1) {
                eg eg1 = aeg[k1];
                aeg[k1] = aeg[j2];
                aeg[j2] = eg1;
            }
        }

        an an1;
        (an1 = new an(bd1)).a(0, k);
        Operation ai3 = null;
        if (k >= 0) {
            (ai3 = ah1.getOperation(k)).addIndex();
        }
        for (int l2 = 0; l2 < i; l2++) {
            Operation ai5;
            String s1;
            if (aeg[l2].a == k) {
                ai5 = null;
                s1 = "B";
                if (ai3 != null) {
                    for (int l3 = 0; l3 < ci1.m(); l3++) {
                        if (ci1.b(l3) == aeg[l2].a) {
                            ai3.minusIndex();
                        }
                    }

                }
                if (l2 == i - 1) {
                    break;
                }
            } else {
                ai5 = ah1.getOperation(aeg[l2].a);
                if (aeg[l2].b == -1) {
                    s1 = "R";
                } else if (aeg[l2].b == k) {
                    s1 = "B";
                    if (ai3 != null) {
                        ai3.minusIndex();
                    }
                } else {
                    s1 = "F";
                }
            }
            s s2 = new s(s1, (am) ai5);
            if (aeg[l2].e != 0x7fffffff) {
                for (int i4 = 1; i4 < ci1.m(); i4++) {
                    if (ci1.b(i4) == aeg[l2].a) {
                        s2.a(ci1.a(i4));
                    }
                }

            }
            an1.a(s2);
        }

        ah1.deleteOperation(bd1);
        ah1.deleteOperation(ci1);
        for (int i3 = 0; i3 < i; i3++) {
            if (aeg[i3].a != k) {
                ah1.deleteOperation(aeg[i3].a);
            }
        }

        return ah1.setOperation(an1);
    }

    cq() {
    }
}
