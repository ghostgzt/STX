
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   HGB2312.java

package mpe;

import java.io.InputStream;

public class HGB2312
{

    public HGB2312()
        throws Exception
    {
        map = new byte[15228];
        InputStream is = getClass().getResourceAsStream("/mpe/gb2u.dat");
        is.read(map);
        is.close();
    }

    public String gb2utf8(byte gb[])
        throws Exception
    {
        buffer = new byte[gb.length + gb.length / 2 + 3];
        index = 0;
        for(int i = 0; i < gb.length;)
            if(gb[i] >= 0)
            {
                fillBuffer(gb[i++]);
            } else
            {
                int h = 256 + gb[i++];
                int l = 256 + gb[i++];
                h = h - 160 - 1;
                l = l - 160 - 1;
                if(h < 9)
                {
                    int ind = h * 94 + l << 1;
                    int c = byte2Int(map[ind]) << 8 | byte2Int(map[ind + 1]);
                    fillBuffer(c);
                } else
                if(h >= 9 && h <= 14)
                    fillBuffer(0);
                else
                if(h > 14)
                {
                    int ind = (h -= 6) * 94 + l << 1;
                    int c = byte2Int(map[ind]) << 8 | byte2Int(map[ind + 1]);
                    fillBuffer(c);
                } else
                {
                    fillBuffer(0);
                }
            }

        return new String(buffer, 0, index, "UTF-8");
    }

    private void fillBuffer(int value)
    {
        if(value <= 127)
            buffer[index++] = (byte)value;
        else
        if(value >= 128 && value <= 2047)
        {
            byte b1 = (byte)(0x60 | value >> 6);
            byte b2 = (byte)(0x80 | value & 0x3f);
            buffer[index++] = b1;
            buffer[index++] = b2;
        } else
        if(value >= 2048 && value <= 65535)
        {
            byte b1 = (byte)(0xe0 | value >> 12);
            byte b2 = (byte)(0x80 | value >> 6 & 0x3f);
            byte b3 = (byte)(0x80 | value & 0x3f);
            buffer[index++] = b1;
            buffer[index++] = b2;
            buffer[index++] = b3;
        } else
        if(value >= 0x10000 && value <= 0x1fffff)
        {
            byte b1 = (byte)(0x1e | value >> 18);
            byte b2 = (byte)(0x80 | value >> 12 & 0x3f);
            byte b3 = (byte)(0x80 | value >> 6 & 0x3f);
            byte b4 = (byte)(0x80 | value & 0x3f);
            buffer[index++] = b1;
            buffer[index++] = b2;
            buffer[index++] = b3;
            buffer[index++] = b4;
        } else
        if(value >= 0x200000 && value <= 0x3ffffff)
        {
            byte b1 = (byte)(0x3e | value >> 24);
            byte b2 = (byte)(0x80 | value >> 18 & 0x3f);
            byte b3 = (byte)(0x80 | value >> 12 & 0x3f);
            byte b4 = (byte)(0x80 | value >> 6 & 0x3f);
            byte b5 = (byte)(0x80 | value & 0x3f);
            buffer[index++] = b1;
            buffer[index++] = b2;
            buffer[index++] = b3;
            buffer[index++] = b4;
            buffer[index++] = b5;
        } else
        if(value >= 0x4000000 && value <= 0x7fffffff)
        {
            byte b1 = (byte)(0x7e | value >> 30);
            byte b2 = (byte)(0x80 | value >> 24 & 0x3f);
            byte b3 = (byte)(0x80 | value >> 18 & 0x3f);
            byte b4 = (byte)(0x80 | value >> 12 & 0x3f);
            byte b5 = (byte)(0x80 | value >> 6 & 0x3f);
            byte b6 = (byte)(0x80 | value & 0x3f);
            buffer[index++] = b1;
            buffer[index++] = b2;
            buffer[index++] = b3;
            buffer[index++] = b4;
            buffer[index++] = b5;
            buffer[index++] = b6;
        }
    }

    private int byte2Int(byte b)
    {
        if(b < 0)
            return 256 + b;
        else
            return b;
    }

    private byte map[];
    private byte buffer[];
    private int index;
}
