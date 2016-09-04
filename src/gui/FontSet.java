package gui;

import chen.c;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class FontSet extends List {

    Showable his;
    int face, style, size;
    Font pre;

   public FontSet(Showable h) {
        super(new FileNode("字体设置", new String[]{"字体选择", "monospace", "proportional", "system", "字体风格",
                    "粗体", "斜体", "下划线", "普通", "字体大小", "大", "中", "小"}, new int[13]), null);
        his = h;
        kd = 2;
        pre = font;
        face = font.getFace();
        size = font.getSize();
        style = font.getStyle();
        initselectmode();
        update();
    }

    public void paint() {
        super.paint();
        gc.drawString("保存 ", 0, height, Graphics.BOTTOM | Graphics.LEFT);
    }

    void save() {
        c.browser.ll.init();
        c.saveSetting();
        Showhelper.show = his;
        c.initicon();
        c.browser.update();
        repaint();
    }

    public void keyPressed(int code) {
        if (code == -6) {
            save();
        }
        if (code == -7) {
            Showhelper.show = his;
            font = pre;
            super.update();
            repaint();
        } else if (code == -5) {
            select();
            repaint();
        } else {
            super.keyPressed(code);
        }
    }

    public void select() {
        boolean gai = true;
        switch (hs) {
            case 2:
                face = 32;
                break;
            case 3:
                face = 64;
                break;
            case 4:
                face = 0;
                break;
            case 6:
                style ^= 1;
                break;
            case 7:
                style ^= 2;
                break;
            case 8:
                style ^= 4;
                break;
            case 9:
                style = 0;
                break;
            case 11:
                size = 16;
                break;
            case 12:
                size = 0;
                break;
            case 13:
                size = 8;
                break;
            default:
                gai = false;
        }
        if (gai) {
            update();
        }
    }

    public void update() {
        selected[1] = face == 32;
        selected[2] = face == 64;
        selected[3] = face == 0;
        selected[5] = (style & 1) > 0;
        selected[6] = (style & 2) > 0;
        selected[7] = (style & 4) > 0;
        selected[8] = style == 0;
        selected[10] = size == 16;
        selected[11] = size == 0;
        selected[12] = size == 8;
        font = Font.getFont(face, style, size);
        super.update();
    }
}
