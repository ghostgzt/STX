package pim;

import chen.c;
import gui.FileNode;
import gui.List;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.pim.Contact;
import javax.microedition.pim.PIM;
import javax.microedition.pim.PIMItem;
import javax.microedition.pim.PIMList;

public class p extends List implements gui.Contrlable {

    private PIM pim = PIM.getInstance();
    private PIMList plist;
    private Vector ps = new Vector();
    private FileNode info = new FileNode("信息");
    private FileNode list;

    public p() {
        super(new FileNode("联系人"), null);
        kd = c.browser.kd;
        append("返回", 3);
        super.g = this;
        s();
    }

    public void select() {
        PIMItem i = (PIMItem) ps.elementAt(hs - 2);
        int support[] = i.getPIMList().getSupportedFields();
        list = this.getDir();
        this.setDir(info);
        deleteAll(false);
        for (int j = 0; j < support.length; j++) {
            int dataType = i.getPIMList().getFieldDataType(support[j]);
            if (dataType == PIMItem.STRING && i.countValues(support[j]) != 0 && support[j] != Contact.CLASS) {
                append(i.getPIMList().getFieldLabel(support[j]), 5);
                append(i.getString(support[j], 0), 8);
            }
        }
        repaint();
    }

    void s() {

        try {
            plist = pim.openPIMList(PIM.CONTACT_LIST, PIM.READ_ONLY, pim.listPIMLists(PIM.CONTACT_LIST)[0]);
            Enumeration e = plist.items();
            PIMItem p;
            while (e.hasMoreElements()) {
                ps.addElement(p = (PIMItem) e.nextElement());
                append(p.getString(Contact.FORMATTED_NAME, 0), 5);
            }
        } catch (Throwable ex) {
            c.show.showError(ex.toString());
        }
    }

    public void back() {
        if (this.getDir() != info) {
            gui.Showhelper.show = c.browser;
            c.browser.repaint();
        } else {
            this.setDir(list);
            repaint();
        }
    }

    public void create() {
    }

    public boolean dodelete(int index) {
        return false;
    }

    public void fresh() {
    }

    public void openDir() {
    }

    public void showInfo(String name) {
    }
}
