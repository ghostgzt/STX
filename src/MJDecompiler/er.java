
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ae, ah, ai, 
//            am, ar, ey, fa, 
//            q, y

final class er extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof am))
            return null;
        if(ai1.getIndex() < 2)
            return null;
        am am1 = (am)ai1;
        int i = ai1.index();
        int j = ai1.b(0);
        ExceptionHander ay[] = ah1.excptionHanders;
        int k = -1;
        int l = 0;
        for(int i1 = 0; i1 < ay.length; i1++)
        {
            if(ay[i1].start != i || ay[i1].b().equals("<anyException>"))
                continue;
            if(k < 0)
                k = ay[i1].end;
            if(ay[i1].end != k)
                continue;
            if(!((ai1 = ah1.getOperation(ay[i1].hander)) instanceof Store) && !(ai1 instanceof Pop))
                return null;
            if(((ai1 instanceof Store) || ah1.d(ai1) != null) && !((ai1 = ah1.d(ai1)) instanceof am))
                return null;
            if(j < 0)
                j = ai1.b(0);
            if(ai1.b(0) >= 0 && ai1.b(0) != j)
                return null;
            l++;
        }

        if(l == 0)
            return null;
        super.a++;
        q aq[] = new q[l];
        ExceptionHander ay1[] = new ExceptionHander[ay.length - l];
        int j1 = 0;
        int k1 = 0;
        for(int l1 = 0; l1 < ay.length; l1++)
        {
            ExceptionHander y1;
            if((y1 = ay[l1]).start == i && y1.end == k)
            {
                ai1 = ah1.getOperation(ay[l1].hander);
                ah1.deleteOperation(ai1);
                am am2 = null;
                Operation ai2;
                if((ai2 = ah1.d(ai1)) == null)
                {
                    ai2 = ai1;
                } else
                {
                    ah1.deleteOperation(ai2);
                    am2 = (am)ai2;
                }
                q q1;
                if(ai1 instanceof Store)
                {
                    q1 = new q(am2);
                } else
                {
                    MethodDescriptor ar1;
                    LocalName bw = (ar1 = ah1.desc).c(y1.a());
                    q1 = new q(am2, y1.b(), bw);
                }
                q1.a(ai1);
                aq[j1++] = q1;
                am1.minusIndex();
                if((ai1 = ah1.getOperation(ay[l1].end)) != null)
                    ai1.minusIndex();
                if(ai2.b(0) >= 0)
                    ah1.getOperation(ai2.b(0)).minusIndex();
            } else
            {
                ay1[k1++] = y1;
            }
        }

        if(j >= 0 && am1.b(0) != j)
            ah1.getOperation(j).addIndex();
        ae ae1;
        (ae1 = new ae(am1, aq, null)).a(0, j);
        ah1.excptionHanders = ay1;
        ah1.deleteOperation(am1);
        return ah1.setOperation(ae1);
    }

    er()
    {
    }
}
