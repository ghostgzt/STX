
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, ao, 
//            bd, ey

final class el extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof Pop))
            return null;
        Operation ai2;
        if(!((ai2 = ah1.c(ai1)) instanceof bd))
        {
            return null;
        } else
        {
            bd bd1 = (bd)ai2;
            super.a++;
            ao ao1;
            (ao1 = new ao(bd1)).b(ai1);
            ah1.deleteOperation(bd1);
            ah1.deleteOperation(ai1);
            return ah1.setOperation(ao1);
        }
    }

    el()
    {
    }
}
