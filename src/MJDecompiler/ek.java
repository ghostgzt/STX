
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, r, 
//            t, x

final class ek extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof t))
            return null;
        for(OperationEnum x1 = ah1.c(); x1.haveMoreElement();)
            if(x1.NextElement() == ai1 && !x1.haveMoreElement())
            {
                super.a++;
                r r1 = new r(ai1);
                ah1.deleteOperation(ai1);
                return ah1.setOperation(r1);
            }

        return null;
    }

    ek()
    {
    }
}
