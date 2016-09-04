package classsys;

public final class ConstantPool {

    public int index;
    public int type;
    public int intVal;
    public long longVal;
    public String strVal1;
    public String strVal2;
    public String strVal3;
    ConstantPool next;

    ConstantPool(final int index) {
        this.index = index;
    }

    ConstantPool(final int index, final ConstantPool i) {
        this.index = index;
        type = i.type;
        intVal = i.intVal;
        longVal = i.longVal;
        strVal1 = i.strVal1;
        strVal2 = i.strVal2;
        strVal3 = i.strVal3;

    }

    public String toString() {

        switch (type) {
            case Classsys.INT:
                return "int: " + intVal;
            case Classsys.FLOAT:
                return "float: " + intVal;
            case Classsys.LONG:
                return "long: " + longVal;
            case Classsys.DOUBLE:
                return "double: " + longVal;
            case Classsys.CLASS:
                return "class: " + Classsys.change3(strVal1);
            case Classsys.STR:
                return "string: " + strVal1;
            case Classsys.METH:
                return "Method: " + Classsys.change3(strVal1) + "." + strVal2 + " : " + Classsys.change(strVal3);
            case Classsys.IMETH:
                return "interfaceMethod: " + Classsys.change3(strVal1) + "." + strVal2 + " : " + Classsys.change(strVal3);
            case Classsys.FIELD:
                return "Field: " + Classsys.change3(strVal1) + "." + strVal2 + " : " + Classsys.change2(strVal3);
            case Classsys.UTF8:
                return "Utf-8: " + strVal1;
            case Classsys.NAME_TYPE:
                return "Name_Type: " + strVal1 + " : " + strVal2;
            default:
                return null;
        }

    }

    public String Value() {
        switch (type) {
            case Classsys.INT:
            case Classsys.FLOAT:
                return "" + intVal;
            case Classsys.LONG:
            case Classsys.DOUBLE:
                return "" + longVal;
            case Classsys.STR:
            case Classsys.UTF8:
                return "\"" + strVal1 + "\"";
            case Classsys.FIELD:
                return strVal2;
            default:
                return null;
        }
    }

    public String type() {
        switch (type) {
            case Classsys.INT:
                return "int";
            case Classsys.FLOAT:
                return "float";
            case Classsys.LONG:
                return "long";
            case Classsys.DOUBLE:
                return "double";
            case Classsys.STR:
            case Classsys.UTF8:
                return "ref";
            case Classsys.FIELD:
                //   System.out.println("getType: "+Classsys.change2(strVal3));
                return Classsys.change2(strVal3);
            default:
                return null;
        }
    }

    void set(final int intVal) {
        this.type = Classsys.INT;
        this.intVal = intVal;

    }

    void set(final long longVal) {
        this.type = Classsys.LONG;
        this.longVal = longVal;

    }

    void set(final float floatVal) {
        this.type = Classsys.FLOAT;
        this.intVal = (int) floatVal;

    }

    void set(final double doubleVal) {
        this.type = Classsys.DOUBLE;
        this.longVal = (long) doubleVal;

    }

    void set(final int type, final String strVal1, final String strVal2, final String strVal3) {
        this.type = type;

        this.strVal1 = type == Classsys.UTF8 ? change(strVal1) : strVal1;

        this.strVal2 = strVal2;
        this.strVal3 = strVal3;
    }

    private static String change(String s) {
        char[] c = s.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < c.length; i++) {
            switch (c[i]) {
                case 0: // '\0'
                    sb.append("\\0");
                    break;

                case 8: // '\b'
                    sb.append("\\b");
                    break;

                case 9: // '\t'
                    sb.append("\\t");
                    break;

                case 10: // '\n'
                    sb.append("\\n");
                    break;

                case 12: // '\f'
                    sb.append("\\f");
                    break;

                case 13: // '\r'
                    sb.append("\\r");
                    break;

                case 34: // '"'
                    sb.append("\\\"");
                    break;

                case 92: // '\\'
                    sb.append("\\\\");
                    break;
                default:
                    sb.append(c[i]);
            }
        }
      //  System.out.println(s + "   ---vs---   " + sb);
        return sb.toString();
    }
}
