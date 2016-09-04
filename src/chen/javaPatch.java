package chen;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class javaPatch {

    static int offset;

    public static void main(byte[] ap, String s) throws IOException {
        int c0, c1, c2, c3;
        int old_code_c0 = 0, old_code_c1 = 0, old_code_c2 = 0, old_code_c3 = 0;
        int[] old_RFormat_Open = new int[400];
        int i, step = 0, bytes_written;
        int where_RFile_Open = 0, code_addr = 0;
        int where_RFormat_Open = 0;
        int low_bits, high_bits, old_where_jmp = 0, new_where_jmp = 0;
        int len = ap.length;
        offset = 16;
        c0 = getc(ap);
        c1 = getc(ap);
        c2 = getc(ap);
        c3 = getc(ap);
        code_addr = c3 * 256 * 256 * 256 + c2 * 256 * 256 + c1 * 256 + c0;
        //printf("code_addr=" + hex(code_addr, 8));

        while (len > offset && where_RFile_Open == 0) {
            c0 = getc(ap);
            c1 = getc(ap);
            if (c0 == 0x10 && c1 == 0xB5) {
                c0 = getc(ap);
                c1 = getc(ap);
                if (c0 == 0x86 && c1 == 0xB0) {
                    c0 = getc(ap);
                    c1 = getc(ap);
                    if (c0 == 0x01 && c1 == 0x92) {
                        c0 = getc(ap);
                        c1 = getc(ap);
                        if (c0 == 0x07 && c1 == 0x22) {
                            c0 = getc(ap);
                            c1 = getc(ap);
                            where_RFile_Open = offset - 10;
                            // printf("RFile::Open=" + hex(code_addr - 0x78 + where_RFile_Open, 8));
                            for (i = 0; i < 14; i++) {
                                c0 = getc(ap);
                                c1 = getc(ap);
                                if (c0 == 0x02 && c1 == 0x94) {
                                    c0 = getc(ap);
                                    c1 = getc(ap);
                                    c2 = getc(ap);
                                    c3 = getc(ap);
                                    //printf("jmp to= " + hex(c0, 2) + " " + hex(c1, 2) + " " + hex(c2, 2) + " " + hex(c3, 2));
                                    high_bits = (c1 * 256 + c0) & 0x000007FF;
                                    low_bits = ((c3 * 256 + c2) & 0x000007FF) * 2;
                                    //printf("low_bits=" + hex(low_bits, 8));
                                    //printf("high_bits=" + hex(high_bits, 8));
                                    old_where_jmp = (high_bits * 256 * 16 + low_bits) * 1 + 2;
                                    // printf("old_where_jmp=" + hex(old_where_jmp, 8));
                                    // printf("j_Session=" + hex(code_addr - 0x79 + offset + old_where_jmp, 8));
                                    old_where_jmp = code_addr - 0x79 + offset + old_where_jmp;
                                    //printf("old_where_jmp=" + hex(old_where_jmp, 8));
                                }
                            }
                        }
                        offset = where_RFile_Open;
                        old_code_c0 = getc(ap);
                        old_code_c1 = getc(ap);
                        old_code_c2 = getc(ap);
                        old_code_c3 = getc(ap);
                    }
                }
            }
        }
        offset = 0;
        while (len > offset && where_RFormat_Open == 0) {
            c0 = getc(ap);
            c1 = getc(ap);
            if (c0 == 0xF0 && c1 == 0xB5) {
                c0 = getc(ap);
                c1 = getc(ap);
                if (c0 == 0x04 && c1 == 0x00) {
                    c0 = getc(ap);
                    c1 = getc(ap);
                    if (c0 == 0x16 && c1 == 0x00) {
                        c0 = getc(ap);
                        c1 = getc(ap);
                        if (c0 == 0x0D && c1 == 0x00) {
                            c0 = getc(ap);
                            c1 = getc(ap);
                            offset -= 10;
                            where_RFormat_Open = offset;
                            new_where_jmp = code_addr - 0x78 + where_RFormat_Open;
                            //  printf("RFormat::Open=" + hex(new_where_jmp, 8));
                            for (i = 0; i < 0x4 * 80; i++) {
                                c0 = getc(ap);
                                old_RFormat_Open[i] = c0;
                            }
                        }
                    }
                }
            }
        }

        //printf("where_RFile_Open=" + hex(where_RFile_Open, 4));
        // printf("where_RFormat_Open=" + hex(where_RFormat_Open, 4));
        // printf("driveCbeforeZ");
        // printf("just4U");
        StringBuffer sb = new StringBuffer("rel:sys\\bin\\EFSrv.dll:");
        sb.append(hex(where_RFormat_Open + 0x4 * 80 - 4, 8)).append(':');
        int j = 4;
        while (j > 0) {
            sb.append(hex(old_RFormat_Open[0x4 * 80 - j], 2));
            j--;
        }
        sb.append(':');
        j = 4;
        while (j > 0) {
            sb.append(hex(old_RFormat_Open[0x4 * 80 - j], 2));
            j--;
        }
        //printf(sb.toString());
        OutputStream out = io.FileSys.getOutputStream(s + "c2z.rmp");
        fprintf(out, "; c2z-20080511\r\n");
        fprintf(out, "; maps Z: to C: for some files\r\n");
        fprintf(out, sb.toString());
        fprintf(out, "\r\n");
        //printf("real:" + hex(where_RFormat_Open + code_addr - 0x70, 8) + hex(where_RFormat_Open + code_addr - 0x70 + 0x4 * 80, 8));
        sb.setLength(0);
        sb.append("rel:sys\\bin\\EFSrv.dll:").append(hex(where_RFormat_Open, 8)).append(':');
        for (i = 0; i < 0x4 * 80 - 3 * 4; i++) {
            sb.append(hex(old_RFormat_Open[i], 2));
        }
        sb.append(':');
        InputStream in = "".getClass().getResourceAsStream("/chen/p");
        int l = in.available();
        byte[] bininput = new byte[l];
        int t = l;
        while (t > 0) {
            t -= in.read(bininput, l - t, t);
        }
        in.close();
        step = 0;
        bytes_written = 4;
        int off = 0;
        while (off < l) {
            c0 = bininput[off++] & 0xff;
            c1 = bininput[off++] & 0xff;
            c2 = bininput[off++] & 0xff;
            c3 = bininput[off++] & 0xff;
            bytes_written += 4;
            if (c0 == 0xA9) {
                c0 = old_where_jmp & 0xFF;
                sb.append(hex(c0, 2)).append("60A0E3");
                step = 2;
            } else if (step == 2) {
                c0 = (old_where_jmp >> 8) & 0xFF;
                // printf("old_where_jmp=" + hex(old_where_jmp, 8) +" - "+ hex(c0, 8));
                sb.append(hex(c0, 2)).append("6C86E2");
                step++;
            } else if (step == 3) {
                c0 = (old_where_jmp >> 16) & 0xFF;
                sb.append(hex(c0, 2)).append("6886E2");
                step++;
            } else if (step == 4) {
                c0 = (old_where_jmp >> 24) & 0xFF;
                sb.append(hex(c0, 2)).append("6486E2");
                step = 0;
            } else if (off <= l) {
                sb.append(hex(c0, 2)).append(hex(c1, 2)).append(hex(c2, 2)).append(hex(c3, 2));
            }
        }
        for (i = bytes_written; i <= 0x4 * 80 - 3 * 4; i += 4) {
            sb.append("01234567");
        }
        fprintf(out, sb.toString());
        fprintf(out, "\r\n");
        sb.setLength(0);
        sb.append("rel:sys\\bin\\EFSrv.dll:").append(hex(where_RFile_Open, 8)).append(':').append(hex(old_code_c0, 2));
        sb.append(hex(old_code_c1, 2)).append(hex(old_code_c2, 2)).append(hex(old_code_c3, 2));
        sb.append("019207221C0005921E2201AB").append(':');
        sb.append("10B5014CA04710BD");
        sb.append(hex((new_where_jmp >> 0) & 0xFF, 2));
        sb.append(hex((new_where_jmp >> 8) & 0xFF, 2));
        sb.append(hex((new_where_jmp >> 16) & 0xFF, 2));
        sb.append(hex((new_where_jmp >> 24) & 0xFF, 2));
        sb.append(hex((new_where_jmp >> 0) & 0xFF, 2));
        sb.append(hex((new_where_jmp >> 8) & 0xFF, 2));
        sb.append(hex((new_where_jmp >> 16) & 0xFF, 2));
        sb.append(hex((new_where_jmp >> 24) & 0xFF, 2));
        fprintf(out, sb.toString());
        fprintf(out, "\r\n");
        out.close();
        in = "".getClass().getResourceAsStream("/chen/m");
        l = in.available();
        bininput = new byte[l];
        t = l;
        while (t > 0) {
            t -= in.read(bininput, l - t, t);
        }
        in.close();
        io.FileSys.savefile(s + "midp2_rp.xpf", bininput, 0, l);
    }

    private static String hex(int num, int len) {
        StringBuffer sb = new StringBuffer(Integer.toHexString(num));
        while (sb.length() < len) {
            sb.insert(0, '0');
        }
        return sb.toString().toUpperCase();
    }

    private static int getc(byte[] ap) throws IOException {
        return ap[offset++] & 0xff;
    }

    //  private static void printf(String string) {
    //      System.out.println(string);
    //  }
    private static void fprintf(OutputStream out, String string) throws IOException {
        out.write(string.getBytes());
    }
}
