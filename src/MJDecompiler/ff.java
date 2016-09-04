
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, af, ah, ai, 
//            aj, am, bd, h

final class ff extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof af))
            return null;
        af af1 = (af)ai1;
        Operation ai2;
        if(!((ai2 = ah1.c(af1)) instanceof am))
            return null;
        bd bd1;
        int i;
        if(af1.b(0) == ai2.index())
        {
            bd1 = af1.a.s();
            i = af1.b(1);
        } else
        if(af1.b(1) == ai2.index())
        {
            bd1 = af1.a;
            i = af1.b(0);
        } else
        {
            return null;
        }
        super.a++;
        ce ce = ah1.g(af1.index());
        ce ce1 = ah1.e(af1.index());
        Object obj = new h(bd1, (am)ai2, ce, ce1);
        ai2.minusIndex();
        ((aj)(aj)(obj = ((aj)(aj)obj).a(ai2))).a(0, i);
        ah1.deleteOperation(af1);
        ah1.deleteOperation(ai2);
        return ah1.setOperation((Operation)(Operation)obj);
    }

    ff()
    {
    }
}
