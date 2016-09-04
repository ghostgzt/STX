
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;

import java.io.IOException;

// Referenced classes of package MJDecompiler:
//            bm, bf, bh, bj, 
//            bc

class cd extends CpField
{

    cd(ClazzInputStream bc, ClassFile bj1)
        throws IOException
    {
        super(bc, bj1);
    }

    void writeBytecode(ByteCodeOutput bf1)
        throws IOException
    {
        bf1.writeByte(10);
        bf1.writeUshort(super.b);
        bf1.writeUshort(super.o);
    }

    final String e()
    {
        return super.a.getConstant(super.o).type();
    }

    String a(ClassFile bj1)
    {
        return "MethodRef(" + super.b + ":" + bj1.getConstant(super.b).a(bj1) + ", " + super.o + ":" + bj1.getConstant(super.o).a(bj1) + ")";
    }
}
