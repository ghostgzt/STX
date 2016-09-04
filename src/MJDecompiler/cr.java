
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


import java.util.Vector;

// Referenced classes of package MJDecompiler:
//            bz, cg

final class cr extends bz
{

    cr(bz bz1, int i, int j)
    {
        super(bz1, i, j);
        super.c = null;
    }

    final void a(bz bz1)
    {
        super.d.addElement(bz1);
    }

    final cg a(int i)
    {
        return null;
    }

    final cg a(int i, int j, String s, char c)
    {
        if(super.a < j && j <= super.b)
        {
            for(int k = 0; k < super.d.size(); k++)
            {
                bz bz1;
                cg cg1;
                if((cg1 = (bz1 = (bz)super.d.elementAt(k)).a(i, j, s, c)) != null)
                    return cg1;
            }

        }
        return null;
    }

    final void a(Pstream printstream, int i, String s)
    {
    }
}
