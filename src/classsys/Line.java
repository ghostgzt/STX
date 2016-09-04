package classsys;

public class Line {

    public int index, startOffset, endOffset;
    public String value, lable;

    public Line(int start, int end, int i, String v) {
        startOffset = start;
        endOffset = end - 1;
        index = i;
        value = v;
        lable = "";
    }

    public boolean match(int offset) {
        return startOffset <= offset && endOffset >= offset;
    }

    
    public String toString() {
        if (!value.startsWith("try{")) {
            return //startOffset + "-" + endOffset + "  " + index + ": " +
                    lable + value;
        } else {

            return "try{\r\n    " + lable + value.substring(6);
        }
    }

    boolean addpoint() {
        if (value.startsWith("if(")) {
            return true;
        } else {
            value += "\r\n}";
            return false;
        }
    }

    void setLable(int l) {
        lable = "L" + l + ":\r\n    ";
    }

    void addHead(String h) {
        value = h + value;
    }

    void addTail(String t) {
        value += t;
    }

    void bind(Line s) {
        int l = value.indexOf('(');
        value = value.substring(0, l) + "(" + value.substring(l, value.indexOf(')') + 1);
    }
}
