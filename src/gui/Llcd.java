package gui;

import io.FileSys;
import io.MemorySet;
import chen.c;
import io.BufDataOutputStream;
import zip.ZipOutputStream;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.file.FileConnection;

public class Llcd extends command implements Runnable {

    List dis;
    Contrlable g;
    ColorSet cset;
    public Rms e;
    String name;
    String[][] lable = new String[][]{
        {"从文件导入", "从rms导入", "从包内导入", "导出为rms", "导出为文件"},//1
        {"命名", "删除", "新建", "信息", "设置", "工具", "rms", "游戏", "退出"},//2
        {"类型设置", "颜色设置", "字体设置", "屏幕设置", "", "设为背景", "", "内存设置"},//3
        {"分割", "合并", "压缩", "解压", "计算", "rms", "多选", "旋转"},//4
        {"反编译", "汇编译", "反汇编译", "提取png", "破解", "txt", "改字", "照相"},//5
    };

    void select5() {
        String url = "file:///" + c.currDirName + c.browser.getString(c.browser.getSelectedIndex());
        switch (hs) {
            case 0:
            case 1:
            case 2:
                new Thread(new Jar(url, 120423 + hs)).start();
                break;
            case 3:
                new Thread(new Jar(url, 120755)).start();
                break;
            case 4:
                new Thread(new Jar(url, 3738991)).start();
                break;
            case 5:
                new Thread(new Jar(url, 120826)).start();
                break;
            case 6:
                if (!c.zip) {
                    Hex h = new Hex(url, c.browser, false);
                    h.setbytes(FileSys.getdata(url, 0, MemorySet.MAXREAD), 0, 0);
                    Showhelper.show = h;
                } else if (c.zip) {
                    c.zipsy.opendb(c.browser.getString(c.browser.getSelectedIndex()));
                }
                break;
            case 7:
                new Video("file:///" + c.currDirName + "myzhaxian", dis);
        }
    }

    void select4() {

        switch (hs) {
            case 0:
                if (!c.zip) {
                    ((c) dis.g).fenge();
                }
                break;
            case 1:
                if (!c.zip) {
                    String na = c.browser.getString(c.browser.getSelectedIndex());
                    int y, k;
                    if ((y = na.toLowerCase().indexOf(".part")) <= 0) {
                        break;
                    }
                    k = Integer.parseInt(na.substring(y + 5, na.length()));
                    OutputStream o = FileSys.getOutputStream("file:///" + c.currDirName + na.substring(0, y - 1));
                    byte b[];
                    while ((b = FileSys.getdata("file:///" + c.currDirName + na.substring(0, y + 5) + (k++), 0, 0)) != null) {
                        try {
                            o.write(b);

                        } catch (IOException ex) {
                        }
                    }
                    try {
                        o.close();
                    } catch (IOException ex) {
                    }
                }
                break;
            case 2:

                if (!c.zip && name == null) {
                    name = c.browser.getString(c.browser.getSelectedIndex());
                    new Thread(this).start();
                }
                break;
            case 3:
                if (!c.zip) {
                    int i = c.browser.getSelectedIndex();
                    int type = c.browser.getType(i);
                    String filename = "file:///" + c.currDirName + c.browser.getString(i);
                    if (type == 9 || type == 12) {
                        new Thread(new ZipSys(filename, null)).start();
                    } else if (type == 5) {
                        new Thread(new RarSys(filename, null)).start();
                    }

                } else {
                    if (!c.browser.selectmode) {
                        c.zipsy.jieya(c.browser.getString(c.browser.getSelectedIndex()));
                    } else {
                        int select[] = c.browser.getselected();
                        if (select == null) {
                            return;
                        }
                        String[] sename = new String[select.length];
                        for (int i = select.length - 1; i >= 0; i--) {
                            sename[i] = c.browser.getString(select[i]);
                        }
                        c.zipsy.jieya(sename);
                    }
                }
                break;
            case 4:
                new Change(new Calculate().f);
                break;
            case 5:
                if (e == null) {
                    e = new Rms(c.browser);
                }
                e.star();
                e.ru(c.browser.getString(c.browser.getSelectedIndex()));
                Showhelper.show = dis;

                break;
            case 6:
                if (c.browser.selectmode) {
                    c.browser.closeselectmode();
                } else {
                    if (!c.browser.title.equals(c.MainMenu) && !c.browser.title.equals(c.Root)) {
                        c.browser.initselectmode();
                    }
                }
                break;
            case 7:
                c.show.changeView();
                break;
        }

    }

    public void init() {
        if (state == 3) {
            lable[2][4] = "生txt " + (c.autotxt ? "是" : "否");
            lable[2][6] = "长亮 " + (Light.alwaysLight ? "是" : "否");
        }
        show = lable[state - 1];
        kd = Canvas.font.stringWidth(lable[state - 1][0] + "8. ") + 3;
        zhs = show.length;
        hs = 0;
    }

    void yasuo() {
        String head = "file:///" + c.currDirName;
        int len = c.currDirName.length() + 1;
        String[] files;
        if (!c.browser.selectmode) {
            files = new String[]{name};
        } else {
            int select[] = c.browser.getselected();
            if (select == null) {
                return;
            }
            files = new String[select.length];
            for (int i = select.length - 1; i >= 0; i--) {
                files[i] = c.browser.getString(select[i]);
            }
        }
        Vector fcs = new Vector();
        FileConnection a;
        int sumsize = 0;
        for (int i = files.length - 1; i >= 0; i--) {
            if ((a = FileSys.getfile(head + files[i])) == null) {
                return;
            }
            if (a.isDirectory()) {
                sumsize += add(a, fcs, head + files[i]);
            } else {
                try {
                    sumsize += a.fileSize();
                    fcs.addElement(a);
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    fcs.removeAllElements();
                    return;
                }
            }
        }
        if (sumsize <= 0) {
            return;
        }
        String outname = head + (files.length > 1 ? "FilesZip.zip" : (name.endsWith("/") ? name.substring(0, name.length() - 1) : name) + "zip.zip");
        OutputStream t = FileSys.getOutputStream(outname);
        if (t == null) {
            fcs.removeAllElements();
            return;
        }
        ZipOutputStream out = new ZipOutputStream(new BufDataOutputStream(t), 10240);
        out.setLevel(9);
        out.setMethod(8);
        Showhelper.jindu = new Gauge(head, sumsize, true);
        int size = fcs.size();

        byte[] buf = new byte[20480];
        InputStream in = null;
        String entryname = null;
        try {
            for (int i = 0; i < size; i++) {
                a = (FileConnection) fcs.elementAt(i);
                try {
                    int sum = (int) a.fileSize();
                    entryname = a.getPath().substring(len) + a.getName();
                    Showhelper.jindu.setMaxValue(sum);
                    Showhelper.jindu.setName(entryname);
                    in = a.openInputStream();
                    int l = 0;
                    out.putNextEntry(entryname);
                    while (sum > 0 && (l = in.read(buf)) > 0) {
                        out.write(buf, 0, l);
                        sum -= l;
                        Showhelper.jindu.showRemain(sum);
                    }
                    in.close();
                    out.closeEntry();
                } catch (Throwable ex) {
                    chen.c.show.showError(entryname + ex.toString());
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (Exception ex1) {
                    }
                }
            }
        } finally {
            try {
                out.finish();
                out.close();
                t.close();
            } catch (Exception ex) {
            }
        }
    }

    public Llcd(List his) {
        this.g = his.g;
        dis = his;
        state = 2;
        init();

    }

    void select1() {
        if (hs < 3) {
            e.ru("file:///" + c.currDirName + dis.getString(dis.getSelectedIndex()), hs);
        } else {
            e.chu("file:///" + c.currDirName + dis.getString(dis.getSelectedIndex()) + "rms.zip", hs);
        }

        state = 2;
        init();
    }

    void select2() {
        switch (hs) {
            case 0:
                ((c) dis.g).rename();
                break;
            case 1:
                if (dis.g.dodelete(dis.hs - 1)) {
                    dis.delete(dis.hs - 1);
                }

                break;
            case 2:
                dis.g.create();
                break;

            case 3:
                dis.g.showInfo(null);
                break;

            case 4:
                dis.showll = false;
                state = 3;
                init();
                break;

            case 5:
                dis.showll = false;
                state = 4;
                init();
                break;

            case 6:
                if (e == null) {
                    e = new Rms(dis);
                }

                e.star();
                break;

            case 7:
                dis.showll = false;
                state = 5;
                init();
                break;

            case 8:
                ((c) dis.g).destroyApp(false);
                break;

        }
    }

    void select3() {
        switch (hs) {
            case 0:
                ((c) g).setname();
                break;

            case 1:
                if (cset == null) {
                    cset = new ColorSet(dis);
                }

                Showhelper.show = cset;
                break;

            case 2:
                Showhelper.show = new FontSet(dis);
                break;

            case 3:
                ((c) g).setpinmu();
                break;

            case 4:
                c.autotxt = !c.autotxt;
                init();
                dis.showll = false;
                hs = 4;
                break;
            case 6:
                Light.alwaysLight = !Light.alwaysLight;
                init();
                dis.showll = false;
                hs = 6;
                break;

            case 5:
                ((c) g).setback();
                break;
            case 7:
                new MemorySet();
                break;

        }


    }

    public void select() {
        switch (state) {
            case 1:
                select1();
                break;
            case 2:
                select2();
                break;
            case 3:
                select3();
                if (hs != 4 && hs != 6) {
                    state = 2;
                    init();
                }
                break;
            case 4:
                select4();
                state = 2;
                init();
                break;
            case 5:
                select5();
                state = 2;
                init();
                break;
        }
    }

    public void run() {
        yasuo();
        name = null;
        Showhelper.jindu.close();
        c.show.repaint();
    }

    private int add(FileConnection a, Vector fcs, String head) {
        int sum = 0;
        FileConnection c;
        try {
            Enumeration enm = a.list("*", true);
            String f;
            while (enm.hasMoreElements()) {
                f = head + enm.nextElement();
                c = FileSys.getfile(f);
                if (c.isDirectory()) {
                    sum += add(c, fcs, f);
                } else {
                    sum += c.fileSize();
                    fcs.addElement(c);
                }
            }

        } catch (Throwable ex) {
            chen.c.show.showError(head + ex.toString());
        }
        return sum;
    }
}
