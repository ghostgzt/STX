
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Split.java

package mpe;


public class Split
{

    public Split()
    {
    }

    public String[] Split(String Source, String Delimiter)
    {
        String aSplit[] = (String[])null;
        String t[] = (String[])null;
        String sTemp = Source;
        int iCount = 0;
        int iLength = Delimiter.length();
        boolean bEnd = sTemp.endsWith(Delimiter);
        do
        {
            int iPos = sTemp.indexOf(Delimiter);
            if(iPos < 0)
                break;
            if(iCount > 0)
                t = aSplit;
            aSplit = new String[++iCount];
            if(iCount > 1)
            {
                for(int i = 0; i < t.length; i++)
                    aSplit[i] = t[i];

            }
            aSplit[iCount - 1] = sTemp.substring(0, iPos);
            sTemp = sTemp.substring(iPos + iLength);
        } while(true);
        if(sTemp.length() >= 0 || bEnd)
        {
            if(iCount > 0)
                t = aSplit;
            aSplit = new String[++iCount];
            if(iCount > 1)
            {
                for(int i = 0; i < t.length; i++)
                    aSplit[i] = t[i];

            }
            aSplit[iCount - 1] = sTemp;
        }
        return aSplit;
    }
}
