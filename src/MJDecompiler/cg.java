
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


import java.util.Vector;

// Referenced classes of package MJDecompiler:
//            ar, az, bj, bx, 
//            bz, ch, cl

final class cg
{

    cg(int i)
    {
        a = i;
        b = new Vector();
        c = new Vector();
        d = false;
    }

    final void a(int i, String s, char c1)
    {
        bx bx1 = new bx(i, s, c1);
        for(int j = 0; j < c.size(); j++)
            if(((bx)c.elementAt(j)).a > bx1.a)
            {
                c.insertElementAt(bx1, j);
                return;
            }

        c.addElement(bx1);
    }

    final void a(cg cg1)
    {
        for(int i = 0; i < c.size(); i++)
        {
            bx bx1 = (bx)c.elementAt(i);
            cg1.a(bx1.a, bx1.b, bx1.c);
        }

    }

    final int a()
    {
        bx bx1;
        if(c.size() > 0)
            return (bx1 = (bx)c.elementAt(0)).a;
        else
            return -1;
    }

    final int b()
    {
        bx bx1;
        if(c.size() > 0)
            return (bx1 = (bx)c.elementAt(c.size() - 1)).a;
        else
            return -1;
    }

    final void c()
    {
        d = true;
    }

    final boolean d()
    {
        return d;
    }

    final void a(LocalVariableTable ch1)
    {
        b.addElement(ch1);
    }

    final LocalVariableTable a(int i)
    {
        LocalVariableTable ch1 = null;
        for(int j = 0; j < b.size(); j++)
        {
            LocalVariableTable ch2;
            if((ch2 = (LocalVariableTable)b.elementAt(j)).b(i))
                ch1 = ch2;
        }

        return ch1;
    }

    final int e()
    {
        return a;
    }

    final Vector a(bz bz1)
    {
        Vector vector = new Vector();
        for(int i = 0; i < c.size(); i++)
        {
            bx bx1 = (bx)c.elementAt(i);
            cg cg2 = bz1.a(a, bx1.a, bx1.b, bx1.c);
            if(!vector.contains(cg2))
                vector.addElement(cg2);
        }

        cg cg1;
        for(int j = 0; j < vector.size();)
            if((cg1 = (cg)vector.elementAt(j)) == null || cg1.d)
                vector.removeElementAt(j);
            else
                j++;

        return vector;
    }

    final void a(bz bz1, ClassFile bj1, MethodDescriptor ar1)
    {
        Vector vector = a(bz1);
        for(int i = 0; i < vector.size(); i++)
        {
            cg cg1 = (cg)vector.elementAt(i);
            LocalVariableTable ch1 = a(cg1.a());
            cg1.a(ch1);
        }

    }

    final void b(bz bz1, ClassFile bj1, MethodDescriptor ar1)
    {
        b = new Vector();
        Vector vector = a(bz1);
        for(int i = 0; i < vector.size(); i++)
        {
            cg cg1;
            String s = (cg1 = (cg)vector.elementAt(i)).f();
            LocalName bw = ar1.c(s);
            LocalVariableTable ch1 = new LocalVariableTable(a, bw, s, cg1.a(), cg1.b(), bj1);
            cg1.a(ch1);
            a(ch1);
        }

    }

    final String f()
    {
        String s = null;
        for(int i = 0; i < c.size(); i++)
        {
            bx bx1;
            if((bx1 = (bx)c.elementAt(i)).c == 'L')
                continue;
            if(s == null || bx1.c == 'T')
                s = bx1.b;
            else
                s = az.a(s, bx1.b);
        }

        return s;
    }

    public final String toString()
    {
        String s = "";
        for(int i = 0; i < b.size(); i++)
        {
            LocalVariableTable ch1 = (LocalVariableTable)b.elementAt(i);
            az az1 = null;
            if(ch1 != null)
                az1 = ch1.f();
            if(az1 == null)
                continue;
            if(s.length() > 0)
                s = s + ", ";
            s = s + az1;
        }

        return s;
    }

    final void a(Pstream printstream, int i)
    {
        for(int j = 0; j < b.size(); j++)
        {
            LocalVariableTable ch1;
            if((ch1 = (LocalVariableTable)b.elementAt(j)) != null)
                ch1.a(printstream, i);
        }

        if(Decompiler.debug)
        {
            for(int k = 0; k < c.size(); k++)
            {
                bx bx1;
                (bx1 = (bx)c.elementAt(k)).a(printstream);
            }

        }
    }

    int a;
    Vector b;
    Vector c;
    boolean d;
}
