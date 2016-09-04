package MJDecompiler;

final class ee extends db {

    final Operation a(Operation ai1, CodeInfo ah1) {
        if (ai1.f() != 1) {
            return null;
        }
        int i;
        bd abd[] = new bd[i = ai1.e()];
        Operation ai2 = ai1;
        for (int j = 0; j < i; j++) {
            if (!((ai2 = ah1.c(ai2)) instanceof bd)) {
                return null;
            }
            abd[i - j - 1] = (bd) ai2;
        }

        super.a++;
        bd bd1 = new bd(ai1, abd);
        ah1.deleteOperation(ai1);
        for (int k = 0; k < i; k++) {
            ah1.deleteOperation(abd[k]);
        }

        return ah1.setOperation(bd1);
    }

    
}
