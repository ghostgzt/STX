package io;

import gui.*;

public class Txtcd extends command {

    private String[] lablezhu = new String[]{
        "��ɫ����", "��������", "�������", "��������", "ȫ��"
    };
    private String[] lableliu = new String[]{
        "�ַ�", "����", "����", "��ҳ", "��ҳ", "�ر�"
    };
    Txt txt;

    Txtcd(Txt cd) {
        txt = cd;
        init();
    }

    public void init() {
        show = lablezhu;
        kd = Canvas.font.stringWidth(lablezhu[0] + "8. ") + 3;
        zhs = show.length;
        hs = 0;
    }

    public void select() {
        if (show == lableliu) {
            switch (hs) {
                case 0:
                    txt.mode = 0;
                    break;
                case 2:
                    txt.mode = 2;
                    txt.sleeptime = 600;
                    break;
                case 5:
                    txt.mode = 5;
                    break;
            }

            show = lablezhu;
            zhs = show.length;
        } else if (show == lablezhu) {
            switch (hs) {
                case 0:
                    new ColorSet(txt);
                    txt.auto = false;
                    break;
                case 1:
                    new FontSet(txt);
                    txt.auto = false;
                    break;
                case 2:
                    show = lableliu;
                    txt.showmenu = true;
                    zhs = show.length;
                    break;
                case 3:
                    Light.alwaysLight = !Light.alwaysLight;
                    break;
                case 4:
                    txt.changeScreenmode();
                    break;

            }
        }

    }
}
