
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;



// Referenced classes of package MJDecompiler:
//            am, aj, bd, bz

final class ag extends am
{

    ag(bd bd1, am am1)
    {
        a = bd1;
        e = am1;
        a(bd1);
        b(am1);
    }

    public final int l()
    {
        return a.l();
    }

    public final int a()
    {
        return e.a();
    }

    public final void a(bz bz)
    {
        e.a(bz);
    }

    public final void c()
    {
        a.c();
        e.c();
    }

    public final int p()
    {
        return e.p();
    }

    public final void a(Pstream printstream, int i)
    {
        printstream.println(aj.d(i) + "synchronized (" + a + ")");
        e.a(printstream, i + 1, false);
    }

    bd a;
    am e;
}
