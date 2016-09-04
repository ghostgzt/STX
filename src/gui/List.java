package gui;

import chen.c;
import javax.microedition.lcdui.*;

public class List extends Canvas {

    public static Image[] filetype;
    boolean showll;
    public command ll;
    private FileNode dir;
    boolean[] selected;
    public int dinwei, hangHeight, dinHeight, yhs, hs, key, kd;
    public Contrlable g;
    String title;
    public static int bcolor, fcolor, xcolor, kcolor, num;
    boolean selectmode = false;

    public FileNode getDir() {
        dir.hs = hs;
        dir.dinwei = dinwei;
        return dir;
    }

    public int getType(int index) {
        return dir.image[index];
    }

    public void set(int index, String name, int n) {
        dir.show[index] = name;
        dir.image[index] = n + 1;
    }

    public void setDir(FileNode di) {
        dir.hs = hs;
        dir.dinwei = dinwei;
        dir.trim();
        dir = di;
        title = di.title;
        hs = di.hs;
        dinwei = di.dinwei;
    }

    public List(FileNode currDir, Contrlable g) {
        num = 1;
        ini();
        this.g = g;
        dir = currDir;
        title = currDir.title;
        Showhelper.show = this;
        repaint();
    }

    public void setCommand(command ll) {
        this.ll = ll;
    }

    void initselectmode() {
        selectmode = true;
        selected = new boolean[dir.show.length];
    }

    public void sort(int a, int b) {

        if (b - a < 2) {
            return;
        }
        FileNode.sort(dir.show, dir.image, a, b - 1);
    }

    public void setTitle(String s) {
        if (s != null) {
            title = s;
        }
    }

    public String getTitle() {
        return title;
    }

    public int getSelectedIndex() {
        return hs - 1;
    }

    public void deleteAll(boolean newdir) {
        if (newdir) {
            dir.trim();
        } else {
            dir.zhs = 0;
        }
    }

    void ini() {
        if (font == null) {
            font = Font.getFont(0);
        }
        dinwei = 1;
        hs = 1;
        hangHeight = font.getHeight() + 2;
        dinHeight = font.getHeight() + 1;
        yhs = height / hangHeight - 2;
        showll = false;
    }

    public void update() {
        hangHeight = font.getHeight() + 2;
        dinHeight = font.getHeight() + 1;
        yhs = height / hangHeight - 2;
    }

    public void append(String na, int n) {
        dir.append(na, n);
    }

    void delete(int n) {
        dir.delete(n);
        up();
    }

    public void insert(String s) {

        dir.insert(s);
    }

    public void paint() {
        if (dir == null) {
            return;
        }
        gc.setFont(font);
        int kuan = 0;
        gc.setColor(bcolor);
        gc.fillRect(0, 0, width, height);
        if (filetype[14] != null) {
            gc.drawImage(filetype[14], width / 2, height / 2, Graphics.VCENTER | Graphics.HCENTER);
        }
        kuan = font.stringWidth(dinwei + "." + dir.show[hs - 1]);
        gc.setColor(xcolor);
        gc.fillRoundRect(kd, 2 + dinwei * hangHeight - hangHeight + dinHeight, kuan, dinHeight, 7, dinHeight);
        gc.setColor(kcolor);
        gc.fillRect(0, 0, width, dinHeight);
        gc.setColor(fcolor);
        kuan = font.stringWidth(title);
        String time = c.showTime();
        if (kuan + 2 + font.stringWidth(time) < width) {
            gc.drawString(time, width - 2, 2, 24);
        } else {
            gc.drawString(time, kuan + 3, 2, 20);
        }
        paints();
        gc.drawString(dir.zhs + " " + hs + " ·µ»Ø", width, height, 40);
        if (ll != null) {
            gc.drawString("²Ëµ¥ ", 0, height, 36);
            if (showll) {
                ll.paint(gc);
            }
        }

    }

    void paints() {
        int cha = hs - dinwei;
        int hohs = Math.min(yhs, dir.zhs - cha);
        gc.drawString(title, 2, 2 + dinHeight - hangHeight, 20);
        for (int i = 0; i < hohs; i++) {
            if (dir.image[cha + i] > 0) {
                gc.drawImage(filetype[dir.image[cha + i] - 1], 1, dinHeight + hangHeight * i, Graphics.LEFT | Graphics.TOP);
            }
            String t = (i + 1) + ".";
            gc.drawString(t, kd, dinHeight + hangHeight * i, 20);
            if (selectmode && cha + i != 0 && selected[cha + i]) {
                gc.drawImage(filetype[13], 1, dinHeight + hangHeight * i, 20);
            }
            gc.drawString(dir.show[cha + i], kd + font.stringWidth(t), dinHeight + hangHeight * i, 20);
        }
    }

    void up() {
        dinwei--;
        hs--;
        if (dinwei < 1) {
            dinwei = 1;
        }

        if (hs < 1) {
            hs = dir.zhs;
        }
        if (dir.zhs <= yhs) {
            dinwei = hs;
        }
    }

    void down() {
        dinwei++;
        if (dinwei > yhs && hs < dir.zhs) {
            dinwei = yhs;
        }
        hs++;
        if (hs > dir.zhs) {
            hs = dinwei = 1;
        }
    }

    void left() {
        hs += 1 - yhs - dinwei;
        dinwei = 1;

        if (hs < 1) {
            hs = (dir.zhs - dir.zhs % yhs) + 1;
        }
        if (hs > dir.zhs) {
            hs -= yhs;
        }
    }

    void right() {
        hs += yhs - dinwei + 1;
        dinwei = 1;
        if (hs > dir.zhs) {
            hs = 1;
        }
    }

    public void keyRepeated(int k) {
        keyPressed(k);
    }

    public void keyPressed(int keycode) {
        key = keycode;

        switch (keycode) {
            case 48:
                g.fresh();
                break;
            case -6:
                if (ll != null) {
                    showll = !showll;
                    repaint();
                }
                break;
            case -7:
                if (showll && ll != null) {
                    showll = false;
                    ll.state = 2;
                    ll.init();
                } else {
                    g.back();
                }
                repaint();
                break;
            case -4:
                if (showll && ll != null) {
                    ll.right();
                } else {
                    right();
                }
                repaint();
                break;
            case -3:
                if (showll && ll != null) {
                    ll.left();
                } else {
                    left();
                }
                repaint();
                break;
            case -2:
                if (showll && ll != null) {
                    ll.down();
                } else {
                    down();
                }
                repaint();
                break;
            case -1:
                if (showll && ll != null) {
                    ll.up();
                } else {
                    up();
                }
                repaint();
                break;
            case -5:
                if (showll && ll != null) {
                    ll.select();
                    showll = !showll;
                    repaint();
                } else if (selectmode) {
                    selected[hs - 1] = !selected[hs - 1];
                    repaint();
                } else {
                    select();
                }
                break;
            case -8:
                if (selectmode) {
                    int select[] = this.getselected();
                    if (select == null) {
                        return;
                    }
                    for (int i = 0; i < select.length; i++) {
                        selected[select[i]] = false;
                        if (g.dodelete(select[i])) {
                            delete(select[i]);
                        }

                    }
                } else if (g.dodelete(hs - 1)) {
                    delete(hs - 1);
                }
                repaint();
                break;
            case -10:
                c.d.setCurrent(null);
                break;
            case 35://#
                jiepin();
                break;
            case 42://*
                break;
            case 49://1
            case 50://2
            case 51://3
            case 52://4
            case 53://5
            case 54://6
            case 55://7
            case 56://8
            case 57://9
                keycode -= 48;
                if (!showll && keycode + hs - dinwei <= dir.zhs) {
                    hs -= dinwei - keycode;
                    dinwei = keycode;
                    select();
                } else if (showll && ll != null && keycode <= ll.zhs) {
                    ll.hs = keycode - 1;
                    ll.select();
                    showll = !showll;
                }
                repaint();
        }

    }

    public int[] getselected() {
        if (!selectmode) {
            return null;
        }
        int selectednum = 0;
        for (int i = dir.zhs; i > 0; i--) {
            if (selected[i]) {
                selectednum++;
            }
        }
        if (selectednum == 0) {
            return null;
        }
        int select[] = new int[selectednum];
        selectednum = 0;
        for (int i = dir.zhs; i > 0; i--) {
            if (selected[i]) {
                select[selectednum++] = i;
            }
        }
        return select;
    }

    public String getString(int i) {
        return dir.show[i];
    }

    void imagedraw(Graphics g) {
    }

    public void select() {
        g.select();
    }

    void closeselectmode() {
        selectmode = false;
        selected = null;
        repaint();
    }
}
