
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Utils.java
package mpe;

import java.io.ByteArrayOutputStream;
import java.io.UTFDataFormatException;
import java.util.Vector;

public class Utils {

    public Utils() {
    }

    public static String byteArrayToString(byte bytes[], String encoding) {
        char map[];
        map = "\u0402\u0403\u201A\u0453\u201E\u2026\u2020\u2021\u20AC\u2030\u0409\u2039\u040A\u040C\u040B\u040F\u0452\u2018\u2019\u201C\u201D\u2022\u2013\u2014\uFFFD\u2122\u0459\u203A\u045A\u045C\u045B\u045F\240\u040E\u045E\u0408\244\u0490\246\247\u0401\251\u0404\253\254\255\256\u0407\260\261\u0406\u0456\u0491\265\266\267\u0451\u2116\u0454\273\u0458\u0405\u0455\u0457\u0410\u0411\u0412\u0413\u0414\u0415\u0416\u0417\u0418\u0419\u041A\u041B\u041C\u041D\u041E\u041F\u0420\u0421\u0422\u0423\u0424\u0425\u0426\u0427\u0428\u0429\u042A\u042B\u042C\u042D\u042E\u042F\u0430\u0431\u0432\u0433\u0434\u0435\u0436\u0437\u0438\u0439\u043A\u043B\u043C\u043D\u043E\u043F\u0440\u0441\u0442\u0443\u0444\u0445\u0446\u0447\u0448\u0449\u044A\u044B\u044C\u044D\u044E\u044F".toCharArray();
        if (encoding.equals("UTF-8")) {
            encoding = "ISO-8859-1";
        }
        try {
            return decodeUTF8(bytes, false);
        } catch (UTFDataFormatException ex) {

            char chars[] = new char[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[i] = b < 0 ? map[b + 128] : (char) b;
            }

            return new String(chars);
        }
    }

    public static byte[] encodeUTF8(String text) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c != 0 && c < '\200') {
                baos.write(c);
            } else if (c == 0 || c >= '\200' && c < '\u0800') {
                baos.write((byte) (0xc0 | 0x1f & c >> 6));
                baos.write((byte) (0x80 | 0x3f & c));
            } else {
                baos.write((byte) (0xe0 | 0xf & c >> 12));
                baos.write((byte) (0x80 | 0x3f & c >> 6));
                baos.write((byte) (0x80 | 0x3f & c));
            }
        }

        byte ret[] = baos.toByteArray();
        return ret;
    }

    private static String decodeUTF8(byte data[], boolean gracious)
            throws UTFDataFormatException {
        StringBuffer ret = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            try {
                byte a = data[i];
                if ((a & 0x80) == 0) {
                    ret.append((char) a);
                } else if ((a & 0xe0) == 192) {
                    byte b = data[i + 1];
                    if ((b & 0xc0) == 128) {
                        ret.append((char) ((a & 0x1f) << 6 | b & 0x3f));
                        i++;
                    } else {
                        throw new UTFDataFormatException("Illegal 2-byte group");
                    }
                } else if ((a & 0xf0) == 224) {
                    byte b = data[i + 1];
                    byte c = data[i + 2];
                    if ((b & 0xc0) == 128 && (c & 0xc0) == 128) {
                        ret.append((char) ((a & 0xf) << 12 | (b & 0x3f) << 6 | c & 0x3f));
                        i += 2;
                    } else {
                        throw new UTFDataFormatException("Illegal 3-byte group");
                    }
                } else if ((a & 0xf0) == 240 || (a & 0xc0) == 128) {
                    throw new UTFDataFormatException("Illegal first byte of a group");
                }
            } catch (UTFDataFormatException udfe) {
                if (gracious) {
                    ret.append("?");
                } else {
                    throw udfe;
                }
            } catch (ArrayIndexOutOfBoundsException aioobe) {
                if (gracious) {
                    ret.append("?");
                } else {
                    throw new UTFDataFormatException("Unexpected EOF");
                }
            }
        }

        return ret.toString();
    }

    public static String[] splitString(String str, String delim) {
        if (str == null) {
            return null;
        }
        if (str.equals("") || delim == null || delim.length() == 0) {
            return (new String[]{
                        str
                    });
        }
        Vector v = new Vector();
        int pos = 0;
        for (int newpos = str.indexOf(delim, pos); newpos != -1; newpos = str.indexOf(delim, pos)) {
            v.addElement(str.substring(pos, newpos));
            pos = newpos + delim.length();
        }

        v.addElement(str.substring(pos));
        String s[] = new String[v.size()];
        for (int i = 0; i < s.length; i++) {
            s[i] = (String) v.elementAt(i);
        }

        return s;
    }

    public static String stringDelete(String str, int startIndex, int endIndex) {
        if (endIndex < str.length()) {
            return str.substring(0, startIndex).concat(str.substring(endIndex));
        } else {
            return str.substring(0, str.length() - 1);
        }
    }

    public static String stringDelete(String str, int index) {
        return stringDelete(str, index, index + 1);
    }

    public static String stringInsert(String str, String ins, int index) {
        if (index > 0) {
            return str.substring(0, index) + ins + str.substring(index);
        } else {
            return ins + str;
        }
    }

    public static String stringReplace(String str, String replace, int index) {
        return stringInsert(stringDelete(str, index, index + replace.length()), replace, index);
    }

    public static String replace(String s, String s1, String s2) {
        String out = " ";
        s = s + " ";
        for (int i = s.indexOf(s1); i > -1; i = s.indexOf(s1)) {
            out = out + s.substring(0, i) + s2;
            s = s.substring(i + s1.length());
        }

        out = out + s;
        return out.substring(1, out.length() - 1);
    }

    public static String processMacros(String s) {
        return replace(s, "^n", "\n");
    }
}
