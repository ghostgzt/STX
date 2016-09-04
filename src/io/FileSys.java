package io;

import chen.c;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

public class FileSys {

    public static Hashtable in, out;

    public static byte[] getdata(String f, int off, int length) {
        try {
            byte[] b = null;
            FileConnection fcc = getfile(f);
            if (fcc == null) {
                c.ready = true;
                return b;
            }
            int len = (int) fcc.fileSize();
            if (len == 0) {
                return new byte[0];
            }
            if (length <= 0 || length > len) {
                length = (int) (len - off);
            }
            if (length <= 0 || off >= len || off < 0) {
                return b;
            }

            try {
                b = new byte[length];
            } catch (OutOfMemoryError e) {
                return b;
            }
            DataInputStream input = fcc.openDataInputStream();
            if (off > 0) {
                int l;
                if (length >= off) {
                    input.readFully(b, 0, off);
                } else {
                    byte[] buffer = length >= 40960 ? b : new byte[Math.min(off, 40960)];
                    while (off > 0) {
                        l = Math.min(off, 40960);
                        input.readFully(buffer, 0, l);
                        off -= l;
                    }
                    if (buffer != b) {
                        buffer = null;
                    }
                }
            }
            input.readFully(b);
            input.close();
            return b;

        } catch (Exception ex) {
            return null;
        }

    }

    public static InputStream getinputstream(String f) {
        try {
            FileConnection fcin = getfile(f);
            if (fcin == null || !fcin.exists()) {
                return null;
            }
            return fcin.openInputStream();
        } catch (Exception ex) {
            show(ex.toString());
            return null;
        }
    }

    public static FileConnection getfile(String u) {
        try {
            FileConnection fcc;
            if (in.containsKey(u)) {
                fcc = (FileConnection) in.get(u);
            } else {
                fcc = (FileConnection) Connector.open(u, Connector.READ);
                if (fcc != null) {
                    in.put(u, fcc);
                }
            }
            return fcc;
        } catch (SecurityException ep) {
            c.show.show("权限错误", ep.toString());
        } catch (Throwable ex) {
            show(ex.toString());
        }
        return null;
    }

    public static FileConnection getoutfc(String u) {

        try {
            FileConnection fcc;
            if (out.containsKey(u) && ((FileConnection) out.get(u)).getPath().equals(u)) {
                fcc = (FileConnection) out.get(u);

            } else {
                fcc = (FileConnection) Connector.open(u, Connector.WRITE);
                if (fcc != null) {
                    out.put(u, fcc);
                }

            }
            return fcc;
        } catch (SecurityException ep) {
            c.show.show("权限错误", ep.toString());
        } catch (Exception ex) {
            show(ex.toString());
        }
        return null;
    }

    public static OutputStream getOutputStream(String u) {

        try {
            FileConnection fcout = getoutfc(u);
            try {
                fcout.delete();
            } catch (Exception e) {
            }
            try {
                fcout.create();
                c.append(u);
            } catch (Exception e) {
            }
            return fcout.openOutputStream();
        } catch (Exception ex) {
            show(ex.toString());
        }
        return null;
    }

    public static void makedir(String u) {

        try {
            FileConnection fc = getoutfc(u);
            fc.mkdir();
        } catch (IOException ex) {
        }

    }

    public static boolean savefile(String u, byte[] b, int off, int len) {

        String fail = null;
        if (b == null) {
            return true;
        }
        if (u.toLowerCase().endsWith(".jar") || u.toLowerCase().endsWith(".jad")) {
            fail = u;
            u = u + "d";

        }
        try {
            OutputStream o = getOutputStream(u);
            if (o == null) {
                return false;
            }
            if (len <= 0) {
                len = b.length - off;
            }
            o.write(b, off, len);
            o.flush();
            o.close();
            if (fail != null) {
                FileSys.getoutfc(u).rename(fail.substring(fail.lastIndexOf('/')) + 1);
            }
            return true;
        } catch (Exception ex) {
            show(ex.toString());
            return false;
        }
    }

    static void show(String s) {
        c.show.showError(s);
    }
}
