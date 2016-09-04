package util;

import java.util.Enumeration;
import java.util.Vector;

public class Iterator {

    public Iterator(Vector v) {
        en = v.elements();
    }

    public boolean hasNext() {
        return en.hasMoreElements();
    }

    public Object next() {
        return en.nextElement();
    }
    Enumeration en;
}
