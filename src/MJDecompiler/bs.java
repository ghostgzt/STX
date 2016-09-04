
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            bn

class bs extends bn
{

    bs(String s, bn bn1, bn bn2, String s1, int i)
    {
        super(s, i);
        a = bn1;
        d = bn2;
        e = s1;
    }

    public String toString()
    {
        return a.a(super.c) + " " + e + " " + d.a(super.c);
    }

    bn a;
    bn d;
    String e;
}
