package gui;

import io.Txt;
import chen.c;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public class main implements Contrlable {

    static byte[] data;
    List l, f;
    int search[] = {0, 9806, 16862, 31466, 39530, 46695, 53790,
        57924, 62611, 68560, 69641, 70446, 75667, 82658, 85623, 89525,
        101406, 102069, 111539, 129772, 138167, 140979, 143265, 147839,
        147863, 148378
    };
    int wordnum[] = {295, 226, 433, 243, 211, 219, 130, 150, 171, 36, 28, 169,
        226, 96, 121, 373, 21, 287, 576, 268, 82, 74, 151, 1, 18, 6
    };
    show sh;
    ceshi cs;
    boolean word;
    boolean cesh;

    public main() {

        startApp();
    }

    void startApp() {
        String s[] = new String[]{"新的开始", "旧的回忆", "测试 否", "选择", "浏览所有"};
        int img[] = new int[]{9, 9, 9, 9, 9};
        l = new List(new FileNode("首字母"), this);
        f = new List(new FileNode("菜单", s, img), this);
        f.kd = l.kd = Math.max(List.filetype[5].getWidth() + 1, List.filetype[6].getWidth());
        cesh = false;
        f.repaint();
        word = false;
        getdata();
        for (int i = 0; i < 26; i++) {
            l.append((char) ('a' + i) + "  单词数  " + wordnum[i], 9);
        }
        l.append("新,难单词", 9);

    }

    void getdata() {
        InputStream in = this.getClass().getResourceAsStream("/chen/english.txt");
        if (in != null) {
            try {
                data = new byte[in.available()];
                in.read(data);
                in.close();
            } catch (IOException ex) {
            }
        } else {
            c.show.showError("资源不存在");
        }
    }

    void getword() {
        try {
            RecordStore ss;
            ss = RecordStore.openRecordStore("hardword", true);
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(o);
            byte[] b;
            if (ss.getNumRecords() > 0) {
                ByteArrayInputStream in = new ByteArrayInputStream(ss.getRecord(1));
                DataInputStream din = new DataInputStream(in);
                while (din.available() > 0) {
                    b = din.readUTF().getBytes("Utf-8");
                    dos.write(b, 0, b.length);
                    dos.write(32);
                    b = din.readUTF().getBytes("Utf-8");
                    dos.write(b, 0, b.length);
                    dos.write(13);
                    dos.write(10);
                }
                data = o.toByteArray();
                dos.close();
                o.close();
                din.close();
                in.close();
                ss.closeRecordStore();
            }
        } catch (IOException ex) {
        } catch (RecordStoreException ex) {
        }
    }

    void add(String word, String jieshi) {
        try {
            RecordStore s;
            s = RecordStore.openRecordStore("hardword", true);
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(o);
            if (s.getNumRecords() > 0) {
                dos.write(s.getRecord(1), 0, s.getRecordSize(1));
                dos.writeUTF(word);
                dos.writeUTF(jieshi);
                dos.flush();
                s.setRecord(1, o.toByteArray(), 0, o.size());
            } else {
                dos.writeUTF(word);
                dos.writeUTF(jieshi);
                dos.flush();
                s.addRecord(o.toByteArray(), 0, o.size());
            }
            dos.close();
            o.close();
            s.closeRecordStore();
        } catch (RecordStoreException ex) {
        } catch (IOException ex) {
        }
    }

    void save(int k) {

        try {
            RecordStore s;
            s = RecordStore.openRecordStore("jilu", true);
            ByteArrayOutputStream o = new ByteArrayOutputStream();
            new DataOutputStream(o).writeInt(k);
            byte[] b = o.toByteArray();
            if (s.getNumRecords() > 0) {
                s.setRecord(1, b, 0, 4);
            } else {
                s.addRecord(b, 0, 4);
            }
            o.close();
            s.closeRecordStore();
        } catch (RecordStoreException ex) {
        } catch (IOException ex) {
        }
    }

    void showsh(int k) {
        if (sh == null) {
            sh = new show(this, k);
        } else {
            sh.k = k;
            sh.start();
        }

    }

    void showcs(int k, int num) {
        if (cs == null) {
            cs = new ceshi(k, num, this);
        } else {
            cs.k = k;
            cs.wordnum = num;
            cs.initdata();
        }

    }

    void openold() {
        if (word) {
            getdata();
            word = false;
        }
        RecordStore s;
        try {
            s = RecordStore.openRecordStore("jilu", false);
            byte[] b = s.getRecord(1);
            try {
                if (cesh) {
                    showcs(new DataInputStream(new ByteArrayInputStream(b)).readInt(), 100);
                } else {
                    showsh(new DataInputStream(new ByteArrayInputStream(b)).readInt());
                }
            } catch (IOException ex) {
            }
            s.closeRecordStore();

        } catch (RecordStoreException ex) {
            if (cesh) {
                showcs(0, 100);
            } else {
                showsh(0);
            }
        }

    }

    public void back() {
        if (Showhelper.show == f) {

            Showhelper.show = c.browser;
            f = null;
            l = null;
            cs = null;
            sh = null;
            data = null;
        } else {
            Showhelper.show = f;
        }
    }

    public void create() {
    }

    public void fresh() {
    }

    public void select() {
        if (Showhelper.show == l) {
            int s = l.getSelectedIndex();
            if (s == 26) {
                getword();
                word = true;
                showsh(0);
            } else {
                if (word) {
                    getdata();
                    word = false;
                }
                if (cesh) {
                    showcs(search[s], wordnum[s]);
                } else {
                    showsh(search[s]);
                }
            }
        } else {
            switch (f.hs) {
                case 1:
                    if (word) {
                        getdata();
                        word = false;
                    }
                    if (cesh) {
                        showcs(0, 100);
                    } else {
                        showsh(0);
                    }
                    break;
                case 2:
                    openold();
                    break;
                case 3:
                    cesh = !cesh;
                    f.set(2, "测试 " + (cesh ? "是" : "否"), 10);
                    break;
                case 4:
                    Showhelper.show = l;
                    break;
                case 5:
                    new Txt(data, "四级词汇");
                    break;
            }
        }
    }

    public void openDir() {
    }

    public void showInfo(String name) {
    }

    public boolean dodelete(int index) {
        return true;
    }
}
