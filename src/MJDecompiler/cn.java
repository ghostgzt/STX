
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, ao, 
//            bd, ev, fq

final class cn extends db
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
        if(!((ai2 = (bd1 = (bd)obj).h) instanceof CaozuoTwo))
            return null;
        Convert fq1 = null;
        for(int i = 0; i < 2 && fq1 == null; i++)
        {
            bd bd2;
            Operation ai3;
            if(((ai3 = (bd2 = bd1.a(1)).h) instanceof Convert) && (fq1 = (Convert)ai3).b())
                fq1 = null;
        }

        if(fq1 == null || fq1.h)
        {
            return null;
        } else
        {
            super.a++;
            fq1.g();
            return ai1;
        }
    }

    cn()
    {
    }
}
