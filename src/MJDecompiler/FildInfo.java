
package MJDecompiler;

import java.io.IOException;


final class FildInfo
{

    FildInfo(ClazzInputStream in, ClassFile bj1)throws IOException
    {
        a = bj1;
        b = new Access(in);
        c = bj1.getConstant(in);
        d = bj1.getConstant(in);
        int i = in.readUShort();
        for(int j = 0; j < i; j++)
        {
            String s = bj1.getConstant(in).toString();
            int k = in.readInt();
            if(s.equals("ConstantValue"))
            {
                e = bj1.getConstant(in);
            } else
            {
                System.err.println("Ignoring field attribute " + s);
                in.skip(k);
            }
        }

    }

    final void a(ByteCodeOutput bf1)
        throws IOException
    {
        b.writeBytecode(bf1);
        a.a(bf1, c);
        a.a(bf1, d);
        int i = 0;
        if(e != null)
            i++;
        bf1.writeUshort(i);
        if(e != null)
        {
            a.a(bf1, a.h("ConstantValue"));
            bf1.writeInt(2);
            a.a(bf1, e);
        }
    }

    final void a()
    {
        a.a(c, "internal member");
        a.a(d, "signature");
        if(e != null)
        {
            a.a(a.h("ConstantValue"), "attribute");
            a.a(e, "literal");
        }
    }

    final String b()
    {
        return c.toString();
    }

    final String c()
    {
        return d.toString();
    }

    final boolean d()
    {
        return b.isFinal();
    }

    final void a(bd bd1)
    {
        f = bd1;
    }

    final void writeSource(Pstream printstream)
    {
        az az1 = new az(c, d, a);
        printstream.print("    ");
        printstream.print(b.toString());
        printstream.print(az1.toString());
        if(e != null)
        {
            e.a(d.toString());
            printstream.print(" = ");
            printstream.print(e.toString());
        } else
        if(f != null)
        {
            f = f.a(d.toString());
            f.c();
            printstream.print(" = ");
            printstream.print(f);
        }
        printstream.println(";");
    }

    ClassFile a;
    Access b;
    ConstantPool c;
    ConstantPool d;
    ConstantPool e;
    bd f;
}
