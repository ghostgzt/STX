
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, af, ah, ai, 
//            aj, am, bd, c

final class fd extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof af))
            return null;
        af af1 = (af)ai1;
        Operation ai2 = ah1.getOperation(af1.b(0));
        Operation ai3 = ah1.getOperation(af1.b(1));
        bd bd1;
        am am1;
        int i;
        if(af1.b(0) == af1.index())
        {
            bd1 = af1.a.s();
            am1 = null;
            i = af1.b(1);
        } else
        if(af1.b(1) == af1.index())
        {
            bd1 = af1.a;
            am1 = null;
            i = af1.b(0);
        } else
        if((ai2 instanceof am) && ai2.getIndex() == 1 && ai2.b(0) == af1.index())
        {
            bd1 = af1.a.s();
            am1 = (am)ai2;
            i = af1.b(1);
        } else
        if((ai3 instanceof am) && ai3.getIndex() == 1 && ai3.b(0) == af1.index())
        {
            bd1 = af1.a;
            am1 = (am)ai3;
            i = af1.b(0);
        } else
        {
            return null;
        }
        super.a++;
        ce ce = ah1.g(af1.index());
        ce ce1 = ah1.e(af1.index());
        Object obj = new c(bd1, am1, ce, ce1);
        af1.minusIndex();
        ((aj)(aj)(obj = ((aj)(aj)obj).a(af1))).a(0, i);
        ah1.deleteOperation(af1);
        ah1.deleteOperation(am1);
        return ah1.setOperation((Operation)(Operation)obj);
    }

    fd()
    {
    }
}
