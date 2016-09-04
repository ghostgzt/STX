package MJDecompiler;

final class InvokeSpecial extends InvokeVirtual {

    InvokeSpecial(int i, int j, int k) {
        super(i, j, k);
    }

    final boolean d() {

        return (super.g.getConstant(super.f)).name().equals("<init>");
    }

    final boolean a(bn bn1) {
        if (!bn1.toString().equals("this")) {
            return false;
        }
        return (super.g.getConstant(super.f).next()) != super.g.thisClass;
    }

    final boolean b(bn bn1) {
        if (!bn1.toString().equals("this")) {
            return false;
        }
        ConstantPool bh1;
        ConstantPool bh2;
        return (bh2 = (bh1 = super.g.getConstant(super.f)).next()) == super.g.thisClass;
    }

    final void a(Pstream printstream) {
        ConstantPool bh1;
        ConstantPool bh2 = (bh1 = super.g.getConstant(super.f)).next();
        printstream.println("invoke " + bh2 + "." + bh1);
    }
}
