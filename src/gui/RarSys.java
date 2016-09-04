package gui;

import io.MemorySet;
import io.ContDataInputStream;
import chen.c;
import io.FileSys;
import rar.unpack.FileHeader;
import java.io.*;
import java.util.Hashtable;
import java.util.Vector;
import rar.unpack.ComprDataIO;

import rar.unpack.Unpack;

public class RarSys extends Yasuo {

    private final ComprDataIO dataIO = new ComprDataIO();
    public Unpack unpack;
    int filelength;
    private boolean oldFormat;
    boolean isNewNumbering;

    public RarSys(String url, c v) {
        super(v, url);
        try {
            filelength = (int) io.FileSys.getfile(url).fileSize();
        } catch (Exception e) {
            return;
        }
        f = url;
        fileName = url.substring(url.lastIndexOf('/') + 1);

    }

    public RarSys(Yasuo z, String name, byte[] tb) {
        super(z.c, name);
        filelength = tb.length;
        f = z.f;
        b = tb;
        pre = z;
        fileName = name;

    }

    void setFile(InputStream in) throws IOException {
        close();
        rof = new ContDataInputStream(in);
        Showhelper.jindu = new Gauge(f, filelength, false);
        Showhelper.jindu.setIn(rof);
        try {
            readHeaders();
        } catch (Throwable t) {
            c.show.showError(t.toString());
            Showhelper.jindu.close();
            close();
        }

        Showhelper.jindu.close();
    }

    public boolean isSignature(short headCRC, byte headerType, short flags, short headerSize) {
        boolean valid = false;
        if (headCRC == 0x4552 && headerType == 126) {
            oldFormat = true;
            valid = true;
        } else if (headCRC == 0x6152 && headerType == 114 && flags == 6689 && headerSize == 7) {
            oldFormat = false;
            valid = true;
        }
        return valid;
    }

    public static String decode(byte[] name, int encPos) {
        int decPos = 0;
        int flags = 0;
        int flagBits = 0;
        int len = name.length;
        int highByte = name[encPos++] & 0xff;
        StringBuffer buf = new StringBuffer();
        while (encPos < len) {
            if (flagBits == 0) {
                flags = name[encPos++] & 0xff;
                flagBits = 8;
            }
            switch (flags >> 6) {
                case 0:
                    buf.append((char) (name[encPos++] & 0xff));
                    ++decPos;
                    break;
                case 1:
                    buf.append((char) ((name[encPos++] & 0xff) | (highByte << 8)));
                    ++decPos;
                    break;
                case 2:
                    buf.append((char) ((name[encPos++] & 0xff) | ((name[encPos++] & 0xff) << 8)));
                    ++decPos;
                    break;
                case 3:

                    int length = name[encPos++] & 0xff;
                    if ((length & 0x80) != 0) {
                        int correction = name[encPos++] & 0xff;
                        for (length = (length & 0x7f) + 2; length > 0 && decPos < len; length--) {
                            buf.append((char) ((highByte << 8) | (((name[decPos++] & 0xff) + correction) & 0xff)));
                        }
                    } else {
                        for (length += 2; length > 0 && decPos < len; length--, decPos++) {
                            buf.append((char) (name[decPos] & 0xff));
                        }
                    }
                    break;
            }
            flags = (flags << 2) & 0xff;
            flagBits -= 2;
        }
        return buf.toString();
    }

    private void readHeaders() throws IOException {
        jar.removeAllElements();
        while (rof.getOffst() < filelength) {
            short headCRC = (short) rof.readLshort();
            byte headerType = rof.readUnsignedByte();
            short flags = (short) rof.readLshort();
            short headerSize = (short) rof.readLshort();
            switch (headerType) {
                case 114: // 'r'
                    if (!isSignature(headCRC, headerType, flags, headerSize)) {
                        close();
                        throw new RuntimeException("badRarArchive");
                    }
                    continue;

                case 115: // 's'
                    rof.skip((flags & 0x200) == 0 ? 6 : 7);
                    isNewNumbering = (flags & 0x10) != 0;
                    if ((flags & 0x80) != 0) {
                        close();
                        throw new RuntimeException("rarEncryptedException");
                    }
                    continue;

                case 121: // 'y'
                    rof.skip(8);
                    continue;

                case 118: // 'v'
                    rof.skip(7);
                    continue;

                case 117: // 'u'
                    rof.skip(headerSize - 7);
                    continue;

                case 123: // '{'
                    if ((flags & 2) != 0) {
                        rof.skip(4);
                    }
                    if ((flags & 8) != 0) {
                        rof.skip(2);
                    }
                    return;

                case 116: // 't'
                case 119: // 'w'
                case 120: // 'x'
                case 122: // 'z'
                default:
                    switch (headerType) {
                        case 116: // 't'
                        case 122: // 'z'
                            FileHeader fh = new FileHeader();
                            String name;
                            fh.headerSize = headerSize;
                            fh.flag = flags;
                            fh.offset = rof.getOffst() - 7;
                            fh.compressedSize = rof.readLint();
                            fh.size = rof.readLint();
                            rof.read();
                            fh.crc = rof.readLint();
                            fh.dostime = rof.readLint();
                            fh.unpVersion = rof.readUnsignedByte();
                            fh.method = rof.readUnsignedByte();
                            short nameSize = (short) Math.min(rof.readLshort(), 4096);
                            rof.skip(4);
                            byte[] fileNameBytes = new byte[nameSize];
                            rof.readFully(fileNameBytes, 0, nameSize);
                            if (116 == headerType) {
                                if ((flags & 0x200) != 0) {
                                    int length = 0;
                                    while (length < nameSize && fileNameBytes[length++] != 0) {
                                    }
                                    if (length != nameSize) {
                                        name = decode(fileNameBytes, length);
                                    } else {
                                        name = new String(fileNameBytes, 0, length);
                                    }

                                } else {
                                    name = new String(fileNameBytes);
                                }
                                fh.name = name.replace('\\', '/');
                                Showhelper.jindu.show(name, rof.getOffst());
                            }
                            if (headerType == 116 && !fh.isDirectory()) {
                                jar.addElement(fh);
                            }
                            rof.skip(fh.compressedSize + fh.offset + headerSize - rof.getOffst());
                            continue;

                        case 120: // 'x'
                            rof.skip(headerSize - 11);
                            continue;

                        case 119: // 'w'

                            int subhead = rof.readLshort();
                            rof.readUnsignedByte();
                            switch (subhead) {
                                case 258:
                                    rof.skip(3);
                                    break;

                                case 256:
                                    rof.skip(10);
                                    break;

                                case 257:
                                    rof.skip(headerSize - 14);
                                    break;
                            }
                            break;

                        case 117: // 'u'
                        case 118: // 'v'
                        case 121: // 'y'
                        default:
                            close();
                            throw new RuntimeException("Not Rar Archive");
                    }
                    break;
            }
        }
    }

    private void doExtractFile(FileHeader hd, OutputStream os, byte[] buf) throws Exception {
        dataIO.init(os, hd, oldFormat, getInputstream(hd.offset + hd.headerSize));
        if (unpack == null) {
            unpack = new Unpack(dataIO);
        }
        if (!hd.isSolid()) {
            unpack.init(buf);
        }
        unpack.setDestSize(hd.size, hd.compressedSize);
        try {
            unpack.doUnpack(hd.unpVersion, hd.isSolid());
            int actualCRC = (hd.flag & 2) != 0 ? ~dataIO.getPackedCRC() : ~dataIO.getUnpFileCRC();
            if (actualCRC != hd.crc) {
                throw new RuntimeException("crc mismatch");
            }
        } catch (Exception e) {
            unpack.cleanUp();
            throw e;
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
        if (unpack != null) {
            unpack.cleanUp();
        }
    }

    public void run() {
        if (first) {
            first = false;
            hash = new Hashtable();
            jar = new Vector();
            try {
                if (b != null) {
                    tooBig = false;
                    setFile(new DataInputStream(new ByteArrayInputStream(b)));
                } else if (filelength < MemorySet.MAXREAD) {
                    tooBig = false;
                    setFile(new DataInputStream(new ByteArrayInputStream(b = io.FileSys.getdata(f, 0, 0))));
                } else {
                    b = null;
                    tooBig = true;
                    setFile(io.FileSys.getinputstream(f));
                }
                if (c != null) {
                    readEntrys(jar);
                    if (c.autotxt) {
                        fresh();
                    } else {
                        c.browser.deleteAll(true);
                        c.browser.append("上一级", 2);
                        for (int i = 0; i < jar.size(); i++) {
                            c.geticon(jar.elementAt(i).toString());
                        }
                    }
                    c.zip = true;
                    c.show.repaint();
                } else {
                    new Thread(this).start();
                }
            } catch (Exception ex) {
                c.show.show("Rarsys", ex.toString() + "  rof.off=" + rof.getOffst());
                back();
                return;
            }
        } else {
            super.run();
        }
    }

    public byte[] getdata(String name) {
        FileHeader df = (FileHeader) hash.get(dir + name);
        ByteArrayOutputStream bout = null;
        byte[] buf = new byte[Math.min(MemorySet.RARMEM, df.size)];
        try {
            if (df.method == 48) {
                getInputstream(df.offset + df.headerSize).readFully(buf, 0, df.size);
                return buf;
            }
            bout = new ByteArrayOutputStream(df.size);
            doExtractFile(df, bout, buf);
            buf = null;
            unpack.clear();
            return bout.toByteArray();
        } catch (Exception ex) {
            c.show.showError(ex.toString());
            return bout.toByteArray();
        } catch (OutOfMemoryError er) {
            c.show.showError("内存不足");
        } finally {
            if (bout != null) {
                bout.reset();
                try {
                    bout.close();
                } catch (IOException ex) {
                }
            }
            System.gc();
        }
        return null;
    }

    public int getSize(String name) {
        return (int) ((FileHeader) hash.get(dir + name)).size;
    }

    public void jieya(Vector jar) {
        FileHeader[] entrys = new FileHeader[jar.size() + 1];
        jar.copyInto(entrys);
        chose = entrys.length - 1;
        if (sortEntry) {
            sort(entrys, 0, entrys.length - 2);
        }
        FileHeader fh;
        String head;
        if (f.startsWith("file:///")) {
            head = f.substring(0, f.lastIndexOf('.') > 0 ? f.lastIndexOf('.') : f.length() - 1) + "/";
        } else {
            head = "file:///" + c.currDirName + f + (f.endsWith("/") ? "" : "/");
        }
        FileSys.makedir(head);
        path.clear();
        int num = chose;
        Showhelper.jindu = new Gauge(f, num, false);
        String filename;
        for (int i = 0; i < num; i++) {
            fh = (FileHeader) entrys[i];
            if (!fh.isDirectory()) {
                try {
                    filename = fh.name;
                    Showhelper.jindu.show(filename, i);
                    makedir(head, filename);
                    saveFile(head + (filename.endsWith(".jar") ? filename.substring(0, filename.lastIndexOf('.')) + ".zip" : filename), fh);
                } catch (Throwable t) {
                }
            }
        }
        Showhelper.jindu.close();
        c.show.repaint();
    }

    private void saveFile(String string, FileHeader fh) {
        OutputStream out = FileSys.getOutputStream(string);
        if (out == null) {
            return;
        }

        try {
            if (fh.method == 48) {
                byte buf[] = new byte[40960];
                ContDataInputStream in = getInputstream(fh.offset + fh.headerSize);
                int sum = fh.size;
                int len = 0;
                while (sum > 0) {
                    len = Math.min(40960, sum);
                    in.readFully(buf, 0, len);
                    out.write(buf, 0, len);
                    sum -= len;
                }
            } else {
                doExtractFile(fh, out, new byte[Math.min(MemorySet.RARMEM, fh.size)]);
            }
        } catch (Throwable ex) {
            c.show.showError(ex.toString());
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException ex) {
            }
        }
    }
}
