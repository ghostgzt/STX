
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            bd, ai, bn, bv, 
//            cu, fo, ax

class g extends bd
{

    g(bn bn1, Operation ai1, String s, bd bd1)
    {
        a = bn1;
        e = ai1;
        f = s;
        g = bd1;
    }

    public final int a()
    {
        return e.a();
    }

    public final fo a(fo fo1)
    {
        return fo1.a(b());
    }

    public bn b()
    {
        bn bn1 = g.b();
        return new bv(bn1.b, a, bn1, f, 11);
    }

    final boolean a(bn bn1)
    {
        if(toString().equals(bn1.toString()))
            return true;
        if(a.toString().equals(bn1.toString()))
            return true;
        if(g != null)
            return g.a(bn1);
        else
            return false;
    }

    public int e()
    {
        return 1;
    }

    final bd a(int i)
    {
        return g;
    }

    final void a(int i, bd bd1)
    {
        g = bd1;
    }

    public final void c()
    {
        if(g != null)
        {
            g = g.a(a.b);
            g.c();
        }
    }

    final bn d()
    {
        return a;
    }

    final FildInfo g()
    {
        if(e instanceof Put)
            return ((Put)e).b();
        else
            return null;
    }

    bn a;
    Operation e;
    String f;
    bd g;
}
