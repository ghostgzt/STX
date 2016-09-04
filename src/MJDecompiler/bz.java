
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


import java.util.Vector;

// Referenced classes of package MJDecompiler:
//            cg, cl, bj

class bz
{

    bz(bz bz1, int i, int j)
    {
        a = i;
        b = j;
        c = new Vector();
        d = new Vector();
        if(bz1 != null)
            bz1.a(this);
    }

    void a(bz bz1)
    {
        d.addElement(bz1);
    }

    cg a(int i)
    {
        for(int j = 0; j < c.size(); j++)
        {
            cg cg1;
            if((cg1 = (cg)c.elementAt(j)).a == i)
                return cg1;
        }

        return null;
    }

    final Vector a()
    {
        return c;
    }

    cg a(int i, int j, String s, char c1)
    {
        if(a < j && j <= b)
        {
            cg cg1;
            if((cg1 = a(i)) == null)
            {
                for(int k = 0; k < d.size(); k++)
                {
                    bz bz1;
                    if((cg1 = (bz1 = (bz)d.elementAt(k)).a(i, j, s, c1)) != null)
                        return cg1;
                }

                cg1 = new cg(i);
                if(c1 != 'S')
                {
                    boolean flag = false;
                    for(int l = d.size() - 1; !flag && l >= 0; l--)
                    {
                        bz bz2;
                        if((bz2 = (bz)d.elementAt(l)).b < j)
                            flag = bz2.a(cg1);
                    }

                    if(!flag)
                        return null;
                }
                c.addElement(cg1);
            }
            cg1.a(j, s, c1);
            return cg1;
        } else
        {
            return null;
        }
    }

    final boolean a(cg cg1)
    {
        boolean flag = false;
        cg cg2;
        if((cg2 = a(cg1.a)) != null)
        {
            cg2.a(cg1);
            c.removeElement(cg2);
            cg2.c();
            flag = true;
        }
        for(int i = 0; i < d.size(); i++)
        {
            bz bz1 = (bz)d.elementAt(i);
            flag |= bz1.a(cg1);
        }

        return flag;
    }

    public final String toString()
    {
        String s = "";
        for(int i = 0; i < c.size(); i++)
        {
            if(s.length() > 0)
                s = s + ", ";
            cg cg1 = (cg)c.elementAt(i);
            s = s + cg1;
        }

        return s;
    }

    void a(Pstream printstream, int i, String s)
    {
        if(Decompiler.debug && c.size() > 0)
            printstream.println("---- start " + s + " scope ----");
        for(int j = 0; j < c.size(); j++)
        {
            cg cg1;
            (cg1 = (cg)c.elementAt(j)).a(printstream, i);
        }

        if(Decompiler.debug && c.size() > 0)
            printstream.println("---- end " + s + " scope ----");
    }

    int a;
    int b;
    Vector c;
    Vector d;
    ClassFile e;
}
