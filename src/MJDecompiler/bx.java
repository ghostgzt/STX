
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;



final class bx
{

    bx(int i, String s, char c1)
    {
        a = i;
        b = s;
        c = c1;
    }

    final void a(Pstream printstream)
    {
        String s = null;
        switch(c)
        {
        case 76: // 'L'
            s = "Load";
            break;

        case 83: // 'S'
            s = "Store";
            break;

        case 84: // 'T'
            s = "Temporary";
            break;

        case 88: // 'X'
            s = "Scalar";
            break;
        }
        printstream.println("        // " + a + "\t" + b + "\t" + s);
    }

    int a;
    String b;
    char c;
}
