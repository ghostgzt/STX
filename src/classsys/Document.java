package classsys;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public class Document {

    Vector lines = new Vector();
    int num = 0;

    public int write(Line l) {
        lines.addElement(l);
        if (l instanceof GotoLine) {
            num++;

        }
        // lines.add(new Line(startOffset, endOffset - 1, index, s));
        return l.endOffset + 1;
    }

    public void show(OutputStream o) throws IOException {
        sys(lines);
        //  Object l;
        String s;
        for (int i = 0; i < lines.size(); i++) {
            s = lines.elementAt(i).toString();
            StringBuffer sb = s.startsWith("L") ? new StringBuffer(" ") : new StringBuffer("    ");
            o.write((sb.append(s).append("\r\n").toString()).getBytes("Utf-8"));
        }
    }

    public int find(int off) {
        for (int i = 0; i < lines.size(); i++) {
            if (((Line) lines.elementAt(i)).match(off)) {
                return i;
            }
        }

        return 0;
    }

    private void sys(Vector v) {
        if (num > 0) {
//
//            int[] from = new int[num];
//            int to[] = new int[num];
//
//            //  int sum[] = new int[num * 2];
//            int now = 0;
//            int off = 0;
            GotoLine g;
            for (int i = 0; i < v.size(); i++) {
                if (v.elementAt(i) instanceof GotoLine) {
                    g = (GotoLine) v.elementAt(i);
                    g.setGotoLable(g.gotooff);
                    ((Line) (lines.elementAt(find(g.gotooff)))).setLable(g.gotooff);
                    //  sum[off++] =
                    //   from[now] = g.startOffset;
                    //  sum[off++] =
                    //   to[now++] = g.gotooff;
                }
            }
            //  java.util.Arrays.sort(to);
//            int temp;
//            for (now = num - 1; now >= 1; now--) {
//                for (off = 0; off < now; off++) {
//                    if (to[off] > to[off + 1]) {
//                        temp = to[off];
//                        to[off] = to[off + 1];
//                        to[off + 1] = temp;
//                        temp = from[off];
//                        from[off] = from[off + 1];
//                        from[off + 1] = temp;
//                    }
//                }
//            }
//            now = to[0];
//            off = 0;
//            for (int i = 0; i < num;) {
//                while (i < num && now == to[i]) {
//                    //System.out.println(now);
//                    ((Line) (lines.elementAt(find(now)))).setLable(off);
//                    ((GotoLine) (lines.elementAt(find(from[i])))).setGotoLable(off);
//                    i++;
//                }
//                if (i < num) {
//                    now = to[i];
//                    off++;
//                }
//            }
//            ((Line) (lines.elementAt(find(now)))).setLable(off);

        }
        for (int i = 0; i < quote.size(); i++) {
            ((Line) lines.elementAt(find(((Integer) quote.elementAt(i)).intValue()) - 1)).addTail("\r\n  }");
            System.err.println(((Integer) quote.elementAt(i)).intValue());
        }
    }
    Vector quote = new Vector();

    void addQuote(int i) {
        quote.addElement(new Integer(i));

    }
}
