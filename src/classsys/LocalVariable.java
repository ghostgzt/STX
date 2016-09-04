package classsys;

public class LocalVariable {

    public int start, length, index,end;
    public String name, type;

    public LocalVariable(int s, int l, int i, String n, String t) {
        start = s;
        length = l;
        index = i;
        end=s+l;
        name = n;
        type = t;
    }
}
