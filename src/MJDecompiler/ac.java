
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;



// Referenced classes of package MJDecompiler:
//            am, aj, bd, bz, 
//            ce, cr

final class ac extends am
{

    ac(bd bd1, am am1)
    {
        a = new bd[1];
        e = new am[2];
        a[0] = bd1;
        e[0] = am1;
        e[1] = null;
    }

    ac(bd bd1, am am1, am am2)
    {
        if(am2 != null && (am2 instanceof ac))
        {
            ac ac1;
            int i = (ac1 = (ac)am2).a.length;
            a = new bd[i + 1];
            e = new am[i + 2];
            for(int j = 0; j < i; j++)
                a[j + 1] = ac1.a[j];

            for(int k = 0; k <= i; k++)
                e[k + 1] = ac1.e[k];

        } else
        {
            a = new bd[1];
            e = new am[2];
            e[1] = am2;
        }
        a[0] = bd1;
        e[0] = am1;
    }

    public final int a()
    {
        int i = l();
        for(int j = 0; j < e.length; j++)
            if(e[j] != null && i < e[j].a())
                i = e[j].a();

        return i;
    }

    public final void a(bz bz1)
    {
        cr cr1 = new cr(bz1, l(), a());
        for(int i = 0; i < e.length; i++)
            if(e[i] != null)
                e[i].a(cr1);

    }

    public final void c()
    {
        for(int i = 0; i < a.length; i++)
        {
            a[i] = a[i].a("Z");
            a[i].c();
        }

        for(int j = 0; j < e.length; j++)
            if(e[j] != null)
                e[j].c();

    }

    final boolean a(ce ce1, int i)
    {
        boolean flag = false;
        for(int j = 0; j < e.length; j++)
            if(e[j] != null && e[j].a(ce1, i))
                flag = true;

        return flag;
    }

    public final int p()
    {
        int i = 0;
        for(int j = 0; j < e.length; j++)
            if(e[j] != null)
                i += e[j].p();

        return i;
    }

    public final void a(Pstream printstream, int i)
    {
        printstream.println(aj.d(i) + "if (" + a[0] + ")");
        e[0].a(printstream, i + 1, false);
        for(int j = 1; j < a.length; j++)
        {
            printstream.println(aj.d(i) + "else if (" + a[j] + ")");
            e[j].a(printstream, i + 1, false);
        }

        if(e[a.length] != null)
        {
            printstream.println(aj.d(i) + "else");
            e[a.length].a(printstream, i + 1, false);
        }
    }

    bd a[];
    am e[];
}
