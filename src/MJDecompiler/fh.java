
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, a, ad, ah, 
//            ai, ec, ey, fa, 
//            y

final class fh extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(ai1 instanceof Jsr)
            ai1 = ah1.c(ai1);
        if(!(ai1 instanceof Store) && !(ai1 instanceof Pop))
            return null;
        if(ai1.getIndex() != 1)
            return null;
        Operation ai2;
        int i = (ai2 = ai1).index();
        ExceptionHander ay[] = ah1.excptionHanders;
        int j = -1;
        for(int k = 0; j < 0 && k < ay.length; k++)
            if(ay[k].hander == i && ay[k].b().equals("<anyException>"))
                j = k;

        if(j < 0)
            return null;
        if(ai2 instanceof Store)
        {
            if(!((ai1 = ah1.d(ai1)) instanceof Jsr))
                return null;
            if(!((ai1 = ah1.getOperation(ai1.b(0))) instanceof ThrowView))
                return null;
        }
        super.a++;
        int l = ay[j].start;
        int i1 = ay[j].end;
        int j1 = 0;
        ai1 = ai2;
        ah1.deleteOperation(ai1);
        if(ai2 instanceof Store)
        {
            ai1 = ah1.d(ai1);
            ah1.deleteOperation(ai1);
            j1 = ai1.b(1);
            ai1 = ah1.getOperation(ai1.b(0));
            ah1.deleteOperation(ai1);
        } else
        {
            j1 = ai2.b(0);
            ah1.getOperation(j1).minusIndex();
        }
        ay[j] = new ad(j1, l, i1);
        ah1.excptionHanders = ay;
        (ai1 = ah1.getOperation(j1)).minusIndex();
        return ai1;
    }

    fh()
    {
    }
}
