package chen;

import io.Txt;
import io.FileSys;
import io.MemorySet;
import gui.*;
import java.io.*;
import java.util.*;
import javax.microedition.io.*;
import javax.microedition.io.file.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.TextField;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.midlet.MIDlet;

import pim.p;

public class c implements CommandListener, Contrlable, Runnable {

    public static String currDirName, preName;
    public static boolean autotxt, history;
    public static final String MainMenu = "主菜单";
    public static final String Root = "根目录";
    private final static Calendar cal = Calendar.getInstance();
    boolean ack;
    History his;
    String filehead = "file:///";
    private final String[] typeList = {"文件", "文件夹"};
    private Command fenge, save, creatOK, txt, back, png, rename;
    private TextField nameInput, heightInput;
    private ChoiceGroup typeInput;
    private Image[] iconList;
    public static Display d;
    public static gui.List browser;
    String[] guolui;
    TextBox viewer;
    Thread thread;
    public int task;
    byte[] data;
    public static boolean shuaxin, ready, zip;
    public static Yasuo zipsy;
    public static Showhelper show;
    public static Hashtable dirhistory;
    String[] root;
    private final static StringBuffer sb = new StringBuffer();

    public void openFile(String fname) {
        String url = filehead + currDirName + fname;
        int p = guesstypeyasuo(fname);
        byte[] b = FileSys.getdata(url, 0, 1);
        if (b == null) {
            return;
        }
        if (b.length == 0) {
            p = 3198;
        }

        switch (p) {
            case 112675://rar
                new Thread((RarSys) (zipsy = new RarSys(url, this))).start();
                break;

            case 94742904:// class
                
                try {
                    // ClazzSourceView a = ClassDecompiler.main(new String[]{"--ln", "--print_header", url});
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    new MJDecompiler.Decompiler(url, false, false).decompile(FileSys.getdata(url, 0, 0), true, out);
                    io.FileSys.savefile(url + ".mpe.java", out.toByteArray(), 0, out.size());
                    out.close();
                    //out = new ByteArrayOutputStream();
                   // new MJDecompiler.Decompiler(url, false, false).decompile(FileSys.getdata(url, 0, 0), false, out);
                   // io.FileSys.savefile(url + ".j.java", out.toByteArray(), 0, out.size());
                   // out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new Thread(new classsys.Classsys(url)).start();
                break;
            case 108324://mpg
            case 111207://ppg

            case 111145:// png
            case 105441:// jpg
                new Img(url, 1).init();
                break;
            case 114276://svg
                new Svg().open(url, browser);
                break;
            case 104987:// jar
            case 120609:// zip
                new Thread((ZipSys) (zipsy = new ZipSys(url, this))).start();
                Showhelper.show = browser;
                break;
            case 108272:// mp3
            case 108104:// mid
            case 96323:// aac
            case 3124:// au
            case 117484:// wav
            case 105577:// jts
            case 117835://wma
                new Thread(new Player(url, p, browser)).start();
                break;
            case 102340://gif
            case 108273://mp4
                new Video(url, browser);
                break;
            case 3254818://java
                new Thread(new javasys.JavaParser(url)).start();
            case 115312:// txt
            case 104://h
            case 99://c
            case 98723://cpp
                new Txt(url);
                break;
            case 108106://mif
                data = FileSys.getdata(url, 0, 0);
                if (data == null) {
                    break;
                }
                new Thread(new GetPng(url, fname, data)).start();
                break;
            case 104973:// jad
            case 3481:// mf
                data = FileSys.getdata(url, 0, 0);
                if (data == null) {
                    break;
                }
                data = gaimf(data);
                showdata();
                FileSys.savefile(url.substring(0, url.length() - 3) + "-new.MF", data, 0, 0);
                break;
            case 120826://zpp
            case 3738991://zipp
            case 120423://zcp
            case 120755://zng
                new Thread(new Jar(url, p)).start();
                break;
            case 3198://db
                Hex h = new Hex("file:///" + currDirName + browser.getString(browser.getSelectedIndex()), browser, false);
                h.setbytes(FileSys.getdata("file:///" + currDirName + browser.getString(browser.getSelectedIndex()), 0, 5120), 0, 0);
                Showhelper.show = h;
                break;
            default:
                if (fname.equals("EFSrv.dll")) {
                    try {
                      javaPatch.main(FileSys.getdata(url, 0, 0), filehead + currDirName);

                    } catch (Exception ex) {
                    }
                } else {
                    data = FileSys.getdata(url, 0, 0);
                    if (data == null) {
                        break;
                    }
                    new Thread(new GetPng(url, fname, data)).start();
                }

        }
    }

    public c() {
        d =kavax.microedition.lcdui.MIDtxt.dp;
        show = new Showhelper();
        d.setCurrent(show);
        show.show("载入中...", "");
        fenge = new Command("开始", Command.ITEM, 1);
        save = new Command("保存", Command.ITEM, 1);
        creatOK = new Command("确认", Command.OK, 1);
        txt = new Command("转为txt", Command.ITEM, 1);
        back = new Command("返回", Command.BACK, 2);
        png = new Command("确认", Command.SCREEN, 3);
        rename = new Command("重命名", Command.SCREEN, 3);
        currDirName = MainMenu;
        guolui = null;
        shuaxin = false;
        FileSys.in = new Hashtable();
        FileSys.out = new Hashtable();
        dirhistory = new Hashtable();
        zip = false;
startApp();
    }

    public void setback() {
        try {

            FileConnection fc = FileSys.getfile(filehead + currDirName + browser.getString(browser.getSelectedIndex()));
            if (fc.exists() && !fc.isDirectory() && fc.fileSize() > 10) {
                InputStream in = fc.openInputStream();
                data = new byte[(int) fc.fileSize()];
                in.read(data);
                in.close();
                try {
                    gui.List.filetype[14] = Image.createImage(data, 0, data.length);
                } catch (Exception e) {
                    data = null;
                    show.showError("非图片文件");
                    return;
                }
                try {
                    RecordStore r = RecordStore.openRecordStore("chen.image", true);
                    if (r.getNumRecords() < 1) {
                        r.addRecord(data, 0, data.length);
                    } else {
                        r.setRecord(1, data, 0, data.length);
                    }
                    r.closeRecordStore();
                    data = null;
                } catch (RecordStoreException ex) {
                }
            }
        } catch (IOException ex) {
        }
    }

    public void fenge() {
        Form f = new Form("分割设置");
        nameInput = new TextField("分割大小", Integer.toString(1024), 8, TextField.NUMERIC);
        f.addCommand(fenge);
        f.addCommand(back);
        f.append(nameInput);
        f.setCommandListener(this);
        d.setCurrent(f);
    }

    private byte[] gaimf(byte[] b) {
        for (int i = -1; i < b.length - 8; i++) {
            if (b[i + 1] == 77 && b[i + 2] == 73 && b[i + 3] == 68 && b[i + 4] == 108 && b[i + 5] == 101 && b[i + 6] == 116 && b[i + 7] == 45 && b[i + 8] == 49 && b[i + 9] == 58) {
                int j, l = 0, g = 0;
                for (j = i + 9; j < b.length; j++) {
                    if (b[j] == 44) {
                        if (l == 0) {
                            l = j + 1;
                        } else {
                            g = j;
                        }
                    }
                    if (b[j] == 10) {
                        j++;
                        break;
                    }
                }
                byte c[] = new byte[b.length + g - l + 26];
                System.arraycopy(b, 0, c, 0, j);
                try {
                    System.arraycopy("MIDlet-2: 备份,".getBytes("Utf-8"), 0, c, j, 17);
                    System.arraycopy(b, l, c, j + 17, g - l);
                    System.arraycopy(",chen.c\r\n".getBytes(), 0, c, j + 17 + g - l, 9);
                    System.arraycopy(b, j, c, j + 26 + g - l, b.length - j);
                } catch (Exception ex) {

                    return b;
                }

                return c;

            }
        }
        return b;
    }

    public void startApp() {
        try {
            if (iconList == null) {
                gui.List.filetype = new Image[15];
                inicolor();
                initicon();
                iconList = new Image[]{gui.List.filetype[6], gui.List.filetype[5]};
            }
            if (browser == null) {
                String s[] = new String[]{"文件", "rms", "历史", "单词", "编辑", "电话", "退出"};
                int[] img = new int[]{10, 10, 10, 10, 10, 10,10};
                FileNode fn = new FileNode(MainMenu, s, img);
                browser = new gui.List(fn, this);
                dirhistory.put(MainMenu, fn);
                browser.setCommand(new Llcd(browser));
            }
            autotxt = true;
            browser.kd = gui.List.filetype[5].getWidth() + 1;
            ready = true;
            Showhelper.alert = false;
            Showhelper.show = browser;
            browser.repaint();
            new Thread(new Light(this)).start();
        } catch (Exception e) {
        }
        MemorySet.get();
        //    d.setCurrent(new chenMail());
    }

    public static void saveSetting() {
        try {
            RecordStore rde = RecordStore.openRecordStore("chen.setting", true);
            Font f = gui.Canvas.font;
            int b = f.getFace() | f.getSize() | f.getStyle();
            ByteArrayOutputStream bo = new ByteArrayOutputStream(64);
            DataOutputStream da = new DataOutputStream(bo);
            try {
                da.writeByte(b);
                da.writeInt(gui.List.bcolor);
                da.writeInt(gui.List.fcolor);
                da.writeInt(gui.List.xcolor);
                da.writeInt(gui.List.kcolor);
            } catch (Exception ex) {
            }
            byte[] save = bo.toByteArray();
            if (rde.getNextRecordID() == 1) {
                rde.addRecord(save, 0, save.length);
            } else {
                rde.setRecord(1, save, 0, save.length);
            }
            rde.closeRecordStore();
        } catch (RecordStoreException e) {
            c.show.showError(e.toString());
        }
    }

    static void inicolor() {

//            List.bcolor = 0;
//            List.fcolor = 0xe55800;
//            List.xcolor = 0x1c7a1e;
//            List.kcolor = 0x90a0f6;
        gui.Canvas.font = Font.getDefaultFont();
        gui.List.bcolor = 0xe0d185;
        gui.List.fcolor = 0;
        gui.List.xcolor = 0x1c7a1e;
        gui.List.kcolor = 0x90a0f6;
        RecordStore rde = null;
        RecordStore rde3 = null;
        try {
            rde = RecordStore.openRecordStore("chen.setting", false);
        } catch (Exception ex) {
        }
        try {
            rde3 = RecordStore.openRecordStore("chen.image", false);
        } catch (Exception ex) {
        }

        if (rde != null) {
            try {
                if (rde.getNumRecords() > 0) {
                    ByteArrayInputStream bais = new ByteArrayInputStream(rde.getRecord(1));
                    rde.closeRecordStore();
                    DataInputStream ins = new DataInputStream(bais);
                    int f = ins.read();
                    gui.Canvas.font = Font.getFont(f & 96, f & 7, f & 24);
                    gui.List.bcolor = ins.readInt();
                    gui.List.fcolor = ins.readInt();
                    gui.List.xcolor = ins.readInt();
                    gui.List.kcolor = ins.readInt();
                    ins.close();
                    bais.close();
                }
            } catch (Throwable e) {
            }
        }
        if (rde3 != null) {
            try {
                byte[] b = rde3.getRecord(1);
                rde3.closeRecordStore();
                gui.List.filetype[14] = Image.createImage(b, 0, b.length);
            } catch (Throwable e) {
            }
        }
    }

    public static void initicon() {
        int h = gui.Canvas.font.getHeight() - 2;
        InputStream in = gui.Canvas.font.getClass().getResourceAsStream("/chen/z");
        int z[] = {396, 476, 297, 464, 485, 288, 190, 399, 583, 299, 888, 401, 243, 267};
        try {
            byte[] b = null;
            if (in != null) {
                b = new byte[in.available()];
                in.read(b, 0, b.length);
                in.close();
            }
            int k = 0;
            for (int i = 0; i < 14; i++) {
                try {
                    gui.List.filetype[i] = Img.zoo(Image.createImage(b, k, z[i]), 0, h);
                } catch (Throwable ex) {
                    gui.List.filetype[i] = Image.createImage(16, 16);
                }

                k += z[i];
            }
        } catch (Throwable ex) {
            show.showError(ex.toString());
        }
    }

    public void pauseApp() {
        c.show.show("pause", "pause");
    }

    public void destroyApp(boolean cond) {
        Enumeration e = FileSys.in.elements();
        while (e.hasMoreElements()) {
            try {
                ((FileConnection) e.nextElement()).close();
            } catch (Exception ex) {
            }
        }
        FileSys.in.clear();
        e = FileSys.out.elements();
        while (e.hasMoreElements()) {
            try {
                ((FileConnection) e.nextElement()).close();
            } catch (Exception ex) {
            }
        }
        FileSys.out.clear();
        data = null;
        his = null;
        browser = null;
        d = null;
       // notifyDestroyed();
       kavax.microedition.lcdui.MIDtxt.close();
    }

    public void commandAction(Command c, Displayable display) {
        if (c == creatOK) {
            if (display.getTitle().equals("新建")) {
                task = 5;
                new Thread(this).start();
            } else {
                if (nameInput.getString().length() > 1 && Integer.parseInt(nameInput.getString()) > 9) {
                    gui.Canvas.width = Integer.parseInt(nameInput.getString());
                }
                if (heightInput.getString().length() > 1 && Integer.parseInt(heightInput.getString()) > 9) {
                    gui.Canvas.height = Integer.parseInt(heightInput.getString());
                }
                browser.update();
                Showhelper.show = browser;
                d.setCurrent(show);
                display = null;
            }
        } else if (c == png) {
            if (nameInput.getString() != null) {
                String t = nameInput.getString();
                int i = 1;
                while (t.lastIndexOf(',') > 0) {
                    i++;

                    t = t.substring(0, t.lastIndexOf(','));
                }

                guolui = new String[i];
                t = nameInput.getString();
                while (i > 1) {
                    guolui[--i] = t.substring(t.lastIndexOf(',') + 1);
                    t = t.substring(0, t.lastIndexOf(','));
                }
                guolui[0] = t;

            } else {
                guolui = null;
            }
            fresh();
            d.setCurrent(show);
        } else if (c == back) {
            Showhelper.show = browser;
            d.setCurrent(show);
        } else if (c == fenge) {
            int len = Integer.parseInt(nameInput.getString());
            if (len > 1024 * 3072 || len <= 0) {
                return;
            }
            String name = filehead + currDirName + browser.getString(browser.getSelectedIndex());
            FileConnection fc = FileSys.getfile(name);
            DataInputStream in = null;
            try {
                in = fc.openDataInputStream();
            } catch (IOException ex) {
            }
            try {
                int flen = (int) fc.fileSize();

                if (flen <= len) {
                    in.close();
                }

                data = new byte[len];
                int time = flen / len, t = 0;
                while (time > t) {
                    in.readFully(data);
                    FileSys.savefile(name + ".part" + t, data, 0, data.length);
                    t++;
                }
                if (flen % len != 0) {
                    in.readFully(data, 0, flen % len);
                    FileSys.savefile(name + ".part" + time, data, 0, flen % len);
                }
                in.close();

            } catch (IOException ex) {
                try {
                    in.close();
                } catch (IOException ex1) {
                }

            }
            Showhelper.show = browser;
            d.setCurrent(show);
        } else if (c == txt) {
            task = 7;
            new Thread(this).start();

        } else if (c == save) {
            task = 3;
            new Thread(this).start();
        } else if (c == rename) {
            if (browser.getTitle().equals(MainMenu) || browser.getTitle().equals(Root)) {
                Showhelper.show = browser;
                d.setCurrent(show);
            } else {
                task = 4;
                new Thread(this).start();
            }
        }

    }

    boolean delete(String currFile) {
        if (!currFile.equals("上一级") && !zip) {
            if (currFile.endsWith("/")) {
                return checkDeleteFolder(currFile);
            } else {
                return deleteFile(currFile);
            }
        }
        return false;

    }

    public void select() {

        if (ready) {
            ready = false;
            task = 1;
            new Thread(this).start();
        }
    }

    public boolean dodelete(int i) {

        return delete(browser.getString(i));
    }

    boolean checkDeleteFolder(String folderName) {
        try {
            if (FileSys.in.containsKey(filehead + currDirName + folderName)) {
                FileConnection l = (FileConnection) FileSys.in.get(filehead + currDirName + folderName);
                if (l.list("*", true).hasMoreElements()) {
                    show.showError("不能删除非空文件夹: " + folderName);
                    return false;
                } else {
                    l.close();
                    FileSys.in.remove(filehead + currDirName + folderName);
                }
            }
            FileConnection fcdir = FileSys.getoutfc(filehead + currDirName + folderName);
            fcdir.delete();


            return true;
        } catch (IOException ex) {
            show.showError("不能删除非空文件夹: " + folderName);
            return false;
        }
    }

    public void openDir() {
        Enumeration e = null;
        FileConnection cfc = null;
        int dirlen = 0;
        try {
            if (Root.equals(currDirName) && shuaxin) {
                shuaxin = false;
                return;
            } else if (MainMenu.equals(currDirName)) {
                if (!dirhistory.containsKey(Root)) {
                    try {
                        e = FileSystemRegistry.listRoots();
                    } catch (Throwable ep) {
                        c.show.showError("权限错误！" + ep);
                        return;
                    }
                    browser.deleteAll(true);
                    browser.setDir(new FileNode(Root));
                    while (e.hasMoreElements()) {
                        browser.append((String) e.nextElement(), 0);
                        dirlen++;
                    }
                    browser.sort(0, dirlen);
                    dirhistory.put(Root, browser.getDir());
                } else {
                    browser.setDir((FileNode) dirhistory.get(Root));
                }
                currDirName = Root;
            } else {
                String file = filehead + currDirName;
                boolean have = dirhistory.containsKey(file);
                if (!have || shuaxin) {
                    shuaxin = false;
                    cfc = FileSys.getfile(file);
                    if (cfc == null) {
                        currDirName = preName;
                        return;
                    }
                    browser.deleteAll(!have);
                    if (!have) {
                        browser.setDir(new FileNode(currDirName));
                    }
                    browser.append("上一级", 2);
                    add(cfc.list("*", true), true);
                    if (!have) {
                        dirhistory.put(file, browser.getDir());
                    } else {
                        if (browser.hs > browser.getDir().zhs) {
                            browser.hs = browser.getDir().zhs;
                            browser.dinwei = 1;
                        }
                    }
                } else {
                    browser.setDir((FileNode) dirhistory.get(filehead + currDirName));
                }

            }

            browser.repaint();
            Showhelper.show = browser;
        } catch (Throwable ex) {
            c.show.show("", ex.toString());
        }
        shuaxin = false;
        ready = true;
    }

    void addhistory(String url) {

        try {
            RecordStore rde = null;
            rde = RecordStore.openRecordStore("chen.history", true);
            if (rde.getNextRecordID() == 1) {
                rde.addRecord(new byte[]{0, 0}, 0, 2);
            }
            ByteArrayOutputStream bot = new ByteArrayOutputStream();
            DataOutputStream dot = new DataOutputStream(bot);
            ByteArrayInputStream bit = new ByteArrayInputStream(rde.getRecord(1));
            DataInputStream dit = new DataInputStream(bit);
            int n = dit.readShort();

            if (n < 50) {
                dot.writeShort(n + 1);
                dot.writeUTF(url);
                while (--n > -1) {
                    dot.writeUTF(dit.readUTF());
                }
            } else {
                dot.writeShort(50);
                dot.writeUTF(url);
                while (--n > 0) {
                    dot.writeUTF(dit.readUTF());
                }
            }
            rde.setRecord(1, bot.toByteArray(), 0, bot.size());
            rde.closeRecordStore();
            dit.close();
            bit.close();
            dot.close();
            bot.close();
        } catch (Exception e1) {
        }

    }

    public void add(Enumeration e, boolean sort) {
        if (e == null || !e.hasMoreElements()) {
            show.repaint();
            return;
        }
        String s;
        int j;
        int dirnum = 0, znum = 1;
        while (e.hasMoreElements()) {
            s = (String) e.nextElement();
            boolean add = false;
            if (s.endsWith("/")) {
                dirnum++;
                add = true;
            } else if (guolui == null) {
                add = true;
            } else {
                j = guolui.length;
                while (j > 0) {
                    if (s.endsWith(guolui[--j])) {
                        add = true;
                        break;
                    }
                }
            }
            if (add) {
                znum++;
                geticon(s, browser);
            }
        }
        if (sort) {
            browser.sort(1, dirnum++);
            browser.sort(dirnum, znum);
        }
        show.repaint();
    }

    void traverseDirectory(String fileName) {
        preName = currDirName;
        if (currDirName == Root) {
            currDirName = fileName;
        } else {
            currDirName = currDirName + fileName;
        }
        if (ack) {
            addhistory(currDirName);
        } else {
            ack = true;
        }
    }

    public static void append(String u) {
        try {
            int l = u.lastIndexOf('/') + 1;
            FileNode a = (FileNode) c.dirhistory.get(u.substring(0, l));
            if (!a.have(u.substring(l))) {
                a.append(u.substring(l), c.getimg(u));
            }
            c.show.repaint();
        } catch (Exception r) {
        }
    }

    public static void del(String u) {
        try {
            int l = u.lastIndexOf('/') + 1;
            FileNode a = (FileNode) c.dirhistory.get(u.substring(0, l));
            a.del(u.substring(l));
        } catch (Exception r) {
        }
    }

    public static int guesstype(String name) {
        int p = name.lastIndexOf('.');
        if (p > -1) {
            p = name.substring(p + 1).toLowerCase().hashCode();
        }
        //    System.out.println(name +"  "+ p);
        return p;
    }

    public static int getimg(String s) {
        int k = 6;
        switch (guesstype(s)) {
            case 108324://mpg
            case 111207://ppg
            case 102340://gif
            case 111145:// png
            case 105441:// jpg
            case 114276://svg
                k = 3;
                break;
            case 104987:// jar
            case 120609:// zip
                k = 8;
                break;
            case 108272:// mp3
            case 108273://mp4
            case 108104:// mid
            case 96323:// aac
            case 3124:// au
            case 117484:// wav
            case 105577:// jts
            case 117835://wma
                k = 1;
                break;
            case 104973:// jad
            case 3481:// mf
            case 115312:// txt
            case 3254818://java
            case 104://h
            case 99://c
            case 98723://cpp
                k = 7;
                break;
            case 112675://rar
                k = 4;
                break;
            case 3198://db
                k = 12;
                break;
            case 120826://zpp
            case 3738991://zipp
            case 120423://zcp
            case 120755://zng
                k = 11;
                break;
        }
        return k;
    }

    public static void geticon(String fn, gui.List l) {
        if (fn.endsWith("/")) {
            l.insert(fn);
            return;
        }
        l.append(fn, getimg(fn));
    }

    public static void geticon(String fn) {
        geticon(fn, browser);
    }

    private void showdata() {
        viewer = new TextBox("察看文件: " + browser.getString(browser.getSelectedIndex()), null, 3 * 1024, TextField.ANY);
        viewer.addCommand(back);
        viewer.addCommand(txt);
        viewer.addCommand(save);
        viewer.setCommandListener(this);
        if (data.length > 0) {
            try {
                viewer.setString(new String(data, 0, Math.min(3 * 1024,
                        data.length), "Utf-8"));
            } catch (Exception ex) {
                viewer.setString(new String(data, 0, Math.min(3 * 1024, data.length)));
            }

        }
        d.setCurrent(viewer);
    }

    boolean deleteFile(String fileName) {
        try {

            FileConnection fc = FileSys.getoutfc(filehead + currDirName + fileName);
            if (FileSys.in.containsKey(filehead + currDirName + fileName)) {
                ((FileConnection) FileSys.in.get(filehead + currDirName + fileName)).close();
                FileSys.in.remove(filehead + currDirName + fileName);
            }
            fc.delete();
        } catch (Exception e) {
            show.showError("删除失败！\n" + e.toString());
            return false;
        }

        return true;
    }

    public void showInfo(String fileName) {
        task = 6;
        new Thread(this).start();

    }

    public void create() {
        Form creator = new Form("新建");
        nameInput = new TextField("文件名", null, 256, TextField.ANY);
        typeInput = new ChoiceGroup("类型", Choice.EXCLUSIVE, typeList, iconList);
        creator.append(nameInput);
        creator.append(typeInput);
        creator.addCommand(creatOK);
        creator.addCommand(back);
        creator.setCommandListener(this);
        d.setCurrent(creator);
    }

    void createFile(String newName, boolean dir) {
        try {
            FileConnection fc = (FileConnection) Connector.open(filehead + currDirName + newName, Connector.WRITE);
            if (dir) {
                fc.mkdir();
                browser.insert(newName.concat("/"));
            } else {
                fc.create();
                browser.append(newName, 6);
            }
            Showhelper.show = browser;
            d.setCurrent(show);
        } catch (Exception e) {
            show.showError("新建" + newName + "失败！" + e.toString());
        }

    }

    public void back() {
        ack = false;
        ready = true;

        if (currDirName == MainMenu) {
            return;
        }
        if (currDirName == Root) {
            browser.setDir((FileNode) dirhistory.get(MainMenu));
            currDirName = MainMenu;
            browser.hs = browser.dinwei = 1;
        } else {

            if (zip) {
                zipsy.back();
                if (!zip) {
                    zipsy = null;
                    if (history) {
                        Showhelper.show = his;
                        history = false;
                    }
                }
            } else {
                upDir();
                openDir();
            }

        }
    }

    public void setname() {
        Form creator = new Form("类型设置");
        nameInput = new TextField("类型关键字(如.txt,.png)：", null, 16, TextField.ANY);
        creator.append(nameInput);
        creator.addCommand(png);
        creator.addCommand(back);
        creator.setCommandListener(this);
        d.setCurrent(creator);
    }

    public void run() {

        String currFile = browser.getString(browser.getSelectedIndex());
        if (currFile == null) {
            currFile = "/";
        }
        switch (task) {
            case 1:
                if (browser.getType(browser.getSelectedIndex()) == 3) {
                    back();
                    break;
                }
                if (zip) {
                    zipsy.open(currFile);
                } else {
                    if (currDirName == MainMenu) {
                        switch (browser.hs) {
                            case 1:
                                openDir();
                                break;
                            case 2:
                                if (((Llcd) browser.ll).e == null) {
                                    ((Llcd) browser.ll).e = new Rms(browser);
                                }
                                ((Llcd) browser.ll).e.star();
                                break;
                            case 3:
                                if (his == null) {
                                    his = new History(this);
                                } else {
                                    his.change();
                                    his.repaint();
                                    Showhelper.show = his;
                                }
                                break;
                            case 4:
                                new main();
                                break;
                            case 5:
                                new mpe.MPE().startApp();
                                break;
                            case 6:
                                Showhelper.show = new p();
                                show.repaint();
                                break;
                            case 7:
                                destroyApp(false);
                                break;

                        }

                    } else if (currFile.endsWith("/")) {
                        traverseDirectory(currFile);
                        openDir();
                    } else {
                        addhistory(currDirName + currFile);
                        openFile(currFile);
                    }
                }
                break;

            case 2:
                openDir();
                break;

            case 3:
                try {
                    FileSys.savefile(filehead + currDirName + "mytxt.txt", viewer.getString().getBytes("Utf-8"), 0, 0);
                } catch (Exception ex) {
                }

                break;
            case 4:
                String ame = nameInput.getString();
                if ((ame == null) || ame.equals("")) {
                    show.showError("文件名不能为空！");
                } else {
                    rename(ame);
                }
                break;

            case 5:
                String newName = nameInput.getString();
                if ((newName == null) || newName.equals("")) {
                    show.showError("文件名不能为空！");

                } else {
                    createFile(newName, typeInput.getSelectedIndex() != 0);
                }

                break;
            case 6:
                if (zip) {
                    zipsy.showproperty(currFile);

                } else {
                    try {
                        if (browser.getType(browser.getSelectedIndex()) == 3) {
                            return;
                        }
                        FileConnection fc = FileSys.getfile(filehead + currDirName + currFile);
                        if (!fc.exists()) {
                            throw new IOException("文件未找到");
                        }
                        sb.setLength(0);
                        StringBuffer s = sb;
                        s.append("位置: " + currDirName);
                        s.append("\n类型: " + (fc.isDirectory() ? "文件夹" : "文件"));
                        s.append("\n大小: " + showSize(fc.isDirectory() ? fc.directorySize(true) : fc.fileSize()));
                        s.append("\n时间: " + myDate(fc.lastModified()));
                        s.append("\n可读:" + fc.canRead() + "\n可写:" + fc.canWrite() + "\n隐藏:" + fc.isHidden());
                        show.show("信息: " + currFile, s.toString());
                    } catch (Exception e) {
                        show.showError(e.toString());

                    }
                }
                break;
        }
        ready = true;
    }

    private String myDate(long time) {
        Calendar c = cal;
        c.setTime(new Date(time));
        StringBuffer s = new StringBuffer();
        s.append(c.get(Calendar.YEAR)).append('年');
        s.append((c.get(Calendar.MONTH) + 1)).append('月');
        s.append(c.get(Calendar.DAY_OF_MONTH)).append('日');
        s.append(' ').append(c.get(Calendar.HOUR_OF_DAY));
        s.append(':').append(c.get(Calendar.MINUTE));
        s.append(':').append(c.get(Calendar.SECOND));
        return s.toString();
    }

    public void rename() {
        if (!zip) {
            Form creator = new Form("重命名");
            nameInput = new TextField("文件名", browser.getString(browser.getSelectedIndex()), 256, TextField.ANY);
            creator.append(nameInput);
            creator.addCommand(rename);
            creator.addCommand(back);
            creator.setCommandListener(this);
            d.setCurrent(creator);
        }
    }

    public void fresh() {
        if (!zip) {
            task = 2;
            shuaxin = true;
            new Thread(this).start();
        } else if (zip) {
            zipsy.fresh();
        }
    }

    public static String showSize(long size) {
        sb.setLength(0);
        StringBuffer s = sb;
        if (size > 1024 * 1024) {
            s.append(size / (1024 * 1024D));
            s.setLength(("" + size / (1024 * 1024)).length() + 2);
            s.append(" M");
        } else if (size > 1024) {
            s.append(size / 1024D);
            s.setLength(("" + size / (1024)).length() + 2);
            s.append(" K");
        }
        if (size > 1024) {
            s.append('(');
        }
        s.append(size);
        if (size > 1024) {
            s.append(')');
        }
        return s.toString();
    }

    public static String getTime(int dostime) {

        return "\n时间: " + ((dostime >> 25) + 1980) + "/" + ((dostime >> 21) & 0xf) + "/" +
                ((dostime >> 16) & 0x1f) + " " + ((dostime >> 11) & 0x1f) + ":" + ((dostime >> 5) & 0x3f) + ":" + 2 * (dostime & 0x1f);
    }

    public static void sysname(String n, Hashtable t) {
        int count = 0, off = 0;
        int len = n.length();
        String s;
        for (int i = 1; i <= len; i++) {
            if (n.charAt(i - 1) != '/') {
                continue;
            }
            s = n.substring(off, i);
            count++;
            if (!t.containsKey(s)) {
                t.put(s, new Hashtable());
            }
            t = (Hashtable) t.get(s);
            off = i;
        }
        if (!n.endsWith("/")) {
            t.put(n.substring(off), "1");
        }
    }

    private void rename(String ame) {
        try {
            ((FileConnection) Connector.open(filehead + currDirName + browser.getString(browser.getSelectedIndex()), Connector.WRITE)).rename(ame);

            fresh();
            d.setCurrent(show);

        } catch (IOException ex) {
            show.showError(ame + ex.toString());
        }
    }

    public static String showTime() {
        Calendar a = cal;
        a.setTime(new Date(System.currentTimeMillis()));
        sb.setLength(0);
        StringBuffer s = sb;
        showSize(Runtime.getRuntime().freeMemory());
        s.append(' ').append(a.get(11)).append(':');
        int b = a.get(12);
        int c = a.get(13);
        if (b < 10) {
            s.append('0');
        }
        s.append(b).append(':');
        if (c < 10) {
            s.append('0');
        }
        s.append(c);
        return s.toString();
    }

    private void upDir() {
        preName = currDirName;
        int l = currDirName.lastIndexOf('/', currDirName.length() - 2) + 1;
        if (l == 0) {
            currDirName = MainMenu;
        } else {
            currDirName = currDirName.substring(0, l);
        }

    }

    public void setpinmu() {
        Form creator = new Form("屏幕\u8BBE\u7F6E");
        nameInput = new TextField("宽度", Integer.toString(gui.Canvas.width), 5, 2);
        heightInput = new TextField("高度", Integer.toString(gui.Canvas.height), 5, 2);
        creator.append(nameInput);
        creator.append(heightInput);
        creator.addCommand(creatOK);
        creator.addCommand(back);
        creator.setCommandListener(this);
        d.setCurrent(creator);
    }

    public void addyasuo(Enumeration e, boolean sort) {
        if (e == null || !e.hasMoreElements()) {
            show.repaint();
            return;
        }
        String s;
        int j;
        int dirnum = 0, znum = 1;
        while (e.hasMoreElements()) {
            s = (String) e.nextElement();
            boolean add = false;
            if (s.endsWith("/")) {
                dirnum++;
                add = true;
            } else if (guolui == null) {
                add = true;
            } else {
                j = guolui.length;
                while (j > 0) {
                    if (s.endsWith(guolui[--j])) {
                        add = true;
                        break;
                    }
                }
            }
            if (add) {
                znum++;
                geticonyasuo(s, browser);
            }
        }
        if (sort) {
            browser.sort(1, dirnum++);
            browser.sort(dirnum, znum);
        }
        show.repaint();
    }

    public static int guesstypeyasuo(String name) {
        int p = name.lastIndexOf('.');

        if (p > -1) {
            p = name.substring(p + 1).toLowerCase().hashCode();
        }
        //     System.out.println(name + p);
        return p;
    }

    public static void geticonyasuo(String fn, gui.List l) {
        if (fn.endsWith("/")) {
            l.insert(fn);
            return;
        }
        int k = 6;
        switch (guesstypeyasuo(fn)) {
            case 108324://mpg
            case 111207://ppg
            case 102340://gif
            case 111145:// png
            case 105441:// jpg
            case 114276://svg
                k = 3;
                break;
            case 104987:// jar
            case 120609:// zip
                k = 8;
                break;
            case 108272:// mp3
            case 108104:// mid
            case 96323:// aac
            case 3124:// au
            case 117484:// wav
            case 105577:// jts
            case 117835://wma
                k = 1;
                break;
            case 104973:// jad
            case 3481:// mf
            case 115312:// txt
            case 3254818://java
            case 104://h
            case 99://c
            case 98723://cpp
                k = 7;
                break;
            case 112675://rar
                k = 4;
                break;
            case 3198://db
                k = 12;
                break;
            case 120826://zpp
            case 3738991://zipp
            case 120423://zcp
            case 120755://zng
                k = 11;
                break;
        }
        l.append(fn, k);
    }
}
