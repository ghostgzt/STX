
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, ao, 
//            bd, fq

final class dn extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        Object obj;
        if((obj = ai1) instanceof ao)
            obj = ((ao)obj).a;
        if(!(obj instanceof bd))
            return null;
        bd bd1;
        Operation ai2;
        if(!((ai2 = (bd1 = (bd)obj).h) instanceof Convert))
            return null;
        Convert fq1;
        if(!(fq1 = (Convert)ai2).b())
            return null;
        bd bd2;
        Operation ai3;
        if(!((ai3 = (bd2 = bd1.a(0)).h) instanceof Convert))
            return null;
        Convert fq2;
        if(!(fq2 = (Convert)ai3).b() || fq2.h)
        {
            return null;
        } else
        {
            super.a++;
            fq2.g();
            return ai1;
        }
    }

    dn()
    {
    }
}
