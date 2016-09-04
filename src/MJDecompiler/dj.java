
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, aj, 
//            ao, au, bd, bh, 
//            bn, cu, dc, dq, 
//            dr, ev, g

final class dj extends db
{

    private boolean a(Operation ai1, String s, String s1)
    {
        if(!(ai1 instanceof InvokeVirtual))
            return false;
        InvokeVirtual dc1;
        ConstantPool bh1;
        if(!(bh1 = (dc1 = (InvokeVirtual)ai1).g()).next().name().equals(s))
            return false;
        else
            return bh1.name().equals(s1);
    }

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        boolean flag = false;
        Dup dq1 = null;
        Dup dq2 = null;
        Get au1 = null;
        Object obj;
        if(!((obj = ai1) instanceof Put))
            return null;
        Put cu1;
        int i = (cu1 = (Put)obj).e();
        if((obj = ah1.c((Operation)obj)) instanceof Dup)
        {
            flag = true;
            if((dq1 = (Dup)obj).f != i - 1)
                return null;
            if(dq1.e() - dq1.f != 1)
                return null;
            obj = ah1.c((Operation)obj);
        }
        if(!a((Operation)(Operation)obj, "StringBuffer", "toString"))
            return null;
        InvokeVirtual dc1 = (InvokeVirtual)obj;
        int j = 0;
        do
        {
            obj = ah1.c((Operation)(Operation)obj);
            if(!a((Operation)(Operation)obj, "StringBuffer", "append"))
                return null;
            if((obj = ah1.c((Operation)(Operation)obj)) instanceof bd)
                j++;
            else
            if(!(obj instanceof Swap))
                return null;
        } while(obj instanceof bd);
        if(j < 1)
            return null;
        InvokeVirtual adc[] = new InvokeVirtual[j + 1];
        bd abd[] = new bd[j];
        obj = dc1;
        for(int k = 0; k < j; k++)
        {
            obj = ah1.c((Operation)(Operation)obj);
            adc[k] = (InvokeVirtual)obj;
            obj = ah1.c((Operation)(Operation)obj);
            abd[k] = (bd)obj;
        }

        obj = ah1.c((Operation)obj);
        adc[j] = (InvokeVirtual)obj;
        if(!((obj = ah1.c((Operation)obj)) instanceof Swap))
            return null;
        Swap dr1 = (Swap)obj;
        if(!((obj = ah1.c((Operation)obj)) instanceof bd))
            return null;
        bd bd1;
        if(!(bd1 = (bd)obj).b().toString().equals("new StringBuffer()"))
            return null;
        Operation ai3;
        if((ai3 = (Operation)(Operation)(obj = ah1.c((Operation)obj))) instanceof bd)
            ai3 = ((bd)ai3).h;
        if(!(ai3 instanceof Get))
            return null;
        if(!(au1 = (Get)ai3).a(cu1))
            return null;
        Operation ai2;
        obj = ah1.c((Operation)(ai2 = ((Operation) (obj))));
        if(i > 1)
        {
            if(!(obj instanceof Dup))
                return null;
            if((dq2 = (Dup)obj).f != 0)
                return null;
            if(i - 1 != dq2.e() - dq2.f)
                return null;
            obj = ah1.c((Operation)(ai2 = ((Operation) (obj))));
        }
        bd abd1[] = new bd[i - 1];
        for(int l = 1; l < i; l++)
        {
            if(!(obj instanceof bd))
                return null;
            abd1[i - l - 1] = (bd)obj;
            obj = ah1.c((Operation)(ai2 = ((Operation) (obj))));
        }

        super.a++;
        bd bd2 = null;
        for(int i1 = j - 1; i1 >= 0; i1--)
            if(bd2 == null)
            {
                bd2 = abd[i1];
            } else
            {
                bd abd2[] = {
                    bd2, abd[i1]
                };
                CaozuoTwo ev1 = new CaozuoTwo(0, 0, "Ljava/lang/String;", "+");
                bd2 = new bd(ev1, abd2);
            }

        bn bn1 = cu1.a(abd1);
        g g1 = new g(bn1, cu1, "+=", bd2);
        Object obj1;
        if(flag)
            obj1 = g1;
        else
            obj1 = new ao(g1);
        ((aj)(aj)obj1).a((Operation)(Operation)ai2).b(cu1);
        ah1.deleteOperation(cu1);
        ah1.deleteOperation(dq1);
        ah1.deleteOperation(dc1);
        for(int j1 = 0; j1 < adc.length; j1++)
            ah1.deleteOperation(adc[j1]);

        for(int k1 = 0; k1 < abd.length; k1++)
            ah1.deleteOperation(abd[k1]);

        ah1.deleteOperation(dr1);
        ah1.deleteOperation(bd1);
        ah1.deleteOperation(au1);
        ah1.deleteOperation(dq2);
        for(int l1 = 0; l1 < i - 1; l1++)
            ah1.deleteOperation(abd1[l1]);

        return ah1.setOperation((Operation)(Operation)obj1);
    }

    dj()
    {
    }
}
