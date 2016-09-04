package gui;

import chen.c;
import javax.microedition.lcdui.*;

public class Calculate implements CommandListener, ItemStateListener {

    Form f, set;
    ChoiceGroup g;
    dstack shu, fu;
    private Command exitCommand = new Command("退出", Command.EXIT, 1);
    private Command back = new Command("设置", Command.SCREEN, 2);
    private Command ok = new Command("开始计算", Command.OK, 1);
    private Command bac = new Command("数制转换", Command.BACK, 1);
    private TextField t, e;
    boolean rad = true;
    int num;

    public Calculate() {
        f = new Form("计算器");
        set = new Form("设置");
        t = new TextField("计算式：", null, 256, TextField.ANY);
        e = new TextField("计算结果：", null, 256, TextField.ANY);
        f.append(t);
        f.append(e);
        f.addCommand(back);
        f.addCommand(bac);
        f.setCommandListener(this);
        f.setItemStateListener(this);
        g = new ChoiceGroup("模式", ChoiceGroup.EXCLUSIVE);
        g.append("角度", null);
        g.append("弧度", null);
        g.setSelectedIndex(1, rad);
        set.append(g);
        set.addCommand(ok);
        set.addCommand(exitCommand);
        set.setCommandListener(this);
    }

    public void commandAction(Command cm, Displayable s) {
        if (cm == exitCommand) {
            c.d.setCurrent(c.show);

        } else if (cm == back) {
            c.d.setCurrent(set);
        } else if (cm == ok) {
            rad = g.getSelectedIndex() == 1 ? true : false;
            c.d.setCurrent(f);
        } else if (cm == bac) {
            c.d.setCurrent(new gui.Change(f).f);
        }

    }

    private double cal(double a, double b, double c) {
        switch ((int) c) {
            case 43:
                return a + b;
            case 45:
                return b - a;
            case 42:
                return a * b;
            case 47:
                return b / a;

            default:
                return 0;
        }
    }

    int sort(double q, int h) {
        switch ((int) q) {
            case 35: //#
                if (h == 35) {
                    return 0;
                } else {
                    return 1;
                }


            case 41://)
                return -1;
            case 43://+
            case 45: //-
                if (h == 42 || h == 47 || h == 40 || h < 5) {
                    return 1;
                } else {
                    return -1;
                }
            case 42://*
            case 47:///
                if (h == 40 || h < 5) {
                    return 1;
                } else {
                    return -1;
                }
            default:

                if (h == 41) {
                    return 0;
                } else {
                    return 1;
                }
        }

    }

    double getnum() {
        shu = new dstack();
        fu = new dstack();
        byte[] bt = t.getString().getBytes();
        int len = bt.length + 1;
        if (len <= 1) {
            return 0;
        }
        byte[] b = new byte[len];
        for (int y = 0; y < len - 1; y++) {
            b[y] = bt[y];
        }
        b[len - 1] = 35;

        fu.push(35);
        int now = 0;


        while (now < len) {
            int lf = 0;

            for (int y = now; y < len && (b[y] > 47 && b[y] < 60 || b[y] == 46); y++) {
                lf++;
            }

            if (lf == 0) {
                if (now > 0) {
                    if (b[now] == 40 && ((b[now - 1] < 90 && b[now - 1] > 47) || b[now - 1] == 41) && b[now + 1] != 41) {
                        fu.push(42);
                    }
                }
                int fc = 0;

                for (int y = now; y < len && b[y] > 96; y++) {
                    fc++;
                }
                int cs = 0;
                if (fc == 0) {
                    cs = b[now];
                } else {
                    if (b[now] == 115 && b[now + 1] == 105 && b[now + 2] == 110) {
                        cs = 1;
                    }
                    if (b[now] == 99 && b[now + 1] == 111 && b[now + 2] == 115) {
                        cs = 2;
                    }
                    if (b[now] == 116 && b[now + 1] == 97 && b[now + 2] == 110) {
                        cs = 3;
                    }
                    if (b[now] == 115 && b[now + 1] == 113 && b[now + 2] == 114 && b[now + 3] == 116) {
                        cs = 4;
                    }
                }
                switch (sort(fu.pek(), cs)) {

                    case 0:
                        if (!rad) {
                            sys2();
                        } else {
                            sys();
                        }
                        break;

                    case -1:
                        while (sort(fu.pek(), cs) == -1) {
                            shu.push(cal(shu.pop(), shu.pop(), fu.pop()));
                        }
                        if (sort(fu.pek(), cs) == 0) {
                            if (!rad) {
                                sys();
                            } else {
                                sys2();
                            }
                            break;
                        }
                    case 1:
                        fu.push(cs);
                        break;
                }
                now += fc + 1;
                continue;
            }
            double j = 0;
            double m = 1;

            for (int h = now; b[h + 1] != 46 && h < now + lf - 1; h++) {
                m *= 10;
            }
            for (int k = now; k < now + lf; k++) {
                if (b[k] != 46) {
                    j += (b[k] - 48) * m;
                    m /= 10;
                }
            }
            now += lf;
            shu.push(j);

        }

        return shu.pop();
    }

    private void sys() {
        switch ((int) fu.pop()) {
            case 40:
            case 35:
                break;
            case 1:
                shu.push(Math.sin(shu.pop()));
                break;
            case 2:
                shu.push(Math.cos(shu.pop()));
                break;
            case 3:
                shu.push(Math.tan(shu.pop()));
                break;
            case 4:
                shu.push(Math.sqrt(shu.pop()));
                break;

        }
    }

    private void sys2() {
        switch ((int) fu.pop()) {
            case 40:
            case 35:
                break;
            case 1:
                shu.push(Math.sin(Math.toRadians(shu.pop())));
                break;
            case 2:
                shu.push(Math.cos(Math.toRadians(shu.pop())));
                break;
            case 3:
                shu.push(Math.tan(Math.toRadians(shu.pop())));
                break;
            case 4:
                shu.push(Math.sqrt(shu.pop()));
                break;

        }
    }

    public void itemStateChanged(Item item) {
        if (item == t) {
            e.setString("" + getnum());
            if (!shu.empt() || !fu.empt()) {
                e.setString("error");
            }
        }
    }
}
