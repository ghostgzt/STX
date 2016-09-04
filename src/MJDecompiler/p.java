
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;



// Referenced classes of package MJDecompiler:
//            am, aj, ce

class p extends am
{

    p(int i, ce ce1, String s)
    {
        e = false;
        super.c = i;
        super.b = 1;
        a = ce1;
        f = s;
    }

    final boolean a(ce ce1, int i)
    {
        if(ce1 != a || i <= 1)
            return false;
        if(i < 1000)
            e = true;
        return true;
    }

    public final void a(Pstream printstream, int i)
    {
        printstream.print(aj.d(i) + f);
        if(e)
            printstream.print(" " + a);
        printstream.println(";");
    }

    ce a;
    boolean e;
    String f;
}
