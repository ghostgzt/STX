
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


final class ce
{

    ce(int i, int j, int k, String s)
    {
        a = i;
        b = j;
        c = k;
        d = s;
    }

    final boolean a(int i)
    {
        return i == a;
    }

    final boolean b(int i)
    {
        return b <= i && i < c;
    }

    final boolean a(String s)
    {
        return d.indexOf(s) >= 0;
    }

    final void c(int i)
    {
        e = i;
    }

    public final String toString()
    {
        return "loop" + e;
    }

    private int a;
    private int b;
    private int c;
    private String d;
    private int e;
}
