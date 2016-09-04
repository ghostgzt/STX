package util;

public interface Collection
        extends Iterable {

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

    public abstract boolean removeAll(Collection collection);

    public abstract boolean retainAll(Collection collection);

    public abstract void clear();

    public abstract boolean equals(Object obj);

    public abstract int hashCode();
}
