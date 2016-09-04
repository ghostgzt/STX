package zip;

import io.BufDataOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

public class ZipOutputStream extends DeflaterOutputStream {

    private Vector entries = new Vector();
    private CRC32 crc;
    private ZipEntry curEntry = null;
    private int curMethod, size;

    public ZipOutputStream(BufDataOutputStream out, int bufsize) {
        super(out, new Deflater(0, true), bufsize);
        crc = new CRC32();
    }

    public void setMethod(int method) {
        curMethod = method;
    }

    public void setLevel(int level) {
        def.setLevel(level);
    }

    public void preEntry() {
        curEntry = null;
    }

    public void putNextEntry(String name) throws Exception {
        ZipEntry entry = new ZipEntry(name);
        entry.flag = 8;
        entry.setMethod(curMethod);
        entry.setTime(System.currentTimeMillis());
        putNextEntry(entry);
    }

    public void putNextEntry(ZipEntry entry) throws Exception {
        if (curEntry != null) {
            closeEntry();
        }
        curEntry = entry;
        entries.addElement(entry);
        entry.offset = out.getoffse();
        out.writeLint(0x04034b50);//4
        out.writeLshort(entry.getMethod() == 0 ? 10 : 20);//6
        writeEntry(entry);
        out.write(curEntry.getNamebytes());
        if (entry.extralen > 0) {
            out.write(entry.getExtra());
        }
        crc.reset();
        def.reset();
        size = 0;
    }

    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }

    public void write(int b) throws IOException {
        write(new byte[]{(byte) b, 0, 1});
    }

    public void closeEntry() throws Exception {

        if (curEntry == null) {
            return;
        }
        if (curMethod == 8) {
            super.finish();
        }
        ZipEntry entry = curEntry;
        entry.size = size;
        entry.compressedSize = (curMethod == 8 ? def.getTotalOut() : size);
        entry.crc = (int) (crc.getValue());
        int now = out.getoffse();
        if (!out.resetTo(entry.offset + 6)) {
            out.writeLint(0x08074b50);
            out.writeLint(entry.crc);
            out.writeLint(entry.compressedSize);
            out.writeLint(entry.size);
        } else {
            entry.flag = 0;
            writeEntry(entry);
            out.skipto(now);
        }
        out.flush();
        curEntry = null;
    }

    public void write(byte b[], int off, int len) throws IOException {
        if (curMethod == 0) {
            out.write(b, off, len);
        } else {
            super.write(b, off, len);
        }
        crc.update(b, off, len);
        size += len;
    }

    private void writeEntry(ZipEntry entry) throws IOException {
        out.writeLshort(entry.flag);//8
        out.writeLshort(entry.getMethod());//10
        out.writeLint(entry.dostime);//14
        out.writeLint(entry.crc);//18
        out.writeLint(entry.compressedSize);//22
        out.writeLint(entry.size);//26
        out.writeLshort(entry.namelen);//28
        out.writeLshort(entry.extralen);//30
    }

    public void finish() throws Exception {
        if (entries == null) {
            return;
        }
        if (curEntry != null) {
            closeEntry();
        }
        int offset = out.getoffse();
        int numEntries = 0;
        int sizeEntries = 0;
        Enumeration enu = entries.elements();
        ZipEntry entry;
        while (enu.hasMoreElements()) {
            entry = (ZipEntry) enu.nextElement();
            int method = entry.getMethod();
            out.writeLint(0x02014b50);
            out.writeLshort(method == 0 ? 10 : 20);
            out.writeLshort(0);
            writeEntry(entry);
            out.writeLshort(entry.commentlen);
            out.writeLint(0);/* disk number; internal file attr */
            out.writeLint(0);   /* external file attr */
            out.writeLint(entry.offset);
            out.write(entry.getNamebytes());
            if (entry.extralen > 0) {
                out.write(entry.getExtra());
            }
            if (entry.commentlen > 0) {
                out.write(entry.comment);
            }
            numEntries++;
            sizeEntries += 46 + entry.namelen + entry.extralen + entry.commentlen;
        }
        out.writeLint(0x06054b50);//4
        out.writeLint(0); /* disk number ; disk with start of central dir *///8
        out.writeLshort(numEntries);
        out.writeLshort(numEntries);//12
        out.writeLint(sizeEntries);//16
        out.writeLint(offset);
        out.writeLshort(0);
        out.flush();
        entries = null;
    }
}
