package gui;

import io.FileSys;
import io.BufOutputStream;
import chen.c;
import io.BufDataOutputStream;
import zip.ZipOutputStream;

public class GetPng implements Runnable {

    String f, n;
    byte[] d;

    public GetPng(String file, String name, byte[] data) {
        f = file;
        n = name;
        d = data;
    }

    void getsvg(byte[] b) {
        BufOutputStream buf = new BufOutputStream();
        ZipOutputStream os = new ZipOutputStream(new io.BufDataOutputStream(buf), 10240);
        for (int i = 0; i < b.length - 8; i++) {
            if (b[i] != 60 || b[i + 1] != 63 || b[i + 2] != 120 || b[i + 3] != 109) {
                continue;
            }
            for (int j = i + 5; j < b.length; j++) {
                if (b[j - 1] == 47 && b[j] == 115 && b[j + 1] == 118 && b[j + 2] == 103 && b[j + 3] == 62) {
                    try {
                        os.putNextEntry(n + i + ".svg");
                        os.write(b, i, j - i + 4);
                        i += j - i;
                        break;
                    } catch (Exception ex) {
                    }
                }
            }

        }
        if (buf.size() > 0) {
            FileSys.savefile(f + "svg.zip", buf.getbuf(), 0, buf.size());
        }

        d = null;
    }

    private void b(byte b[]) {
        BufOutputStream buf = new BufOutputStream();
        ZipOutputStream os = new ZipOutputStream(new BufDataOutputStream(buf), 10240);
        int l = b.length;
        int k = l - 8;
        for (int i = 0; i < k; i++) {
            if (b[i] != -119 || b[i + 1] != 80 || b[i + 2] != 78 || b[i + 3] != 71) {
                continue;
            }
            for (int j = i; j < l; j++) {
                if (b[j] == 66 && b[j + 1] == 96 && b[j + 2] == -126) {
                   try {
                        os.putNextEntry(n + i + ".png");
                        os.write(b, i, j - i + 3);
                        i += j - i;
                        os.closeEntry();
                        break;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

        }
        if (buf.size() > 0) {
            try {
                os.finish();
            } catch (Exception ex) {
            }
            FileSys.savefile(f + "get.zip", buf.getbuf(), 0, buf.size());
        } else if (c.autotxt) {
            int i = 0;
            while (i < b.length) {
                if (b[i] == 0) {
                    b[i] = 0x20;
                }
                i++;
            }
            FileSys.savefile(f + ".txt", d, 0, 0);
        }

    }

    public void run() {
        if (f.toLowerCase().endsWith("mif")) {
            getsvg(d);
        } else {
            b(d);
        }
    }
}
