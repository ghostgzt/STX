
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Database.java

package mpe;

import java.io.*;
import java.util.Vector;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;

// Referenced classes of package mpe:
//            Utils, Document

public class Database
{

    public Database()
    {
    }

    public static void load()
    {
        try
        {
            RecordStore rs = RecordStore.openRecordStore("mpe.cfg", true);
            DataInputStream dis = new DataInputStream(new ByteArrayInputStream(rs.getRecord(1)));
            lang = dis.readUTF();
            dis.close();
            dis = new DataInputStream(new ByteArrayInputStream(rs.getRecord(2)));
            linenum = dis.readBoolean();
            rls = dis.readBoolean();
            lastsession = dis.readUTF();
            fontFace = dis.readInt();
            fontSize = dis.readInt();
            dis.close();
            dis = new DataInputStream(new ByteArrayInputStream(rs.getRecord(3)));
            cyrLayout = dis.readInt();
            separator = dis.readInt();
            template = dis.readUTF();
            templLine = dis.readInt();
            dis.close();
            rs.closeRecordStore();
        }
        catch(Exception e)
        {
            try
            {
                RecordStore.deleteRecordStore("mpe.cfg");
            }
            catch(Exception exception) { }
            try
            {
                RecordStore rs = RecordStore.openRecordStore("mpe.cfg", true);
                byte temp[] = new byte[0];
                rs.addRecord(temp, 0, temp.length);
                rs.addRecord(temp, 0, temp.length);
                rs.addRecord(temp, 0, temp.length);
                rs.closeRecordStore();
                saveLang();
                saveInterface();
                saveEditor();
            }
            catch(Exception exception1) { }
        }
    }

    public static void saveLang()
    {
        try
        {
            RecordStore rs = RecordStore.openRecordStore("mpe.cfg", true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeUTF(lang);
            byte temp[] = baos.toByteArray();
            dos.close();
            baos.close();
            rs.setRecord(1, temp, 0, temp.length);
            rs.closeRecordStore();
        }
        catch(Exception exception) { }
    }

    public static void saveInterface()
    {
        try
        {
            RecordStore rs = RecordStore.openRecordStore("mpe.cfg", true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeBoolean(linenum);
            dos.writeBoolean(rls);
            dos.writeUTF(lastsession);
            dos.writeInt(fontFace);
            dos.writeInt(fontSize);
            byte temp[] = baos.toByteArray();
            dos.close();
            baos.close();
            rs.setRecord(2, temp, 0, temp.length);
            rs.closeRecordStore();
        }
        catch(Exception exception) { }
    }

    public static void saveEditor()
    {
        try
        {
            RecordStore rs = RecordStore.openRecordStore("mpe.cfg", true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(cyrLayout);
            dos.writeInt(separator);
            dos.writeUTF(template);
            dos.writeInt(templLine);
            byte temp[] = baos.toByteArray();
            dos.close();
            baos.close();
            rs.setRecord(3, temp, 0, temp.length);
            rs.closeRecordStore();
        }
        catch(Exception exception) { }
    }

    public static void loadTemplates()
    {
        try
        {
            RecordStore rs = RecordStore.openRecordStore("mpe.tmpl", true);
            templates = new Vector();
            if(rs.getNumRecords() > 0)
            {
                byte record[] = rs.enumerateRecords(null, null, false).nextRecord();
                DataInputStream dis = new DataInputStream(new ByteArrayInputStream(record));
                int count = dis.readInt();
                for(int i = 0; i < count; i++)
                {
                    templates.addElement(dis.readUTF());
                    templates.addElement((new Integer(dis.readInt())).toString());
                }

                dis.close();
            }
            rs.closeRecordStore();
        }
        catch(Exception exception) { }
    }

    public static void saveTemplates()
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try
        {
            dos.writeInt(templates.size() / 2);
            for(int i = 0; i < templates.size() / 2; i++)
            {
                dos.writeUTF((String)templates.elementAt(i * 2));
                dos.writeInt(Integer.parseInt(templates.elementAt(i * 2 + 1).toString()));
            }

            dos.close();
            baos.close();
            RecordStore.deleteRecordStore("mpe.tmpl");
            RecordStore rs = RecordStore.openRecordStore("mpe.tmpl", true);
            byte bytes[] = baos.toByteArray();
            rs.addRecord(bytes, 0, bytes.length);
            rs.closeRecordStore();
        }
        catch(Exception exception) { }
    }

    public static void insertTemplate(int idx, Document d)
    {
        d.Insert(Utils.processMacros(templates.elementAt(idx * 2).toString()));
        int b = Integer.parseInt(templates.elementAt(idx * 2 + 1).toString());
        if(b <= templates.elementAt(idx * 2).toString().length())
        {
            for(int i = 0; i < b; i++)
                d.moveCursor(0);

        }
    }

    public static void addTemplate(String text, String getBack)
    {
        templates.addElement(text);
        templates.addElement(getBack);
    }

    public static void editTemplate(int idx, String text, String getBack)
    {
        templates.setElementAt(text, idx * 2);
        templates.setElementAt(getBack, idx * 2 + 1);
    }

    public static void delTemplate(int idx)
    {
        templates.removeElementAt(idx * 2);
        templates.removeElementAt(idx * 2);
    }

    public static String[] getTemplates()
    {
        String s[] = new String[templates.size() / 2];
        for(int i = 0; i < templates.size() / 2; i++)
            s[i] = templates.elementAt(i * 2).toString();

        return s;
    }

    public static String getTemplateStr(int idx)
    {
        return templates.elementAt(idx * 2).toString();
    }

    public static String getTemplateInt(int idx)
    {
        return templates.elementAt(idx * 2 + 1).toString();
    }

    private static final String TEMPLATES_STORE = "mpe.tmpl";
    private static final String CONFIG_STORE = "mpe.cfg";
    private static Vector templates;
    public static String lang = "english.lang";
    public static boolean linenum = true;
    public static boolean rls = false;
    public static String lastsession = "";
    public static int fontFace = 0;
    public static int fontSize = 0;
    public static int cyrLayout = 0;
    public static int separator = 0;
    public static String template = "";
    public static int templLine = 1;

}
