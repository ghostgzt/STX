
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;



// Referenced classes of package MJDecompiler:
//            am, aj, bz, bw

final class q extends am
{

    q(am am1)
    {
        a = am1;
    }

    q(am am1, String s, LocalName bw)
    {
        a = am1;
        e = s;
        f = bw;
    }

    public final int a()
    {
        if(a != null)
            return a.a();
        else
            return index();
    }

    public final void a(bz bz1)
    {
        g = new bz(bz1, l(), a());
        if(a != null)
            a.a(g);
    }

    public final void c()
    {
        if(a != null)
            a.c();
    }

    public final int p()
    {
        if(a == null)
            return 1;
        else
            return a.p();
    }

    public final void a(Pstream printstream, int i)
    {
        printstream.print(aj.d(i) + "catch (");
        if(e == null)
            printstream.print(g);
        else
            printstream.print(e + " " + f);
        printstream.println(")");
        printstream.println(aj.d(i) + "{");
        if(a != null)
            a.a(printstream, i + 1, true);
        printstream.println(aj.d(i) + "}");
    }

    am a;
    String e;
    LocalName f;
    bz g;
}
