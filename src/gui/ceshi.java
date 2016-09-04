package gui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Random;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class ceshi extends Canvas {

    Hashtable h1, h2;
    Image charset[];
    int li, cont, state;
    String word, jieshi;
    Random s;
    boolean shuru, anser;
    long time;
    int wordindex;
    char ruchar;
    char[] wordshuru;
    int key;
    int sssss;
    boolean run;
    char[][] in = {{'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'},
        {'j', 'k', 'l'}, {'m', 'n', 'o'}, {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}
    };
    main Midlet;
    int wordnum, k;

    ceshi(int k, int num, main m) {
        Midlet = m;
        wordnum = num;
        this.k = k;
        s = new Random();
        Image temp;
        charset = new Image[58];
        try {
            int c2 = List.fcolor | 0xff000000;
            temp = Image.createImage("/chen/e");
            int[] a = new int[36 * 270];
            temp.getRGB(a, 0, 270, 0, 0, 270, 36);
            for (int i = 0; i < a.length; i++) {
                if (a[i] == 0xffffffff) {
                    a[i] = 0;
                } else {
                    a[i] = c2;
                }
            }
            temp = null;
            temp = Image.createRGBImage(a, 270, 36, true);
            a = null;
            for (int i = 0; i < 26; i++) {
                charset[i + 32] = Image.createImage(temp, i * 10, 0, 10, 18, 0);
                charset[i] = Image.createImage(temp, i * 10, 18, 10, 18, 0);
            }
            charset[26] = Image.createImage(temp, 260, 0, 10, 18, 0);
            charset[27] = Image.createImage(temp, 260, 18, 10, 18, 0);
        } catch (IOException ex) {
        }
        temp = null;

        initdata();
    }

    void initdata() {
        run = true;
        li = 0;
        Showhelper.show = this;
        h1 = new Hashtable();
        h2 = new Hashtable();
        String a, b = null;
        int n = 0, t;
        while (n < wordnum && k < main.data.length) {
            t = k;
            while (main.data[k] != 32) {
                k++;
            }
            a = new String(main.data, t, k - t);
            t = k + 1;
            while (main.data[k] != 10) {
                k++;
            }
            try {
                b = new String(main.data, t, k - t - 1, "Utf-8");

            } catch (UnsupportedEncodingException ex) {
            }
            h1.put(a, b);
            h2.put(n + "", a);
            k++;
            n++;
        }

        nextword();
        shuru = false;
        new Thread(this).start();
    }

    void inishuru() {

        wordshuru = new char[word.length()];
        for (int i = 0; i < wordshuru.length; i++) {
            wordshuru[i] = 92;
        }
    }

    public void keyPressed(int keyCode) {

        switch (keyCode) {
            case -6:
                break;
            case -7:
                run = false;
                Showhelper.show = Midlet.f;
                break;
            case -8:
                if (li > 0) {
                    wordshuru[li - 1] = 92;
                }
            case -3:
                if (li > 0) {
                    li--;
                    repaint();
                }
                break;
            case -5:
                nextword();
                li = 0;
                break;
            case -4:
                if (li < word.length()) {
                    li++;
                    repaint();
                }
                break;

            case 49:
                anser = !anser;
                repaint();
                break;
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
                shuru(keyCode);
                break;

        }
    }

    void nextword() {
        wordindex = s.nextInt(wordnum);
        word = (String) h2.get("" + wordindex);
        inishuru();
        jieshi = (String) h1.get(word);
        repaint();
    }

    public void paint() {
        gc.setColor(List.bcolor);
        gc.fillRect(0, 0, width, height);
        gc.setColor(List.fcolor);
        int stt = (width - word.length() * 10) / 2;
        if (anser) {
            // g.drawString(wordindex+". "+word, 10, 10, Graphics.TOP | Graphics.LEFT);
            for (int i = 0; i < word.length(); i++) {
                gc.drawImage(charset[word.charAt(i) - 65], stt + 10 * i, 10, Graphics.TOP | Graphics.LEFT);
            }
        }
        for (int i = 0; i < word.length(); i++) {
            gc.drawLine(stt + i * 10, 60, stt + 8 + i * 10, 60);
            gc.drawImage(charset[wordshuru[i] - 65], stt + i * 10, 40, Graphics.TOP | Graphics.LEFT);
        }
        if (cont == 0) {
            if (shuru) {
                gc.drawImage(charset[ruchar - 65], stt + li * 10, 40, Graphics.TOP | Graphics.LEFT);
            } else {

                gc.drawLine(stt + li * 10, 40, stt + li * 10, 58);
            }

        }
        gc.drawString(jieshi, width / 2, 70, Graphics.TOP | Graphics.HCENTER);
        gc.drawString("·µ»Ø ", width, height, Graphics.BOTTOM | Graphics.RIGHT);
        gc.drawString(" ²Ëµ¥", 0, height, Graphics.BOTTOM | Graphics.LEFT);
        //  g.drawImage(charset[0], 200, 10, Graphics.TOP | Graphics.LEFT);
    }

    private void shuru(int d) {
        if (li >= word.length()) {
            return;
        }
        if (!shuru) {
            shuru = true;
            for (int i = 0; i < in[d - 50].length; i++) {
                if (in[d - 50][i] == word.charAt(li) || in[d - 50][i] == word.charAt(li) + 32) {
                    wordshuru[li] = word.charAt(li);
                    if (li < word.length() - 1) {
                        li++;
                    }
                    shuru = false;
                    return;
                }

            }
            ruchar = in[d - 50][state = 0];
        } else {
            if (key != d) {
                wordshuru[li] = ruchar;
                if (li < word.length() - 1) {
                    li++;
                }
                state = -1;
            }
            for (int i = 0; i < in[d - 50].length; i++) {
                if (in[d - 50][i] == word.charAt(li)) {
                    state = i - 1;
                }
            }
            ruchar = in[d - 50][++state % in[d - 50].length];
        }
        key = d;
        time = System.currentTimeMillis();
    }

    public void run() {
        while (run) {
            try {
                Thread.sleep(100);

                cont = ++cont % 5;
                if (shuru && System.currentTimeMillis() - time > 500) {
                    shuru = false;
                    wordshuru[li] = ruchar;
                    if (li < word.length() - 1) {
                        li++;
                    }
                }
                repaint();
                if (new String(wordshuru).equals(word)) {
                    nextword();
                    li = 0;
                }
            } catch (InterruptedException ex) {
            }
        }
    }

    public void keyRepeated(int key) {
    }
}
