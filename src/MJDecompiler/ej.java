
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, am, 
//            ap

final class ej extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        am am1 = null;
        am am2 = null;
        int i = 0;
        for(Operation ai2 = ai1; ai2 instanceof am; ai2 = ah1.c(am1))
        {
            am1 = (am)ai2;
            i += am1.g();
        }

        if(am1 == null)
            return null;
        for(Operation ai3 = ai1; ai3 instanceof am; ai3 = ah1.d(am2))
        {
            am2 = (am)ai3;
            i += am2.g();
        }

        if(am2 == am1)
            return null;
        super.a++;
        am aam[] = new am[i -= ((am)ai1).g()];
        Object obj = am1;
        for(int j = 0; j < i;)
        {
            am am3 = (am)obj;
            for(int k = 0; k < am3.g(); k++)
                aam[j + k] = am3.a(k);

            j += am3.g();
            ah1.deleteOperation((Operation)(Operation)obj);
            obj = ah1.d((Operation)(Operation)obj);
        }

        ap ap1 = new ap(aam);
        return ah1.setOperation(ap1);
    }

    ej()
    {
    }
}
