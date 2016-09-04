package io;

import gui.*;
import chen.c;
import javax.microedition.lcdui.Graphics;

public class Txt extends Canvas {

    String name;
    byte data[];
    char[] temp;
    int maxwidth, charset, offset, maxbytes, automode, dx;
    ContDataInputStream rof;
    public static final int ZIFU = 0, PAGE = 1, HANG = 2, XIANSU = 3, BANGPAGE = 4, NONE = 5;
    Stack offsets;
    int offs[] = new int[900];
    int lens[] = new int[900];
    int yhs, zhs, zg, now = 0, nowhs, showchars, charsOff, tempsize;
    long sleeptime = 150;
    boolean tooBig;
    boolean auto = true, fullScreenmode = false;
    private int remain, currenhs;
    private int tembFullsize;
    Txtcd menu;
    Showable pre;
    boolean showmenu = false;
    int mode;

    public Txt(String url) {
        int size = 0;
        try {
            if ((size = (int) FileSys.getfile(url).fileSize()) < 2) {
                return;
            }
        } catch (Throwable ex) {
            return;
        }
        name = url;
        init(new ContDataInputStream(FileSys.getinputstream(url)), size);
    }

    public Txt(ContDataInputStream in, String url, int size) {
        if (in == null || size < 2) {
            return;
        }
        name = url;
        init(in, size);
    }

    public Txt(byte b[], String url) {
        name = url;
        init(b, false);
    }

    private void init(ContDataInputStream in, int size) {
        byte[] b;
        try {
            if (size > MemorySet.TXTDATA) {
                b = new byte[MemorySet.TXTDATA];
                in.readFully(b, 0, b.length);
                init(b, true);
            } else {
                b = new byte[size];
                in.readFully(b, 0, size);
                in.close();
                init(b, false);
            }
        } catch (Throwable ex) {
            c.show.showError(ex.toString());
            return;
        }
    }

    private void init(byte[] b, boolean toobig) {
        if (b == null || b.length < 2) {
            return;
        }
        menu = new Txtcd(this);
        data = b;
        tooBig = toobig;
        if (b[0] == (byte) 0xff && b[1] == (byte) 0xfe) {
            charset = 1;
        } else if (b[1] == (byte) 0xff && b[0] == (byte) 0xfe) {
            charset = 2;
        }
        update();
        tempsize = MemorySet.TXTTEMP / 2;
        temp = new char[tempsize];
        offsets = new Stack();
        zhs = 0;
        auto = true;
        remain = 0;
        readtxt();
        mode = 2;
        new Thread(this).start();
    }

    private void update() {
        zg = font.getHeight() + 2;
        yhs = height / zg;
        if (!fullScreenmode) {
            yhs--;
        }
    }

    void readtxt() {
        if (data != null) {
            int pos = data.length - offset;
            remain = tembFullsize - offs[nowhs];
            int readsize = tempsize - 1 - remain;
            if (offset > 0 && pos < readsize && rof != null && rof.getOffst() < maxbytes) {
                System.arraycopy(data, offset, data, 0, pos);
                offset = 0;
                offsets.clear();
                try {
                    rof.readFully(data, pos, Math.min(data.length - pos, maxbytes - rof.getOffst()));
                } catch (Throwable ex) {
                }
            }
            int toread = Math.min(data.length - offset, readsize);
            if (toread <= 0 || (toread == 1 && data[offset] < 0)) {
                return;
            }
            nowhs = 0;
            deleteall();
            offsets.push(offset);
            if (remain > 0) {
                System.arraycopy(temp, tembFullsize - remain, temp, 0, remain);
            }
            offset = decode(toread);
            maxwidth = Canvas.width - 2;
            int fillsize = temp[temp.length - 1];
            int off = 0;
            for (int i = 0; i < fillsize; i++) {
                if (temp[i] == 10) {
                    temp[i] = 32;
                    fenduan(off, i, true);
                    i++;
                    while (i < tembFullsize && (temp[i] == 13 || temp[i] == 10)) {
                        i++;
                    }
                    off = i;
                    i--;
                } else if (temp[i] >= 0 && temp[i] < 32) {
                    temp[i] = 32;
                }
            }
            currenhs = 1;
            nowhs = 0;
            tembFullsize = fillsize;
            fenduan(off, fillsize, false);
            if (Showhelper.show != this) {
                pre = Showhelper.show;
                Showhelper.show = this;
            }
            repaint();
        }
    }

    public synchronized void paint() {
        Graphics g = Canvas.gc;
        g.setColor(List.bcolor);
        g.fillRect(0, 0, width, height);
        g.setColor(List.fcolor);
        if (!fullScreenmode) {
            g.drawString("当前:" + currenhs + " 速度:" + sleeptime + " 总:" + zhs + " 返回", 1, Canvas.height - 1, Graphics.BOTTOM | Graphics.LEFT);
        }
        switch (mode) {
            case 0:
                paintChar(g);
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                paintNone(g);
                break;
        }
        if (showmenu) {
            menu.paint(g);
        }
    }

    private void paintNone(Graphics g) {
        int max = Math.min(zhs, nowhs + yhs);
        for (int i = nowhs; i < max; i++) {
            g.drawChars(temp, offs[i], lens[i], 1, zg * (i - nowhs) + 2, 20);
        }
    }

    private synchronized void paintChar(Graphics g) {
        int num = showchars - charsOff;
        for (int i = nowhs; i < zhs; i++) {
            int len = offs[i + 1] - offs[i];
            if (num > len) {
                num -= len;
                g.drawChars(temp, offs[i], lens[i], 1, zg * (i - nowhs) + 2, 20);
            } else {
                g.setColor(List.xcolor);
                g.drawChars(temp, offs[i], num, 1, zg * (i - nowhs) + 2, 20);
                currenhs = i + 1;
                break;
            }
        }
    }

    private int decode(int toread) {
        int k = offset;
        switch (charset) {
            case 0:
                k = CharDecoder.decodeUtf8(data, offset, toread, temp, remain);
                if (k < offset + toread - 3) {
                    charset = 3;
                    return decode(toread);
                }
                break;
            case 1:
                k = CharDecoder.decodeUnicode(data, offset, toread, temp, remain);
                break;
            case 2:
                k = CharDecoder.decodeUnicodeBig(data, offset, toread, temp, remain);
                break;
            case 3:
                k = CharDecoder.decodeGbk(data, offset, toread, temp, remain);
                break;
        }
        return k;
    }

    private void fenduan(int low, int high, boolean end) {
        if (low >= high) {
            return;
        }
        if (temp[high] == 32) {
            high--;
        }
        int l;
        int off;
        while (high > low) {
            off = low;
            l = 0;
            while (l < maxwidth && low <= high) {
                l += font.charWidth(temp[low++]);
            }
            if (l >= maxwidth) {
                low--;
            } else if (!end) {
                break;
            }
            append(off, low - off);

        }
    }

    private void nextLine() {

        if (nowhs < zhs - 1) {
            nowhs++;
            currenhs++;
            charsOff = offs[nowhs];
            showchars = offs[nowhs + 1];
        }
    }

    private void preLine() {
        if (nowhs > 0 && currenhs > 0) {
            nowhs--;
            currenhs--;
            charsOff = offs[nowhs];
            showchars = offs[nowhs + 1];
        }
    }

    private void nextChar() {
        showchars++;
        if (nowhs < zhs - yhs && (showchars > offs[nowhs + yhs + 1])) {
            nowhs += yhs - 1;
            charsOff = offs[nowhs];
            showchars = offs[nowhs + 1];
        }
    }

    private void nextPage() {
        if (nowhs < zhs - yhs - 1) {
            nowhs += yhs;
            currenhs += yhs;
            charsOff = offs[nowhs];
            showchars = offs[nowhs + 1];
        }
    }

    private void prePage() {
        if (nowhs >= yhs && currenhs >= yhs) {
            nowhs -= yhs;
            currenhs -= yhs;
        } else {
            nowhs = currenhs = 0;
            showchars = charsOff = 0;
        }
        charsOff = offs[nowhs];
        showchars = offs[nowhs + 1];
    }

    public void run() {
        long time = 0;
        while (auto) {
            switch (mode) {
                case ZIFU:
                    nextChar();
                    break;
                case HANG:
                    nextLine();
                    break;
                case PAGE:
                    nextPage();
                    break;
                case XIANSU:
                    nextXiansu();
                    break;
                case BANGPAGE:

            }
            try {
                Thread.sleep(Math.max(0, sleeptime - (System.currentTimeMillis() - time)));
            } catch (InterruptedException ex) {
            }
            time = System.currentTimeMillis();
            if (zhs - nowhs <= yhs) {
                readtxt();
            }
            repaint();
        }
    }

    void addBookMack(int offset) {
        Rms.save(name.substring(name.lastIndexOf('/') + 1), offset, currenhs, (int) sleeptime);
    }

    void getBookMack() {
        try {
            int[] k = Rms.get(name.substring(name.lastIndexOf('/') + 1));
            if (k == null) {
                return;
            }
            offset = k[0];
            //     hs = k[1];
            sleeptime = k[2];
            System.out.println("offset=" + offset + " currenhs=" + k[1] + " sleeptime=" + sleeptime);
        } catch (Exception e) {
        }
    }

    public void keyRepeated(int key) {
        if (!showmenu) {
            switch (key) {
                case -4:
                    sleeptime += 30;
                    break;
                case -3:
                    sleeptime -= 30;
                    if (sleeptime <= 0) {
                        sleeptime = 5;
                    }
                    break;
            }
        } else {
            keyPressed(key);
        }
    }

    private void deleteall() {
        zhs = 0;
    }

    private void append(int off, int len) {
        offs[zhs] = off;
        lens[zhs++] = len;
        if (zhs == offs.length) {
            int l = offs.length + 500;
            int[] t1 = new int[l];
            int[] t2 = new int[l];
            System.arraycopy(offs, 0, t1, 0, zhs);
            System.arraycopy(lens, 0, t2, 0, zhs);
            offs = t1;
            lens = t2;
        }
    }

    private void nextXiansu() {
    }

    public void keyPressed(int code) {

        switch (code) {
            case -2:
                if (showmenu) {
                    menu.down();
                    break;
                }
                if (zg < Canvas.height * 2) {
                    zg++;
                }
                yhs = height / zg;
                if (!fullScreenmode) {
                    yhs--;
                }
                break;
            case -1:
                if (showmenu) {
                    menu.up();
                    break;
                }
                if (zg > 2) {
                    zg--;
                }
                yhs = height / zg;
                if (!fullScreenmode) {
                    yhs--;
                }
                break;
            case -4:
                sleeptime += 5;
                break;
            case -3:
                sleeptime -= 5;
                if (sleeptime <= 0) {
                    sleeptime = 5;
                }
                break;
            case -6:
                showmenu = !showmenu;
                break;
            case -5:
                if (showmenu) {
                    showmenu = false;
                    menu.select();
                    break;
                }
                auto = !auto;
                if (auto) {
                    new Thread(this).start();
                }
                break;
            case 35:
                super.run();
                break;


            case -7:
                if (showmenu) {
                    showmenu = false;
                    break;
                }
                auto = false;
                data = null;
                temp = null;
                offsets.pop();
                offset = offsets.pop();
                System.out.println(offset);
                if (rof != null) {
                    addBookMack(rof.getOffst() - MemorySet.TXTDATA + offset);
                    try {
                        rof.close();
                        rof = null;
                    } catch (Exception ex) {
                    }
                } else {
                    addBookMack(offset);
                }
                offsets = null;
                Showhelper.show = pre;
                auto = false;
                System.gc();
            case 56:
                nextLine();
                break;
            case 50:
                preLine();
                break;
            case 54:
                nextPage();
                break;
            case 52:
                prePage();
                break;

        }
        repaint();
    }

    public void changeScreenmode() {
        fullScreenmode = !fullScreenmode;
        update();
    }
}
