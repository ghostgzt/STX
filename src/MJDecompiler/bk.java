package MJDecompiler;

import java.io.IOException;
final class bk extends ConstantPool
{

    bk(ClazzInputStream bc1)throws IOException
    {
        int i;
        char ac[] = new char[i = bc1.readUShort()];
        for(int j = 0; j < i; j++)
            ac[j] = bc1.readChar();

        a = new String(ac);
    }

    final void writeBytecode(ByteCodeOutput bf1)
        throws IOException
    {
        bf1.writeByte(2);
        bf1.writeUshort(a.length());
        bf1.writeChars(a);
    }

    final void a(ClassFile bj, String s)
    {
        a(s, "literal");
    }

    public final String toString()
    {
        return a;
    }

    final String a(ClassFile bj)
    {
        return "Unicode '" + a + "'";
    }

    String a;
}
