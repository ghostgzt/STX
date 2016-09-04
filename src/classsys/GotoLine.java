package classsys;

public class GotoLine extends Line {

    public String first, second, gotolable;
    public int sig, gotooff;

    public GotoLine(int start, int end, int i, String first, String second, int offset, int code) {
        super(start, end, i, "if(" + first + " " + ByteCodeReader.getold(code) + " " + second + ")");
        sig = code;
        this.first = first;
        this.second = second;
        gotooff = offset;

    }

    public String toString() {
        String s;
        if (first != null) {
            //startOffset + "-" + endOffset + "  " + index + ": " +
            s = value + "goto " + gotolable + ";";
        } else {
            s = ">>>>>goto " + gotolable + ";";
        }
        if (sig == 3) {
            s = first + "goto " + gotolable + ";" + second + "\r\n";
        }
        if (!value.startsWith("try{")) {
            return lable + s;
        } else {
            return "try{\r\n    " + lable + s.substring(6);
        }
    }

    public void setGotoLable(int l) {
        gotolable = "L" + l;
    }
}
