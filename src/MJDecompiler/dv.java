
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, ce, 
//            e

final class dv extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        boolean flag = false;
        int i = ai1.m();
        int j = ai1.index();
        for(int k = 0; k < i; k++)
        {
            int l;
            ce ce1;
            if((l = ai1.b(k)) != ah1.c(j) && (ce1 = ah1.f(l)) != null && ce1.b(j))
            {
                int i1 = ah1.a(j, l);
                e e1 = new e(i1, ce1);
                ah1.setOperation(e1);
                ah1.getOperation(l).minusIndex();
                ai1.a(k, i1);
                flag = true;
            }
        }

        if(!flag)
        {
            return null;
        } else
        {
            super.a++;
            return ah1.getOperation(0);
        }
    }

    dv()
    {
    }
}
