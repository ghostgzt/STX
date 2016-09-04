
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;

import java.io.OutputStream;

public final class cf extends OutputStream
{

    public cf(OutputStream outputstream)
    {
        String s = "line.separator";
        a = new byte[s.length()];
        for(int i = 0; i < s.length(); i++)
            a[i] = (byte)s.charAt(i);

    }

    public final void write(int i)
    {
        if(i == 10)
        {
            try
            {
                write(a);
                return;
            }
            catch(Exception _ex)
            {
                write(0);
            }
            return;
        } else
        {
            write(i);
            return;
        }
    }

    private byte a[];
}
