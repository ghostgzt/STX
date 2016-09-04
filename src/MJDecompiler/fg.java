
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ag, ah, ai, 
//            am, ao, ap, ar, 
//            ay, bd, eb, es, 
//            fa, g, l, y

final class fg extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof MonitorEnter))
            return null;
        MonitorEnter eb1 = (MonitorEnter)ai1;
        if(!((ai1 = ah1.c(eb1)) instanceof bd))
            return null;
        bd bd1;
        String s = (bd1 = (bd)ai1).b().toString();
        ai1 = ah1.c(bd1);
        ap ap1 = null;
        if(ai1 instanceof ap)
            ai1 = (ap1 = (ap)ai1).a(ap1.g() - 1);
        if(!(ai1 instanceof ao))
            return null;
        ao ao1;
        if(!((ao1 = (ao)ai1).a instanceof g))
            return null;
        g g1;
        if(!(g1 = (g)ao1.a).a.toString().equals(s))
            return null;
        bd bd2;
        if((bd2 = g1.a(0)) == null)
            return null;
        if(!((ai1 = ah1.getOperation(eb1.b(0))) instanceof am))
            return null;
        am am1;
        int i = (am1 = (am)ai1).index();
        ExceptionHander ay1[] = ah1.excptionHanders;
        int j = -1;
        for(int k = 0; j < 0 && k < ay1.length; k++)
            if(ay1[k].start == i && ay1[k].b().equals("<anyException>"))
                j = k;

        if(j < 0)
            return null;
        if(!((ai1 = ah1.getOperation(ay1[j].hander)) instanceof bd))
            return null;
        bd bd3;
        if(!(bd3 = (bd)ai1).b().toString().equals(s))
            return null;
        if(!((ai1 = ah1.d(bd3)) instanceof MonitorExit))
            return null;
        MonitorExit ay2 = (MonitorExit)ai1;
        if(!((ai1 = ah1.d(ay2)) instanceof Throw))
            return null;
        Throw l1 = (Throw)ai1;
        Store fa1 = null;
        bd bd4 = null;
        MonitorExit ay3 = null;
        Ret es1 = null;
        if(am1.b(0) < 0)
        {
            int i1;
            if((i1 = ah1.c(l1.index())) < 0)
                return null;
            if(!((ai1 = ah1.getOperation(i1)) instanceof Store))
                return null;
            fa1 = (Store)ai1;
            if(!((ai1 = ah1.d(fa1)) instanceof bd))
                return null;
            if(!(bd4 = (bd)ai1).b().toString().equals(s))
                return null;
            if(!((ai1 = ah1.d(bd4)) instanceof MonitorExit))
                return null;
            ay3 = (MonitorExit)ai1;
            if(!((ai1 = ah1.d(ay3)) instanceof Ret))
                return null;
            es1 = (Ret)ai1;
        } else
        {
            if(!((ai1 = ah1.getOperation(am1.b(0))) instanceof bd))
                return null;
            if(!(bd4 = (bd)ai1).b().toString().equals(s))
                return null;
            if(!((ai1 = ah1.d(bd4)) instanceof MonitorExit))
                return null;
            ay3 = (MonitorExit)ai1;
        }
        super.a++;
        ah1.desc.b(bd1.b(), eb1.index());
        am1.minusIndex();
        if((ai1 = ah1.getOperation(ay1[j].end)) != null)
            ai1.minusIndex();
        ag ag1;
        (ag1 = new ag(bd2, am1)).b(ay3);
        if(ap1 != null)
        {
            am aam[] = new am[ap1.g() - 1];
            for(int j1 = 0; j1 < aam.length; j1++)
                aam[j1] = ap1.a(j1);

            ah1.deleteOperation(ap1);
            ah1.setOperation(new ap(aam));
        } else
        {
            ah1.deleteOperation(ao1);
        }
        ah1.deleteOperation(bd1);
        ah1.deleteOperation(eb1);
        ah1.deleteOperation(am1);
        ah1.deleteOperation(bd4);
        ah1.deleteOperation(ay3);
        ah1.deleteOperation(bd3);
        ah1.deleteOperation(ay2);
        ah1.deleteOperation(l1);
        ah1.deleteOperation(fa1);
        ah1.deleteOperation(es1);
        return ah1.setOperation(ag1);
    }

    
}
