
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;



// Referenced classes of package MJDecompiler:
//            am, aj, bd, bz, 
//            ce, cr, s

final class an extends am
{

    an(bd bd1)
    {
        a = bd1;
        a(bd1);
    }

    public final int a()
    {
        int i = l();
        for(int j = 0; j < e.length; j++)
            if(i < e[j].a())
                i = e[j].a();

        return i;
    }

    public final void a(bz bz1)
    {
        cr cr1 = new cr(bz1, l(), a());
        for(int i = 0; i < e.length; i++)
            e[i].a(cr1);

    }

    public final void c()
    {
        a.c();
        for(int i = 0; i < e.length; i++)
            e[i].c();

    }

    final boolean a(ce ce1, int i)
    {
        boolean flag = false;
        for(int j = 0; j < e.length; j++)
            if(e[j].a(ce1, i + 1))
                flag = true;

        return flag;
    }

    final void a(s s1)
    {
        int i;
        s as[] = new s[(i = e == null ? 0 : e.length) + 1];
        for(int j = 0; j < i; j++)
            as[j] = e[j];

        as[i] = s1;
        e = as;
    }

    public final int p()
    {
        return e.length + 3;
    }

    public final void a(Pstream printstream, int i)
    {
        printstream.println(aj.d(i) + "switch (" + a + ")");
        printstream.println(aj.d(i) + "{");
        for(int j = 0; j < e.length; j++)
        {
            if(j > 0)
                printstream.println();
            e[j].a(printstream, i, true);
        }

        printstream.println(aj.d(i) + "}");
    }

    bd a;
    s e[];
}
