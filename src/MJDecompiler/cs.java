
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package MJDecompiler;



final class cs
{

    private static boolean a(String s, int i, String s1, int j)
    {
label0:
        do
        {
            do
            {
                if(i < s.length() && j < s1.length())
                {
                    switch(s1.charAt(j))
                    {
                    default:
                        continue;

                    case 42: // '*'
                        if(s.charAt(i++) == '.')
                            return a(s, i - 1, s1, j + 1);
                        if(a(s, i - 1, s1, j + 1))
                            return true;
                        break;

                    case 63: // '?'
                        if(s.charAt(i++) == '.')
                            return false;
                        j++;
                        break;
                    }
                } else
                {
                    for(; j < s1.length() && s1.charAt(j) == '*'; j++);
                    return i == s.length() && j == s1.length();
                }
                continue label0;
            } while(s.charAt(i++) == s1.charAt(j++));
            return false;
        } while(true);
    }

    public static final boolean a(String s, String s1)
    {
        return a(s, 0, s1, 0);
    }

    public static final String[] a(String s)
        throws Exception
    {
        return null;
    }

    public static final String[] a(String as[])
        throws Exception
    {
        for(int i = 0; i < as.length; i++)
        {
            if(as[i].indexOf('*') < 0 && as[i].indexOf('?') < 0)
                continue;
            String as1[] = a(as[i]);
            String as2[] = new String[(as.length - 1) + as1.length];
            for(int j = 0; j < i; j++)
                as2[j] = as[j];

            for(int k = 0; k < as1.length; k++)
                as2[k + i] = as1[k];

            for(int l = i + 1; l < as.length; l++)
                as2[(l - 1) + as1.length] = as[l];

            as = as2;
        }

        return as;
    }

    public static final void b(String as[])
        throws Exception
    {
        as = a(as);
        for(int i = 0; i < as.length; i++)
            System.out.println(as[i]);

    }

    cs()
    {
    }
}
