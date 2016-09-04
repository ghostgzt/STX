package gui;

import chen.c;

public class ColorSet extends List {

    private int b, f, x, k, bu;
    private Showable his;

    public void select() {
    }

    public ColorSet(Showable s) {
        super(new FileNode("��ɫ����"), null);
        his = s;
        b = bcolor;
        f = fcolor;
        x = xcolor;
        k = kcolor;
        kd = 2;
        hs = dinwei = 1;
        Showhelper.show = this;
        paint();
    }

    public void paint() {
        getDir().setShow(new String[]{"������ɫ", "��  " + (bcolor >> 16), "��  " + ((bcolor & 0xff00) >> 8), "��  " + (bcolor & 0xff),
                    "������ɫ", "��  " + (fcolor >> 16), "��  " + ((fcolor & 0xff00) >> 8), "��  " + (fcolor & 0xff),
                    "ѡ����ɫ", "��  " + (xcolor >> 16), "��  " + ((xcolor & 0xff00) >> 8), "��  " + (xcolor & 0xff),
                    "�߿���ɫ", "��  " + (kcolor >> 16), "��  " + ((kcolor & 0xff00) >> 8), "��  " + (kcolor & 0xff)});
        super.paint();
        gc.drawString("���� ", 0, height, 36);
    }

    public void save() {
        Showhelper.show = his;
        c.saveSetting();
    }

    public void keyRepeated(int i) {
        if (i == -4) {
            bu = 10;
        } else if (i == -3) {
            bu = -10;
        }
        update();
        repaint();
    }

    public void update() {
        switch (hs) {
            case 2:
            case 3:
            case 4:
                bcolor += bu << (32 - hs * 8);
                bcolor &= 0xffffff;
                break;
            case 6:
            case 7:
            case 8:
                fcolor += bu << (64 - hs * 8);
                fcolor &= 0xffffff;
                break;
            case 10:
            case 11:
            case 12:
                xcolor += bu << (96 - hs * 8);
                xcolor &= 0xffffff;
                break;
            case 14:
            case 15:
            case 16:
                kcolor += bu << (128 - hs * 8);
                kcolor &= 0xffffff;
                break;
        }
    }

    void right() {
        bu = 1;
        update();
    }

    void left() {
        bu = -1;
        update();
    }

    public void keyPressed(int i) {
        if (i == -6) {
            save();
            repaint();
        } else if (i == -7) {
            bcolor = b;
            fcolor = f;
            xcolor = x;
            kcolor = k;
            Showhelper.show = his;
            repaint();
        } else {
            super.keyPressed(i);
        }
    }
}
