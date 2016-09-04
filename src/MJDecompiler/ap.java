
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


import java.util.Vector;

// Referenced classes of package MJDecompiler:
//            am, ao, bd, bz, 
//            ce, cg, f, g

final class ap extends am
{

    ap(am aam[])
    {
        a(aam[0]);
        b(aam[aam.length - 1]);
        a = aam;
    }

    public final void addIndex()
    {
        super.b++;
        if(a.length > 0)
            a[0].addIndex();
    }

    public final void minusIndex()
    {
        super.b--;
        if(a.length > 0)
            a[0].minusIndex();
    }

    public final int a()
    {
        if(a.length > 0)
            return a[a.length - 1].a();
        else
            return l();
    }

    public final void a(bz bz1)
    {
        e = new bz(bz1, l(), a());
        for(int j = 0; j < a.length; j++)
            a[j].a(e);

    }

    public final void c()
    {
        for(int j = 0; j < a.length; j++)
            a[j].c();

    }

    final boolean a(ce ce1, int j)
    {
        boolean flag = false;
        for(int k = 0; k < a.length; k++)
            if(a[k].a(ce1, j))
                flag = true;

        return flag;
    }

    final int g()
    {
        return a.length;
    }

    final am a(int j)
    {
        return a[j];
    }

    public final int p()
    {
        int j = 0;
        for(int k = 0; k < a.length; k++)
            j += a[k].p();

        return j;
    }

    public final void a(Pstream printstream, int j)
    {
        b();
        if(e != null)
            e.a(printstream, j, "sequence");
        for(int k = 0; k < a.length; k++)
            a[k].a(printstream, j, true);

    }

    final void b()
    {
        Vector vector = e.c;
        for(int j = 0; j < vector.size();)
        {
            cg cg1;
            int k = (cg1 = (cg)vector.elementAt(j)).a();
            for(int l = 0; cg1 != null && l < a.length; l++)
            {
                am am1;
                ao ao1;
                bd bd1;
                if((am1 = a[l]).a() == k - 1 && (am1 instanceof ao) && ((bd1 = (ao1 = (ao)am1).a) instanceof g))
                {
                    LocalVariableTable ch = cg1.a(k);
                    bd bd2 = bd1.a(0);
                    f f1;
                    (f1 = new f(ch, bd2)).a(a[l]);
                    f1.b(a[l]);
                    a[l] = f1;
                    cg1 = null;
                }
            }

            if(cg1 == null)
                vector.removeElementAt(j);
            else
                j++;
        }

    }

    am a[];
    bz e;
}
