package gui;

import chen.c;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

public class History extends List implements Contrlable {

    c his;
    boolean change;
    int historynum;

    public History(c his) {
        super(new FileNode("\u6D4F\u89C8\u5386\u53F2"), null);
        g = this;
        repaint();
        this.his = his;
        change();
        repaint();
    }

    public void change() {
        kd = c.browser.kd;
        hs = dinwei = 1;
        deleteAll(false);
        String[] s = gethistory();
        int len = s.length;
        historynum = len + 3;
        append("·µ»Ø", 3);
        for (int i = 0; i < len; i++) {
            c.geticon(s[i], this);
        }
        append("Ë¢ÐÂ", 3);
        append("Çå¿Õ", 3);
    }

    public void select() {
        if (hs == 1) {
            back();
        } else if (hs == historynum) {
            delehistory();
        } else if (hs == historynum - 1) {
            change();
        } else {
            String y = getString(getSelectedIndex());
            c.browser.hs = c.browser.dinwei = 1;
            if (y.endsWith("/")) {
                c.currDirName = y;
                his.task = 2;
                new Thread(his).start();
                c.history = true;
            } else {
                c.currDirName = y.substring(0, y.lastIndexOf('/') + 1);
                his.openFile(y.substring(y.lastIndexOf('/') + 1));
                c.history = true;
            }
        }
    }

    private void delehistory() {
        try {
            RecordStore.deleteRecordStore("chen.history");
        } catch (RecordStoreException ex) {
        }
        change();
    }

    private String[] gethistory() {
        String str[] = null;
        RecordStore rde = null;
        try {

            try {
                rde = RecordStore.openRecordStore("chen.history", false);
            } catch (Exception ex) {
            }
            if (rde == null || rde.getNextRecordID() < 2) {
                return new String[]{};
            }
            ByteArrayInputStream bit = new ByteArrayInputStream(rde.getRecord(1));
            DataInputStream dit = new DataInputStream(bit);
            int a = dit.readShort();
            str = new String[a];
            for (int i = 0; i < a; i++) {
                str[i] = dit.readUTF();
            }
            dit.close();
            bit.close();
            rde.closeRecordStore();
            return str;
        } catch (Throwable e1) {
            return new String[]{};
        }


    }

    public void back() {
        Showhelper.show = c.browser;
    }

    public void create() {
    }

    public boolean dodelete(int i) {
        historynum = Math.max(historynum--, 3);
        return true;
    }

    public void fresh() {
    }

    public void openDir() {
    }

    public void showInfo(String name) {
    }
}
