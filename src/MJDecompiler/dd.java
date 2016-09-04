
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, d, 
//            r

final class dd extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof d))
            return null;
        if(ai1.index() != 0)
            return null;
        d d1;
        if(!(d1 = (d)ai1).toString().equals("super()"))
        {
            return null;
        } else
        {
            super.a++;
            r r1 = new r(ai1);
            ah1.deleteOperation(ai1);
            return ah1.setOperation(r1);
        }
    }

    dd()
    {
    }
}
