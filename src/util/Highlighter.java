package util;

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.lcdui.*;
import mpe.Document;

public class Highlighter {

    public Highlighter(String resource) {
        DataInputStream dis = new DataInputStream(getClass().getResourceAsStream(resource));
        try {
            if (dis.readShort() != -5430) {
                throw new IllegalArgumentException("This is not Higlighter *.stx file!");
            }
            int nCategories = dis.readInt();
            vColors = new int[nCategories];
            vKeywords = new String[nCategories][];
            for (int i = 0; i < nCategories; i++) {
                vColors[i] = dis.readInt();
                int nKeywords = dis.readInt();
                vKeywords[i] = new String[nKeywords];
                for (int j = 0; j < nKeywords; j++) {
                    vKeywords[i][j] = dis.readUTF();
                }

            }

            int nQuotes = dis.readInt();
            quoteChar = new char[nQuotes];
            for (int i = 0; i < nQuotes; i++) {
                quoteChar[i] = dis.readChar();
            }

            multiCommentStart = dis.readUTF();
            multiCommentEnd = dis.readUTF();
            singleComment = dis.readUTF();
        } catch (IOException ioexception) {
        }
    }

    public Image highlight(String text, Font font, boolean isOK, int tagx, Document getDocument) {
        int w = font.stringWidth(text);
        if (text.equals("")) {
            w = 1;
        }
        Image img = Image.createImage(w, font.getHeight());
        Graphics g = img.getGraphics();
        g.setFont(font);
        if (isOK && tagx != -1) {
            g.setColor(0xffcc00);
            g.fillRect(font.stringWidth(text.substring(0, tagx)), 0, font.stringWidth(text.substring(tagx, tagx + 1)), font.getHeight());
        }
        g.setColor(0);
        g.drawString(text, 0, 0, 20);
        int i0 = 0;
        for (int j = 0; j < vKeywords.length; j++) {
            g.setColor(vColors[j]);
            i0 = 0;
            for (int i = 0; i < vKeywords[j].length; i++) {
                while (i0 < text.length()) {
                    i0 = text.indexOf(vKeywords[j][i], i0);
                    if (i0 < 0) {
                        break;
                    }
                    char c = i0 + vKeywords[j][i].length() >= text.length() ? '.' : text.charAt(i0 + vKeywords[j][i].length());
                    char c1 = i0 <= 0 ? '.' : text.charAt(i0 - 1);
                    if (!isName(c) && !isName(c1)) {
                        int color = g.getColor();
                        g.setColor(0xffffff);
                        g.fillRect(font.stringWidth(text.substring(0, i0)), 0, font.stringWidth(vKeywords[j][i]), w);
                        g.setColor(color);
                        g.drawSubstring(text, i0, vKeywords[j][i].length(), font.stringWidth(text.substring(0, i0)), 0, 20);
                    }
                    i0++;
                }
            }

        }

        g.setColor(0xb3e6b3);
        for (i0 = 0; i0 < text.length(); i0++) {
            i0 = text.indexOf(multiCommentStart, i0);
            if (i0 < 0) {
                break;
            }
            int i1 = text.indexOf(multiCommentEnd, i0 + 2);
            if (i1 < 0) {
                i1 = text.length() - 2;
            }
            int color = g.getColor();
            g.setColor(0xffffff);
            g.fillRect(font.stringWidth(text.substring(0, i0)), 0, font.stringWidth(text.substring(i0, i1 + 2)), w);
            g.setColor(color);
            g.drawSubstring(text, i0, (i1 - i0) + 2, font.stringWidth(text.substring(0, i0)), 0, 20);
            i0 = i1;
        }

        i0 = text.indexOf(singleComment);
        if (i0 >= 0) {
            int color = g.getColor();
            g.setColor(0xffffff);
            g.fillRect(font.stringWidth(text.substring(0, i0)), 0, font.stringWidth(text.substring(i0)), w);
            g.setColor(color);
            g.drawSubstring(text, i0, text.length() - i0, font.stringWidth(text.substring(0, i0)), 0, 20);
        }
        g.setColor(0xff8000);
        for (int i = 0; i < quoteChar.length; i++) {
            for (i0 = 0; i0 < text.length(); i0++) {
                i0 = text.indexOf(quoteChar[i], i0);
                if (i0 < 0) {
                    break;
                }
                int i1 = text.indexOf(quoteChar[i], i0 + 1);
                if (i1 < 0) {
                    i1 = text.length() - 1;
                }
                int color = g.getColor();
                g.setColor(0xffffff);
                g.fillRect(font.stringWidth(text.substring(0, i0)), 0, font.stringWidth(text.substring(i0, i1 + 1)), w);
                g.setColor(color);
                g.drawSubstring(text, i0, (i1 - i0) + 1, font.stringWidth(text.substring(0, i0)), 0, 20);
                i0 = i1;
            }
        }


        return img;
    }

    private boolean isName(char c) {
        return c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c == '_' || c == '$';
    }
    private String vKeywords[][];
    private int vColors[];
    private char quoteChar[];
    private String multiCommentStart;
    private String multiCommentEnd;
    private String singleComment;
    static final short MAGIC = -5430;
}
