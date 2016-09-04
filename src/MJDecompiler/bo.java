
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            bn

final class bo extends bn
{

    bo(String s, bn bn1, String s1, int i)
    {
        super(s, i);
        a = bn1;
        d = s1;
    }

    public final String toString()
    {
        return a.a(super.c) + d;
    }

    bn a;
    String d;
}
