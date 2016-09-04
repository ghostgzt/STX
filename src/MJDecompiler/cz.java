
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, cx, 
//            x

final class cz extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof GoTo))
            return null;
        int i = ai1.index();
        int j = ai1.b(0);
        for(OperationEnum x1 = ah1.c(); x1.haveMoreElement();)
        {
            Operation ai2 = x1.NextElement();
            int k = 0;
            while(k < ai2.m()) 
            {
                if(ai2.b(k) == i)
                    ai2.a(k, j);
                k++;
            }
        }

        super.a++;
        ah1.deleteOperation(ai1);
        return ai1;
    }

    cz()
    {
    }
}
