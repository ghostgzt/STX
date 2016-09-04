
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            bn

final class bq extends bn
{

    bq(String s, bn bn1, bn bn2, bn bn3, String s1, String s2, int i)
    {
        super(s, i);
        a = bn1;
        d = bn2;
        e = bn3;
        f = s1;
        g = s2;
    }

    public final String toString()
    {
        return a.a(23) + " " + f + " " + d.a(23) + " " + g + " " + e.a(23);
    }

    bn a;
    bn d;
    bn e;
    String f;
    String g;
}
