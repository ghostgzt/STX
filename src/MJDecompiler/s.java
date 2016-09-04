
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;



// Referenced classes of package MJDecompiler:
//            aj, am, bz, ce, 
//            fo

final class s extends aj
{

    s(String s1, am am1)
    {
        e = s1;
        f = am1;
    }

    public final int a()
    {
        if(f != null)
            return f.a();
        else
            return l();
    }

    public final void a(bz bz1)
    {
        if(f != null)
            f.a(bz1);
    }

    public final void c()
    {
        if(f != null)
            f.c();
    }

    final boolean a(ce ce1, int i)
    {
        if(f != null)
            return f.a(ce1, i);
        else
            return false;
    }

    final void a(int i)
    {
        int j;
        int ai[] = new int[(j = a == null ? 0 : a.length) + 1];
        for(int k = 0; k < j; k++)
            ai[k] = a[k];

        ai[j] = i;
        a = ai;
    }

    public final int e()
    {
        return 0;
    }

    public final int f()
    {
        return 0;
    }

    public final fo a(fo fo)
    {
        return fo;
    }

    public final int p()
    {
        return 0;
    }

    public final void a(Pstream printstream, int i)
    {
        if(a == null)
        {
            printstream.println(aj.d(i) + "default:");
        } else
        {
            for(int j = 0; j < a.length; j++)
                printstream.println(aj.d(i) + "case " + a[j] + ":");

        }
        if(f != null)
            f.a(printstream, i + 1, true);
        if(e.equals("B"))
        {
            printstream.println(aj.d(i + 1) + "break;");
            return;
        }
        if(e.equals("F"))
            printstream.println("\n" + aj.d(i + 1) + "// fall thru");
    }

    int a[];
    String e;
    am f;
}
