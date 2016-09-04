package util;

public interface List
        extends Collection {

    public abstract int size();

    public abstract boolean isEmpty();

    public abstract boolean contains(Object obj);

    public abstract Iterator iterator();

    public abstract Object[] toArray();

    public abstract Object[] toArray(Object aobj[]);

    public abstract boolean add(Object obj);

    public abstract boolean remove(Object obj);

    public abstract boolean containsAll(Collection collection);

    public abstract boolean addAll(Collection collection);

    public abstract boolean addAll(int i, Collection collection);

    public abstract boolean removeAll(Collection collection);

    public abstract boolean retainAll(Collection collection);

    public abstract void clear();

    public abstract boolean equals(Object obj);

    public abstract int hashCode();

    public abstract Object get(int i);

    public abstract Object set(int i, Object obj);

    public abstract void add(int i, Object obj);

    public abstract Object remove(int i);

    public abstract int indexOf(Object obj);

    public abstract int lastIndexOf(Object obj);

    public abstract ListIterator listIterator();

    public abstract ListIterator listIterator(int i);

    public abstract List subList(int i, int j);
}
