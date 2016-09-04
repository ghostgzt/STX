package gui;

import chen.c;
import javax.microedition.lcdui.*;

public class Change implements CommandListener, ItemStateListener {

    char[] p;
    int[] l;
    Form f, set;
    private Command exitCommand = new Command("退出", Command.EXIT, 1);
    private Command back = new Command("设置", Command.SCREEN, 2);
    private Command ok = new Command("开始转换", Command.OK, 1);
    private Command add = new Command("增加", Command.SCREEN, 1);
    private Command bac = new Command("计算器", Command.BACK, 1);
    private TextField[] t;
    TextField[] it;
    int num;
    Displayable dis;
    byte[] a = new byte[256];

    public Change(Displayable di) {
        dis = di;
        p = new char[26];
        for (int v = 0; v < 26; v++) {
            p[v] = (char) (65 + v);
        }
        it = new TextField[10];

        for (int y = 0; y < 10; y++) {
            it[y] = new TextField("进制：", null, 10, TextField.NUMERIC);
        }
        set = new Form("设置");
        f = new Form("数制转换");
        l = new int[]{10, 2, 16};
        t = new TextField[3];
        set.addCommand(exitCommand);
        set.addCommand(ok);

        set.addCommand(add);
        for (int y = 0; y < 3; y++) {
            set.append(it[y]);
        }
        num = 3;
        set.setCommandListener(this);
        star();

    }

    public void star() {

        for (int y = 0; y < t.length; y++) {
            t[y] = new TextField(l[y] + "进制：", null, 80, l[y] < 11 ? TextField.NUMERIC : TextField.ANY);
            f.append(t[y]);
        }
        f.setItemStateListener(this);
        f.addCommand(back);
        f.addCommand(bac);
        f.setCommandListener(this);
        c.d.setCurrent(f);
    }

    void change() {

        l = new int[num];

        for (int y = 0; y < num; y++) {
            try {
                l[y] = (int) getnum(it[y], 10);
            } catch (Exception ex) {
            }
            if (l[y] < 2 || l[y] > 36) {
                c.show.showError( "进制必须大于1小于37\n");
                return;
            }
        }
        t = new TextField[num];
        star();
        c.d.setCurrent(f);
    }

    public void commandAction(Command cm, Displayable s) {
        if (cm == exitCommand) {
            c.d.setCurrent(c.show);
        } else if (cm == ok) {
            f.deleteAll();
            change();

        } else if (cm == back) {
            c.d.setCurrent(set);
        } else if (cm == add) {
            set.append(it[num]);
            num++;
        } else if (cm == bac) {
            c.d.setCurrent(dis);
        }
    }

    long getnum(TextField t, int n) throws Exception {
        if (t.getString().length() > 0) {
            return Long.parseLong(t.getString(), n);
        } else {
            return 0;
        }
    }

    String huan(long n, int o) {
        Stack s = new Stack();
        if (n < 0) {
            n = -n;
        }
        int temp;
        while (n > 0) {
            temp = (int) (n % o);
            s.push(temp < 10 ? temp + 48 : p[temp - 10]);
            n /= o;
        }
        temp = 0;
        while (s.haveElement()) {
            a[temp++] = (byte) s.pop();
        }
        return new String(a, 0, temp);
    }

    public void itemStateChanged(Item item) {

        int i = -1;
        while (++i < t.length) {
            if (t[i] == item) {
                break;
            }
        }
        long j = 0;
        try {
            j = getnum(t[i], l[i]);
        } catch (Exception ex) {
        }
        for (int y = t.length - 1; y > i; y--) {
            t[y].setString(huan(j, l[y]));
        }
        for (int y = 0; y < i; y++) {
            t[y].setString(huan(j, l[y]));
        }
    }
}
