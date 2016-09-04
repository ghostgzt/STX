package MJDecompiler;

import java.io.IOException;

final class TableSwitch extends Switch {

    TableSwitch(int i, int j, ClazzInputStream in)throws IOException {
        super(i, j);
        super.h = i + in.readInt();
        f = in.readInt();
        g = in.readInt();
        super.i = new int[(g - f) + 1];
        for (int k = 0; k < super.i.length; k++) {
            super.i[k] = i + in.readInt();
        }

    }

    public final int size() {
        return super.size + 4 * (super.i.length + 3);
    }

    final int a(int i) {
        return (f + i) - 1;
    }
    protected int f;
    protected int g;
}
