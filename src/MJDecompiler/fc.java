
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ab, af, ah, 
//            ai, am, ao, ap, 
//            bd, bn, ce, g, 
//            x

final class fc extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof af))
            return null;
        af af1;
        Operation ai2;
        int i;
        boolean flag;
        if(flag = (af1 = (af)ai1).b(0) < af1.b(1))
        {
            ai2 = ah1.getOperation(af1.b(0));
            i = af1.b(1);
        } else
        {
            ai2 = ah1.getOperation(af1.b(1));
            i = af1.b(0);
        }
        am am1;
        if(ai2.index() == af1.index())
            am1 = null;
        else
        if((ai2 instanceof am) && ai2.getIndex() == 1)
        {
            if(ai2.m() == 0)
                am1 = (am)ai2;
            else
            if(ai2.b(0) == af1.index())
                am1 = (am)ai2;
            else
                return null;
        } else
        {
            return null;
        }
        ce ce1 = ah1.g(af1.index());
        ao ao1 = null;
        bn bn1 = null;
        boolean flag1 = true;
        if(ce1 != null)
        {
            if(am1 == null)
                return null;
            if(am1.a(ce1, 1000))
                if(ce1.a("for"))
                    flag1 = false;
                else
                    return null;
        }
        Object obj = am1;
        if(am1 != null && am1.m() == 0)
        {
            OperationEnum x1 = ah1.c();
            do
            {
                if(!x1.haveMoreElement() || ao1 != null)
                    break;
                if(((ai1 = x1.NextElement()) instanceof ao) && ai1.getIndex() == 0 && ai1.b(0) == af1.index())
                    ao1 = (ao)ai1;
            } while(true);
            if(ao1 == null)
                return null;
        } else
        if(am1 != null && am1.m() == 1)
        {
            int j = am1.g() - 1;
            if((ai1 = am1.a(j)) instanceof ao)
            {
                bn1 = (ao1 = (ao)ai1).a.d();
                if(j == 0)
                {
                    obj = null;
                } else
                {
                    am aam[] = new am[j];
                    for(int k = 0; k < j; k++)
                        aam[k] = am1.a(k);

                    obj = new ap(aam);
                }
            }
        }
        Object obj1 = null;
        ao ao2 = null;
        bn bn2 = null;
        Object obj2 = ah1.c();
        do
        {
            if(!((OperationEnum) (obj2)).haveMoreElement())
                break;
            if((ai1 = ((OperationEnum) (obj2)).NextElement()).m() != 1 || ai1.b(0) != af1.index())
                continue;
            if(ai1 instanceof am)
            {
                if(ai1 instanceof ap)
                {
                    obj1 = (am)ai1;
                    ap ap1;
                    ai1 = (ap1 = (ap)ai1).a(ap1.g() - 1);
                }
                bd bd1;
                if((ai1 instanceof ao) && ((bd1 = ((ao)ai1).a) instanceof g))
                    bn2 = (ao2 = (ao)ai1).a.d();
            }
            break;
        } while(true);
        obj2 = af1.a;
        if(bn2 != null && bn2 != bn1 && !((bd) (obj2)).a(bn2))
        {
            bn2 = null;
            ao2 = null;
            obj1 = null;
        }
        if(ao1 != null && flag1 && (bn1 == null || bn1 != bn2) && (bn1 == null || !((bd) (obj2)).a(bn1)))
        {
            ao1 = null;
            obj = am1;
        }
        if(ao1 == null && ao2 == null)
            return null;
        super.a++;
        if(flag)
            obj2 = ((bd) (obj2)).s();
        ce ce2 = ah1.e(af1.index());
        ab ab1;
        (ab1 = new ab(ao2, ((bd) (obj2)), ao1, (am)(am)obj, ce1, ce2)).a(0, i);
        af1.minusIndex();
        if(ao2 != null)
        {
            ab1.a(ao2);
            if(obj1 != null)
            {
                int l;
                am aam1[] = new am[l = ((am)(am)obj1).g() - 1];
                for(int i1 = 0; i1 < l; i1++)
                    aam1[i1] = ((am)(am)obj1).a(i1);

                obj1 = new ap(aam1);
                ah1.setOperation((Operation)(Operation)obj1);
            } else
            {
                ah1.deleteOperation(ao2);
            }
        } else
        {
            ab1.a(af1);
        }
        ah1.deleteOperation(af1);
        ah1.deleteOperation(am1);
        if(am1 != null && am1.m() == 0)
            ah1.deleteOperation(ao1);
        return ah1.setOperation(ab1);
    }

    fc()
    {
    }
}
