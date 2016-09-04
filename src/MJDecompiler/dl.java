
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            db, ah, ai, aj, 
//            bd, cy, dq, fm, 
//            i

final class dl extends db
{

    final Operation a(Operation ai1, CodeInfo ah1)
    {
        if(!(ai1 instanceof bd))
            return null;
        bd bd1;
        Operation ai2;
        if(!((ai2 = (bd1 = (bd)ai1).h) instanceof NewArray))
            return null;
        int j = 0;
        if(bd1.e() != 1)
            return null;
        try
        {
            j = Integer.parseInt(bd1.a(0).toString());
        }
        catch(NumberFormatException _ex)
        {
            return null;
        }
        if(j == 0)
            return null;
        Operation ai3 = null;
        Operation ai4 = ah1.d(ai1);
        bd abd[] = new bd[j];
        for(int k = 0; k < j; k++)
        {
            if(!(ai4 instanceof Dup))
                return null;
            Dup dq1;
            if((dq1 = (Dup)ai4).f != 0)
                return null;
            if(!((ai4 = ah1.d(ai4)) instanceof bd))
                return null;
            bd bd2 = (bd)ai4;
            int j1 = 0;
            try
            {
                j1 = Integer.parseInt(bd2.toString());
            }
            catch(NumberFormatException _ex)
            {
                return null;
            }
            if(j1 != k)
                return null;
            if(!((ai4 = ah1.d(ai4)) instanceof bd))
                return null;
            abd[k] = (bd)ai4;
            if(!((ai4 = ah1.d(ai4)) instanceof Astore))
                return null;
            ai3 = ai4;
            ai4 = ah1.d(ai4);
        }

        super.a++;
        aj aj1 = (new i(abd)).a(bd1).b(ai3);
        ah1.deleteOperation(bd1);
        ai4 = ah1.d(ai1);
        for(int l = 0; l < j; l++)
        {
            for(int i1 = 0; i1 < 4; i1++)
            {
                ah1.deleteOperation(ai4);
                ai4 = ah1.d(ai4);
            }

        }

        return ah1.setOperation(aj1);
    }

   
}
