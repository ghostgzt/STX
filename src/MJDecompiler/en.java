
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, ec, 
//            r

final class en extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof Jsr))
        {
            return null;
        } else
        {
            ah1.getOperation(ai1.b(1)).minusIndex();
            ai1.a(1, -1);
            r r1 = new r(ai1);
            return ah1.setOperation(r1);
        }
    }

    en()
    {
    }
}
