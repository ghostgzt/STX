package gui;

import io.Txt;
import io.FileSys;
import io.ContDataInputStream;
import chen.c;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public abstract class Yasuo implements Runnable {

    public static Hashtable path = new Hashtable();
    protected Yasuo pre;
    protected String dir, f;
    boolean sort;
    protected c c;
    protected Hashtable hash, rar;
    byte[] b;
    FileNode root;
    protected String fileName;
    private String browserTitle;
    Vector jar;
    Vector jieyaV;
    boolean first;
    protected boolean sortEntry;
    int chose;
    boolean tooBig;
    protected ContDataInputStream rof;

    Yasuo(c v, String name) {
        dir = "";
        sort = true;
        first = true;
        if (v != null) {
            c = v;
            root = c.browser.getDir();
            c.browser.setDir(new FileNode(name));
            browserTitle = c.browser.getTitle();
            c.browser.hs = c.browser.dinwei = 1;
        }
        rar = new Hashtable();
    }

    public void jieya(String name) {
        jieya(new String[]{name});
    }

    public abstract int getSize(String name);

    public abstract void close();

    public abstract byte[] getdata(String name);

    public void back() {
        if (dir.equals("")) {
            if (c != null) {
                c.browser.setDir(root);
                c.browser.setTitle(browserTitle);
            }
            b = null;
            rar.clear();
            rar = null;
            hash.clear();
            hash = null;
            jar.removeAllElements();
            jar = null;
            close();
            if (pre != null) {
                c.zipsy = pre;
            } else {
                c.zip = false;
            }
        } else {
            dir = dir.substring(0, dir.lastIndexOf('/', dir.length() - 2) + 1);
            fresh();
        }
    }

    public void fresh() {
        opendir("");
    }

    public void opendb(String name) {
        Hex h = new Hex("file:///" + c.currDirName + name, c.browser, false);
        h.setbytes(getdata(name), 0, 0);
        Showhelper.show = h;
    }

    public static void makedir(String s, String n) {
        int k;
        StringBuffer t = new StringBuffer();
        while ((k = n.indexOf("/")) >= 0) {
            t.append(n.substring(0, k + 1));
            if (!Yasuo.path.containsKey(t.toString())) {
                Yasuo.path.put(t.toString(), "/");
                FileSys.makedir(s + t.toString());
            }
            n = n.substring(k + 1);
        }
        t = null;
    }

    public void open(String name) {
        if (name.endsWith("/")) {
            opendir(name);
        } else {
            byte[] tb = getdata(name);
            if (tb == null) {
                return;
            }
            int p = c.guesstypeyasuo(name);
            switch (p) {
                case 108324: //mpg
                case 111207:  //ppg
                case 102340: //gif
                case 111145: // png
                case 105441: // jpg
                    new Img(tb, name, 1).init();
                    break;
                case 104987: // jar
                case 120609:  // zip
                    new Thread((ZipSys) (c.zipsy = new ZipSys(this, name, tb))).start();
                    break;
                case 112675:  //rar
                    new Thread((RarSys) (c.zipsy = new RarSys(this, name, tb))).start();
                    break;
                case 108272:  // mp3
                case 108104: // mid
                case 96323: // aac
                case 3124:   // au
                case 117484:// wav
                case 105577:// jts
                case 117835:    //wma
                    new Thread(new Player(tb, name, p, c.browser)).start();
                    break;
                case 104973: // jad
                case 3481: // mf
                case 115312: // txt
                case 3254818://java
                case 104:  //h
                case 99: //c
                case 98723:    //cpp
                    new Txt(tb, name);
                    break;
                case 94742904:// class:

                default:
                    Hex h = new Hex("file:///" + c.currDirName + name, c.browser, false);
                    h.setbytes(tb, 0, 0);
                    Showhelper.show = h;
            }
        }
    }

    public void run() {
        if (jieyaV == null) {
            sortEntry = false;
            jieya(jar);
            if (c == null) {
                back();
            }
        } else {
            sortEntry = true;
            jieya(jieyaV);
            jieyaV.removeAllElements();
            jieyaV = null;
        }
    }

    Enumeration list(String dir, String name) {
        dir = dir + name;
        Hashtable t = rar;
        int off = 0;
        int i = 0;
        int len = dir.length();
        while (++i <= len) {
            if (dir.charAt(i - 1) != '/') {
                continue;
            }
            t = (Hashtable) t.get(dir.substring(off, i));
            off = i;
        }
        return t.keys();
    }

    private void opendir(String name) {
        Enumeration e = list(dir, name);
        dir = dir + name;
        c.browser.deleteAll(false);
        c.browser.hs = c.browser.dinwei = 1;
        c.browser.append("上一级", 2);
        c.browser.setTitle(dir.length() > 0 ? dir : fileName);
        //  c.add(e, sort);
        c.addyasuo(e, sort);
    }

    void add(Enumeration e, String dir) {
        String s;
        while (e.hasMoreElements()) {
            s = (String) e.nextElement();
            if (s.endsWith("/")) {
                add(list(dir, s), dir + s);
            } else {
                jieyaV.addElement(hash.get(dir + s));
            }
        }
    }

    protected abstract void jieya(Vector jar);

    public void jieya(String[] s) {
        if (s == null) {
            return;
        }
        jieyaV = new Vector();
        for (int i = s.length - 1; i >= 0; i--) {
            if (s[i].endsWith("/")) {
                add(list(dir, s[i]), dir + s[i]);
            } else {
                jieyaV.addElement(hash.get(dir + s[i]));
            }
        }
        new Thread(this).start();
    }

    protected void readEntrys(Vector entrys) throws IOException {
        int len = entrys.size();
        Entry fh;
        for (int i = 0; i < len; i++) {
            fh = (Entry) entrys.elementAt(i);
            if (!fh.isDirectory()) {
                hash.put(fh.name, fh);
                c.sysname(fh.name, rar);
            }
        }
    }

    void sort(Entry[] entrys, int low, int high) {
        if (high - low < 1) {
            return;
        }
        entrys[chose] = entrys[low];
        int l = low;
        int h = high;
        int key = entrys[low].offset;
        while (low < high) {
            while (low < high && entrys[high].offset >= key) {
                high--;
            }
            entrys[low] = entrys[high];
            while (low < high && entrys[low].offset <= key) {
                low++;
            }
            entrys[high] = entrys[low];
        }
        entrys[low] = entrys[chose];
        sort(entrys, l, low);
        sort(entrys, low + 1, h);
    }

    public void showproperty(String name) {
        Entry et = (Entry) hash.get(dir + name);
        if (et != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("位置: " + dir);
            sb.append("\n文件大小: ");
            sb.append(c.showSize(et.size));
            sb.append("\n压缩后大小: ");
            sb.append(c.showSize(et.compressedSize));
            sb.append("\nCRC32: 0x" + Integer.toHexString(et.crc));
            sb.append("\n偏移量：" + c.showSize(et.offset));
            sb.append(c.getTime(et.dostime));
            c.show.show("信息: " + name, sb.toString());
        }
    }

    public ContDataInputStream getInputstream(int startPos) {
        try {
            if (rof != null && !rof.resetto(startPos)) {
                rof.close();
                rof = null;
            }
        } catch (Throwable ex) {
            rof = null;
        }
        if (rof != null) {
            return rof;
        }
        if (!tooBig) {
            rof = new ContDataInputStream(new ByteArrayInputStream(b, startPos, b.length - startPos));
            rof.setOffset(startPos);
        } else {
            try {
                rof = new ContDataInputStream(FileSys.getinputstream(f));
                rof.skip(startPos);
            } catch (Exception ex) {
             }
        }
        return rof;
    }
}
