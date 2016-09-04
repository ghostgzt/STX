package MJDecompiler;

import java.io.IOException;

final class LookupSwitch extends Switch {

    LookupSwitch(int i, int j, ClazzInputStream bc1) throws IOException {
        super(i, j);
        super.h = i + bc1.readInt();
        int k = bc1.readInt();
        f = new int[k];
        super.i = new int[k];
        for (int l = 0; l < k; l++) {
            f[l] = bc1.readInt();
            super.i[l] = i + bc1.readInt();
        }

    }

    public final int size() {
        return super.size + 8 * (super.i.length + 1);
    }

    final int a(int i) {
        return f[i - 1];
    }
    protected int f[];
}
