package gui;

import chen.c;

public class Rmscd extends command {

    Rms dis;
    String name;
    Contrlable g;

    Rmscd(Rms g) {
        this.g = g;
        dis = g;
        show = new String[]{"打开", "删除", "新建", "信息", "刷新", "截屏", "导入", "导出", "返回"};
        kd = Canvas.font.stringWidth("8. 察看") + 2;
        zhs = show.length;
    }

  public  void select() {

        switch (hs) {
            case 0:
                dis.select();
                break;

            case 1:
                if (dis.dodelete(dis.hs-1)) {
                    dis.dodelete(dis.hs - 1);
                }
                break;
            case 2:
                dis.create();
                break;

            case 3:
                dis.info();
                break;

            case 4:
                dis.openDir();
                break;

            case 5:
                dis.jiepin();
                break;
            case 6:

            case 7:
                c.browser.ll.state = 1;
                c.browser.showll = true;
                c.browser.ll.init();
               Showhelper.show=c.browser;

            default:
                break;


        }
    }
}
