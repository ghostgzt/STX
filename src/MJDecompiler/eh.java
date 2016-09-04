
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, bd, 
//            d, dc

final class eh extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof InvokeVirtual))
            return null;
        if(ai1.f() > 0)
            return null;
        int i;
        bd abd[] = new bd[i = ai1.e()];
        Operation ai2 = ai1;
        for(int j = 0; j < i; j++)
        {
            if(!((ai2 = ah1.c(ai2)) instanceof bd))
                return null;
            abd[i - j - 1] = (bd)ai2;
        }

        super.a++;
        d d1 = new d((InvokeVirtual)ai1, abd);
        ah1.deleteOperation(ai1);
        for(int k = 0; k < i; k++)
            ah1.deleteOperation(abd[k]);

        return ah1.setOperation(d1);
    }

    eh()
    {
    }
}
