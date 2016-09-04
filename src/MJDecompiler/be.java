
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;

import java.io.IOException;

// Referenced classes of package MJDecompiler:
//            cd, bf, bh, bj, 
//            bc

final class be extends cd
{

    be(ClazzInputStream bc, ClassFile bj1)
        throws IOException
    {
        super(bc, bj1);
    }

    final void writeBytecode(ByteCodeOutput bf1)
        throws IOException
    {
        bf1.writeByte(11);
        bf1.writeUshort(super.b);
        bf1.writeUshort(super.o);
    }

    final String a(ClassFile bj1)
    {
        return "InterfaceMethodRef(" + super.b + ":" + bj1.getConstant(super.b).a(bj1) + ", " + super.o + ":" + bj1.getConstant(super.o).a(bj1) + ")";
    }
}
