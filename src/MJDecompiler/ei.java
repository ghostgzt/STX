
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ae, ah, ai, 
//            am, es, fa, y

final class ei extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(ai1.getIndex() > 0)
            return null;
        Operation ai2;
        int i = (ai2 = ai1).index();
        if(ai1 instanceof Store)
            ai1 = ah1.d(ai1);
        if(!(ai1 instanceof am))
            return null;
        am am1 = (am)ai1;
        Operation ai3 = ai1;
        if((ai2 instanceof Store) && !((ai3 = ah1.d(ai1)) instanceof Ret))
            return null;
        ExceptionHander ay[] = ah1.excptionHanders;
        int j = -1;
        for(int k = 0; j < 0 && k < ay.length; k++)
            if(ay[k].hander == i && ay[k].b().equals("<noException>"))
                j = k;

        if(j < 0)
            return null;
        int l = ay[j].start;
        if(!((ai1 = ah1.getOperation(l)) instanceof am))
            return null;
        am am2;
        int i1 = (am2 = (am)ai1).b(0);
        if(ai2 instanceof Store)
        {
            if(i1 >= 0 && i1 != ay[j].end)
                return null;
        } else
        if(i1 != i || ai3.m() > 0)
            return null;
        super.a++;
        am2.minusIndex();
        if((ai1 = ah1.getOperation(ay[j].end)) != null)
            ai1.minusIndex();
        ae ae1;
        if(am2 instanceof ae)
        {
            ae ae2;
            am2 = (ae2 = (ae)am2).a;
            q aq[] = ae2.e;
            ae1 = new ae(am2, aq, am1);
            ah1.deleteOperation(ae2);
        } else
        {
            ae1 = new ae(am2, null, am1);
            ah1.deleteOperation(am2);
        }
        if(!(ai2 instanceof Store))
            ae1.a(0, -1);
        ah1.deleteOperation(ai2);
        ah1.deleteOperation(am1);
        ah1.deleteOperation(ai3);
        return ah1.setOperation(ae1);
    }

    ei()
    {
    }
}
