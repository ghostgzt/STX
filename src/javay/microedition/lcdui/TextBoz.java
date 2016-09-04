 
// Source File Name:   TextBoz.java

package javay.microedition.lcdui;

import com.nokia.mid.ui.DeviceControl;
import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.rms.*;

public class TextBoz extends Form
    implements ItemCommandListener
{

    public void bxjc()
    {
        deleteAll();
        append(tbx);
        append(item$1);
        append(size$1);
        append(size$2);
        append(size$3);
        append(size$4);
        append(size$5);
        append(fgx);
        append(as$1);
    }

    public void about()
    {
        deleteAll();
        append("\n");
        append(fgx);
        append(bs$1);
    }

    public void tihuan()
    {
        deleteAll();
        append(tbx);
        append(field$1);
        append(field$2);
        append(size$6);
        append(fgx);
        append(bs$1);
    }

    public void commandAction(Command command, Item item)
    {
        if(command == ok$1)
        {
            restoreOptions();
            zt = tbx.getString();
            if(firstStart)
                firstStart = false;
            saveOptions();
        }
        if(command == ok$2)
            tbx.insert(zt, tbx.getString().length());
        if(command == ok$3 && tbx.size() > 0)
            tbx.delete(0, tbx.size());
        if(command == ok$4)
            tihuan();
        if(command == ok$5)
            lights();
        if(command == ok$6 && field$1.size() > 0)
            tbx.setString(replace(field$1.getString(), field$2.getString(), tbx.getString()));
        if(command == about$1)
            about();
        if(command == back$1)
            bxjc();
    }

    public void lights()
    {
        int i = 0;
        try
        {
            i = Integer.parseInt(tbx.getString().trim());
        }
        catch(NumberFormatException numberformatexception)
        {
            numberformatexception.printStackTrace();
        }
        if(i < 1 || i > 100 || tbx.size() == 0)
        {
            item$1.setText(String.valueOf(ls).concat("仅可为1-100之间").concat(String.valueOf(br)));
            return;
        } else
        {
            DeviceControl.setLights(0, i);
            item$1.setText(ls + String.valueOf(i) + "%" + String.valueOf(br));
            return;
        }
    }

    public String replace(String s, String s1, String s2)
    {
        if(s2 == null || s == null || s1 == null)
            return null;
        StringBuffer stringbuffer = new StringBuffer();
        for(int i = -1; (i = s2.indexOf(s)) != -1; i = -1)
        {
            stringbuffer.append(s2.substring(0, i) + s1);
            s2 = s2.substring(i + s.length());
        }

        stringbuffer.append(s2);
        return stringbuffer.toString();
    }

    public void saveOptions()
    {
        if(rmsStore != null)
        {
            byte abyte0[] = null;
            try
            {
                ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
                dataoutputstream.writeBoolean(firstStart);
                dataoutputstream.writeUTF(zt);
                dataoutputstream.flush();
                abyte0 = bytearrayoutputstream.toByteArray();
                dataoutputstream.close();
                rmsStore.setRecord(1, abyte0, 0, abyte0.length);
            }
            catch(InvalidRecordIDException invalidrecordidexception)
            {
                try
                {
                    rmsStore.addRecord(abyte0, 0, abyte0.length);
                }
                catch(RecordStoreException recordstoreexception1) { }
            }
            catch(Exception exception) { }
        }
        if(rmsStore != null)
            try
            {
                rmsStore.closeRecordStore();
                rmsStore = null;
            }
            catch(RecordStoreException recordstoreexception) { }
    }

    public void restoreOptions()
    {
        try
        {
            rmsStore = RecordStore.openRecordStore(RmsName, true);
        }
        catch(RecordStoreException recordstoreexception)
        {
            rmsStore = null;
        }
        if(rmsStore != null)
            try
            {
                if(rmsStore.getNumRecords() != 0)
                {
                    DataInputStream datainputstream = new DataInputStream(new ByteArrayInputStream(rmsStore.getRecord(1)));
                    firstStart = datainputstream.readBoolean();
                    zt = datainputstream.readUTF();
                    datainputstream.close();
                }
            }
            catch(Exception exception) { }
    }

    public String getString()
    {
        return tbx.getString();
    }

    public int size()
    {
        return tbx.size();
    }

    public void setString(String s)
    {
        tbx.setString(s);
    }

    public void delete(int i, int j)
    {
        tbx.delete(i, tbx.size());
    }

    public int getCaretPosition()
    {
        return tbx.getCaretPosition();
    }

    public int getConstraints()
    {
        return tbx.getConstraints();
    }

    public int getMaxSize()
    {
        return tbx.getMaxSize();
    }

    public void insert(char ac[], int i, int j, int k)
    {
        tbx.insert(ac, i, j, k);
    }

    public void insert(String s, int i)
    {
        tbx.insert(s, i);
    }

    public int setMaxSize(int i)
    {
        return tbx.setMaxSize(i);
    }

    public void setConstraints(int i)
    {
        tbx.setConstraints(i);
    }

    public void setChars(char ac[], int i, int j)
    {
        tbx.setChars(ac, i, j);
    }

    public TextBoz(String s, String s1, int i, int j)
    {
        super(s + "(" + String.valueOf(i) + "字)");
        RmsName = "rms";
        zt = "";
        ls = "\n亮度: ";
        br = "\n";
        fgx = "\n--------------\n";
        rmsStore = null;
        firstStart = true;
        tbx = new TextField(null, s1, i, j);
        field$1 = new TextField("查找内容", null, i, 0);
        field$2 = new TextField("替换内容", null, i, 0);
        item$1 = new StringItem(null, ls + br);
        size$1 = new StringItem(null, "复制", 2);
        size$2 = new StringItem(null, "粘贴", 2);
        size$3 = new StringItem(null, "清空", 2);
        size$4 = new StringItem(null, "替换", 2);
        size$5 = new StringItem(null, "亮度", 2);
        size$6 = new StringItem(null, "开始替换", 2);
        bs$1 = new StringItem(null, "返回上级   ", 1);
        as$1 = new StringItem(null, "关于修改者", 1);
        ok$1 = new Command("复制", 4, 0);
        ok$2 = new Command("粘贴", 4, 0);
        ok$3 = new Command("清空", 4, 0);
        ok$4 = new Command("替换", 4, 0);
        ok$5 = new Command("亮度", 4, 0);
        ok$6 = new Command("替换", 4, 0);
        back$1 = new Command("确认", 4, 0);
        about$1 = new Command("关于", 4, 0);
        size$1.setDefaultCommand(ok$1);
        size$2.setDefaultCommand(ok$2);
        size$3.setDefaultCommand(ok$3);
        size$4.setDefaultCommand(ok$4);
        size$5.setDefaultCommand(ok$5);
        size$6.setDefaultCommand(ok$6);
        bs$1.setDefaultCommand(back$1);
        as$1.setDefaultCommand(about$1);
        size$1.setItemCommandListener(this);
        size$2.setItemCommandListener(this);
        size$3.setItemCommandListener(this);
        size$4.setItemCommandListener(this);
        size$5.setItemCommandListener(this);
        size$6.setItemCommandListener(this);
        bs$1.setItemCommandListener(this);
        as$1.setItemCommandListener(this);
        bxjc();
        restoreOptions();
    }

    public TextField tbx;
    public TextField field$1;
    public TextField field$2;
    public Command ok$1;
    public Command ok$2;
    public Command ok$3;
    public Command ok$4;
    public Command ok$5;
    public Command ok$6;
    public Command back$1;
    public Command about$1;
    public Item size$1;
    public Item size$2;
    public Item size$3;
    public Item size$4;
    public Item size$5;
    public Item size$6;
    public Item bs$1;
    public Item as$1;
    public StringItem item$1;
    public String RmsName;
    public String zt;
    public String ls;
    public String br;
    public String fgx;
    public RecordStore rmsStore;
    public boolean firstStart;
}
