package io;

import chen.c;
import javax.microedition.lcdui.*;
import javax.microedition.rms.RecordStore;

public class MemorySet extends Form implements CommandListener {

    public static int MAXREAD = 512 * 1024, RARMEM = 512 * 1024, RARPPMMEM = 1024 * 1024, POJIE = 0;
    public static int TXTDATA = 64 * 1024, TXTTEMP = 4 * 1024, INBUFSIZE = 64 * 1024, OUTBUFSIZE = 64 * 1024;
    Command save = new Command("����", Command.OK, 1);
    Command back = new Command("����", Command.BACK, 1);
    ChoiceGroup rumem, rarppm, rarmem, txtdata, txttemp, inbufsize, outbufsize, pojie;

    public MemorySet() {
        super("�ڴ�����");
        this.addCommand(save);
        this.addCommand(back);
        this.setCommandListener(this);
        if (!init()) {
            rumem.setSelectedIndex(3, true);
            rarmem.setSelectedIndex(3, true);
            rarppm.setSelectedIndex(4, true);
            txtdata.setSelectedIndex(0, true);
            txttemp.setSelectedIndex(1, true);
            inbufsize.setSelectedIndex(2, true);
            outbufsize.setSelectedIndex(2, true);
            pojie.setSelectedIndex(0, true);
        }
        c.d.setCurrent(this);
    }

    private boolean init() {
        String s[] = new String[]{"64k", "128k", "256k", "512k", "1M", "2M", "4M", "8M"};
        String s2[] = new String[]{"64k", "128k", "256k", "512k"};
        String s3[] = new String[]{"2k", "4k", "8k", "16k", "32k", "64k"};
        String s4[] = new String[]{"16k", "32k", "64k", "128k", "256k", "512k", "1M", "2M"};
        String[] s5 = new String[]{"���浵����", "���浵����", "�����浵����", "�����浵����"};
        rumem = new ChoiceGroup("�������ڴ�", ChoiceGroup.POPUP, s, null);
        rarppm = new ChoiceGroup("RarPPM���ռ���ڴ�", ChoiceGroup.POPUP, s, null);
        rarmem = new ChoiceGroup("Rar���ռ���ڴ�", ChoiceGroup.POPUP, s, null);
        txtdata = new ChoiceGroup("Txt�����ڴ�", ChoiceGroup.POPUP, s2, null);
        txttemp = new ChoiceGroup("Txt�����ַ�", ChoiceGroup.POPUP, s3, null);
        inbufsize = new ChoiceGroup("����������", ChoiceGroup.POPUP, s4, null);
        outbufsize = new ChoiceGroup("���������", ChoiceGroup.POPUP, s4, null);
        pojie = new ChoiceGroup("�ƽ�����", ChoiceGroup.POPUP, s5, null);
        this.append(rumem);
        this.append(rarppm);
        this.append(rarmem);
        this.append(txtdata);
        this.append(txttemp);
        this.append(inbufsize);
        this.append(outbufsize);
        this.append(pojie);
        try {
            RecordStore rt = RecordStore.openRecordStore("chen.mem", false);
            if (rt == null || rt.getNextRecordID() < 2) {
            }
            byte[] b = rt.getRecord(1);
            rumem.setSelectedIndex(b[0], true);
            rarmem.setSelectedIndex(b[1], true);
            rarppm.setSelectedIndex(b[2], true);
            txtdata.setSelectedIndex(b[3], true);
            txttemp.setSelectedIndex(b[4], true);
            inbufsize.setSelectedIndex(b[5], true);
            outbufsize.setSelectedIndex(b[6], true);
            pojie.setSelectedIndex(b[7], true);
            rt.closeRecordStore();
        } catch (Throwable e) {
            return false;
        }
        return true;
    }

    public void commandAction(Command c, Displayable d) {
        if (c == save) {
            save();
        } else if (c == back) {
            chen.c.d.setCurrent(chen.c.show);
        }

    }

    public static void get() {
        try {
            RecordStore rt = RecordStore.openRecordStore("chen.mem", false);
            if (rt == null || rt.getNextRecordID() < 2) {
                return;
            }
            byte[] b = rt.getRecord(1);
            MAXREAD = change(b[0]);
            RARMEM = change(b[1]);
            RARPPMMEM = change(b[2]);
            TXTDATA = change(b[3]);
            TXTTEMP = change(b[4]) / 32;
            INBUFSIZE = change(b[5]) / 4;
            OUTBUFSIZE = change(b[6]) / 4;
            POJIE = b[7];
            rt.closeRecordStore();
        } catch (Exception e) {
        }
    }

    private static int change(int ru) {
        return 64 * 1024 * (1 << ru);
    }

    private void save() {
        try {
            byte b[] = new byte[]{(byte) rumem.getSelectedIndex(), (byte) rarppm.getSelectedIndex(),
                (byte) rarmem.getSelectedIndex(), (byte) txtdata.getSelectedIndex(),
                (byte) txttemp.getSelectedIndex(), (byte) inbufsize.getSelectedIndex(),
                (byte) outbufsize.getSelectedIndex(), (byte) pojie.getSelectedIndex()};
            RecordStore rt = RecordStore.openRecordStore("chen.mem", true);
            if (rt.getNextRecordID() < 2) {
                rt.addRecord(b, 0, b.length);
            } else {
                rt.setRecord(1, b, 0, b.length);
            }
            rt.closeRecordStore();
            get();
            c.d.setCurrent(c.show);
        } catch (Throwable ex) {
            c.show.show("����ʧ��", ex.toString());
        }
    }
}
