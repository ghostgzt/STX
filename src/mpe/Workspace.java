package mpe;

import java.util.Vector;
import javax.microedition.lcdui.*;
import util.Highlighter;


public class Workspace extends Canvas implements CommandListener {

    private class TermInputThread extends Thread {

        public void kill() {
            synchronized (killtoken) {
                interrupt();
            }
        }

        public void run() {
            try {
                Thread.sleep(1000L);
                inputKey = -1;
                selChar = -1;
                repaint();
            } catch (Exception exception) {
            }
        }
        final Object killtoken;

        private TermInputThread() {
            killtoken = "killtoken";
        }

        TermInputThread(TermInputThread terminputthread) {
            this();
        }
    }

    public Workspace(MPE mpe) {
        press = false;
        inputKey = -1;
        selChar = -1;
        charMode = 0;
        inCharTable = 0;
        isO = false;
        tagX = -1;
        tagY = -1;
        content = "";
        state = 1002;
        gg = new Highlighter("/mpe/keyword.stx");
        cc = 0;
        dd = 0;
        tX = -100;
        setFullScreenMode(true);
        this.mpe = mpe;
        cmdOk = new Command("\u786E\u5B9A", 4, 0);
        cmdBack = new Command("\u8FD4\u56DE", 2, 0);
        docs = new Vector();
        index = 0;
        font = Font.getFont(Database.fontFace != 0 ? Database.fontFace != 1 ? 32 : 64 : 0, 0, Database.fontSize != 0 ? ((int) (Database.fontSize != 1 ? 16 : 0)) : 8);
        new Constants();
        loadLayout(Constants.layoutLat);
        CharTable.posX = 0;
        CharTable.posY = 0;
        sizeChanged(getWidth(), getHeight());
    }

    protected void paint(Graphics g) {
        g.setColor(0xffffff);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setFont(font);
        numBarW = Database.linenum ? font.stringWidth((new Integer(getDocument().data.size())).toString()) + 4 : 0;
        if (font.substringWidth(getDocument().data.elementAt(getDocument().globalY()).toString(), 0, getDocument().curX) > (getDocument().stringOffset + getWidth()) - numBarW - 7) {
            getDocument().stringOffset = (font.substringWidth(getDocument().data.elementAt(getDocument().globalY()).toString(), 0, getDocument().curX) - getWidth()) + numBarW + 7;
        } else if (font.substringWidth(getDocument().data.elementAt(getDocument().globalY()).toString(), 0, getDocument().curX) < getDocument().stringOffset) {
            getDocument().stringOffset = font.substringWidth(getDocument().data.elementAt(getDocument().globalY()).toString(), 0, getDocument().curX);
        }
        if (Database.cyrLayout == 1 && getDocument().inSelection) {
            getDocument().calcSelection();
            g.setColor(0xbbbafc);
            if (getDocument().selY1 == getDocument().selY2) {
                try {
                    g.fillRect((font.substringWidth(getDocument().data.elementAt(getDocument().selY1).toString(), 0, getDocument().selX1) + numBarW) - getDocument().stringOffset, (getDocument().selY1 - getDocument().vPosition) * font.getHeight(), font.substringWidth(getDocument().data.elementAt(getDocument().selY1).toString(), getDocument().selX1, getDocument().selX2 - getDocument().selX1), font.getHeight());
                } catch (Exception exception) {
                }
            } else {
                for (int i = getDocument().selY1 < getDocument().vPosition ? getDocument().vPosition : getDocument().selY1; i <= getDocument().selY2 && i < getDocument().vPosition + maxLines; i++) {
                    if (i == getDocument().selY1) {
                        g.fillRect((font.substringWidth(getDocument().data.elementAt(getDocument().selY1).toString(), 0, getDocument().selX1) + numBarW) - getDocument().stringOffset, (getDocument().selY1 - getDocument().vPosition) * font.getHeight(), font.stringWidth(getDocument().data.elementAt(getDocument().selY1).toString().substring(getDocument().selX1)), font.getHeight());
                    } else if (i == getDocument().selY2) {
                        g.fillRect(numBarW - getDocument().stringOffset, (getDocument().selY2 - getDocument().vPosition) * font.getHeight(), font.substringWidth(getDocument().data.elementAt(getDocument().selY2).toString(), 0, getDocument().selX2), font.getHeight());
                    } else {
                        g.fillRect(numBarW - getDocument().stringOffset, (i - getDocument().vPosition) * font.getHeight(), font.stringWidth(getDocument().data.elementAt(i).toString()), font.getHeight());
                    }
                }

            }
        }
        g.setColor(0);
        for (int i = 0; getDocument().data.size() > i + getDocument().vPosition && i < maxLines; i++) {
            content = getDocument().data.elementAt(i + getDocument().vPosition).toString();
            if (Database.cyrLayout == 0) {
                if (getDocument().vPosition == 0) {
                    g.drawImage(gg.highlight(content, font, i == tagY, tagX, getDocument()), (numBarW - getDocument().stringOffset) + 1, i * font.getHeight() + 1, 20);
                } else if (getDocument().vPosition > 0) {
                    g.drawImage(gg.highlight(content, font, i == tagY - getDocument().vPosition, tagX, getDocument()), (numBarW - getDocument().stringOffset) + 1, i * font.getHeight() + 1, 20);
                }
            } else {
                g.setColor(0);
                g.drawString(content, (numBarW - getDocument().stringOffset) + 1, i * font.getHeight() + 1, 20);
            }
        }

        content = null;
        System.gc();
        g.setColor(0xe0e0e0);
        g.fillRect(0, 0, numBarW, getHeight());
        g.setColor(0);
        for (int i = 0; getDocument().data.size() > i + getDocument().vPosition && i < maxLines; i++) {
            g.drawString((new Integer(i + getDocument().vPosition + 1)).toString(), numBarW, i * font.getHeight() + 1, 24);
        }

        int wsHeight = getHeight() - font.getHeight();
        if (getDocument().data.size() > maxLines) {
            g.setColor(0xc8c8c8);
            g.fillRect(getWidth() - 3, 0, 3, wsHeight);
            g.setColor(0);
            int barH = wsHeight - (wsHeight * (getDocument().data.size() - maxLines)) / getDocument().data.size();
            g.fillRect(getWidth() - 3, ((wsHeight - barH) * getDocument().vPosition) / (getDocument().data.size() - maxLines), 3, barH);
            g.setColor(0xc8c8c8);
            g.fillRect(0, getHeight() - font.getHeight() - 4, getWidth(), getHeight() - 4);
            g.setColor(0);
            g.fillRect(0, getHeight() - font.getHeight() - 4, getWidth(), getHeight() - 4);
        }
        if (getDocument().data.elementAt(getDocument().globalY()).toString().length() > 0) {
            int fsize = getDocument().data.elementAt(getDocument().globalY()).toString().length();
            double ftmp = (double) getDocument().curX * 1.0D;
            double percent = (ftmp / (double) fsize) * 100D;
            g.setColor(0xc8c8c8);
            g.fillRect(0, getHeight() - font.getHeight() - 3, getWidth(), getHeight() - 4);
            g.setColor(0);
            g.fillRect(0, getHeight() - font.getHeight() - 3, (int) (percent * ((double) getWidth() / 100D)), getHeight() - 4);
            System.out.println(percent);
        }
        g.setColor(39423);
        g.fillRect(0, getHeight() - font.getHeight(), getWidth(), font.getHeight());
        g.setColor(0);
        if (inputKey > -1 && charMode != 2) {
            int leftPos = 3;
            if (!isO) {
                for (int i = 0; i < layout[charMode * 10 + inputKey + 1].length(); i++) {
                    g.drawString(layout[charMode * 10 + inputKey + 1].substring(i, i + 1), leftPos, getHeight(), 36);
                    if (selChar == i) {
                        g.drawRect(leftPos - 3, getHeight() - font.getHeight(), font.stringWidth(layout[charMode * 10 + inputKey + 1].substring(i, i + 1)) + 6, font.getHeight() - 1);
                    }
                    leftPos += font.stringWidth(layout[charMode * 10 + inputKey + 1].substring(i, i + 1)) + 6;
                }

            } else {
                for (int i = 0; i < layout[0].length(); i++) {
                    g.drawString(layout[0].substring(i, i + 1), leftPos, getHeight(), 36);
                    if (selChar == i) {
                        g.drawRect(leftPos - 3, getHeight() - font.getHeight(), font.stringWidth(layout[charMode * 10 + inputKey + 1].substring(i, i + 1)) + 6, font.getHeight() - 1);
                    }
                    leftPos += font.stringWidth(layout[0].substring(i, i + 1)) + 6;
                }

            }
        } else {
            if (state == 1001) {
                g.drawString("\u5BF9\u5E94  \u884C: " + String.valueOf(tagY + 1) + ";" + "\u5217: " + String.valueOf(tagX + 1), 1, getHeight(), 36);
            } else {
                g.drawString(getDocument().inSelection ? "\u6807\u8BB0\u6587\u672C" : "\u884C: " + (new Integer(getDocument().globalY() + 1)).toString() + "; " + "\u5217" + ": " + (new Integer(getDocument().curX + 1)).toString(), 1, getHeight(), 36);
            }
            if (charMode == 0) {
                g.drawString("abc", getWidth(), getHeight(), 40);
            } else if (charMode == 1) {
                g.drawString("ABC", getWidth(), getHeight(), 40);
            } else if (charMode == 2) {
                g.drawString("\u4E2D\u6587", getWidth(), getHeight(), 40);
            } else if (charMode == 3) {
                g.drawString("123", getWidth(), getHeight(), 40);
            }
        }
        if (Database.cyrLayout == 0 && getDocument().inSelection) {
            getDocument().calcSelection();
            g.setColor(255);
            if (getDocument().selY1 == getDocument().selY2) {
                try {
                    g.drawRect((font.substringWidth(getDocument().data.elementAt(getDocument().selY1).toString(), 0, getDocument().selX1) + numBarW) - getDocument().stringOffset, (getDocument().selY1 - getDocument().vPosition) * font.getHeight(), font.substringWidth(getDocument().data.elementAt(getDocument().selY1).toString(), getDocument().selX1, getDocument().selX2 - getDocument().selX1), font.getHeight());
                } catch (Exception exception1) {
                }
            } else {
                for (int i = getDocument().selY1 < getDocument().vPosition ? getDocument().vPosition : getDocument().selY1; i <= getDocument().selY2 && i < getDocument().vPosition + maxLines; i++) {
                    if (i == getDocument().selY1) {
                        g.drawRect((font.substringWidth(getDocument().data.elementAt(getDocument().selY1).toString(), 0, getDocument().selX1) + numBarW) - getDocument().stringOffset, (getDocument().selY1 - getDocument().vPosition) * font.getHeight(), font.stringWidth(getDocument().data.elementAt(getDocument().selY1).toString().substring(getDocument().selX1)), font.getHeight());
                    } else if (i == getDocument().selY2) {
                        g.drawRect(numBarW - getDocument().stringOffset, (getDocument().selY2 - getDocument().vPosition) * font.getHeight(), font.substringWidth(getDocument().data.elementAt(getDocument().selY2).toString(), 0, getDocument().selX2), font.getHeight());
                    } else {
                        g.drawRect(numBarW - getDocument().stringOffset, (i - getDocument().vPosition) * font.getHeight(), font.stringWidth(getDocument().data.elementAt(i).toString()), font.getHeight());
                    }
                }

            }
        }
        g.setColor(0xff0000);
        g.drawLine((font.substringWidth(getDocument().data.elementAt(getDocument().globalY()).toString(), 0, getDocument().curX) + numBarW) - getDocument().stringOffset, getDocument().curY * font.getHeight(), (font.substringWidth(getDocument().data.elementAt(getDocument().globalY()).toString(), 0, getDocument().curX) + numBarW) - getDocument().stringOffset, getDocument().curY * font.getHeight() + font.getHeight());
        if (inCharTable > 0) {
            CharTable.draw(g, font, getWidth(), getHeight(), inCharTable - 1);
        }
    }

    private void finddown() {
        int p = getDocument().globalY();
        int x = getDocument().curX;
        String tag = "";
        String s = "";
        if (x < getDocument().data.elementAt(p).toString().length()) {
            s = getDocument().data.elementAt(p).toString().substring(x, x + 1);
        }
        if (s.equals("{")) {
            tag = "}";
        } else if (s.equals("(")) {
            tag = ")";
        }
        String str = "";
        boolean find = false;
        if (s.equals("(") || s.equals("{")) {
            int a = 0;
            int b = 0;
            while (!find) {
                str = getDocument().data.elementAt(p).toString();
                if (x < str.length()) {
                    str = str.substring(x, x + 1);
                    if (str.equals(tag)) {
                        b++;
                    } else if (str.equals(s)) {
                        a++;
                    }
                    if (a - b == 0) {
                        System.out.println("x=" + x);
                        System.out.println("y=" + p);
                        tagX = x;
                        tagY = p;
                        state = 1001;
                        cc = getDocument().globalY();
                        dd = 0;
                        tX = numBarW + font.stringWidth(getDocument().data.elementAt(p).toString().substring(0, tagX));
                        break;
                    }
                    x++;
                    continue;
                }
                if (p >= getDocument().data.size() - 1) {
                    break;
                }
                p++;
                str = getDocument().data.elementAt(p).toString();
                x = 0;
            }
        }
    }

    private void findup() {
        int p = getDocument().globalY();
        int x = getDocument().curX;
        String tag = "";
        String s = "";
        if (x > 0) {
            s = getDocument().data.elementAt(p).toString().substring(x - 1, x);
        }
        if (s.equals("}")) {
            tag = "{";
        } else if (s.equals(")")) {
            tag = "(";
        }
        String str = "";
        boolean find = false;
        if (s.equals(")") || s.equals("}")) {
            int a = 0;
            int b = 0;
            while (!find) {
                str = getDocument().data.elementAt(p).toString();
                if (x > 0) {
                    str = str.substring(x - 1, x);
                    if (str.equals(s)) {
                        a++;
                    } else if (str.equals(tag)) {
                        b++;
                    }
                    if (a - b == 0) {
                        x--;
                        cc = getDocument().globalY();
                        dd = 0;
                        tagX = x;
                        tagY = p;
                        state = 1001;
                        System.out.println("x=" + x);
                        System.out.println("y=" + p);
                        break;
                    }
                    x--;
                    continue;
                }
                if (p <= 0) {
                    break;
                }
                p--;
                str = getDocument().data.elementAt(p).toString();
                x = str.length();
            }
        }
    }

    protected void keyPressed(int keyCode) {
       
        if (inCharTable > 0) {
            tagX = -1;
            tagY = -1;
            switch (keyCode) {
                case -1:
                case 50: // '2'
                    CharTable.up();
                    break;

                case -3:
                case 52: // '4'
                    CharTable.left();
                    break;

                case -4:
                case 54: // '6'
                    CharTable.right();
                    break;

                case -2:
                case 56: // '8'
                    CharTable.down();
                    break;

                case -5:
                    if (!press) {
                        getDocument().Insert(CharTable.select());
                    }
                    findup();
                    finddown();
                    inCharTable = 0;
                    press = false;
                    break;

                case 53: // '5'
                    press = true;
                    getDocument().Insert(CharTable.select());
                    break;

                case 42: // '*'
                    inCharTable = 0;
                    break;
            }
        } else {
            state = 1002;
            isO = false;
            if (keyCode == -1) {
                if (t != null) {
                    t.kill();
                    t = null;
                } else {
                    getDocument().moveCursor(1);
                    findup();
                    finddown();
                }
            } else if (keyCode == -3) {
                if (t != null) {
                    t.kill();
                    t = null;
                } else {
                    if (getDocument().curX == 0 && getDocument().globalY() == 0) {
                        getDocument().setCursor(getDocument().data.elementAt(getDocument().data.size() - 1).toString().length(), getDocument().data.size() - 1);
                    } else {
                        getDocument().moveCursor(0);
                    }
                    findup();
                    finddown();
                }
            } else if (keyCode == -4) {
                if (t != null) {
                    t.kill();
                    t = null;
                } else {
                    if (getDocument().globalY() == getDocument().data.size() - 1 && getDocument().curX == getDocument().data.elementAt(getDocument().data.size() - 1).toString().length()) {
                        getDocument().setCursor(0, 0);
                    } else {
                        getDocument().moveCursor(2);
                    }
                    findup();
                    finddown();
                }
            } else if (keyCode == -2) {
                if (t != null) {
                    t.kill();
                    t = null;
                } else {
                    getDocument().moveCursor(3);
                    System.out.println();
                    findup();
                    finddown();
                }
            } else if (keyCode == -6) {
                mpe.showMenu(0);
            } else if (keyCode == -8) {
                getDocument().Backspace();
                tagX = -1;
                tagY = -1;
            } else if (keyCode == -7) {
                if (getDocument().inSelection) {
                    getDocument().endSelection();
                } else {
                    getDocument().startSelection();
                }
            } else if (keyCode == -5) {
                getDocument().Insert("\n");
                tagX = -1;
                tagY = -1;
                int a = 0;
                int ff = 0;
                String upstr = "";
                for (int i = getDocument().globalY(); i > -1; i--) {
                    upstr = getDocument().data.elementAt(i).toString();
                    if (upstr.length() <= 0) {
                        continue;
                    }
                    if (upstr.substring(upstr.length() - 1, upstr.length()).equals(";") || upstr.substring(upstr.length() - 1, upstr.length()).equals(")")) {
                        for (int j = 0; j < upstr.length(); j++) {
                            if (!upstr.substring(j, j + 1).equals(" ")) {
                                break;
                            }
                            ff++;
                        }

                        for (int k = 0; k < ff; k++) {
                            getDocument().Insert(" ");
                        }

                        ff = 0;
                        break;
                    }
                    if (upstr.substring(upstr.length() - 1, upstr.length()).equals("{")) {
                        for (int j = 0; j < upstr.length(); j++) {
                            if (!upstr.substring(j, j + 1).equals(" ")) {
                                break;
                            }
                            ff++;
                        }

                        if (ff > 0) {
                            for (int k = 0; k < ff; k++) {
                                getDocument().Insert(" ");
                            }

                            ff = 0;
                        }
                        break;
                    }
                    if (upstr.substring(upstr.length() - 1, upstr.length()).equals("}")) {
                        break;
                    }
                }

            } else if (keyCode == 35) {
                if (charMode < 3) {
                    charMode++;
                } else {
                    charMode = 0;
                }
            } else if (keyCode == 42) {
                inCharTable = 2;
            } else if (keyCode >= 49 && keyCode <= 57) {
                tagX = -1;
                tagY = -1;
                if (charMode == 3) {
                    getDocument().Insert((char) keyCode + "");
                    if (getDocument().inSelection) {
                        getDocument().endSelection();
                    }
                } else if (charMode == 2 && keyCode != -5) {
                    mpe.tbLineEditor = new TextBox("\u7F16\u8F91\u884C", mpe.w.getDocument().data.elementAt(mpe.w.getDocument().getCursor(1)).toString(), 5000, 0);
                    mpe.tbLineEditor.addCommand(cmdOk);
                    mpe.tbLineEditor.addCommand(cmdBack);
                    mpe.tbLineEditor.setCommandListener(this);
                    mpe.display.setCurrent(mpe.tbLineEditor);
                    if (getDocument().inSelection) {
                        getDocument().endSelection();
                    }
                } else if (charMode != 3 && charMode != 2) {
                    if (getDocument().inSelection) {
                        getDocument().endSelection();
                    }
                    if (inputKey == keyCode - 49) {
                        selChar = selChar >= layout[charMode * 10 + inputKey + 1].length() - 1 ? 0 : selChar + 1;
                        getDocument().Backspace();
                        if (keyCode == 49 && selChar == 1) {
                            getDocument().Insert(",");
                        } else {
                            getDocument().Insert(layout[charMode * 10 + inputKey + 1].substring(selChar, selChar + 1));
                        }
                    } else {
                        inputKey = keyCode - 49;
                        selChar = 0;
                        if (keyCode == 49 && selChar == 0) {
                            getDocument().Insert(".");
                        } else {
                            getDocument().Insert(layout[charMode * 10 + inputKey + 1].substring(selChar, selChar + 1));
                        }
                    }
                    killTimer();
                    createTimer();
                }
            } else if (keyCode == 48) {
                tagX = -1;
                tagY = -1;
                if (charMode != 3) {
                    if (getDocument().inSelection) {
                        getDocument().endSelection();
                    }
                    isO = true;
                    if (inputKey == keyCode - 48) {
                        selChar = selChar >= layout[0].length() - 1 ? 0 : selChar + 1;
                        getDocument().Backspace();
                        getDocument().Insert(layout[0].substring(selChar, selChar + 1));
                    } else {
                        inputKey = keyCode - 48;
                        selChar = 0;
                        getDocument().Insert(layout[0].substring(selChar, selChar + 1));
                    }
                    killTimer();
                    createTimer();
                } else {
                    getDocument().Insert("0");
                }
            }
            if (keyCode < 48 || keyCode > 57) {
                inputKey = -1;
                selChar = -1;
            }
        }
        repaint();
    }

    public void commandAction(Command c, Displayable d) {
        if (c == cmdOk) {
            mpe.w.getDocument().data.setElementAt("", mpe.w.getDocument().getCursor(1));
            mpe.w.getDocument().setCursor(0, mpe.w.getDocument().getCursor(1));
            mpe.w.getDocument().Insert(mpe.tbLineEditor.getString());
            mpe.showMenu(-1);
        } else if (c == cmdBack) {
            mpe.tbLineEditor = null;
            mpe.showMenu(-1);
        }
    }

    public void createDocument() {
        docs.addElement(new Document("NewMidlet", true, maxLines));
        index = docs.size() - 1;
    }

    public boolean openDocument(String name) {
        for (int i = 0; i < docs.size(); i++) {
            if (getDocumentNum(i).filename.equals(name)) {
                index = i;
                return true;
            }
        }

        return false;
    }

    public void addDocument(Document doc) {
        docs.addElement(doc);
        index = docs.size() - 1;
    }

    public void closeDocument() {
        if (docs.size() != 1) {
            docs.removeElementAt(index);
        }
        if (index >= docs.size()) {
            index--;
        }
        System.gc();
    }

    public int getDocCount() {
        return docs.size();
    }

    public Document getDocument() {
        return (Document) docs.elementAt(index);
    }

    public Document getDocumentNum(int num) {
        return (Document) docs.elementAt(num);
    }

    public void showDocument(int num) {
        index = num;
        repaint();
    }

    protected void keyRepeated(int key) {
        if (key == -1 || key == -2 || key == -3 || key == -4 || key == -7 || key == -8) {
            keyPressed(key);
        }
    }

    private void loadLayout(String l[]) {
        layout = new String[36];
        for (int i = 0; i < 20; i++) {
            layout[i] = Constants.layoutLat[i];
        }

    }

    protected void sizeChanged(int w, int h) {
        super.sizeChanged(w, h);
        setFullScreenMode(true);
        maxLines = (int) Math.floor(getHeight() / font.getHeight()) - 1;
        for (int i = 0; i < docs.size(); i++) {
            ((Document) docs.elementAt(i)).setMaxLines(maxLines);
        }

    }

    private void createTimer() {
        t = new TermInputThread(null);
        t.start();
    }

    private void killTimer() {
        if (t != null) {
            t.kill();
            t = null;
        }
    }
    boolean press;
    private int inputKey;
    private int selChar;
    private int charMode;
    private int index;
    public int maxLines;
    private Font font;
    private String layout[];
    private Vector docs;
    private int inCharTable;
    private TermInputThread t;
    private MPE mpe;
    private Command cmdBack;
    private Command cmdOk;
    private boolean isO;
    private int tagX;
    private int tagY;
    String content;
    private final int showXY = 1001;
    private final int showLine = 1002;
    private int state;
    Highlighter gg;
    int cc;
    int dd;
    int numBarW;
    int tX;
    public static int errorLine;
    public static int errorStart;
    public static int errorEnd;
}
