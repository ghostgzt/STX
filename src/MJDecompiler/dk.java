
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, ao, 
//            ax, bd, g, r

final class dk extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof ao))
            return null;
        ao ao1;
        bd bd1;
        if(!((bd1 = (ao1 = (ao)ai1).a) instanceof g))
            return null;
        g g1;
        FildInfo ax1;
        if((ax1 = (g1 = (g)bd1).g()) == null)
        {
            return null;
        } else
        {
            super.a++;
            ax1.f = g1.a(0);
            r r1 = new r(ai1);
            return ah1.setOperation(r1);
        }
    }

    dk()
    {
    }
}
