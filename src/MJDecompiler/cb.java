
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            bn, ar, ch

final class cb extends bn
{

    cb(MethodDescriptor ar1, int i, int j)
    {
        super("?", 100);
        a = ar1;
        d = i;
        e = j;
        super.b = ar1.a(i, j).type;
    }

    final int c()
    {
        return d;
    }

    public final String toString()
    {
        return a.a(d, e).toString();
    }

    MethodDescriptor a;
    int d;
    int e;
}
