
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;



// Referenced classes of package MJDecompiler:
//            am, ai, bz, cl, 
//            fo

final class r extends am
{

    r(Operation ai1)
    {
        a = ai1;
        if(ai1 != null)
        {
            a(ai1);
            b(ai1);
        }
    }

    public final int e()
    {
        return 0;
    }

    public final int f()
    {
        return 0;
    }

    public final int a()
    {
        if(a != null)
            return a.a();
        else
            return l();
    }

    public final void a(bz bz1)
    {
        if(a != null)
            a.a(bz1);
    }

    public final fo a(fo fo)
    {
        return fo;
    }

    public final int p()
    {
        return a != null && Decompiler.debug ? 2 : 0;
    }

    public final void a(Pstream printstream, int i)
    {
        if(a != null && Decompiler.debug)
        {
            printstream.println("/*");
            a.a(printstream, i, true);
            printstream.println("*/");
        }
    }

    Operation a;
}
