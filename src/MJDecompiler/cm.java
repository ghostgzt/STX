
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, ao, 
//            bd, fq, g

final class cm extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        Object obj;
        if((obj = ai1) instanceof ao)
            obj = ((ao)obj).a;
        if(!(obj instanceof g))
            return null;
        g g1;
        bd bd1;
        if((bd1 = (g1 = (g)obj).a(0)) == null)
            return null;
        Operation ai2;
        if(!((ai2 = bd1.h) instanceof Convert))
            return null;
        Convert fq1;
        if((fq1 = (Convert)ai2).b() || fq1.h)
        {
            return null;
        } else
        {
            super.a++;
            fq1.g();
            return ai1;
        }
    }

}
