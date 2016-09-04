package gui;

import javax.microedition.lcdui.Graphics;

public class command {

    public Llcd ll;
    public String show[];
    public int width, height, hs, zhs, kd, state;

    public command() {
        hs = zhs = 0;
        width = Canvas.width;
        height = Canvas.height;
    }

    public void paint(Graphics g) {
        width = Canvas.width;
        height = Canvas.height;
        int zg = Canvas.font.getHeight();
        g.setFont(Canvas.font);
        int gao = zhs * zg + 3;
        g.setColor(List.bcolor);
        g.fillRect(0, height - gao, kd, gao - 1);
        g.setColor(List.xcolor);
        g.drawRect(0, height - gao + 1, kd, gao - 1);
        g.fillRect(1, height - gao + 2 + hs * zg, kd, zg);
        g.setColor(List.fcolor);
        for (int i = 0; i < zhs; i++) {
            g.drawString((i + 1) + ". " + show[i], 1, height - gao + zg * i + 3, 20);
        }

    }

    public void init() {
    }

    public void left() {
    }

    public void right() {
    }

    public void select() {
    }

    public void up() {
        hs--;
        if (hs < 0) {
            hs = zhs - 1;
        }
    }

    public void down() {
        hs++;
        if (hs >= zhs) {
            hs = 0;
        }
    }
}
