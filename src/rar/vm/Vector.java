
package rar.vm;

import java.util.Enumeration;

public class Vector extends java.util.Vector {

    String name;

    public Vector(String n) {
        name = n;
    }

    public void setSize(int newSize) {
        System.out.println(name + "  setSize: " + newSize);
        super.setSize(newSize);
    }

    public int capacity() {
        System.out.println(name + "  capacity: ");
        return super.capacity();
    }

    public boolean contains(Object elem) {
        System.out.println(name + "  contains: " + elem);

        return super.contains(elem);
    }

    public void copyInto(Object[] anArray) {
        System.out.println(name + "  copyInto: " +anArray);
        super.copyInto(anArray);
    }

    public Enumeration elements() {
        System.out.println(name + "  elements: ");
        return super.elements();
    }

    public void ensureCapacity(int minCapacity) {
        System.out.println(name + "  ensureCapacity: " + minCapacity);
        super.ensureCapacity(minCapacity);
    }

    public Object firstElement() {
        System.out.println(name + "  firstElement: " );
        return super.firstElement();
    }

    public int indexOf(Object elem) {
        System.out.println(name + "  indexOf: " +elem);
        return super.indexOf(elem);
    }

    public int indexOf(Object elem, int index) {
        System.out.println(name + "  indexOf: " + elem+" index: "+index);
        return super.indexOf(elem, index);
    }

    public void insertElementAt(Object obj, int index) {
        System.out.println(name + "  insertElementAt: " + obj+"  index: "+index);
        super.insertElementAt(obj, index);
    }

    public boolean isEmpty() {
        System.out.println(name + "  isEmpty: ");
        return super.isEmpty();
    }

    public Object lastElement() {
        System.out.println(name + "  lastElement: " );
        return super.lastElement();
    }

    public int lastIndexOf(Object elem) {
        System.out.println(name + "  lastIndexOf: " + elem);
        return super.lastIndexOf(elem);
    }

    public int lastIndexOf(Object elem, int index) {
       System.out.println(name + "  lastIndexOf: " + elem+ " index: "+index);
        return super.lastIndexOf(elem, index);
    }

    public void removeAllElements() {
        System.out.println(name + "  removeAllElements: " );
        super.removeAllElements();
    }

    public boolean removeElement(Object obj) {
        System.out.println(name + "  removeElement: " + obj);
        return super.removeElement(obj);
    }

    public void removeElementAt(int index) {
        System.out.println(name + "  removeElementAt: " +index);
        super.removeElementAt(index);
    }

    public String toString() {
        System.out.println(name + "  toString: ");
        return super.toString();
    }

    public void trimToSize() {
        System.out.println(name + "  trimToSize: ");
        super.trimToSize();
    }

    public void addElement(Object obj) {
        System.out.println(name + "  addElement: " + obj);
        super.addElement(obj);
    }

    public Object elementAt(int index) {
        System.out.println(name + "  elementAt: " + index);
        return super.elementAt(index);
    }

    public void setElementAt(Object obj, int index) {
        System.out.println(name + "  setElementAt: " + index+"  to  "+obj);
        super.setElementAt(obj, index);
    }

    public int size() {
        System.out.println(name + "  size: " + super.size());
        return super.size();
    }
}
