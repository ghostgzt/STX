
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;

import java.util.Enumeration;

// Referenced classes of package MJDecompiler:
//            bn, ar, ch

final class bt extends bn
{

    bt(bn bn1, bn bn2, MethodDescriptor ar1, int i)
    {
        super(bn2.b, 26);
        a = bn1;
        d = bn2;
        e = ar1;
        f = i;
        if(c())
            super.c = 100;
    }

    final boolean c()
    {
        boolean flag = false;
        if(a.toString().equals("this"))
        {
            flag = true;
            if(e != null)
            {
                String s = d.toString();
                Enumeration enumeration = e.c(f);
                do
                {
                    if(!enumeration.hasMoreElements() || !flag)
                        break;
                    LocalVariableTable ch1;
                    if((ch1 = (LocalVariableTable)enumeration.nextElement()).name.toString().equals(s))
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
            return a.a(super.c) + "." + d;
    }

    bn a;
    bn d;
    MethodDescriptor e;
    int f;
}
