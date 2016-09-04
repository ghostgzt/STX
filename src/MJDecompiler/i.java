
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;


// Referenced classes of package MJDecompiler:
//            bd, bg, bn, fo

final class i extends bd
{

    i(bd abd[])
    {
        super.i = abd;
    }

    public final int a()
    {
        return super.i[super.i.length - 1].a();
    }

    public final fo a(fo fo1)
    {
        return fo1.a(b());
    }

    public final bn b()
    {
        StringBuffer stringbuffer = new StringBuffer("{ ");
        for(int j = 0; j < super.i.length; j++)
        {
            if(j != 0)
                stringbuffer.append(", ");
            stringbuffer.append(super.i[j]);
        }

        stringbuffer.append(" }");
        String s = "[" + super.i[0].b().b;
        return new bg(s, stringbuffer.toString());
    }
}
