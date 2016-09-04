
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ac, af, ah, 
//            ai, am, bd

final class fi extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        Operation ai2;
        if(!((ai2 = ai1) instanceof af))
            return null;
        af af1;
        if((af1 = (af)ai2).b(0) <= af1.index() || af1.b(1) <= af1.index())
            return null;
        am am1 = null;
        int i = af1.b(0);
        if(((ai2 = ah1.getOperation(i)) instanceof am) && ai2.getIndex() == 1)
            i = (am1 = (am)ai2).b(0);
        am am2 = null;
        int j = af1.b(1);
        if(((ai2 = ah1.getOperation(j)) instanceof am) && ai2.getIndex() == 1)
            j = (am2 = (am)ai2).b(0);
        if(i >= 0 && j >= 0 && i != j)
            return null;
        if(i < 0 && j >= 0)
        {
            am2 = null;
            j = af1.b(1);
        } else
        if(j < 0 && i >= 0)
        {
            am1 = null;
            i = af1.b(0);
        }
        ac ac1;
        if(am1 != null && am2 != null)
        {
            boolean flag = false;
            if((am2 instanceof ac) && !(am1 instanceof ac))
                flag = true;
            else
            if((am1 instanceof ac) && !(am2 instanceof ac))
                flag = false;
            else
                flag = true;
            if(flag)
                ac1 = new ac(af1.a.s(), am1, am2);
            else
                ac1 = new ac(af1.a, am2, am1);
        } else
        if(am1 != null)
            ac1 = new ac(af1.a.s(), am1);
        else
            ac1 = new ac(af1.a, am2);
        super.a++;
        ac1.a(af1);
        ac1.a(0, i >= 0 ? i : j);
        if(i >= 0 && j >= 0)
            ah1.getOperation(i).minusIndex();
        ah1.deleteOperation(af1);
        ah1.deleteOperation(am2);
        ah1.deleteOperation(am1);
        return ah1.setOperation(ac1);
    }

    fi()
    {
    }
}
