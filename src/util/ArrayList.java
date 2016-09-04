package util;

import java.util.Vector;

public class ArrayList
        implements List {

    public boolean isEmpty() {
        return v.isEmpty();
    }

    public boolean contains(Object elem) {
        return v.contains(elem);
    }

    public synchronized int lastIndexOf(Object elem, int index) {
        return v.lastIndexOf(elem, index);
    }

    public int lastIndexOf(Object elem) {
        return v.lastIndexOf(elem);
    }

    public synchronized int indexOf(Object elem, int index) {
        return v.indexOf(elem, index);
    }

    public int indexOf(Object elem) {
        return v.indexOf(elem);
    }

    public ArrayList() {
        v = new Vector();
    }

    public ArrayList(Vector v) {
        this.v = v;
    }

    public void clear() {
        v.removeAllElements();
    }

    public boolean add(Object value) {
        v.addElement(value);
        return v.contains(value);
    }

    public void add(int i, Object obj) {
        v.insertElementAt(obj, i);
    }

    public boolean addAll(Collection c) {
        if (c == null) {
            throw new NullPointerException();
        }
        Object o;
        for (Iterator i = c.iterator(); i.hasNext(); add(o)) {
            o = i.next();
            if (o == null) {
                throw new NullPointerException();
            }
        }

        return true;
    }

    public boolean addAll(int ind, Collection c) {
        if (c == null) {
            throw new NullPointerException();
        }
        Object o;
        for (Iterator i = c.iterator(); i.hasNext(); add(ind, o)) {
            o = i.next();
            if (o == null) {
                throw new NullPointerException();
            }
        }

        return true;
    }

    public boolean containsAll(Collection c) {
        if (c == null) {
            throw new NullPointerException();
        }
        if (c == this) {
            return true;
        }
        for (Iterator i = c.iterator(); i.hasNext();) {
            Object o = i.next();
            if (o == null) {
                throw new NullPointerException();
            }
            if (!v.contains(o)) {
                return false;
            }
        }

        return true;
    }

    public Object set(int index, Object obj) {
        Object o = v.elementAt(index);
        v.setElementAt(obj, index);
        return o;
    }

    public boolean retainAll(Collection c) {
        if (c == null) {
            throw new NullPointerException();
        }
        ArrayList vec = new ArrayList();
        for (Iterator i = c.iterator(); i.hasNext();) {
            Object obj = i.next();
            if (obj == null) {
                throw new NullPointerException();
            }
            if (v.contains(obj)) {
                vec.add(obj);
            }
        }

        addAll(vec);
        return true;
    }

    public Object remove(int position) {
        Object value = v.elementAt(position);
        v.removeElementAt(position);
        return value;
    }

    public boolean remove(Object o) {
        v.removeElement(o);
        return !v.contains(o);
    }

    public Object[] toArray() {
        Object data[] = new Object[v.size()];
        v.copyInto(data);
        return data;
    }

    public boolean removeAll(Collection col) {
        if (col == null) {
            throw new NullPointerException();
        }
        for (Iterator i = col.iterator(); i.hasNext();) {
            Object o = i.next();
            if (o == null) {
                throw new NullPointerException();
            }
            if (v.contains(o)) {
                v.removeElement(o);
            }
        }

        return true;
    }

    public Object get(int position) {
        return v.elementAt(position);
    }

    public int size() {
        return v.size();
    }

    public Iterator iterator() {
        return new Iterator(v);
    }

    public ListIterator listIterator() {
        return new ListIterator(v);
    }

    public ListIterator listIterator(int i) {
        return new ListIterator(v);
    }

    public Object[] toArray(Object arr[]) {
        v.copyInto(arr);
        return arr;
    }

    public List subList(int a, int b) {
        Vector vec = new Vector();
        for (int i = a; i < b; i++) {
            vec.addElement(v.elementAt(i));
        }

        return new ArrayList(vec);
    }
    Vector v;
}
