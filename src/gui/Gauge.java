package gui;

import chen.c;
import io.ContDataInputStream;
import javax.microedition.lcdui.Graphics;

public class Gauge implements Runnable {

    private long maxValue, value;
    private long maxValue2, value2;
    private String name, name2;
    private int height, zg, width;
    private long time;
    private boolean run, many;
    private ContDataInputStream in;

    Gauge(String s, long max, boolean duo) {
        name = s;
        name2 = s;
        maxValue2 = maxValue = Math.max(1, max);
        value2 = value = 0;
        zg = Canvas.font.getHeight();
        height = duo ? Canvas.height - zg * 4 - 50 : Canvas.height - zg * 2 - 50;
        width = Canvas.width - 6;
        time = System.currentTimeMillis();
        run = true;
        many = duo;
    }

    public void setIn(ContDataInputStream c) {
        in = c;
        new Thread(this).start();
    }

    public void close() {
        run = false;
        Showhelper.jindu = null;
    }

    public void paint(Graphics g) {
        g.setFont(Canvas.font);
        g.setColor(0xcd5c5c);
        g.fillRect(0, height, Canvas.width, Canvas.height - height);
        g.setColor(0xcd);
        if (many) {
            paintmany(g);
        } else {
            paintone(g);
        }
    }

    private void paintmany(Graphics g) {
        int y = height + 1;
        g.drawString(name, 5, y, 20);
        g.drawString("已完成：" + 100 * value / maxValue + "%", 5, y += 1 + zg, 20);
        g.drawString(name2, 5, y += zg + 18, 20);
        g.drawString("总完成：" + 100 * value2 / maxValue2 + "% 时间:" + (System.currentTimeMillis() - time), 5, y += 2 + zg, 20);
        g.setColor(0x55ff);
        y = height + 3 + 2 * zg;
        g.fillRoundRect(3, y, width, 15, 5, 15);
        if (value > 0) {
            g.setColor(0xffd700);
            g.fillRoundRect(3, y, (int) (width * value / maxValue), 15, 5, 15);
        }
        g.setColor(0x55ff);
        y = height + 26 + 4 * zg;
        g.fillRoundRect(3, y, width, 15, 5, 15);
        if (value > 0) {
            g.setColor(0xffd700);
            g.fillRoundRect(3, y, (int) (width * value2 / maxValue2), 15, 5, 15);
        }
    }

    private void paintone(Graphics g) {
        int y = height + 3;
        g.drawString(name, 5, y, 20);
        g.drawString("已完成：" + 100 * value / maxValue + "% 时间:" + (System.currentTimeMillis() - time), 5, y += 3 + zg, 20);
        g.setColor(0x55ff);
        g.fillRoundRect(3, y += zg + 2, width, 30, 5, 30);
        if (value > 0) {
            g.setColor(0xffd700);
            g.fillRoundRect(3, y, (int) (width * value / maxValue), 30, 5, 30);
        }
    }

    public void setMaxValue(long max) {
        maxValue = Math.max(1, max);
        value = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void show(long v) {
        if (v < value) {
            return;
        }
        if (v >= maxValue) {
            v = maxValue;
        }
        if (many) {
            value2 += (v - value);
        }
        value = v;
        c.show.repaint();

    }

    public void show(String s, long v) {
        name = s;
        show(v);

    }

    public void showRemain(long v) {
        show(maxValue - v);
    }

    public void showAdd(long v) {
        show(value + v);
    }

    public void run() {
        while (run) {
            try {
                Thread.sleep(500);
                if (in != null) {
                    show(in.getOffst());
                    if (in.bytes == null) {
                        return;
                    }
                }
            } catch (Exception ex) {
            }

        }
    }
}
