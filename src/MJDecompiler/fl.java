
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, aa, ah, ai, 
//            ar, bd, ed

final class fl extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof Return))
            return null;
        if(ai1.e() == 0)
            return null;
        Operation ai2;
        if(!((ai2 = ah1.c(ai1)) instanceof bd))
        {
            return null;
        } else
        {
            bd bd1 = (bd)ai2;
            super.a++;
            String s = ah1.desc.c();
            ReturnView aa1;
            (aa1 = new ReturnView(bd1, s)).b(ai1);
            ah1.deleteOperation(bd1);
            ah1.deleteOperation(ai1);
            return ah1.setOperation(aa1);
        }
    }

    fl()
    {
    }
}
