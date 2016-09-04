
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, aj, 
//            bd, bh, dc, ev

final class dx extends db
{

    private boolean a(Operation ai1, String s, String s1)
    {
        if(!(ai1 instanceof bd))
            return false;
        bd bd1;
        Operation ai2;
        if(!((ai2 = (bd1 = (bd)ai1).h) instanceof InvokeVirtual))
            return false;
        InvokeVirtual dc1;
        ConstantPool bh1;
        if(!(bh1 = (dc1 = (InvokeVirtual)ai2).g()).next().name().equals(s))
            return false;
        else
            return bh1.name().equals(s1);
    }

    private int a(bd bd1)
    {
        if(a(((Operation) (bd1)), "StringBuffer", "<init>"))
            return 0;
        if(a(((Operation) (bd1)), "StringBuffer", "append"))
            return a(bd1.a(0)) + 1;
        else
            return -1000;
    }

    private bd a(bd bd1, bd bd2, bd bd3)
    {
        if(a(((Operation) (bd1)), "StringBuffer", "append"))
            bd2 = a(bd1.a(0), bd1.a(1), bd2);
        if(bd3 == null)
        {
            return bd2;
        } else
        {
            bd abd[] = {
                bd2, bd3
            };
            CaozuoTwo ev1 = new CaozuoTwo(0, 0, "Ljava/lang/String;", "+");
            return new bd(ev1, abd);
        }
    }

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!a(ai1, "StringBuffer", "toString"))
            return null;
        bd bd1 = (bd)ai1;
        int i;
        if((i = a(bd1.a(0))) < 2)
        {
            return null;
        } else
        {
            super.a++;
            Object obj;
            obj = ((aj)(aj)(obj = a(bd1.a(0), ((bd) (null)), ((bd) (null))))).a(ai1).b(ai1);
            ah1.deleteOperation(ai1);
            return ah1.setOperation((Operation)(Operation)obj);
        }
    }

    dx()
    {
    }
}
