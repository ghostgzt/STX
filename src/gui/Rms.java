package gui;

import io.FileSys;
import chen.c;
import io.BufDataOutputStream;
import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;
import zip.ZipEntry;
import zip.ZipOutputStream;

public class Rms extends List implements CommandListener, Contrlable {

    Command back = new Command("返回", 2, Command.BACK);
    Command ok = new Command("确认", 2, Command.ITEM);
    Command save = new Command("保存", 2, Command.ITEM);
    private String rmsname;
    Display d;
    List l;
    Form f;
    int recordlen;
    byte[] rdata;
    private TextField nameInput;

    public Rms(List l) {
        super(new FileNode("rms列表"), null);
        repaint();
        g = this;
        this.l = l;
        ll = new Rmscd(this);
        d = c.d;
        kd = c.browser.kd;
        f = new Form("rms");
        f.addCommand(back);
        f.setCommandListener(this);
    }

    public void star() {

        String rms[] = RecordStore.listRecordStores();
        deleteAll(false);
        setTitle("rms列表");
        append("返回", 2);
        if (rms != null) {

            for (int i = 0; i < rms.length; i++) {
                append(rms[i], 9);
            }

            hs = dinwei = 1;

        }
        Showhelper.show = this;
        repaint();
    }

    void info() {
        deleteAll(false);
        try {

            RecordStore r = RecordStore.openRecordStore(getString(getSelectedIndex()), false);
            setTitle("rms信息");
            append("返回", 2);
            append("记录条数： " + r.getNumRecords(), 10);
            append("记录大小： " + r.getSize(), 10);
            append("下条编号： " + r.getNextRecordID(), 10);
            append("修改次数： " + r.getVersion(), 10);
            append("剩余空间： " + r.getSizeAvailable(), 10);
            r.closeRecordStore();
        } catch (RecordStoreException ex) {
            c.show.showError(ex.toString());

        }

    }

    void listrecord(String name) {
        RecordEnumeration en;
        try {
            RecordStore r = RecordStore.openRecordStore(rmsname, false);
            en = r.enumerateRecords(null, null, false);
            deleteAll(false);
            setTitle("记录列表" + " 记录数  " + r.getNumRecords());
            append("返回", 2);
            while (en.hasNextElement()) {
                append("记录" + en.nextRecordId(), 10);
            }
            hs = dinwei = 1;
            en.destroy();
            r.closeRecordStore();
        } catch (RecordStoreException ex) {
            c.show.showError(ex.toString());

        }

    }

    public void commandAction(Command cc, Displayable dd) {
        if (cc == back) {
            Showhelper.show = this;
            c.d.setCurrent(c.show);
        } else if (cc == ok) {
            String name = nameInput.getString();
            try {
                RecordStore r = RecordStore.openRecordStore(name, true);
                r.addRecord(null, 0, 0);
                r.closeRecordStore();
                star();
            } catch (RecordStoreException ex) {
                c.show.showError(ex.toString());

            }
        }
    }

    private void openrecord() {
        String n = getString(getSelectedIndex());
        int id = Integer.parseInt(n.substring(2));
        try {
            RecordStore r = RecordStore.openRecordStore(rmsname, false);
            Hex h = new Hex("file:///" + c.currDirName + n, this, true);
            h.setbytes(r.getRecord(id), 0, 0);
            r.closeRecordStore();
            Showhelper.show = h;
        } catch (RecordStoreException ex) {
            c.show.showError("打开记录时出错," + ex.toString());

        }

    }

    void save(byte[] b) {
        String n = getString(getSelectedIndex());
        int id = Integer.parseInt(n.substring(2));
        try {
            RecordStore r = RecordStore.openRecordStore(rmsname, false);
            r.setRecord(id, b, 0, b.length);
            r.closeRecordStore();
        } catch (RecordStoreException ex) {
            c.show.showError("保存记录时出错," + ex.toString());
        }
    }

    public void create() {
        Form creator = new Form("新建");
        nameInput = new TextField("rms名", null, 256, TextField.ANY);
        creator.append(nameInput);
        creator.addCommand(ok);
        creator.addCommand(back);
        creator.setCommandListener(this);
        d.setCurrent(creator);
    }

    public boolean dodelete(int i) {
        try {
            RecordStore.deleteRecordStore(getString(i));
            return true;
        } catch (RecordStoreException ex) {
            c.show.showError(ex.toString());

            return false;

        }
    }

    public void openDir() {
        star();

    }

    public void select() {
        if (hs == 1) {
            if (title.endsWith("列表")) {
                Showhelper.show = l;
            } else {
                star();
            }
        } else {
            if (title.endsWith("列表")) {
                rmsname = getString(getSelectedIndex());
                listrecord(rmsname);
            } else if (title.endsWith("信息")) {
                star();
            } else {
                openrecord();
            }
        }
        repaint();
    }

    private void append(String s, ZipOutputStream out) {
        try {
            RecordStore rc = RecordStore.openRecordStore(s, false);
            RecordEnumeration en = rc.enumerateRecords(null, null, false);
            byte b[];
            int id;
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            DataOutputStream dd = new DataOutputStream(bo);
            ByteArrayOutputStream bo2 = new ByteArrayOutputStream();
            DataOutputStream dd2 = new DataOutputStream(bo2);
            ZipEntry entry = new ZipEntry(s);
            if (en.hasNextElement()) {
                Stack ip = new Stack();
                while (en.hasNextElement()) {
                    ip.push(en.nextRecordId());
                }
                ip.sort();
                while (ip.haveElement()) {
                    b = rc.getRecord(id = ip.pop());
                    if (b != null) {
                        dd2.writeInt(b.length);
                        dd2.writeInt(id);
                        dd.write(b, 0, b.length);
                    }
                }
                entry.setExtra(bo2.toByteArray());
                out.putNextEntry(entry);
                out.write(bo.toByteArray());
                out.closeEntry();
                entry.setExtra(null);
                dd.close();
                bo.close();
                dd2.close();
                bo2.close();
                rc.closeRecordStore();
            }
        } catch (Exception ex) {
        }

    }

    void ru(String url, int mode) {

        try {
            byte[] b = null;
            switch (mode) {
                case 0:
                    b = FileSys.getdata(url, 0, 0);
                    break;
                case 1:
                    try {
                        RecordStore rt = RecordStore.openRecordStore("chen.rms", false);
                        if (rt != null && rt.getNumRecords() > 0) {
                            b = rt.getRecord(1);
                        }
                    } catch (RecordStoreException ex) {
                    }
                    break;
                case 2:
                    InputStream inpu = this.getClass().getResourceAsStream("/chen/chen.rms");
                    b = new byte[inpu.available()];
                    inpu.read(b, 0, b.length);
                    inpu.close();
            }
            if (b != null && b.length > 10) {
                int i;
                for (i = 0; i < b.length - 8; i++) {
                    if (b[i] == 80 && b[i + 1] == 75 && b[i + 2] == 3 && b[i + 3] == 4) {
                        break;
                    }
                }
                ByteArrayInputStream bin = new ByteArrayInputStream(b, i, b.length - i);
                analyze(new DataInputStream(bin));
                bin.close();
                bin = null;
                Showhelper.show = this;
                c.show.show("信息", "导入完成");

            }
        } catch (IOException ex) {
        }

    }

    public void analyze(DataInputStream input) throws IOException {

        while (input.available() > 0) {
            int magic = input.readInt();
            if (magic != 0x504B0304) {
                return;
            }
            input.skip(22);
            int namelen = exchange(input.readUnsignedByte(), input.readUnsignedByte()),
                    extralen = exchange(input.readUnsignedByte(), input.readUnsignedByte());
            if (extralen < 8) {
                continue;
            }
            byte[] nb = new byte[namelen];
            byte[] extra = new byte[extralen];
            input.read(nb, 0, namelen);
            input.read(extra, 0, extralen);
            String name = new String(nb, 0, namelen, "Utf-8");
            RecordStore rt = null;
            try {
                rt = RecordStore.openRecordStore(name, true);
            } catch (RecordStoreException ex) {
                c.show.showError(ex.toString());
            }

            ByteArrayInputStream bin = new ByteArrayInputStream(extra);
            DataInputStream din = new DataInputStream(bin);
            if (rt != null) {
                int id = 0;
                int len;
                byte[] t;
                try {
                    while (din.available() > 7) {
                        len = din.readInt();
                        id = din.readInt();
                        while (id > rt.getNextRecordID() - 1) {
                            rt.addRecord(null, 0, 0);
                        }
                        t = new byte[len];
                        input.readFully(t);
                        rt.setRecord(id, t, 0, t.length);
                    }
                    rt.closeRecordStore();

                } catch (RecordStoreException ex) {
                    c.show.showError(ex.toString());

                }// try
            }// if

        }// while
    }

    private int exchange(int a, int b) {

        return a + (b << 8);
    }

    public void showInfo(String name) {
        info();
    }

    public void back() {
        hs = 1;
        select();
        repaint();
    }

    void chu(String url, int mod) {
        OutputStream dao;
        String rms[] = RecordStore.listRecordStores();
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        if (mod == 3) {
            dao = bo;
        } else {
            dao = FileSys.getOutputStream(url);
        }
        ZipOutputStream zf = new ZipOutputStream(new BufDataOutputStream(dao), 10240);
        if (rms != null) {
            for (int i = 0; i < rms.length; i++) {
                append(rms[i], zf);
            }// for
            try {
                zf.finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            zf = null;
            try {
                if (mod == 3) {
                    byte[] b = bo.toByteArray();
                    RecordStore rt = RecordStore.openRecordStore("chen.rms", true);
                    if (rt.getNextRecordID() < 2) {
                        rt.addRecord(b, 0, b.length);
                    } else {
                        rt.setRecord(1, b, 0, b.length);
                    }
                    rt.closeRecordStore();
                    bo.close();
                } else {

                    try {
                        dao.close();

                    } catch (IOException ex) {
                    }
                }
                star();
            } catch (Exception ex) {
                c.show.showError(ex.toString());
            }
        }// if
    }

    void ru(String name) {
        try {
            byte[] b = FileSys.getdata("file:///" + c.currDirName + name, 0, 0);
            if (b == null) {
                return;
            }
            name = name.substring(0, Math.min(name.length(), 30));
            RecordStore rt = RecordStore.openRecordStore(name, true);
            if (rt.getNextRecordID() < 2) {
                rt.addRecord(b, 0, b.length);
            } else {
                rt.setRecord(1, b, 0, b.length);
            }
            rt.closeRecordStore();
        } catch (RecordStoreException ex) {
        }
    }

    public static void save(String name, int offset, int hs, int dinwei) {
        try {
            RecordStore rt = RecordStore.openRecordStore("chen.book", true);
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            DataOutputStream dot = new DataOutputStream(o);
            if (rt.getNextRecordID() < 2) {
                dot.writeUTF(name);
                dot.writeInt(offset);
                dot.writeInt(hs);
                dot.writeInt(dinwei);
                dot.flush();
                rt.addRecord(o.toByteArray(), 0, o.size());
            } else {
                ByteArrayInputStream in = new ByteArrayInputStream(rt.getRecord(1));
                DataInputStream din = new DataInputStream(in);
                String s;
                byte[] b = new byte[12];
                boolean sus = false;
                while (in.available() > 0) {
                    s = din.readUTF();
                    dot.writeUTF(s);
                    din.readFully(b);
                    if (s.equals(name)) {
                        dot.writeInt(offset);
                        dot.writeInt(hs);
                        dot.writeInt(dinwei);
                        sus = true;
                    } else {
                        dot.write(b);
                    }
                }
                if (!sus) {
                    dot.writeUTF(name);
                    dot.writeInt(offset);
                    dot.writeInt(hs);
                    dot.writeInt(dinwei);
                }
                dot.flush();
                rt.setRecord(1, o.toByteArray(), 0, o.size());
                din.close();
                in.close();
            }
            rt.closeRecordStore();
            dot.close();
            o.reset();
            o.close();
        } catch (Exception ex) {
        }
    }

    public static int[] get(String name) {
        try {
            RecordStore rt = RecordStore.openRecordStore("chen.book", false);
            if (rt == null || rt.getNextRecordID() < 2) {
                return null;
            }
            ByteArrayInputStream in = new ByteArrayInputStream(rt.getRecord(1));
            DataInputStream din = new DataInputStream(in);
            String s;
            byte[] b = new byte[12];
            while (din.available() > 0) {
                try {
                    s = din.readUTF();
                    if (s.equals(name)) {
                        int[] t = new int[]{din.readInt(), din.readInt(), din.readInt()};
                        din.close();
                        rt.closeRecordStore();
                        return t;
                    } else {
                        din.readFully(b);
                    }
                } catch (IOException ex) {
                    return null;
                }
                din.close();
                rt.closeRecordStore();
            }
        } catch (Exception ex) {
        }
        return null;
    }

    public void fresh() {
        star();
    }
}
