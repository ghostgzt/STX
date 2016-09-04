package gui;

import io.FileSys;
import chen.c;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

public class show extends Canvas {

    String word, jieshi;
    main Midlet;
    int k;
    int cont, time;
    boolean run;
    int[] wordindex;
    int wordnum;

    show(main m, int len) {

        k = len;
        wordindex = new int[600];
        Midlet = m;
        cont = 0;
        time = 900;
        start();

        /*   int totalword=0;
        char t = 'b';
        int l=0;
        while (k < data.length) {
        l=k;
        next();
        totalword++;
        if (word.charAt(0) == t||word.charAt(0) == t+'A'-'a') {
        System.err.println(","+l);
        t++;
        totalword=0;
        }
        }*/
    }

    void start() {
        jieshi = "请稍等...";
        word = "载入中...";
        repaint();
        wordnum = 0;
        next();
        Showhelper.show = this;
        run = true;
        new Thread(this).start();
    }

    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case -6:
                FileSys.savefile("file:///E:/englishnewword.txt", main.data, 0, 0);
                break;
            case -7:
                run = false;
                Midlet.save(k);
                Showhelper.show = Midlet.f;
                c.show.repaint();
                break;
            case -1:
                time += 50;
                repaint();
                break;
            case -5:

                run = !run;
                if (run) {
                    new Thread(this).start();
                }
                break;
            case -2:
                time -= 50;
                repaint();
                break;
            case 49:
                try {
                    Player med = Manager.createPlayer("file:///E:/1.mp3");
                    med.realize();
                    med.prefetch();
                    ((VolumeControl) med.getControl("VolumeControl")).setLevel(50);
                    med.start();
                } catch (IOException ex) {
                } catch (MediaException ex) {
                }
                break;
            case 48:
                Midlet.add(word, jieshi);
                break;
            case -3:
                if (wordnum >= 2) {
                    k = wordindex[wordnum -= 2];
                }
            case -4:
                next();
                break;
        }
    }

    void next() {
        if (k >= main.data.length) {
            return;
        }
        word = jieshi = null;
        int t = k;
        if (wordnum >= 580) {
            wordnum = 0;
        }
        wordindex[wordnum++] = k;
        while (main.data[k] != 32) {
            k++;
        }
        word = new String(main.data, t, k - t);
        t = k + 1;
        while (main.data[k] != 10) {
            k++;
        }
        try {
            jieshi = new String(main.data, t, k - t - 1, "Utf-8");
        } catch (UnsupportedEncodingException ex) {
        }
        k++;

    }

    public void paint() {
        gc.setColor(List.bcolor);
        gc.fillRect(0, 0, width, height);
        gc.setColor(List.fcolor);
        gc.drawString(word, width / 2, 10, Graphics.TOP | Graphics.HCENTER);
        if (cont != 0) {
            gc.drawString(jieshi, width / 2, 40, Graphics.TOP | Graphics.HCENTER);
        }

        gc.drawString("time= " + time + "  k= " + k + "  index=" + wordnum, width / 2, 70, Graphics.TOP | Graphics.HCENTER);
        gc.drawString("返回 ", width, height, Graphics.BOTTOM | Graphics.RIGHT);
        gc.drawString(" 菜单", 0, height, Graphics.BOTTOM | Graphics.LEFT);

    }

    public void run() {
        while (run) {
            try {
                cont = ++cont % 3;
                if (cont == 0) {
                    next();
                }
                repaint();
                Thread.sleep(time);

            } catch (InterruptedException ex) {
            }
        }
    }

    public void keyRepeated(int key) {
    }
}
