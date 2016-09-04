
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            bn

final class br extends bn
{

    br(String s, bn bn1, bn bn2)
    {
        super(s, 26);
        a = bn1;
        d = bn2;
    }

    public final String toString()
    {
        return a.a(super.c) + "[" + d + "]";
    }

    bn a;
    bn d;
}
