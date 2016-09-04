
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, af, ah, ai, 
//            aj, bd, ev

final class dm extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        Operation ai2;
        if(!((ai2 = ai1) instanceof af))
            return null;
        int i = ai2.b(0);
        int j = ai2.b(1);
        Operation ai3;
        if(!((ai3 = ah1.c(ai2)) instanceof af))
            return null;
        int k;
        boolean flag;
        if(ai3.b(0) == ai2.index())
        {
            flag = false;
            k = ai3.b(1);
        } else
        {
            flag = true;
            k = ai3.b(0);
        }
        String s;
        if(k == j)
            s = "||";
        else
        if(k == i)
        {
            s = "&&";
            flag = !flag;
        } else
        {
            return null;
        }
        super.a++;
        bd abd[];
        (abd = new bd[2])[0] = ((af)ai3).a;
        if(flag)
            abd[0] = abd[0].s();
        abd[1] = ((af)ai2).a;
        CaozuoTwo ev1 = new CaozuoTwo(0, 0, "Z", s);
        bd bd1 = new bd(ev1, abd);
        Object obj;
        obj = ((aj)(aj)(obj = new af(bd1, i, j))).a(ai3);
        ah1.deleteOperation(ai3);
        ah1.deleteOperation(ai2);
        ah1.getOperation(k).minusIndex();
        return ah1.setOperation((Operation)(Operation)obj);
    }

    dm()
    {
    }
}
