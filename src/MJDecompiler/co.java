
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, af, ah, ai, 
//            aj, bd, cj, ct, 
//            dt, du, ev

final class co extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        byte byte0 = 0;
        if(ai1 instanceof CompareAndIf)
            byte0 = 2;
        else
        if(ai1 instanceof IfNull)
            byte0 = 1;
        else
            return null;
        IfGoto ct1 = (IfGoto)ai1;
        bd abd[] = new bd[2];
        Operation ai2 = ai1;
        for(int i = 0; i < byte0; i++)
        {
            if(!((ai2 = ah1.c(ai2)) instanceof bd))
                return null;
            abd[byte0 - i - 1] = (bd)ai2;
        }

        super.a++;
        if(byte0 < 2)
        {
            Number du1 = new Number(0, 0, "A", "null");
            bd abd1[] = new bd[0];
            abd[1] = new bd(du1, abd1);
        }
        CaozuoTwo ev1 = new CaozuoTwo(ct1.index(), 0, "Z", ct1.h);
        bd bd1 = new bd(ev1, abd);
        Object obj;
        obj = ((aj)(aj)(obj = new af(bd1, ct1.b(0), ct1.b(1)))).a(abd[0]);
        ah1.deleteOperation(ct1);
        for(int j = 0; j < byte0; j++)
            ah1.deleteOperation(abd[j]);

        return ah1.setOperation((Operation)(Operation)obj);
    }

    co()
    {
    }
}
