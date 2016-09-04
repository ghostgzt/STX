
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;



// Referenced classes of package MJDecompiler:
//            am, aj, bz, cr, 
//            q

final class ae extends am
{

    ae(am am1, q aq[], am am2)
    {
        a = am1;
        e = aq;
        f = am2;
        a(am1);
        b(am1);
    }

    public final void addIndex()
    {
        super.b++;
        a.addIndex();
    }

    public final void minusIndex()
    {
        super.b--;
        a.minusIndex();
    }

    final am b()
    {
        return a;
    }

    final q[] d()
    {
        return e;
    }

    final am r()
    {
        return f;
    }

    public final int l()
    {
        return a.l();
    }

    public final int a()
    {
        int j = a.a();
        if(e != null)
        {
            for(int k = 0; k < e.length; k++)
                if(j < e[k].a())
                    j = e[k].a();

        }
        if(f != null && j < f.a())
            j = f.a();
        return j;
    }

    public final void a(bz bz1)
    {
        cr cr1 = new cr(bz1, l(), a());
        a.a(cr1);
        if(e != null)
        {
            for(int j = 0; j < e.length; j++)
                e[j].a(cr1);

        }
        if(f != null)
            f.a(cr1);
    }

    public final void c()
    {
        a.c();
        if(e != null)
        {
            for(int j = 0; j < e.length; j++)
                e[j].c();

        }
        if(f != null)
            f.c();
    }

    public final int p()
    {
        int j = a.p();
        if(e != null)
        {
            for(int k = 0; k < e.length; k++)
                j += e[k].p();

        }
        if(f != null)
            j += f.p();
        return j;
    }

    public final void a(Pstream printstream, int j)
    {
        printstream.println(aj.d(j) + "try");
        printstream.println(aj.d(j) + "{");
        a.a(printstream, j + 1, true);
        printstream.println(aj.d(j) + "}");
        if(e != null)
        {
            for(int k = 0; k < e.length; k++)
                e[k].a(printstream, j, true);

        }
        if(f != null)
        {
            printstream.println(aj.d(j) + "finally");
            f.a(printstream, j + 1, false);
        }
    }

    am a;
    q e[];
    am f;
}
