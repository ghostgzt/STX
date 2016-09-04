package mpe;

import java.io.*;
import java.util.Enumeration;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;
import javax.microedition.io.file.FileSystemRegistry;
import javax.microedition.lcdui.*;

// Referenced classes of package mpe:
//            MPE, Database, Utils, HGB2312, 
//            Document, Workspace
public class FileManager
        implements CommandListener {

    public FileManager(MPE mpe) {
        this.mpe = mpe;
        curPath = "";
        isRoot = true;
        cmdSaveHere = new Command("\u4FDD\u5B58\u5728\u8FD9\u91CC", 4, 0);
        cmdOk = new Command("\u786E\u5B9A", 4, 0);
        cmdCreateDir = new Command("\u65B0\u5EFA\u6587\u4EF6\u5939", 8, 1);
        cmdDelete = new Command("\u5220\u9664", 8, 2);
        cmdRename = new Command("\u91CD\u547D\u540D", 8, 3);
        cmdCancel = new Command("\u53D6\u6D88", 2, 5);
        try {
            java = Image.createImage("/mpe/res/java.png");
            file = Image.createImage("/mpe/res/file.png");
        } catch (Exception exception) {
        }
    }

    public void commandAction(Command c, Displayable d) {
        if (c == List.SELECT_COMMAND) {
            if (lFiles.getSelectedIndex() == 0 && !isRoot) {
                int n = curPath.substring(0, curPath.length() - 1).lastIndexOf('/');
                if (n < 0) {
                    showRoot();
                } else {
                    showDir(curPath = curPath.substring(0, n) + '/');
                }
            } else if (lFiles.getString(lFiles.getSelectedIndex()).charAt(lFiles.getString(lFiles.getSelectedIndex()).length() - 1) == '/') {
                showDir(curPath += lFiles.getString(lFiles.getSelectedIndex()));
            } else if (opening) {
                open(curPath + lFiles.getString(lFiles.getSelectedIndex()));
            } else {
                save(curPath + lFiles.getString(lFiles.getSelectedIndex()));
            }
        } else if (c == cmdSaveHere) {
            input = 0;
            tbInput = new TextBox("\u8F93\u5165\u6587\u4EF6\u540D\u79F0", "*.java", 256, 0);
            tbInput.addCommand(cmdOk);
            tbInput.addCommand(cmdCancel);
            tbInput.setCommandListener(this);
            mpe.display.setCurrent(tbInput);
        } else if (c == cmdCreateDir) {
            input = 1;
            tbInput = new TextBox("\u8F93\u5165\u6587\u4EF6\u5939\u540D\u79F0", "", 256, 0);
            tbInput.addCommand(cmdOk);
            tbInput.addCommand(cmdCancel);
            tbInput.setCommandListener(this);
            mpe.display.setCurrent(tbInput);
        } else if (c == cmdRename && lFiles.getSelectedIndex() > 0) {
            input = 2;
            tbInput = new TextBox("\u8F93\u5165\u65B0\u6587\u4EF6\u540D\u79F0", lFiles.getString(lFiles.getSelectedIndex()).charAt(lFiles.getString(lFiles.getSelectedIndex()).length() - 1) != '/' ? lFiles.getString(lFiles.getSelectedIndex()) : lFiles.getString(lFiles.getSelectedIndex()).substring(0, lFiles.getString(lFiles.getSelectedIndex()).length() - 1), 256, 0);
            tbInput.addCommand(cmdOk);
            tbInput.addCommand(cmdCancel);
            tbInput.setCommandListener(this);
            mpe.display.setCurrent(tbInput);
        } else if (c == cmdDelete && lFiles.getSelectedIndex() > 0) {
            delete(curPath + lFiles.getString(lFiles.getSelectedIndex()));
        } else if (c == cmdOk) {
            if (d == tbInput) {
                switch (input) {
                    case 0: // '\0'
                        save(curPath + tbInput.getString());
                        break;

                    case 1: // '\001'
                        createDir(curPath + tbInput.getString());
                        break;

                    case 2: // '\002'
                        rename(curPath + lFiles.getString(lFiles.getSelectedIndex()), tbInput.getString());
                        break;
                }
            } else {
                mpe.display.setCurrent(lFiles);
            }
        } else if (c == cmdCancel) {
            if (d == lFiles) {
                mpe.display.setCurrent(mpe.w);
                System.gc();
            } else if (d == tbInput) {
                mpe.display.setCurrent(lFiles);
            }
        }
    }

    public void show(boolean opening) {
        this.opening = opening;
        if (curPath.equals("") || isRoot) {
            showRoot();
        } else {
            showDir(curPath);
        }
    }

    private void showRoot() {
        final FileManager fm = this;
        (new Thread(new Runnable() {

            public void run() {
                lFiles = new List(opening ? "\u6253\u5F00" : "\u4FDD\u5B58", 3);
                Enumeration e = FileSystemRegistry.listRoots();
                try {
                    while (e.hasMoreElements()) {
                        lFiles.append(e.nextElement().toString(), Image.createImage("/mpe/res/disk.png"));
                    }
                } catch (Exception exception) {
                }
                isRoot = true;
                curPath = "";
                lFiles.addCommand(cmdCancel);
                lFiles.setCommandListener(fm);
                mpe.display.setCurrent(lFiles);
            }
        })).start();
    }

    private void showDir(final String path) {
        final FileManager fm = this;
        (new Thread(new Runnable() {

            public void run() {
                lFiles = new List(path, 3);
                try {
                    FileConnection fconn = (FileConnection) Connector.open("file://localhost/" + path);
                    Enumeration e = fconn.list("*", true);
                    lFiles.append("..", Image.createImage("/mpe/res/folder.png"));
                    int c = 1;
                    while (e.hasMoreElements()) {
                        String temp = e.nextElement().toString();
                        if (temp.charAt(temp.length() - 1) == '/') {
                            lFiles.insert(c++, temp, Image.createImage("/mpe/res/folder.png"));
                        } else if (temp.endsWith(".java")) {
                            lFiles.append(temp, java);
                        } else {
                            lFiles.append(temp, file);
                        }
                    }
                    fconn.close();
                } catch (Exception exception) {
                }
                isRoot = false;
                if (!opening) {
                    lFiles.addCommand(cmdSaveHere);
                }
                lFiles.addCommand(cmdCreateDir);
                lFiles.addCommand(cmdDelete);
                lFiles.addCommand(cmdRename);
                lFiles.addCommand(cmdCancel);
                lFiles.setCommandListener(fm);
                mpe.display.setCurrent(lFiles);
            }
        })).start();
    }

    private void rename(final String from, final String to) {
        new Thread(new Runnable() {

            public void run() {
                try {
                    FileConnection fconn = (FileConnection) Connector.open("file:///" + from);
                    fconn.rename(to);
                    fconn.close();
                    showDir(curPath);
                } catch (Exception exception) {
                }
            }
        }).start();
    }

    private void delete(final String fn) {
        new Thread(new Runnable() {

            public void run() {
                try {
                    FileConnection fconn = (FileConnection) Connector.open("file:///" + fn);
                    fconn.delete();
                    fconn.close();
                    showDir(curPath);
                } catch (Exception exception) {
                }
            }
        }).start();
    }

    private void createDir(final String dirName) {
        (new Thread(new Runnable() {

            public void run() {
                try {
                    FileConnection fconn = (FileConnection) Connector.open("file:///" + dirName);
                    System.out.println("file:///" + dirName);
                    fconn.mkdir();
                    fconn.close();
                    showDir(curPath);
                } catch (Exception exception) {
                }

            }
        })).start();
    }

    private String replace(String from, String to, String source) {
        if (source == null || from == null || to == null) {
            return null;
        }
        StringBuffer bf = new StringBuffer();
        for (int index = -1; (index = source.indexOf(from)) != -1; index = -1) {
            bf.append(source.substring(0, index) + to);
            source = source.substring(index + from.length());
        }

        bf.append(source);
        return bf.toString();
    }

    public void open(final String fn) {
        (new Thread(new Runnable() {

            public void run() {
                if (!mpe.w.openDocument(fn)) {
                    try {
                        FileConnection fconn = (FileConnection) Connector.open("file://localhost/" + fn);
                        InputStream is = fconn.openInputStream();
                        long l = fconn.fileSize();
                        if (l < 0x7fffffffL) {
                            byte b[] = new byte[(int) fconn.fileSize()];
                            is.read(b);
                            String str = "";
                            try {
                                str = (new HGB2312()).gb2utf8(b);
                            } catch (Exception e) {
                                str = new String(b, "UTF-8");
                            }
                            String s[] = Utils.splitString(replace("\r", "", str), "\n");
                            System.out.println(s[0]);
                            b = (byte[]) null;
                            Document doc = new Document(fn, false, mpe.w.maxLines);
                            for (int i = 0; i < s.length; i++) {
                                doc.data.addElement(s[i]);
                            }

                            mpe.w.addDocument(doc);
                        }
                        is.close();
                        fconn.close();
                    } catch (Exception exception) {
                    }
                }
                mpe.w.repaint();
                mpe.display.setCurrent(mpe.w);
                System.gc();
            }
        })).start();
    }

    public void save(final String fn) {
        (new Thread(new Runnable() {

            public void run() {
                try {
                    FileConnection fconn = (FileConnection) Connector.open("file://localhost/" + fn);
                    if (fconn.exists()) {
                        fconn.truncate(0L);
                    } else {
                        fconn.create();
                    }
                    OutputStream os = fconn.openOutputStream();
                    String temp = "";
                    for (int i = 0; i < mpe.w.getDocument().data.size(); i++) {
                        temp = temp + mpe.w.getDocument().data.elementAt(i).toString() + (i == mpe.w.getDocument().data.size() - 1 ? "" : "\n");
                    }

                    os.write(Utils.encodeUTF8(temp));
                    temp = null;
                    mpe.w.getDocument().filename = fn;
                    os.close();
                    fconn.close();
                } catch (Exception exception) {
                }
                mpe.display.setCurrent(mpe.w);
                System.gc();
            }
        })).start();
    }

    public void restore() {
        String files[] = Utils.splitString(Database.lastsession, "\n");
        for (int i = 0; i < files.length; i++) {
            try {
                FileConnection fconn = (FileConnection) Connector.open("file://localhost/" + files[i]);
                InputStream is = fconn.openInputStream();
                long l = fconn.fileSize();
                if (l < 0x7fffffffL) {
                    byte b[] = new byte[(int) l];
                    is.read(b);
                    String str = "";
                    try {
                        str = (new HGB2312()).gb2utf8(b);
                    } catch (Exception e) {
                        str = new String(b, "UTF-8");
                    }
                    String s[] = Utils.splitString(replace("\r", "", str), "\n");
                    b = (byte[]) null;
                    Document doc = new Document(files[i], false, mpe.w.maxLines);
                    for (int j = 0; j < s.length; j++) {
                        doc.data.addElement(s[j]);
                    }

                    mpe.w.addDocument(doc);
                }
                is.close();
                fconn.close();
            } catch (Exception exception) {
            }
        }

        mpe.w.repaint();
        mpe.display.setCurrent(mpe.w);
        System.gc();
    }
    private final int INPUT_FILENAME = 0;
    private final int INPUT_DIRNAME = 1;
    private final int INPUT_RENAME = 2;
    private int input;
    private List lFiles;
    private String curPath;
    private boolean isRoot;
    private boolean opening;
    private Command cmdSaveHere;
    private Command cmdOk;
    private Command cmdCancel;
    private Command cmdDelete;
    private Command cmdCreateDir;
    private Command cmdRename;
    private TextBox tbInput;
    private MPE mpe;
    private Image java;
    private Image file;
}
