package gui;

import io.FileSys;
import io.ContDataInputStream;
import chen.c;
import javax.microedition.io.file.FileConnection;
import zip.ZipInputStream;
import zip.ZipOutputStream;
import zip.ZipEntry;
import classsys.Classsys;
import io.BufDataOutputStream;
import io.MemorySet;
import java.io.*;
import java.util.Vector;

public class Jar implements Runnable {

    String f;
    boolean hui, beifen;
    int type;
    static int size[] = {2193, 598, 10816, 12499, 7466, 611, 2848, 1496, 5392, 2777, 2132, 2901, 138, 3994, 1315, 701, 5429};

    public Jar(String file, int mod) {
        f = file;
        type = mod;
        beifen = (MemorySet.POJIE & 2) != 0;
    }

    void write(int size, InputStream in, String b, ZipOutputStream zop, int j) {
        try {
            Showhelper.jindu.show(b, j);
            byte[] d = new byte[size];
            in.read(d);
            zop.putNextEntry(b);
            zop.write(d, 0, d.length);
            zop.closeEntry();
            d = null;
        } catch (Exception ex) {
        }
    }

    private boolean pojie() {
        BufDataOutputStream o = null;
        try {
            String name = f.substring(0, f.lastIndexOf('.'));
            FileConnection a = FileSys.getoutfc(name + ".jardata");
            try {
                a.delete();
            } catch (Exception e) {
            }
            try {
                a.create();
                c.append(name + ".jardata");
            } catch (Exception e) {
                return false;
            }
            o = new io.BufDataOutputStream(a.openOutputStream());
            ZipOutputStream zop = new ZipOutputStream(o, 10240);
            zop.setLevel(9);
            zop.setMethod(8);
            int now = 0;
            boolean man = (MemorySet.POJIE & 1) != 0;
            if (man) {
                now = analyzeman(zop);
            } else {
                now = analyze(zop, o);
            }
            InputStream in = getClass().getResourceAsStream("/chen/c");
            write(size[0], in, "lavax/microedition/io/Connector.class", zop, now);
            now += man ? size[0] : 1;
            if (!beifen) {
                for (int i = 'a'; i <= 'p'; i++) {
                    write(size[i - 96], in, "chen/" + (char) i + ".class", zop, now);
                    now += man ? size[i - 96] : 1;
                }
                in.close();
                in = getClass().getResourceAsStream("/chen/z");
                write(4765, in, "chen/z", zop, now++);
                in.close();
                in = getClass().getResourceAsStream("/chen/h");
                write(817, in, "chen/h", zop, now++);
            }
            in.close();
            zop.finish();
            zop.close();
            try {
                a.rename(name.substring(name.lastIndexOf('/') + 1) + ".jar");
                c.append(name + ".jar");
                c.del(name + ".jardata");
            } catch (Exception e) {
            }
            zop = null;
            System.gc();
            return true;
        } catch (Exception ex) {
             if (o != null) {
                try {
                    o.close();
                } catch (IOException e) {
                }
            }
            return false;
        }

    }

    private byte[] addbeifen(byte[] b) {
        int max = b.length;
        for (int i = -1; i < max; i++) {
            if (b[i + 1] == 77 && b[i + 2] == 73 && b[i + 3] == 68 && b[i + 4] == 108 && b[i + 5] == 101 && b[i + 6] == 116 && b[i + 7] == 45 && b[i + 8] == 49 && b[i + 9] == 58) {
                int j, l = 0, g = 0;
                for (j = i + 9; j < max; j++) {
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
                hui = true;
                return c;

            }
        }
        return b;
    }

    private int analyze(ZipOutputStream zf, OutputStream out) throws IOException {
        ZipEntry z;
        byte[] e = null;
        ZipSys zy = new ZipSys(f, null);
        if (!zy.readfile()) {
            return 0;
        }
        zy.readhead();
        Vector en = zy.jar;
        int entrynum = en.size();
        Showhelper.jindu = new Gauge(f, beifen ? entrynum + 1 : entrynum + 18, false);

        int now = 0;
        ContDataInputStream in;
        byte[] temp = new byte[20480];
        try {
            while (now < entrynum) {
                hui = false;
                z = (ZipEntry) en.elementAt(now++);
                if (z.isDirectory()) {
                    continue;
                }
                String name = z.toString().toLowerCase();
                Showhelper.jindu.show(name, now);
                int offset = z.offset;
                if (name.endsWith(".class") || ((name.endsWith(".mf") && !beifen))) {
                    e = zy.getdata(z);
                    if (e != null) {
                        if (name.endsWith(".class")) {
                            changeMessage(e);
                        } else {
                            e = addbeifen(e);
                        }
                    }
                }
                if (hui) {
                    c.browser.append(name, 3);
                    zf.putNextEntry(z.toString());
                    zf.write(e, 0, e.length);
                    zf.closeEntry();
                } else {
                    e = null;
                    zf.putNextEntry(z);
                    zf.preEntry();
                    in = zy.getInputstream(offset + 26);
                    in.skip(in.readLshort() + in.readLshort());
                    int sum = z.compressedSize;
                    int len = 0;
                    while (sum > 0) {
                        len = Math.min(sum, 20480);
                        in.read(temp, 0, len);
                        out.write(temp, 0, len);
                        sum -= len;
                    }
                    out.flush();
                }

                z = null;
            }
            zy.close();
        } catch (Exception eu) {
            eu.printStackTrace();
        }
        return entrynum;
    }

    private int analyzeman(ZipOutputStream zf) throws IOException {
        FileConnection s = FileSys.getfile(f);
        if (s == null) {
            return 0;
        }
        ZipEntry z;
        byte[] e = null;
        int filesize = (int) s.fileSize();
        Showhelper.jindu = new Gauge(f, beifen ? filesize + 2193 : filesize + 65403, false);
        ContDataInputStream in = new ContDataInputStream(s.openInputStream());
        Showhelper.jindu.setIn(in);
        ZipInputStream zin = new ZipInputStream(in);
        try {
            while (true) {
                hui = false;
                z = zin.getNextEntry();
                if (z == null) {
                    break;
                }
                if (z.isDirectory()) {
                    continue;
                }
                String name = z.name.toLowerCase();
                Showhelper.jindu.show(name, in.getOffst());
                if ((e = getdata(zin, z)) == null) {
                    continue;
                }
                if (name.endsWith(".class") || ((name.endsWith(".mf") && !beifen))) {
                    if (name.endsWith(".class")) {
                        changeMessage(e);
                    } else {
                        e = addbeifen(e);
                    }
                }
                if (hui) {
                    c.browser.append(name, 3);
                }
                zf.putNextEntry(z.toString());
                zf.write(e, 0, e.length);
                zf.closeEntry();
                z = null;
            }
            in.close();
        } catch (Exception eu) {
            eu.printStackTrace();
        }
        return filesize;
    }

    private void changeMessage(byte[] b) {
        int l = b.length - 31;
        for (int i = 0; i < l; i++) {
            if (b[i] != 'j' || b[i + 1] != 'a' || b[i + 2] != 'v' || b[i + 3] != 'a' || b[i + 4] != 'x' ||
                    b[i + 5] != '/' || b[i + 6] != 'm' || b[i + 7] != 'i' || b[i + 8] != 'c' || b[i + 9] != 'r' ||
                    b[i + 10] != 'o' || b[i + 11] != 'e' || b[i + 12] != 'd' || b[i + 13] != 'i' || b[i + 14] != 't' ||
                    b[i + 15] != 'i' || b[i + 16] != 'o' || b[i + 17] != 'n' || b[i + 18] != '/' || b[i + 19] != 'i' ||
                    b[i + 20] != 'o' || b[i + 21] != '/' || b[i + 22] != 'C' || b[i + 23] != 'o' || b[i + 24] != 'n' ||
                    b[i + 25] != 'n' || b[i + 26] != 'e' || b[i + 27] != 'c' || b[i + 28] != 't' || b[i + 29] != 'o' ||
                    b[i + 30] != 'r') {
                continue;
            }
            hui = true;
            b[i] = 'l';
            i += 30;
        }
    }

    public void run() {
        long time = System.currentTimeMillis();
        boolean sus = false;
        try {
            switch (type) {
                case 120826://zpp
                    sus = gettxt();
                    break;
                case 3738991://zipp
                    sus = pojie();
                    break;
                case 120423://zcp 反编译
                case 120424://zcp 汇编译
                case 120425://zcp 反汇编译
                    sus = getclass(type - 120423);
                    break;
                case 120755://zng
                    sus = getpng();
                    break;

            }
        } catch (Throwable t) {
        }
        Showhelper.jindu.close();
        c.show.show(sus ? "成功" : "失败", "文件" + f + (System.currentTimeMillis() - time) + "处理完毕！");
    }

    private boolean gettxt() {
        try {
            int max = (int) FileSys.getfile(f).fileSize();
            Showhelper.jindu = new Gauge(f, max, false);
        } catch (Exception ex) {
            return false;
        }
        ContDataInputStream t = new ContDataInputStream(FileSys.getinputstream(f));
        Showhelper.jindu.setIn(t);
        OutputStream o = FileSys.getOutputStream(f + ".txt.zip");
        ZipOutputStream zop = new ZipOutputStream(new io.BufDataOutputStream(o), 10240);
        zop.setLevel(9);
        zop.setMethod(8);
        ZipEntry z = null;
        ZipInputStream zin = new ZipInputStream(t);
        byte[] e = null;
        while (true) {
            try {
                z = zin.getNextEntry();
            } catch (IOException ex) {
            }
            if (z == null) {
                break;
            }
            String name = z.toString();
            Showhelper.jindu.show(name, t.getOffst());
            if (z.isDirectory()) {
                continue;
            }
            e = getdata(zin, z);
            if (e == null && e.length <= 0) {
                continue;
            }
            int i = 0;
            while (i < e.length) {
                if (e[i] == 0) {
                    e[i] = 0x20;
                }
                i++;
            }
            try {
                zop.putNextEntry(name + ".txt");
                zop.write(e, 0, e.length);
                zop.closeEntry();
            } catch (Exception ex) {
            }
        }
        try {
            zop.finish();
            zop.close();
            zin.close();
            t.close();
            o.close();
        } catch (Exception ex) {
        }
        return true;
    }

    private static byte[] getdata(ZipInputStream zin, ZipEntry z) {
        byte[] e;
        if ((z.flag & 8) == 0) {
            int len = z.size;
            if (len > MemorySet.MAXREAD || len < 2) {
                return null;
            }
            e = new byte[len];
            try {
                while (len > 0) {
                    len -= zin.read(e, e.length - len, len);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            byte[] temp = new byte[10240];
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len;
            try {
                while ((len = zin.read(temp, 0, 10240)) > 0) {
                    out.write(temp, 0, len);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
            e = out.toByteArray();
            out.reset();
        }
        return e;
    }

    private boolean getclass(int mode) {
        try {
            int max = (int) FileSys.getfile(f).fileSize();
            Showhelper.jindu = new Gauge(f, max, false);
        } catch (Exception ex) {
            return false;
        }
        ContDataInputStream t = new ContDataInputStream(FileSys.getinputstream(f));
        Showhelper.jindu.setIn(t);
        String h;
        if (mode == 0) {
            h = ".java";
        } else if (mode == 1) {
            h = ".j";
        } else {
            h = ".all";
        }
        OutputStream o = FileSys.getOutputStream(f + h + ".zip");
        ZipOutputStream zop = new ZipOutputStream(new io.BufDataOutputStream(o), 10240);
        zop.setLevel(9);
        zop.setMethod(8);
        ZipEntry z = null;
        ZipInputStream zin = new ZipInputStream(t);
        byte[] e = null;
        String name;
        Classsys cs = new Classsys(new DataOutputStream(zop));
        while (true) {
            try {
                z = zin.getNextEntry();
            } catch (IOException ex) {
            }
            if (z == null) {
                break;
            }
            name = z.toString();
            Showhelper.jindu.show(name, t.getOffst());
            if (!name.endsWith("class")) {
                continue;
            }
            e = getdata(zin, z);
            if (e == null && e.length < 10) {
                continue;
            }
            Classsys.b = e;
            try {
                zop.putNextEntry(name + ".txt");
                cs.readclass(mode);
                zop.closeEntry();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        Classsys.b = null;
        cs = null;
        try {
            zop.finish();
            zop.close();
            zin.close();
            t.close();
            o.close();
        } catch (Exception ex) {
        }
        return true;
    }

    private boolean getpng() {
        try {
            int max = (int) FileSys.getfile(f).fileSize();
            Showhelper.jindu = new Gauge(f, max, false);
        } catch (Exception ex) {
            return false;
        }
        ContDataInputStream t = new ContDataInputStream(FileSys.getinputstream(f));
        Showhelper.jindu.setIn(t);
        OutputStream o = FileSys.getOutputStream(f + ".png.zip");
        ZipOutputStream zop = new ZipOutputStream(new io.BufDataOutputStream(o), 10240);
        zop.setLevel(0);
        zop.setMethod(0);
        ZipEntry z = null;
        ZipInputStream zin = new ZipInputStream(t);
        byte[] e = null;
        int k, l;
        String name;
        while (true) {
            try {
                z = zin.getNextEntry();
            } catch (IOException ex) {
            }
            if (z == null) {
                break;
            }
            if (z.isDirectory()) {
                continue;
            }
            name = z.name;
            Showhelper.jindu.show(name, t.getOffst());
            if (name.endsWith(".class")) {
                continue;
            }
            e = getdata(zin, z);
            if (e == null && e.length < 10) {
                continue;
            }
            l = e.length;
            k = l - 8;
            for (int i = 0; i < k; i++) {
                if (e[i] != -119 || e[i + 1] != 80 || e[i + 2] != 78 || e[i + 3] != 71) {
                    continue;
                }
                for (int j = i; j < l; j++) {
                    if (e[j] == 66 && e[j + 1] == 96 && e[j + 2] == -126) {
                        try {
                            zop.putNextEntry(name + i + ".png");
                            zop.write(e, i, j - i + 3);
                            zop.closeEntry();
                        } catch (Exception ex) {
                        }

                        i += j - i;
                        break;
                    }
                }
            }
        }
        try {
            zop.finish();
            zop.close();
            zin.close();
            t.close();
            o.close();
        } catch (Exception ex) {
        }
        return true;
    }
}
