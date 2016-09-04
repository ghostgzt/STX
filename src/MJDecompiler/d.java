
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;



// Referenced classes of package MJDecompiler:
//            am, aj, bd, bh, 
//            dc, fo, bn

final class d extends am
{

    d(InvokeVirtual dc1, bd abd[])
    {
        a(((Operation) (abd.length > 0 ? ((Operation) (abd[0])) : ((Operation) (dc1)))));
        b(dc1);
        a = dc1;
        e = abd;
    }

    public final int a()
    {
        return a.a();
    }

    public final void c()
    {
        ConstantPool bh1;
        String s = (bh1 = a.g()).e();
        int i;
        for(int j = i = a.b() ? 0 : 1; j < e.length; j++)
            e[j] = e[j].a(a(s, j - i));

        for(int k = 0; k < e.length; k++)
            e[k].c();

    }

    final bn b()
    {
        fo fo1 = new fo();
        for(int i = 0; i < e.length; i++)
            fo1 = e[i].a(fo1);

        return a.b(fo1);
    }

    public final String toString()
    {
        return b().toString();
    }

    public final void a(Pstream printstream, int i)
    {
        printstream.println(aj.d(i) + this + ";");
    }

    InvokeVirtual a;
    bd e[];
}
