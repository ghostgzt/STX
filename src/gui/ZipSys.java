package gui;

import io.FileSys;
import io.MemorySet;
import io.ContDataInputStream;
import chen.c;
import zip.ZipEntry;
import java.io.*;
import java.util.Hashtable;
import java.util.Vector;
import zip.Inflater;

public final class ZipSys extends Yasuo {

    Stack nameoffset;
    boolean need;
    int datalen;
    Inflater inf = new Inflater(true);
    int maxToread;

    public ZipSys(String file, c v) {
        super(v, file);
        init(file);
        fileName = file.substring(file.lastIndexOf('/') + 1);
    }

    void init(String file) {
        f = file;
        pre = null;
        need = false;
        nameoffset = new Stack();
        jar = new Vector();
    }

    public ZipSys(Yasuo z, String name, byte[] tb) {
        super(z.c, name);
        init(z.f);
        tooBig = false;
        b = tb;
        pre = z;
        fileName = name;
    }

    private void endsys(ContDataInputStream input) throws IOException {
        while (input.readInt() == 0x504b0102) {
            ZipEntry entry = new ZipEntry(null);
            input.skip(4);//4
            entry.flag = input.readLshort();//6
            entry.setMethod(input.readLshort());//8
            entry.dostime = input.readLint();//12
            entry.crc = input.readLint();//16
            entry.compressedSize = input.readLint();//20
            entry.size = input.readLint();//24
            entry.namelen = input.readLshort();//26
            int extralen = input.readLshort();//28
            int commentlen = input.readLshort();//30
            input.skip(8);//38
            entry.offset = input.readLint();//42
            nameoffset.push(entry.offset + 30);
            nameoffset.push(entry.offset + 30 + entry.namelen);
            nameoffset.push(rof.getOffst());
            byte[] nb = new byte[entry.namelen];
            input.readFully(nb, 0, entry.namelen);
            nameoffset.push(rof.getOffst());
            entry.setname(nb);
            if (!entry.isDirectory()) {
                jar.addElement(entry);
            }
            input.skip(extralen + commentlen);
        }

    }

    private void gai(int a, int k) {
        for (int i = k; i < a; i++) {
            if (b[i] == '\\') {
                b[i] = '/';
                need = true;
            }
        }
    }

    public boolean readfile() {
        if (b == null) {
            try {
                datalen = (int) FileSys.getfile(f).fileSize();
            } catch (Exception ex) {
                return false;
            }
            if (datalen > MemorySet.MAXREAD) {
                b = FileSys.getdata(f, datalen - MemorySet.MAXREAD, MemorySet.MAXREAD);
                tooBig = true;
            } else {
                b = FileSys.getdata(f, 0, 0);
                tooBig = false;
            }

        }
        if (b == null) {
            return false;
        }
        return true;
    }

    public void run() {
        if (first) {
            first = false;
            try {
                if (!readfile()) {
                    back();
                }
                readhead();
                if (c != null) {
                    c.zip = true;
                    readEntrys(jar);
                    if (c.autotxt) {
                        fresh();
                    } else {
                        c.browser.deleteAll(true);
                        c.browser.append("上一级", 2);
                        for (int i = 0; i < jar.size(); i++) {
                            c.geticon(jar.elementAt(i).toString());
                        }
                         c.show.repaint();
                    }
                    if (c.autotxt && !tooBig) {
                        while (nameoffset.top > 0) {
                            gai(nameoffset.pop(), nameoffset.pop());
                        }
                        if (need) {
                            FileSys.savefile(f.substring(0, f.length() - 4) + "-new.zip", b, 0, 0);
                        }
                    }
                } else {
                    new Thread(this).start();
                }

            } catch (Exception ex) {
                c.show.show("zipsys", ex.toString());

            }

        } else {
            super.run();
        }
    }

    byte[] getdata(ZipEntry slen) {
        getInputstream(slen.offset + 26);
        if (rof == null) {
            return null;
        }
        byte[] tb = null;
        try {
            int len = Math.min(slen.size, MemorySet.MAXREAD);
            tb = new byte[len];
            rof.skip(rof.readLshort() + rof.readLshort());
            if (slen.getMethod() == 0) {
                rof.readFully(tb, 0, len);
                return tb;
            } else {
                byte[] temp = new byte[Math.min(20480, slen.compressedSize)];
                maxToread = slen.compressedSize;
                inf.reset();
                int off = 0;
                while (maxToread > 0 && len - off > 0 && !inf.finished() && !inf.needsDictionary()) {
                    if (inf.needsInput()) {
                        fill(temp);
                    }
                    off += inf.inflate(tb, off, len - off);
                }
            }

        } catch (OutOfMemoryError e) {
            c.show.showError("内存不足");
            return null;
        } catch (Exception ex) {
            tb = null;
            return null;
        }
        return tb;
    }

    public void readhead() {
        int i = b.length - 22;
        while (i > 0) {
            if (b[i] == 0x50 || b[i + 1] == 0x4b || b[i + 2] == 5 || b[i + 3] == 6) {
                break;
            }
            i--;
        }
        if (i < 20) {
            return;
        }
        i += 8;
        hash = new Hashtable(((b[i] & 0xff) | ((b[i + 1] << 8) & 0xff)) + 2);
        i += 8;
        i = (b[i++] & 0xff) | ((b[i++] & 0xff) << 8) | ((b[i++] & 0xff) << 16) | ((b[i] & 0xff) << 24);
        try {
            if (!tooBig) {
                rof = new ContDataInputStream(new ByteArrayInputStream(b, i, b.length - i));
                rof.setOffset(i);
            } else {
                int off = i - datalen + MemorySet.MAXREAD;
                if (off >= 0) {
                    rof = new ContDataInputStream(new ByteArrayInputStream(b, off, b.length - off));
                    rof.setOffset(i);
                } else {
                    b = null;
                    rof = new ContDataInputStream(FileSys.getinputstream(f));
                    rof.skip(i);
                }
            }
            endsys(rof);
            if (tooBig) {
                b = null;
                rof.close();
                rof = null;
            }
        } catch (Exception e) {
            c.show.show("zip read fail", e.toString());
        } finally {
            try {
                rof.close();
            } catch (Exception ex) {
            }
        }

    }

    public void close() {
        if (rof != null) {
            try {
                rof.close();
            } catch (IOException ex) {
            }
            rof = null;
        }
    }

    protected void jieya(Vector jar) {
        ZipEntry[] entrys = new ZipEntry[jar.size() + 1];
        jar.copyInto(entrys);
        chose = entrys.length - 1;
        if (sortEntry) {
            sort(entrys, 0, entrys.length - 2);
        }
        String head;
        if (f.startsWith("file:///")) {
            head = f.substring(0, f.lastIndexOf('.') > 0 ? f.lastIndexOf('.') : f.length() - 1) + "/";
        } else {
            head = "file:///" + c.currDirName + f + (f.endsWith("/") ? "" : "/");
        }
        FileSys.makedir(head);
        path.clear();
        String filename;
        byte[] outtemp = new byte[32768];
        byte[] intemp = new byte[20480];
        int num = chose;
        int sum = 0;
        for (int i = 0; i < num; i++) {
            sum += entrys[i].compressedSize;
        }
        Showhelper.jindu = new Gauge(f, sum, true);
        for (int i = 0; i < num; i++) {
            try {
                if (entrys[i].size > 0) {
                    filename = entrys[i].name;
                    makedir(head, filename);
                    saveFile(head + (filename.endsWith(".jar") ? filename.substring(0, filename.lastIndexOf('.')) + ".zip" : filename), entrys[i], outtemp, intemp);
                }
            } catch (Throwable t) {
            }
        }
        outtemp = null;
        intemp = null;
        Showhelper.jindu.close();
        c.show.repaint();
    }

    void fill(byte[] buf) throws IOException {
        int len = Math.min(maxToread, buf.length);
        maxToread -= len;
        rof.readFully(buf, 0, len);
        inf.setInput(buf, 0, len);
    }

    void saveFile(String url, ZipEntry name, byte[] temp, byte[] buf) {
        getInputstream(name.offset + 26);
        OutputStream out = FileSys.getOutputStream(url);
        if (out == null) {
            return;
        }
        maxToread = name.compressedSize;
        Showhelper.jindu.setMaxValue(maxToread);
        Showhelper.jindu.setName(name.name);
        try {
            int len;
            rof.skip(rof.readLshort() + rof.readLshort());
            if (name.getMethod() != 0) {
                inf.reset();
                while (maxToread > 0 && !inf.finished() && !inf.needsDictionary()) {
                    if (inf.needsInput()) {
                        fill(buf);
                    }
                    Showhelper.jindu.showRemain(maxToread);
                    len = inf.inflate(temp, 0, 32768);
                    out.write(temp, 0, len);
                }
            } else {
                while (maxToread > 0) {
                    len = Math.min(32768, maxToread);
                    rof.readFully(temp, 0, len);
                    maxToread -= len;
                    Showhelper.jindu.showRemain(maxToread);
                    out.write(temp, 0, len);
                }
            }
            out.flush();
        } catch (Exception ex) {
        } finally {
            try {
                out.close();
            } catch (Exception ex) {
            }
        }

    }

    public byte[] getdata(String name) {
        return getdata((ZipEntry) hash.get(dir + name));
    }

    public int getSize(String name) {
        return ((Entry) hash.get(dir + name)).size;
    }
}
