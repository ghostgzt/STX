
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CharTable.java

package mpe;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class CharTable
{

    public CharTable()
    {
    }

    public static void draw(Graphics g, Font f, int CanvasWidth, int CanvasHeight, int variant)
    {
        var = variant;
        TABLE_WIDTH = (byte)(var != 0 ? 6 : 5);
        TABLE_HEIGHT = (byte)(var != 0 ? 6 : 5);
        int cellW = f.stringWidth("W") + 8;
        int w = cellW * TABLE_WIDTH;
        int cellH = f.getHeight() + 2;
        int h = cellH * TABLE_HEIGHT;
        int widthOffset = (CanvasWidth - w) / 2;
        int heightOffset = (CanvasHeight - h) / 2;
        g.setColor(0x666699);
        g.fillRect(widthOffset + 2, heightOffset + 2, w, h);
        g.setColor(0xccccff);
        g.fillRect(widthOffset, heightOffset, w, h);
        g.setColor(0);
        for(int i = 0; i < TABLE_WIDTH * TABLE_HEIGHT; i++)
            g.drawString(""+tables[var].charAt(i), widthOffset + (i % TABLE_WIDTH) * cellW + cellW / 2, heightOffset + (i / TABLE_HEIGHT) * cellH, 17);

        g.drawRect((widthOffset + posX * cellW) - 1, (heightOffset + posY * cellH) - 1, cellW, cellH);
    }

    public static void right()
    {
        if(posX < TABLE_WIDTH - 1)
            posX++;
        else
            posX = 0;
    }

    public static void left()
    {
        if(posX > 0)
            posX--;
        else
            posX = TABLE_WIDTH - 1;
    }

    public static void down()
    {
        if(posY < TABLE_HEIGHT - 1)
            posY++;
        else
            posY = 0;
    }

    public static void up()
    {
        if(posY > 0)
            posY--;
        else
            posY = TABLE_HEIGHT - 1;
    }

    public static String select()
    {
        switch(var)
        {
        case 0: // '\0'
            switch(posY * 5 + posX)
            {
            case 0: // '\0'
                return "&nbsp;";

            case 1: // '\001'
                return "&copy;";

            case 2: // '\002'
                return "&reg;";

            case 3: // '\003'
                return "&trade;";

            case 4: // '\004'
                return "&euro;";

            case 5: // '\005'
                return "&quot;";

            case 6: // '\006'
                return "&bdquo;";

            case 7: // '\007'
                return "&ldquo;";

            case 8: // '\b'
                return "&laquo;";

            case 9: // '\t'
                return "&raquo;";

            case 10: // '\n'
                return "&plusmn;";

            case 11: // '\013'
                return "&gt;";

            case 12: // '\f'
                return "&lt;";

            case 13: // '\r'
                return "&ge;";

            case 14: // '\016'
                return "&le;";

            case 15: // '\017'
                return "&asymp;";

            case 16: // '\020'
                return "&ne;";

            case 17: // '\021'
                return "&equiv;";

            case 18: // '\022'
                return "&sect;";

            case 19: // '\023'
                return "&amp;";

            case 20: // '\024'
                return "&infin;";

            case 21: // '\025'
                return "&times;";

            case 22: // '\026'
                return "&#x2219;";

            case 23: // '\027'
                return "&permil;";

            case 24: // '\030'
                return "<br>";
            }
            // fall through

        case 1: // '\001'
            return ""+tables[var].charAt(posY * TABLE_WIDTH + posX);

        default:
            return "";
        }
    }

    public static void reset()
    {
        posX = posY = 0;
    }

    public static int posX;
    public static int posY;
    private static int var;
    private static int TABLE_WIDTH;
    private static int TABLE_HEIGHT;
    private static String tables[] = {
        " \u6F0F\u5E90\u9229\u2469\u5053\"\u9225\u70A9\uFFFD\u82A6\u7984\u5364<>\u922E\u30E2\u58B9\u922E\u581A\u58B5\u922E\367\uFFFD\u922D\u707B\u688Au2219\u9225\u7668u21b2", ";,(){}\"':$\\.=+-*/%[]!&|`<>?^~_@#\u3010\u3011\u3016\u3017"
    };

}
