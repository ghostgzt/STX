package gui;

import chen.c;
import java.util.Vector;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Showhelper extends javax.microedition.lcdui.Canvas implements Showable {

    public static Showable show;
    public static long time;
    public static Gauge jindu;
    public static boolean alert;
    String ti;
    Displayable td;
    int hangHeight, maxwidth;
    int Width, Height;
    Font font = Font.getFont(0, 0, 8);
    Vector v = new Vector();
    Image buffer;
    private Graphics g;
    int state;

    public Showhelper() {
        setFullScreenMode(true);
        show = this;
        Width = getWidth();
        Height = getHeight();
        initShu();
        state = 0;
        time = System.currentTimeMillis();

    }

    public void paint(Graphics g) {
        show.paint();
        if (alert) {
            paint();
        }
        if (jindu != null) {
            jindu.paint(this.g);
        }
        if (state == 0) {
            g.drawImage(buffer, 0, 0, 20);
        } else {
            g.drawImage(Image.createImage(buffer, 0, 0, buffer.getWidth(), buffer.getHeight(), state), 0, 0, 20);

        }

    }

    void initShu() {
        Canvas.width = Width;
        Canvas.height = Height;
        buffer = Image.createImage(Width, Height);
        Canvas.gc = buffer.getGraphics();
        g = Canvas.gc;
    }

    void initHen() {
        Canvas.width = Height;
        Canvas.height = Width;
        buffer = Image.createImage(Height, Width);
        Canvas.gc = buffer.getGraphics();
        g = Canvas.gc;
    }

    public void changeView() {
        switch (state) {
            case 0:
                to90();
                break;
            case 5:
                to180();
                break;
            case 3:
                to270();
                break;
            case 6:
                to0();
                break;
        }
    }

    private void to90() {
        if (state == 0 || state == 3) {
            initHen();
        }
        state = 5;
    }

    private void to270() {
        if (state == 0 || state == 3) {
            initHen();
        }
        state = 6;
    }

    private void to0() {
        if (state == 5 || state == 6) {
            initShu();
        }
        state = 0;
    }

    private void to180() {
        if (state == 5 || state == 6) {
            initShu();
        }
        state = 3;
    }
/**
 *
 * @param t title
 * @param n info
 */
    public void show(String t, String n) {

        td = c.d.getCurrent();
        ti = t == null ? "ÎÞÌâ" : t;
        if (td != this) {
            c.d.setCurrent(this);
        }
        alert = true;
        init(n == null ? "Î´Öª´íÎó" : n);
        repaint();
    }

    public void showError(String info) {
        show("´íÎó", info);
    }

    void init(String ne) {
        v.removeAllElements();
        hangHeight = font.getHeight() + 3;
        int len = ne.length();
        int now = 0;
        char[] temp = new char[len];
        ne.getChars(0, len, temp, 0);
        maxwidth = Canvas.width;
        for (int i = 0; i < len; i++) {
            if (temp[i] == 10) {
                temp[i] = 32;
                fenduan(temp, now, i);
                now = i + 1;
            } else if (temp[i] >= 0 && temp[i] < 32) {
                temp[i] = 32;
            }
        }
        fenduan(temp, now, len - 1);
        ne = null;
    }

    private void fenduan(char[] temp, int low, int high) {
        if (low >= high) {
            return;
        }
        int l = 0;
        int off = low;
        while (l < maxwidth && off <= high) {
            l += font.charWidth(temp[off++]);
        }
        if (off < high) {
            off--;
        }
        v.addElement(new String(temp, low, off - low));
        if (off < high) {
            fenduan(temp, off, high);
        }

    }

    public void keyPressed(int key) {
//        if (key == -7) {
//            key = -6;
//        } else if (key == -6) {
//            key = -7;
//        }
        time = System.currentTimeMillis();
        if (alert) {
            alert = false;
            c.d.setCurrent(td);
            repaint();
        } else {
            if (state != 0) {
                key = translate(key);
            }
            show.keyPressed(key);
        }
    }

    public void keyRepeated(int key) {
        time = System.currentTimeMillis();
        show.keyRepeated(key);
    }

    public void paint() {
        if (alert) {
            g.setFont(font);
            g.setColor(0xffffff);
            int start = Canvas.height - hangHeight * (v.size() + 1);
            g.fillRect(0, start, maxwidth, hangHeight * (v.size() + 1));
            g.setColor(0xff0000);
            g.drawString(ti, maxwidth / 2, start + 1, Graphics.TOP | Graphics.HCENTER);
            String s;
            for (int i = v.size() - 1; i >= 0; i--) {
                if ((s = (String) v.elementAt(i)) != null) {
                    g.drawString(s, 1, start + hangHeight * (i + 1), Graphics.TOP | Graphics.LEFT);
                }
            }
        }
    }

    private int translate(int key) {
        switch (key) {
            case -4://right
                if (state == 5) {
                    key = -1;
                } else if (state == 6) {
                    key = -2;
                } else {
                    key = -3;
                }
                break;
            case -3://left
                if (state == 5) {
                    key = -2;
                } else if (state == 6) {
                    key = -1;
                } else {
                    key = -4;
                }
                break;
            case -2://down
                if (state == 5) {
                    key = -4;
                } else if (state == 6) {
                    key = -3;
                } else {
                    key = -1;
                }
                break;
            case -1://up
                if (state == 5) {
                    key = -3;
                } else if (state == 6) {
                    key = -4;
                } else {
                    key = -2;
                }
                break;
            case -6:
                if (state == 3) {
                    key = -7;
                }
                break;
            case -7:
                if (state == 3) {
                    key = -6;
                }
                break;
        }
        return key;
    }
}
