
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;



// Referenced classes of package MJDecompiler:
//            j, aj, bd

final class af extends j
{

    af(bd bd1, int i, int k)
    {
        int ai[] = {
            i, k
        };
        a = bd1;
        super.e = ai;
    }

    public final int a()
    {
        return a.a();
    }

    public final void c()
    {
        a = a.a("Z");
        a.c();
    }

    final bd b()
    {
        return a;
    }

    public final void a(Pstream printstream, int i)
    {
        printstream.println(aj.d(i) + "if (" + a + ") goto " + super.e[1] + " else " + super.e[0] + ";");
    }

    bd a;
}
