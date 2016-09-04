
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, aj, 
//            ed, t

final class fk extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof Return))
            return null;
        if(ai1.e() > 0)
        {
            return null;
        } else
        {
            super.a++;
            t t1;
            (t1 = new t()).a(ai1).b(ai1);
            ah1.deleteOperation(ai1);
            return ah1.setOperation(t1);
        }
    }

    fk()
    {
    }
}
