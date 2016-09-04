
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, aj, 
//            ao, au, b, bd, 
//            bn, cu, dq, ev, 
//            g, u

final class dp extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        boolean flag = false;
        boolean flag1 = false;
        String s = null;
        Dup dq1 = null;
        Dup dq2 = null;
        Get au1 = null;
        CaozuoTwo ev1 = null;
        Operation ai2;
        if((ai2 = ai1) instanceof CaozuoTwo)
            ai2 = ah1.d(ai2);
        if(!(ai2 instanceof Put))
            return null;
        Put cu1;
        int i = (cu1 = (Put)ai2).e();
        if((ai2 = ah1.c(ai2)) instanceof Dup)
        {
            flag = true;
            flag1 = true;
            dq1 = (Dup)ai2;
            ai2 = ah1.c(ai2);
        }
        if(ai2 instanceof CaozuoTwo)
        {
            s = (ev1 = (CaozuoTwo)ai2).g;
            ai2 = ah1.c(ai2);
        }
        if(!(ai2 instanceof bd))
            return null;
        bd bd1;
        int j = (bd1 = (bd)ai2).b().size();
        Operation ai3;
        ai2 = ah1.c(ai3 = ai2);
        if(!flag && (ai2 instanceof Dup))
        {
            flag = true;
            dq1 = (Dup)ai2;
            ai2 = ah1.c(ai3 = ai2);
        }
        if(flag)
        {
            if(dq1.f != i - 1)
                return null;
            if(j != dq1.e() - dq1.f)
                return null;
        }
        if(s != null)
        {
            Operation ai4;
            if((ai4 = ai2) instanceof bd)
                ai4 = ((bd)ai4).h;
            if(!(ai4 instanceof Get))
                return null;
            if(!(au1 = (Get)ai4).a(cu1))
                return null;
            ai2 = ah1.c(ai3 = ai2);
            if(i > 1)
            {
                if(!(ai2 instanceof Dup))
                    return null;
                if((dq2 = (Dup)ai2).f != 0)
                    return null;
                if(i - 1 != dq2.e() - dq2.f)
                    return null;
                ai2 = ah1.c(ai3 = ai2);
            }
        }
        bd abd[] = new bd[i - 1];
        for(int k = 1; k < i; k++)
        {
            if(!(ai2 instanceof bd))
                return null;
            abd[i - k - 1] = (bd)ai2;
            ai2 = ah1.c(ai3 = ai2);
        }

        super.a++;
        s = s == null ? "=" : s + "=";
        boolean flag2 = false;
        Object obj;
        if((s.equals("+=") || s.equals("-=")) && (((String) (obj = bd1.toString())).equals("1") || ((String) (obj)).equals("1L") || ((String) (obj)).equals("1.0") || ((String) (obj)).equals("1.0F")))
        {
            flag2 = true;
            if(s.equals("+="))
                s = "++";
            else
                s = "--";
        }
        obj = cu1.a(abd);
        Object obj1 = null;
        if(!flag2)
            obj1 = new g(((bn) (obj)), cu1, s, bd1);
        else
        if(flag1)
            obj1 = new u(((bn) (obj)), cu1, s);
        else
            obj1 = new b(((bn) (obj)), cu1, s);
        Object obj2 = null;
        if(flag)
            obj2 = obj1;
        else
            obj2 = new ao((bd)(bd)obj1);
        ((aj)(aj)obj2).a(ai3).b(cu1);
        ah1.deleteOperation(cu1);
        ah1.deleteOperation(dq1);
        ah1.deleteOperation(ev1);
        ah1.deleteOperation(bd1);
        ah1.deleteOperation(au1);
        ah1.deleteOperation(dq2);
        for(int l = 0; l < i - 1; l++)
            ah1.deleteOperation(abd[l]);

        return ah1.setOperation((Operation)(Operation)obj2);
    }

}
