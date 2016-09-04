
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;



// Referenced classes of package MJDecompiler:
//            al, aj, am, bd, 
//            ce

final class h extends al
{

    h(bd bd, am am1, ce ce, ce ce1)
    {
        super(bd, am1, ce, ce1);
    }

    public final int p()
    {
        return super.e.p() + 1;
    }

    public final void a(Pstream printstream, int i)
    {
        b(printstream, i);
        printstream.println(aj.d(i) + "do");
        super.e.a(printstream, i + 1, false);
        printstream.println(aj.d(i) + "while (" + super.a + ");");
    }
}
