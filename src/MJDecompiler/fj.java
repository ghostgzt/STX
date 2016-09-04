
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, af, ah, ai, 
//            aj, am, bd, du, 
//            r, x

final class fj extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof am) || ai1.b(0) != ai1.index())
            return null;
        am am1 = (am)ai1;
        super.a++;
        int i = am1.index();
        int j;
        if((j = ah1.c(i)) < 0)
        {
            j = ah1.b(i, -1);
            aj aj1 = (new r(null)).c(j);
            ah1.setOperation(aj1);
        }
        int k = ah1.b(i, j);
        Number du1 = new Number(am1.a(), 0, "Z", "true");
        bd abd[] = new bd[0];
        bd bd1 = new bd(du1, abd);
        aj aj2 = (new af(bd1, j, i)).c(k);
        am1.a(0, k);
        aj2.addIndex();
        ah1.getOperation(j).addIndex();
        for(OperationEnum x1 = ah1.c(); x1.haveMoreElement();)
        {
            ai1 = x1.NextElement();
            int l = 0;
            while(l < ai1.m()) 
            {
                if(ai1.b(l) == i)
                {
                    ai1.a(l, k);
                    am1.minusIndex();
                    aj2.addIndex();
                }
                l++;
            }
        }

        if(i == 0)
        {
            am1.minusIndex();
            aj2.addIndex();
        }
        return ah1.setOperation(aj2);
    }

    fj()
    {
    }
}
