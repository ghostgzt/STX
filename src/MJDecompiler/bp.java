
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;

import java.util.Enumeration;

// Referenced classes of package MJDecompiler:
//            bn, ar, bh, bj, 
//            ch

final class bp extends bn
{

    bp(ConstantPool bh1, bn bn1, ClassFile bj1, MethodDescriptor ar1, int i)
    {
        super(bn1.b, 26);
        a = bh1;
        d = bn1;
        e = bj1;
        f = ar1;
        g = i;
        if(c())
            super.c = 100;
    }

    final boolean c()
    {
        boolean flag = false;
        if(a == e.thisClass)
        {
            flag = true;
            if(f != null)
            {
                String s = d.toString();
                Enumeration enumeration = f.c(g);
                do
                {
                    if(!enumeration.hasMoreElements() || !flag)
                        break;
                    LocalVariableTable ch1;
                    if((ch1 = (LocalVariableTable)enumeration.nextElement()).b(g) && ch1.name.equals(s))
                        flag = false;
                } while(true);
            }
        }
        return flag;
    }

    public final String toString()
    {
        if(c())
            return d.toString();
        else
            return e.c(a.toString()) + "." + d;
    }

    ConstantPool a;
    bn d;
    ClassFile e;
    MethodDescriptor f;
    int g;
}
