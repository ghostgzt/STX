package gui;

import io.FileSys;
import chen.c;
import zip.Inflater;
import zip.Deflater;
import java.io.*;

import javax.microedition.lcdui.*;
import zip.CRC32;

public class Img extends Canvas implements Runnable {

    int dx, offset, num, hs, now, add, zon;
    int[] x, y, xp, yp;
    boolean mb, zip;
    public byte[] HEADChunk = {-119, 80, 78, 71, 13, 10, 26, 10};
    //public  byte[] tRNSChunk = {0, 0, 0, 1, 116, 82, 78, 83, 0, 64, -26, -40, 102};
    public byte[] IENDChunk = {0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126};
    Image[] img;
    FileNode urls;
    byte[] mydata;
    String path;
    private int w;
    private int h;
    private command cc;
    private boolean show, jie, ppg, nj;
    private int www, hhh;
    private int x1, x2, y1, y2;

    public Img(String path, int nu) {
        this.path = path;
        urls = c.browser.getDir();
        dx = -21;
        ini(nu);
        mb = false;
        hs = c.browser.hs;
        xp[0] = width * 2;
        yp[0] = height * 2;
        cc = new command();
        cc.show = new String[]{"透明", "裁减", "设置", "截屏", "反转", "旋转"};
        cc.kd = Canvas.font.stringWidth("8. 察看") + 2;
        cc.zhs = cc.show.length;
        cc.hs = 0;
        ppg = true;
        x1 = y1 = 0;
        x2 = width - 1;
        y2 = height - 1;
        zip = false;
    }

    public Img(byte[] data, String path, int nu) {
        mydata = data;
        this.path = path;
        urls = c.browser.getDir();
        dx = -21;
        ini(nu);
        mb = false;
        hs = c.browser.hs;
        xp[0] = width * 2;
        yp[0] = height * 2;
        cc = new command();
        cc.show = new String[]{"透明", "裁减", "设置", "截屏", "反转", "旋转"};
        cc.kd = Canvas.font.stringWidth("8. 察看") + 2;
        cc.zhs = cc.show.length;
        cc.hs = 0;
        ppg = true;
        x1 = y1 = 0;
        x2 = width - 1;
        y2 = height - 1;
        zip = true;
    }

    Image getbim(byte[] data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            DataInputStream din = new DataInputStream(in);
            www = din.readInt();
            hhh = din.readInt();
            if (www * hhh * 4 > 2048 * 1024) {
                data = null;
                return Image.createImage(1, 1);
            } else {
                int[] da = new int[www * hhh];
                int c;

                for (int i = 0; din.available() >= 4; i++) {
                    if (i % www == 0) {
                        din.readByte();
                    }
                    c = din.readInt();
                    da[i] = (c & 0xff) << 24 | c >> 8;
                }
                data = null;
                return Image.createRGBImage(da, www, hhh, true);
            }

        } catch (IOException ex) {
            return Image.createImage(1, 1);
        }
    }

    void getyasou(Image i) {
        String pn;
        if (!path.endsWith("/")) {
            pn = path.substring(0, path.length() - 4) + "myjieping" + num + ".ppg";
        } else {
            pn = path + "myjieping" + num + ".ppg";
        }
        if (zip) {
            pn = "file:///" + c.currDirName + path.substring(Math.max(path.lastIndexOf('/'), 0)) + num + ".ppg";
        }
        num++;
        yasuo(i, 0, 0, i.getWidth(), i.getHeight(), pn);
        c.show.show("完成!", pn);
    }

    private int[] jieya(byte[] data) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Inflater inf = new Inflater(false);
        inf.setInput(data, 0, data.length);
        byte[] temp = new byte[4096];
        int len;
        while ((len = inf.inflate(temp, 0, 4096)) > 0) {
            out.write(temp, 0, len);
        }
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        DataInputStream din = new DataInputStream(in);
        try {
            w = din.readInt();
            h = din.readInt();
            int[] t = new int[w * h];
            int c, l, off = 0;
            while (in.available() > 0) {
                c = din.readInt();
                l = (c >> 24) & 0xff;
                if (l == 0) {
                    l = c;
                    c = din.readInt();
                } else {
                    c |= 0xff000000;
                }
                while (l > 0) {
                    t[off++] = c;
                    l--;
                }
            }
            out.reset();
            out.close();
            din.close();
            in.close();
            return t;
        } catch (IOException ex) {
        }

        return null;


    }

    void ini(int nu) {
        img = new Image[nu];
        x = new int[nu + 1];
        y = new int[nu + 1];
        xp = new int[nu + 1];
        yp = new int[nu + 1];
        num = 1;
        add = -1;
        zon = 0;
    }

    void save() {
        if (ppg) {
            getyasou(img[0]);
        } else {
            if (img[0].getWidth() * img[0].getHeight() > 2048 * 1024) {
                return;
            }
            byte data[] = output(img[0]);
            // img[0] = Image.createImage(data, 0, data.length);
            //  repaint();
            String pn;
            if (!path.endsWith("/")) {
                pn = path.substring(0, path.length() - 4) + num + "-new.png";
            } else {
                pn = path + "mypng.png";
            }
            if (zip) {
                pn = "file:///" + c.currDirName + path.substring(Math.max(path.lastIndexOf('/'), 0)) + "-new.png";
            }

            FileSys.savefile(pn, data, 0, 0);
            data = null;
            System.gc();
            c.show.show("完成!", pn);
        }
        num++;
    }

    public void init() {

        add++;
        zon++;

        if (zon > img.length) {
            zon = img.length;
        }
        if (add > img.length - 1) {
            add = 0;
        }
        now = add + 1;
        x[add + 1] = width / 2;
        y[add + 1] = height / 2;
        if (path != null) {

            if (path.endsWith("/")) {
                img[add] = List.filetype[5];
            } else {
                if (!zip) {
                    mydata = FileSys.getdata(path, 0, 0);
                }
                try {
                    if (mydata != null) {
                        if (path.toLowerCase().endsWith("ppg")) {
                            img[add] = Image.createRGBImage(this.jieya(mydata), w, h, true);
                        } else if (path.toLowerCase().endsWith("mpg")) {
                            img[add] = getbim(mydata);
                        } else {
                            img[add] = Image.createImage(mydata, 0, mydata.length);
                        }

                    }
                } catch (Exception e) {
                    img[add] = List.filetype[5];
                }
            }
        }

        if (img[add] == null) {
            img[add] = Image.createImage(1, 1);
        }
        xp[add + 1] = img[add].getWidth();
        yp[add + 1] = img[add].getHeight();
        Showhelper.show = this;
        repaint();

    }

    public void paint() {
        gc.translate(x[0], y[0]);
        gc.setColor(0xffffff);
        gc.fillRect(0, 0, width, height);
        for (int i = 0; i < img.length; i++) {
            if (img[i] != null) {
                gc.drawImage(img[i], x[i + 1], y[i + 1], 3);
            }
        }
        if (mb) {
            Runtime runtime = Runtime.getRuntime();
            long free = runtime.freeMemory();
            long total = runtime.totalMemory();
            gc.setColor(0xff0000);
            gc.drawString("dx=" + dx, width / 2, 3, 17);
            gc.drawString("now= " + now + " x=" + x[now] + "  y=" + y[now], width / 2, 83, 17);
            gc.drawString("px=" + xp[now] + "  py=" + yp[now], width / 2, 123, 17);
            gc.drawString("\u5269\u4F59\u5185\u5B58 = " + free / 1024 + "k", width / 2, 43, 17);
            gc.drawString("\u5DF2\u7528\u5185\u5B58 = " + (total - free) / 1024 + "k", width / 2, 23, 17);
            gc.drawString("总\u5185\u5B58 = " + total / 1024, width / 2, 63, 17);
            gc.drawString(path, width / 2, 103, 17);
        }
        gc.translate(-x[0], -y[0]);
        if (show) {
            cc.paint(gc);
        }
        if (jie) {
            gc.setColor(0xff0000);
            gc.drawRect(x1, y1, x2 - x1, y2 - y1);
        }
    }

    private void down() {
        y[now] += dx;
        if (y[now] >= height + yp[now] / 2) {
            y[now] -= 2 * dx;
        }

    }

    private void up() {

        y[now] -= dx;
        if (y[now] < -yp[now] / 2) {
            y[now] += 2 * dx;
        }

    }

    private void right() {
        x[now] += dx;
        if (x[now] >= width + xp[now] / 2) {
            x[now] -= 2 * dx;
        }

    }

    private void left() {
        x[now] -= dx;
        if (x[now] < -xp[now] / 2) {
            x[now] += 2 * dx;
        }
    }

    private void jiedown() {
        if (nj) {
            y2 -= dx;
        } else {
            y1 -= dx;
        }

    }

    private void jieup() {
        if (nj) {
            y2 += dx;
        } else {
            y1 += dx;
        }

    }

    private void jieright() {
        if (nj) {
            x2 -= dx;
        } else {
            x1 -= dx;
        }

    }

    private void jieleft() {
        if (nj) {
            x2 += dx;
        } else {
            x1 += dx;
        }
    }

    public void keyRepeated(int code) {
        keyPressed(code);
    }

    public void keyPressed(int code) {

        switch (code) {
            case -4://right
                if (jie) {
                    jieright();
                } else {
                    right();
                }

                break;
            case -6:
                show = !show;
                break;

            case -3://left
                if (jie) {
                    jieleft();
                } else {
                    left();
                }

                break;

            case -2://down
                if (show) {
                    cc.down();
                } else {
                    if (jie) {
                        jiedown();
                    } else {
                        down();
                    }
                }

                break;

            case -1://up
                if (show) {
                    cc.up();
                } else {
                    if (jie) {
                        jieup();
                    } else {
                        up();
                    }
                }
                break;
            case 52://4
                zooin(0, 0);
                break;
            case 54://6
                zooout();
                break;
            case -10:
                if (img.length == 1) {
                    ini(4);
                } else {
                    ini(1);
                }
                init();
                System.gc();
                break;
            case 49://1
                dx = -dx;

                break;
            case 51://3
                if (jie) {
                    jiepin(x1 + 1, y1 + 1, x2 - x1 - 1, y2 - y1 - 1);
                } else {
                    jiepin();
                }
                break;
            case 55://7
                dx -= 2;

                break;
            case 57://9
                dx += 5;

                break;
            case 50://2
                for (int i = urls.zhs - 1; i > 0; i--) {
                    hs--;
                    if (hs < 1) {
                        hs = urls.zhs;
                    }

                    if (urls.image[hs - 1] == 4 && !urls.show[hs - 1].endsWith("svg")) {
                        if (!zip) {
                            path = path.substring(0, path.lastIndexOf('/') + 1) + urls.show[hs - 1];
                        } else {
                            path = urls.show[hs - 1];
                            mydata = c.zipsy.getdata(path);
                        }
                        init();
                        break;
                    }
                }
                break;
            case -5://ok
                if (show) {
                    select();
                    break;
                }
            case 56://8
                for (int i = urls.zhs - 1; i > 0; i--) {
                    hs++;
                    if (urls.zhs < hs) {
                        hs = 1;
                    }

                    if (urls.image[hs - 1] == 4 && !urls.show[hs - 1].endsWith("svg")) {
                        if (!zip) {
                            path = path.substring(0, path.lastIndexOf('/') + 1) + urls.show[hs - 1];
                        } else {
                            path = urls.show[hs - 1];
                            mydata = c.zipsy.getdata(path);
                        }
                        init();
                        break;
                    }
                }
                break;
            case -7://rightsoft
                Showhelper.show = c.browser;
                break;
            case 35://#
                mb = !mb;
                break;
            case 42://*
                if (jie) {
                    nj = !nj;
                } else {
                    now++;
                    if (now > zon) {
                        now = 0;
                    }
                }
                break;
            case 48://0
                if (jie) {
                    keyPressed(51);
                } else {
                    new Thread(this).start();
                }
                break;
            case 53://5
                String pn;
                if (!path.endsWith("/")) {
                    pn = path.substring(0, path.length() - 4) + "myzhoxian";
                } else {
                    pn = path + "myzhoxian";
                }
                num++;
                new Video(pn, this);
        }
        repaint();
    }

    public byte[] output(Image img) {

        try {

            int ww = img.getWidth();
            int hh = img.getHeight();
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            int[] raw = new int[ww];
            byte[] tp = new byte[32768];
            byte[] tget = new byte[32000];
            Deflater def = new Deflater(9, false);
            int sum = 0;
            for (int j = 0; j < hh; j++) {
                tp[sum++] = 0;
                img.getRGB(raw, 0, ww, 0, j, ww, 1);
                if (32768 - sum < ww * 4) {
                    def.setInput(tp, 0, sum);
                    sum = 0;
                    while (!def.needsInput()) {
                        int i = def.deflate(tget, 0, 32000);
                        if (i > 0) {
                            output.write(tget, 0, i);
                        }
                    }
                }

                for (int i = 0; i < ww; i++) {
                    tp[sum++] = (byte) ((raw[i] >> 16) & 0xff);
                    tp[sum++] = (byte) ((raw[i] >> 8) & 0xff);
                    tp[sum++] = (byte) (raw[i] & 0xff);
                    tp[sum++] = (byte) ((raw[i] >> 24) & 0xff);
                }

            }

            if (sum > 1) {
                def.setInput(tp, 0, sum);
                sum = 0;
                while (!def.needsInput()) {
                    int i = def.deflate(tget, 0, 4096);
                    if (i != 0) {
                        output.write(tget, 0, i);
                    }
                }
            }

            def.finish();
            int k;
            for (; !def.finished(); output.write(tget, 0, k)) {
                k = def.deflate(tget, 0, 4096);
            }
            raw = null;
            System.gc();
            byte buffer[] = output.toByteArray();
            output.reset();
            DataOutputStream put = new DataOutputStream(output);
            put.write(HEADChunk);
            put.writeInt(13);
            put.writeInt(1229472850);//4
            put.writeInt(ww);//8
            put.writeInt(hh);//12
            put.writeByte(8);//13
            put.writeInt(6 << 24);//17
            put.writeInt(0);
            int bufferlen = buffer.length;
            // System.out.println("bufferlen=" + bufferlen);
            put.writeInt(bufferlen);
            put.writeInt(1229209940);
            put.write(buffer);
            put.writeInt(0);
            put.write(IENDChunk);
            byte[] data = output.toByteArray();
            CRC32 crc = new CRC32();
            crc.update(data, 12, 17);
            int l = (int) crc.getValue();
            data[29] = (byte) (l >> 24);
            data[30] = (byte) ((l & 0xff0000) >> 16);
            data[31] = (byte) ((l & 0xff00) >> 8);
            data[32] = (byte) (l & 0xff);
            crc.reset();
            crc.update(data, 37, bufferlen + 4);
            l = (int) crc.getValue();
            bufferlen += 41;
            data[bufferlen++] = (byte) (l >> 24);
            data[bufferlen++] = (byte) ((l & 0xff0000) >> 16);
            data[bufferlen++] = (byte) ((l & 0xff00) >> 8);
            data[bufferlen] = (byte) (l & 0xff);
            buffer = null;
            System.gc();
            return data;
        } catch (IOException ex) {
            c.show.showError(ex.toString());

            return null;
        }
    }

    public void run() {
        if (jie) {
            super.run();
        } else {
            save();
        }
    }

    public void zooin(int a, int b) {


        if (now == 0 || img[now - 1] == null) {
            return;
        }
        int i = img[now - 1].getWidth();
        int j = img[now - 1].getHeight();
        if (a > i || a == 0) {
            a = i;
        }
        if (b > j || b == 0) {
            b = j;
        }
        if (a > 400) {
            a = 400;
        }
        if (b > 400) {
            b = 400;
        }
        int k = a << 1;
        int l = b << 1;
        int ai[] = new int[a * b];
        img[now - 1].getRGB(ai, 0, a, 0, 0, a, b);
        int[] image = new int[k * l];
        for (int i1 = 0; i1 < b; i1++) {
            for (int j1 = 0; j1 < a; j1++) {
                image[(j1 << 1) + (i1 * k << 1)] = image[(j1 << 1) + 1 + (i1 * k << 1)] = image[(j1 << 1) + ((i1 << 1) + 1) * k] = image[(j1 << 1) + ((i1 << 1) + 1) * k + 1] = ai[j1 + i1 * a];
            }

        }
        img[now - 1] = Image.createRGBImage(image, k, l, false);


    }

    public static Image zoo(Image oldimg, int w, int h) {
        if (oldimg == null) {
            return null;
        }
        int ow = oldimg.getWidth();
        int oh = oldimg.getHeight();
        if (ow == w && oh == h) {
            return oldimg;
        }
        int data[] = new int[ow * oh];
        oldimg.getRGB(data, 0, ow, 0, 0, ow, oh);
        double wb;
        double hb = (double) oh / h;
        wb = w == 0 ? hb : (double) ow / w;
        w = (int) (w == 0 ? ow / wb : w);
        int temp[] = new int[oh * w];
        for (int i = 0; i < oh; i++) {
            for (int j = 0; j < w; j++) {
                temp[i * w + j] = data[ow * i + (int) (wb * j)];
            }
        }
        data = null;
        data = new int[w * h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                data[w * j + i] = temp[(int) (j * hb) * w + i];
            }
        }
        return Image.createRGBImage(data, w, h, true);

    }

    public void zooout() {
        if (now == 0 || img[now - 1] == null) {
            return;
        }
        int j = img[now - 1].getWidth();
        int k = img[now - 1].getHeight();
        if (j < 3 || k < 3) {
            return;
        }
        int j1;
        int k1;
        j1 = j / 2;
        k1 = k / 2;
        int ai[] = new int[j * k];
        img[now - 1].getRGB(ai, 0, j, 0, 0, j, k);
        int i[] = new int[j1 * k1];
        for (int i1 = 0; i1 < k1; i1++) {
            for (int l1 = 0; l1 < j1; l1++) {
                i[l1 + i1 * j1] = ai[(l1 << 1) + (i1 * j << 1)];
            }

        }
        img[now - 1] = Image.createRGBImage(i, j1, k1, false);
    }

    private void select() {

        switch (cc.hs) {
            case 0:
                int data[] = new int[xp[now] * yp[now]];
                img[now - 1].getRGB(data, 0, xp[now], 0, 0, xp[now], yp[now]);
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i] & 0x7fffffff;
                }
                img[now - 1] = Image.createRGBImage(data, xp[now], yp[now], true);
                data = null;
                break;
            case 1:
                jie = !jie;
                show = false;
                break;
            case 2:
                ppg = !ppg;
                break;
            case 3:
                break;
            case 4:
                img[now - 1] = Image.createImage(img[now - 1], 0, 0, xp[now], yp[now], javax.microedition.lcdui.game.Sprite.TRANS_MIRROR);
                break;
            case 5:
                img[now - 1] = Image.createImage(img[now - 1], 0, 0, xp[now], yp[now], javax.microedition.lcdui.game.Sprite.TRANS_ROT90);

        }
        xp[now] = img[now - 1].getWidth();
        yp[now] = img[now - 1].getHeight();
    }
}

