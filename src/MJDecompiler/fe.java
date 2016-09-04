
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, af, ah, ai, 
//            am, ao, ce, x

final class fe extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof af))
            return null;
        int i = ai1.index();
        int j;
        int k;
        int l;
        if(ai1.b(0) <= i)
        {
            j = ai1.b(0);
            k = i;
            l = ai1.b(1);
        } else
        if(ai1.b(1) <= i)
        {
            j = ai1.b(1);
            k = i;
            l = ai1.b(0);
        } else
        {
            return null;
        }
        if(ah1.d(l) != null)
            return null;
        int i1 = 0;
        int j1 = 0;
        int k1 = 0;
        Operation ai2 = null;
        for(OperationEnum x1 = ah1.c(); x1.haveMoreElement();)
        {
            Operation ai3 = x1.NextElement();
            int i2 = 0;
            while(i2 < ai3.m()) 
            {
                if(ai3.b(i2) == i)
                    if(ai3.index() < j)
                        i1++;
                    else
                    if(ai3.index() < k)
                    {
                        j1++;
                        ai2 = ai3;
                    } else
                    {
                        k1++;
                    }
                i2++;
            }
        }

        String s = "do/while";
        int l1;
        am am1;
        if(k1 > 0)
            l1 = -1;
        else
        if(i1 == 0)
            l1 = i;
        else
        if(j1 > 2)
            l1 = i;
        else
        if(ai2 instanceof am)
        {
            if((am1 = (am1 = (am)ai2).a(am1.g() - 1)) instanceof ao)
            {
                l1 = am1.index();
                s = "for";
            } else
            {
                l1 = i;
            }
        } else
        {
            l1 = -1;
        }
        super.a++;
        ah1.a(l, new ce(i, j, k, "for/do/while"));
        if(l1 >= 0)
            ah1.b(l1, new ce(i, j + 1, l1, s));
        return ai1;
    }

    fe()
    {
    }
}
