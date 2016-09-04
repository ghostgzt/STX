package gui;

import io.FileSys;
import chen.c;
import io.MemorySet;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.TextBox;

public class Hex extends Canvas implements CommandListener {

    byte gai[] = {16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 65, 66, 67, 68, 69, 70};
    private int yhs, zhs, zg, zk, x, y, phs, bytetotalenth, num, bk, findbytelenth, nowoff;
    private boolean show, editor, find, shuzi, rms, editorZijie;
    byte[] doublebyte, outbyte, findbyte;
    Image charset[];
    int state, ystate, dyh;
    String path;
    long jishiqi;
    long time;
    int count;
    command cc;
    Showable d;
    Command ok = new Command("确认", Command.OK, 1);
    TextBox input;

    void end() {
        doublebyte = null;
        outbyte = null;
        findbyte = null;
        charset = null;
        path = null;
        cc = null;
    }

    public Hex(String url, Showable dis, boolean s) {
        input = new TextBox("输入", "", 4096, 0);
        input.setCommandListener(this);
        input.addCommand(ok);
        input.addCommand(new Command("返回", Command.ITEM, 1));
        d = dis;
        rms = s;
        zg = 14;
        zk = 6;
        path = url;
        cc = new command();
        cc.show = new String[]{"查找字符", "查找字节", "找下一个", "", "", "跳至末尾", "跳至开始", "计算", rms ? "保存rms" : "保存文件"};
        cc.kd = font.stringWidth("8. 找下一个") + 2;
        cc.zhs = cc.show.length;
        cc.hs = 0;
        show = false;
        yhs = height / zg - 2;
        num = 8;
        findbyte = new byte[50];
        bk = width * 3 / 4 - 10;
        Image temp;
        charset = new Image[96];
        count = 0;
        try {
            int c2 = List.fcolor | 0xff000000;
            temp = Image.createImage("/chen/h");
            int[] a = new int[14 * 564];
            temp.getRGB(a, 0, 564, 0, 0, 564, 14);
            for (int i = 0; i < a.length; i++) {
                if (a[i] == 0xffffffff) {
                    a[i] &= 0xffffff;
                } else {
                    a[i] = c2;
                }
            }
            temp = null;
            temp = Image.createRGBImage(a, 564, 14, true);
            a = null;
            for (int i = 0; i < 94; i++) {
                charset[i + 1] = Image.createImage(temp, i * 6, 0, 6, 14, 0);
            }
            charset[95] = Image.createImage(temp, 38, 0, 3, 14, 0);
            charset[0] = Image.createImage(temp, 38, 0, 6, 14, 0);
        } catch (Throwable ex) {
            c.show.showError(ex.toString());
        }
        temp = null;
        x = 5;
        y = 1;
        ystate = 0;
        state = 1;
        dyh = 0;
        editorZijie = true;
        findbytelenth = 0;
        editor = true;
        repaint();
    }

    byte[] getbytes() {
        byte[] a = new byte[bytetotalenth];
        int j = 0;
        for (int i = 0; i < bytetotalenth * 2;) {
            a[j++] = (byte) (((doublebyte[i++] < 30 ? doublebyte[i - 1] - 16 : doublebyte[i - 1] - 55) << 4) | (doublebyte[i++] < 30 ? doublebyte[i - 1] - 16 : doublebyte[i - 1] - 55));
        }
        return a;
    }

    public void setbytes(byte b[], int off, int len) {
        if (b == null || off > b.length || off + len > b.length) {
            b = new byte[1];
            len = 1;
        }
        if (len < 1) {
            len = b.length;
        }
        if (len > MemorySet.MAXREAD) {
            len = MemorySet.MAXREAD;
        }
        int off1 = 0;
        bytetotalenth = len;
        outbyte = null;
        doublebyte = null;
        doublebyte = new byte[2 * (len + 3096)];
        outbyte = new byte[len + 3096];
        for (int j = 0; j < len; j++) {
            outbyte[off1 / 2] = ((byte) (b[j] > 31 && b[j] < 127 ? b[j] - 32 : 14));
            doublebyte[off1++] = gai[(b[j] >> 4) & 0xf];
            doublebyte[off1++] = gai[b[j] & 0xf];
        }
        zhs = len / num + (len % num == 0 ? 0 : 1);
        phs = Math.min(zhs, yhs);
        repaint();
    }

    public void paint() {

        int i, j, off;
        gc.setColor(List.bcolor);
        gc.fillRect(0, 0, width, height);
        gc.setColor(List.kcolor);
        gc.drawRect(0, 0, bk, height - 1);
        gc.drawRect(bk, 0, width - bk - 1, height - 1);
        gc.setColor(List.xcolor);
        if (editorZijie) {
            gc.fillRect(x, y, zk, zg);
        } else {
            gc.fillRect(bk - 2 + state * 3, y, zk, zg);
        }
        gc.setColor(List.fcolor);
        byte[] nu;

        for (i = dyh; i < phs + dyh; i++) {
            nu = Integer.toString(i).getBytes();
            for (j = nu.length - 1; j >= 0; j--) {
                gc.drawImage(charset[nu[j] - 32], bk - 2 - (nu.length - j - 1) * 6, zg * (i - dyh), 24);
            }
            for (j = 0, off = 0; j < num && j + i * num < bytetotalenth; j++) {
                gc.drawImage(charset[doublebyte[(i * num + j) * 2]], off += 6, zg * (i - dyh), 20);
                gc.drawImage(charset[doublebyte[(i * num + j) * 2 + 1]], off += 6, zg * (i - dyh), 20);
                gc.drawImage(charset[95], 3 + (off += 3), zg * (i - dyh), 20);
                gc.drawImage(charset[outbyte[i * num + j]], bk + 3 + j * 6, zg * (i - dyh), 20);
            }
        }
        gc.setColor(List.kcolor);
        for (i = 1; i <= yhs; i++) {
            gc.drawLine(0, i * zg, width, i * zg);
        }
        if (editor || find) {
            gc.setColor(List.fcolor);
            gc.drawString(shuzi ? "#-数字" : "#-字母", width / 2, height - 3, 33);
        }
        gc.drawString("返回", width, height, Graphics.BOTTOM | Graphics.RIGHT);
        gc.drawString("菜单 ", 0, height, Graphics.BOTTOM | Graphics.LEFT);
        if (show) {
            cc.show[3] = "编辑" + (editor ? " 是" : " 否");
            cc.show[4] = "编辑" + (editorZijie ? "字符" : "字节");
            cc.paint(gc);
        }

        if (find) {

            gc.setClip(4, height / 2, width - 8, 18);
            gc.setColor(0xffffff);
            gc.fillRect(0, 0, width, height);
            gc.setColor(List.kcolor);
            gc.drawRect(4, height / 2, width - 8, 18);
            for (i = 0, off = 0; i < findbytelenth; i++) {
                gc.drawImage(charset[findbyte[i]], 5 + (off += charset[findbyte[i]].getWidth()) - charset[findbyte[i]].getWidth(), height / 2 + 2, 20);

            }

        }



    }

    public void keyRepeated(int code) {
        keyPressed(code);
    }

    public void keyPressed(int code) {

        switch (code) {
            case -4://right
                right();
                if (!editorZijie) {
                    right();
                }
                break;
            case -6:
                if (!find) {
                    show = !show;
                } else {
                    find = false;
                    find(findbyte, 0, findbytelenth, 0);
                }
                break;
            case -3://left
                left();
                if (!editorZijie) {
                    left();
                }
                break;
            case -2://down
                if (show) {
                    cc.down();
                } else {
                    down();
                }
                break;
            case -1://up
                if (show) {
                    cc.up();
                } else {
                    up();
                }
                break;
            case -5://ok
                if (show) {
                    select();
                } else if (find) {
                    find = false;
                    find(findbyte, 0, findbytelenth, 0);
                } else {
                    FileSys.savefile(path + ".db", getbytes(), 0, 0);
                }
                break;

            case -7://rightsoft
                if (!show && !find) {
                    end();
                    Showhelper.show = d;
                } else if (show) {
                    show = false;
                } else {
                    find = false;
                }
                break;
            case 35://#
                if (editor || find) {
                    shuzi = !shuzi;
                } else if (dyh + yhs < zhs) {
                    dyh += yhs;
                    nowoff += 16 * yhs;
                }
                break;
            case 42://*
                if (dyh >= yhs) {
                    dyh -= yhs;
                    nowoff -= 16 * yhs;
                } else {
                    dyh = 0;
                }
                break;

            case 51://3
                if (!editor && !find) {
                    find(findbyte, 0, findbytelenth, nowoff + 2);
                }
            case 48://0
            case 50://2
            case 52://4
            case 53://5
            case 54://6
            case 55://7
            case 56://8
            case 57://9
            case 49://1
                if (show) {
                    if (code - 49 < cc.show.length && code > 48) {
                        cc.hs = code - 49;
                        select();
                    }
                } else if (find && findbytelenth < 23) {

                    if (shuzi) {
                        gaiFindByte(code - 32);
                    } else {
                        if (code < 55) {
                            gaiFindByte(code + 16);
                        } else {
                            gaiFindByte(code - 32);
                        }
                    }



                } else {
                    if (editor) {
                        if (editorZijie) {
                            if (shuzi) {
                                gaiZijie(code - 32);
                            } else if (code < 55) {
                                gaiZijie(code + 16);
                            } else {
                                gaiZijie(code - 32);
                            }
                        } else {
                            c.d.setCurrent(input);
                        }
                    }
                }

                break;

            case -8:
                if (find && findbytelenth > 0) {
                    findbytelenth--;
                    if (findbyte[findbytelenth] == 95) {
                        findbytelenth--;
                    }
                } else if (editor && nowoff > 0 && nowoff < bytetotalenth * 2) {
                    delete();
                }
        }

        repaint();

    }

    private void gaiFindByte(int a) {
        findbyte[findbytelenth++] = (byte) a;
    }

    private void down() {

        if (ystate < yhs - 1) {
            y += 14;
            ystate++;
            nowoff += 16;
        } else {
            if (dyh < zhs - yhs + 2) {
                dyh++;

                nowoff += 16;
            }
        }

    }

    private void gaiZifu(byte[] b) {
        int len = b.length;
        if (len <= 0) {
            return;
        }
        int off = nowoff;

        for (int j = 0; j < len; j++) {
            outbyte[off / 2] = ((byte) (b[j] > 31 && b[j] < 127 ? b[j] - 32 : 14));
            doublebyte[off++] = gai[(b[j] >> 4) & 0xf];
            doublebyte[off++] = gai[b[j] & 0xf];
            if (off >= bytetotalenth * 2) {
                bytetotalenth++;
            }
        }
    }

    private void gaiZijie(int a) {
        try {
            doublebyte[nowoff] = (byte) a;
            int k = nowoff % 2 == 0 ? nowoff : nowoff - 1;
            k = ((doublebyte[k] < 30 ? doublebyte[k] - 16 : doublebyte[k] - 55) << 4) | (doublebyte[k + 1] < 30 ? doublebyte[k + 1] - 16 : doublebyte[k + 1] - 55);
            outbyte[nowoff / 2] = ((byte) (k > 31 && k < 127 ? k - 32 : 14));

            if (nowoff == doublebyte.length - 1) {
                return;
            }
            if (nowoff > bytetotalenth * 2 - 1) {
                bytetotalenth++;
                if (bytetotalenth % 8 == 0) {
                    phs++;
                }
            }
            right();

        } catch (Exception e) {
        }
    }

    void delete() {
        byte[] t = new byte[doublebyte.length - 2];
        int a = nowoff - (nowoff % 2 == 0 ? 2 : 1);
        System.arraycopy(doublebyte, 0, t, 0, a);
        System.arraycopy(doublebyte, a + 2, t, a, doublebyte.length - a - 2);
        doublebyte = null;
        doublebyte = t;
        bytetotalenth--;
        t = new byte[outbyte.length - 1];
        a = nowoff / 2 - 1 + nowoff % 2;
        System.arraycopy(outbyte, 0, t, 0, a);
        System.arraycopy(outbyte, a + 1, t, a, outbyte.length - a - 1);
        outbyte = null;
        outbyte = t;
        left();
        left();
    }

    private void left() {
        if (state > 1) {
            if (state % 2 == 1) {
                x -= 9;

            } else {
                x -= 6;
            }
            state--;
            nowoff--;
        } else if (ystate > 1) {
            state = 16;
            x = 116;
            y -= 14;
            ystate--;
            nowoff--;
        }

    }

    private void right() {
        if (state < 16) {
            if (state % 2 == 0) {
                x += 9;

            } else {
                x += 6;
            }
            state++;
        } else if (ystate < zhs) {
            state = 1;
            x = 5;
            y += 14;
            ystate++;

        }
        nowoff++;
    }

    private void findZifu(byte[] b) {
        int len = b.length;
        int off = 0;
        byte bb[] = new byte[len * 2];
        if (len <= 0) {
            return;
        }
        for (int j = 0; j < len; j++) {
            bb[off++] = gai[(b[j] >> 4) & 0xf];
            bb[off++] = gai[b[j] & 0xf];
        }
        System.arraycopy(bb, 0, findbyte, 0, findbytelenth = Math.min(len * 2, 50));
        find(findbyte, 0, findbytelenth, nowoff);
    }

    private void find(byte[] a, int off, int len, int start) {

        if (len <= 0 || start > doublebyte.length - 2) {
            return;
        }
        int j;
        boolean g = false;
        for (int i = start; i < doublebyte.length - 1; i += 2) {
            g = false;
            for (j = off; j < len + off; j++) {
                if (doublebyte[i + j - off] != a[j]) {
                    g = true;
                    break;
                }
            }
            if (!g) {
                nowoff = i;
                state = (i + 1) % 16;
                j = i / 16 + 1;
                if (j > 2) {
                    dyh = j - 2;
                } else {
                    dyh = 0;
                }
                ystate = j - dyh - 1;
                y = (i / 16 - dyh) * 14 + 1;
                x = (state - 1) / 2 * 15 + 5 + ((state - 1) % 2) * 6;
                a = null;
                break;
            }
        }

        if (g) {
            c.show.show("信息", "未找到");
            find = true;
        }
    }

    private void select() {
        switch (cc.hs) {
            case 3:
                editor = !editor;
                break;
            case 1:
                find = !find;
                break;
            case 2:
                find(findbyte, 0, findbytelenth, nowoff + 2);
                break;
            case 0:
                find = true;
                c.d.setCurrent(input);
                break;
            case 4:
                editorZijie = !editorZijie;
                if (!editorZijie && nowoff % 2 != 0) {
                    left();
                }
                break;
            case 5:
                nowoff = bytetotalenth * 2 - 1;
                dyh = zhs + 2 > yhs ? (nowoff / 16) - yhs + 2 : 0;
                ystate = dyh == 0 ? nowoff / 16 : yhs - 2;
                state = (bytetotalenth * 2) % 16;
                y = ystate * 14 + 1;
                x = (state - 1) / 2 * 15 + 5 + ((state - 1) % 2) * 6;
                if (nowoff % 16 == 15) {
                    x = 116;
                    state = 16;
                }
                break;
            case 6:
                x = 5;
                y = 1;
                ystate = 0;
                state = 1;
                dyh = 0;
                nowoff = 0;
                break;
            case 7:
                new Change(new Calculate().f);
                break;
            case 8:
                if (rms) {
                    ((Rms) d).save(getbytes());
                    end();
                    Showhelper.show = d;

                } else {
                    FileSys.savefile(path, getbytes(), 0, 0);
                }

        }
        show = false;
    }

    private void up() {

        if (ystate > 0) {
            y -= 14;
            ystate--;
            nowoff -= 16;
        } else {
            if (dyh > 0) {
                dyh--;
                nowoff -= 16;

            }
        }

    }

    public void commandAction(Command cc, Displayable d) {
        if (cc == ok) {
            try {
                if (find) {
                    findZifu(input.getString().getBytes("Utf-8"));
                    find = false;
                } else {
                    gaiZifu(input.getString().getBytes("Utf-8"));
                }
            } catch (Exception ex) {
            }
        }
        Showhelper.show = this;
        c.d.setCurrent(c.show);
    }
}
