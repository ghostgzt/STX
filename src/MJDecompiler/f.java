
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            ao, az, bd, bg, 
//            bv, ch, bn

final class f extends ao
{

    f(LocalVariableTable ch1, bd bd1)
    {
        super(bd1);
        e = ch1;
    }

    public final void c()
    {
        super.a = super.a.a(e.type);
        super.a.c();
    }

    public final bn d()
    {
        bg bg1 = new bg(e.type, e.f().toString());
        bn bn = super.a.b();
        return new bv(bg1.b, bg1, bn, "=", 11);
    }

    LocalVariableTable e;
}
