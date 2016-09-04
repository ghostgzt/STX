package mpe;

import java.io.InputStream;
import java.util.Vector;


// Referenced classes of package mpe:
//            Database, Utils, Split
public class Document {

    public Document(String fName, boolean createTemplate, int maxL) {
        inSelection = false;
        clazz = "";
        filename = fName;
        maxLines = maxL;
        data = new Vector();
        if (createTemplate) {
            String initialCode[] = Utils.splitString(Utils.processMacros(Database.template), "\n");
            for (int i = 0; i < initialCode.length; i++) {
                data.addElement(initialCode[i]);
            }

        }
        curX = stringOffset = vPosition = 0;
        curY = createTemplate ? Database.templLine - 1 : 0;
    }

    public void moveCursor(int dir) {
        switch (dir) {
            default:
                break;

            case 0: // '\0'
                if (curX > 0) {
                    curX--;
                    break;
                }
                if (globalY() > 0) {
                    curX = data.elementAt(globalY() - 1).toString().length();
                }
            // fall through

            case 1: // '\001'
                if (globalY() <= 0) {
                    break;
                }
                curY--;
                if (curY < 0 && vPosition > 0) {
                    curY = 0;
                    vPosition--;
                }
                if (curX > data.elementAt(globalY()).toString().length()) {
                    curX = data.elementAt(globalY()).toString().length();
                }
                break;

            case 2: // '\002'
                if (curX < data.elementAt(globalY()).toString().length()) {
                    curX++;
                    break;
                }
                if (data.size() > globalY() + 1 && globalY() < data.size()) {
                    curX = 0;
                }
            // fall through

            case 3: // '\003'
                if (globalY() >= data.size() - 1) {
                    break;
                }
                curY++;
                if (curY == maxLines) {
                    curY = maxLines - 1;
                    vPosition++;
                }
                if (curX > data.elementAt(globalY()).toString().length()) {
                    curX = data.elementAt(globalY()).toString().length();
                }
                break;
        }
    }

    public void Insert(String str) {
        String s[] = Utils.splitString(str, "\n");
        if (inSelection) {
            Backspace();
        }
        if (curX == 0) {
            if (s.length > 1) {
                String temp = data.elementAt(globalY()).toString();
                data.setElementAt(s[0], globalY());
                for (int i = 1; i < s.length - 1; i++) {
                    curY++;
                    data.insertElementAt(s[i], globalY());
                }

                curY++;
                data.insertElementAt(s[s.length - 1] + temp, globalY());
            } else {
                data.setElementAt(s[0] + data.elementAt(globalY()).toString(), globalY());
            }
            setCursor(s[s.length - 1].length(), globalY());
        } else if (curX == data.elementAt(globalY()).toString().length()) {
            data.setElementAt(data.elementAt(globalY()).toString() + s[0], globalY());
            if (s.length > 1) {
                for (int i = 1; i < s.length; i++) {
                    curY++;
                    data.insertElementAt(s[i], globalY());
                }

            }
            setCursor(data.elementAt(globalY()).toString().length(), globalY());
        } else {
            String temp = data.elementAt(globalY()).toString();
            if (s.length > 1) {
                data.setElementAt(temp.substring(0, curX) + s[0], globalY());
                for (int i = 1; i < s.length - 1; i++) {
                    curY++;
                    data.insertElementAt(s[i], globalY());
                }

                curY++;
                data.insertElementAt(s[s.length - 1] + temp.substring(curX), globalY());
                setCursor(s[s.length - 1].length(), globalY());
            } else {
                data.setElementAt(temp.substring(0, curX) + s[0] + temp.substring(curX), globalY());
                setCursor(temp.substring(0, curX).length() + s[0].length(), globalY());
            }
        }
    }

    public void Backspace() {
        if (inSelection) {
            calcSelection();
            setCursor(selX1, selY1);
            for (int i = selY1; i <= selY2; i++) {
                if (selY1 == selY2) {
                    data.setElementAt(data.elementAt(selY1).toString().substring(0, selX1) + data.elementAt(selY1).toString().substring(selX2), selY1);
                } else if (i == selY1) {
                    if (selX1 == 0) {
                        data.removeElementAt(i);
                        selY2--;
                        i--;
                    } else {
                        data.setElementAt(data.elementAt(i).toString().substring(0, selX1), i);
                    }
                } else if (i == selY2) {
                    if (selX2 == data.elementAt(i).toString().length()) {
                        data.removeElementAt(i);
                        selY2--;
                        i--;
                    } else {
                        data.setElementAt(data.elementAt(i).toString().substring(selX2), i);
                    }
                } else {
                    data.removeElementAt(i);
                    selY2--;
                    i--;
                }
            }

            endSelection();
        } else if (curX != 0 || globalY() != 0) {
            if (curX > 0) {
                data.setElementAt(Utils.stringDelete(data.elementAt(globalY()).toString(), --curX), globalY());
            } else {
                curY--;
                curX = data.elementAt(globalY()).toString().length();
                data.setElementAt(data.elementAt(globalY()).toString() + data.elementAt(globalY() + 1).toString(), globalY());
                data.removeElementAt(globalY() + 1);
                if (curY < 0 && vPosition > 0) {
                    curY = 0;
                    vPosition--;
                }
            }
        }
    }

    public String Copy() {
        String buffer = "";
        if (inSelection) {
            calcSelection();
            if (selY1 == selY2) {
                buffer = data.elementAt(selY1).toString().substring(selX1, selX2);
            } else {
                for (int i = selY1; i <= selY2; i++) {
                    if (i == selY1) {
                        buffer = data.elementAt(i).toString().substring(selX1) + "\n";
                    } else if (i == selY2) {
                        buffer = buffer + data.elementAt(i).toString().substring(0, selX2);
                    } else {
                        buffer = buffer + data.elementAt(i).toString() + "\n";
                    }
                }

            }
        }
        return buffer;
    }

    public String read_UTF(String name) {
        String strReturn = "";
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream(name);
            byte b[] = new byte[0xfa000];
            int length = in.read(b, 0, b.length);
            in.close();
            if (length > 0) {
                return new String(b, 0, length);
            }
        } catch (Exception e) {
            in = null;
            System.out.println("readUTF Error:" + e.toString());
        }

        return strReturn;
    }

    public String[] getApi() {
        String api[] = new String[0];
        String temp[] = new String[0];
        if (inSelection) {
            calcSelection();
            if (selY1 == selY2) {
                clazz = data.elementAt(selY1).toString().substring(selX1, selX2);
            } else {
                for (int i = selY1; i <= selY2; i++) {
                    if (i == selY1) {
                        clazz = data.elementAt(i).toString().substring(selX1) + "\n";
                    } else if (i == selY2) {
                        clazz += data.elementAt(i).toString().substring(0, selX2);
                    } else {
                        clazz += data.elementAt(i).toString() + "\n";
                    }
                }

            }
        }
        if (clazz.length() > 0) {
            temp = (new GetAuto()).Auto(clazz);
            if (temp.length > 0) {
                api = temp;
            }
        }
        return api;
    }

    public void Import() {
        String buffer = "";
        if (inSelection) {
            calcSelection();
            if (selY1 == selY2) {
                buffer = data.elementAt(selY1).toString().substring(selX1, selX2);
            } else {
                for (int i = selY1; i <= selY2; i++) {
                    if (i == selY1) {
                        buffer = data.elementAt(i).toString().substring(selX1) + "\n";
                    } else if (i == selY2) {
                        buffer = buffer + data.elementAt(i).toString().substring(0, selX2);
                    } else {
                        buffer = buffer + data.elementAt(i).toString() + "\n";
                    }
                }

            }
        }
        System.out.println("buffer=" + buffer);
        String pack = read_UTF("/mpe/class.txt");
        String clazzs[] = (new Split()).Split(pack, "\r\n");
        String sss = "";
        for (int i = 0; i < clazzs.length; i++) {
            if (buffer.length() > 1 && clazzs[i].endsWith("." + buffer)) {
                sss = "import " + clazzs[i] + ";\n";
            }
        }

        endSelection();
        if (sss.length() > 0) {
            setCursor(0, 0);
            Insert(sss);
        }
        setCursor(0, sta);
        sss = null;
        clazzs = (String[]) null;
        pack = null;
    }

    public String Cut() {
        String buffer = "";
        if (inSelection) {
            buffer = Copy();
            Backspace();
        }
        return buffer;
    }

    public void setCursor(int x, int y) {
        curX = x;
        if (y < vPosition) {
            curY = 0;
            vPosition = y;
        } else if (y > (vPosition + maxLines) - 1) {
            curY = maxLines - 1;
            vPosition = (y - maxLines) + 1;
        } else {
            curY = y - vPosition;
        }
    }

    public int getCursor(int coord) {
        return coord != 0 ? globalY() : curX;
    }

    public final int globalY() {
        return curY + vPosition;
    }

    public void startSelection() {
        inSelection = true;
        selAnchorX = curX;
        selAnchorY = globalY();
        sta = globalY() + 1;
    }

    public void endSelection() {
        inSelection = false;
    }

    public void calcSelection() {
        if (selAnchorY == globalY()) {
            selX1 = selAnchorX >= curX ? curX : selAnchorX;
            selX2 = selAnchorX >= curX ? selAnchorX : curX;
            selY1 = selY2 = globalY();
        } else {
            selX1 = selAnchorY >= globalY() ? curX : selAnchorX;
            selX2 = selAnchorY >= globalY() ? selAnchorX : curX;
            selY1 = selAnchorY >= globalY() ? globalY() : selAnchorY;
            selY2 = selAnchorY >= globalY() ? selAnchorY : globalY();
        }
    }

    public void setMaxLines(int maxL) {
        maxLines = maxL;
        setCursor(curX, curY);
    }
    public Vector data;
    public boolean inSelection;
    public int curX;
    public int curY;
    public int stringOffset;
    public int vPosition;
    public int selAnchorX;
    public int selAnchorY;
    public int selX1;
    public int selY1;
    public int selX2;
    public int selY2;
    public String filename;
    private int maxLines;
    public static int sta;
    public String clazz;
}
