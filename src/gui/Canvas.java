package gui;

import io.FileSys;
import io.BufOutputStream;
import chen.c;
import java.io.OutputStream;
import zip.Deflater;
import java.io.DataOutputStream;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public abstract class Canvas implements Runnable, Showable {

    public static int width;
    public static int height;
    public static Font font;
    protected boolean jiepin;
    int jiex, jiey, jiew, jieh;
    public static Graphics gc;

    public Canvas() {
        jiex = 0;
        jiey = 0;
        jiew = width;
        jieh = height;
    }

    void jiepin() {
        jiepin(0, 0, width, height);
    }

    public void repaint() {
        chen.c.show.repaint();
    }

    void jiepin(int a, int b, int c, int d) {
        jiex = a;
        jiey = b;
        jiew = c;
        jieh = d;
        new Thread(this).start();
    }

    void append(String s, int h) {
    }

    public void paint() {
    }

    public void run() {
        yasuo(chen.c.show.buffer, jiex, jiey, jiew, jieh, "file:///" + c.currDirName + "jieping" + List.num++ + ".ppg");
        jiepin = false;
    }

    public static void yasuo(Image i, int x, int y, int w, int h, String name) {

        int data[] = new int[w * h];
        chen.c.show.buffer.getRGB(data, 0, w, x, y, w, h);
        try {
            OutputStream o = FileSys.getOutputStream(name);
            if (o != null) {
                BufOutputStream bo = new BufOutputStream(20480);
                DataOutputStream da = new DataOutputStream(bo);
                try {
                    int off = 0;
                    int c, l;
                    da.writeInt(w);
                    da.writeInt(h);
                    int max = data.length;
                    while (off < max) {
                        l = off;
                        c = data[off];
                        off++;
                        while (off < max && data[off] == c && off - l < 16777214) {
                            off++;
                        }
                        if (off - l < 255) {
                            da.writeInt((c & 0x00ffffff) | ((off - l) << 24));
                        } else {
                            da.writeInt(off - l);
                            da.writeInt(c);
                        }
                    }
                } catch (Throwable ex) {
                }
                data = null;
                byte[] buffer = bo.getbuf();
                int size = bo.size();
                da.close();
                bo.close();
                System.gc();
                Deflater def = new Deflater(5, false);
                def.setInput(buffer, 0, size);
                def.finish();
                byte[] temp = new byte[20480];
                int len = 0;
                while (!def.finished()) {
                    len = def.deflate(temp, 0, 20480);
                    o.write(temp, 0, len);
                }
                o.flush();
                o.close();
                temp = null;
                c.show.show("³É¹¦", name);
            } else {
                c.show.show("Ê§°Ü", name);
            }

        } catch (Throwable ex) {

            c.show.show("Ê§°Ü", ex + name);
        }

    }
}
