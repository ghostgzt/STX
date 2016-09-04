package MJDecompiler;

final class OperationEnum {

    protected Operation ops[];
    protected int b;

    OperationEnum(Operation aai[]) {
        ops = aai;
        b = 0;
    }

    final boolean haveMoreElement() {
        for (int i = b; i < ops.length; i++) {
            if (ops[i] != null) {
                return true;
            }
        }

        return false;
    }

    final Operation NextElement() {
        while (b < ops.length) {
            if (ops[b++] != null) {
                return ops[b - 1];
            }
        }
        return null;
    }

    final void a(Operation ai1) {
        if (ai1 == null) {
            b = 0;
            return;
        } else {
            b = ai1.index() + 1;
            return;
        }
    }
}
