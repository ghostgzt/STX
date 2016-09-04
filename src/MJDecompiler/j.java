
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            aj, ai, fo

class j extends aj
{

    final aj b(Operation ai1)
    {
        e = new int[ai1.m()];
        for(int i = 0; i < e.length; i++)
            e[i] = ai1.b(i);

        return this;
    }

    public final int m()
    {
        return e.length;
    }

    public final int b(int i)
    {
        return e[i];
    }

    public final void a(int i, int k)
    {
        e[i] = k;
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

    j()
    {
    }

    protected int e[];
}
