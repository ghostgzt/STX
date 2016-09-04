package util;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public class LinkedList {

    private class ListItr
            implements Enumeration {

        public boolean hasMoreElements() {
            return nextIndex != size;
        }

        public Object nextElement() {
            checkForComodification();
            if (nextIndex == size) {
                throw new NoSuchElementException();
            } else {
                lastReturned = next;
                next = next.next;
                nextIndex++;
                return lastReturned.element;
            }
        }

        public boolean hasPrevious() {
            return nextIndex != 0;
        }

        public Object previous() {
            if (nextIndex == 0) {
                throw new NoSuchElementException();
            } else {
                lastReturned = next = next.previous;
                nextIndex--;
                checkForComodification();
                return lastReturned.element;
            }
        }

        public int nextIndex() {
            return nextIndex;
        }

        public int previousIndex() {
            return nextIndex - 1;
        }

        public void remove() {
            checkForComodification();
            Entry lastNext = lastReturned.next;
            try {
                LinkedList.this.remove(lastReturned);
            } catch (NoSuchElementException e) {
                throw new IllegalStateException();
            }
            if (next == lastReturned) {
                next = lastNext;
            } else {
                nextIndex--;
            }
            lastReturned = header;
            expectedModCount++;
        }

        public void set(Object e) {
            if (lastReturned == header) {
                throw new IllegalStateException();
            } else {
                checkForComodification();
                lastReturned.element = e;
                return;
            }
        }

        public void add(Object e) {
            checkForComodification();
            lastReturned = header;
            addBefore(e, next);
            nextIndex++;
            expectedModCount++;
        }

        final void checkForComodification() {
            if (modCount != expectedModCount) {
                throw new IllegalStateException();
            } else {
                return;
            }
        }
        private Entry lastReturned;
        private Entry next;
        private int nextIndex;
        private int expectedModCount;

        ListItr(int index) {
            lastReturned = header;
            expectedModCount = modCount;
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }
            if (index < size >> 1) {
                next = header.next;
                for (nextIndex = 0; nextIndex < index; nextIndex++) {
                    next = next.next;
                }

            } else {
                next = header;
                for (nextIndex = size; nextIndex > index; nextIndex--) {
                    next = next.previous;
                }

            }
        }
    }

    private static class Entry {

        Object element;
        Entry next;
        Entry previous;

        Entry(Object element, Entry next, Entry previous) {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }

    private class DescendingIterator
            implements Enumeration {

        public boolean hasMoreElements() {
            return itr.hasPrevious();
        }

        public Object nextElement() {
            return itr.previous();
        }

        public void remove() {
            itr.remove();
        }
        final ListItr itr;

        private DescendingIterator() {
            itr = new ListItr(size());
        }

        DescendingIterator(DescendingIterator descendingiterator) {
            this();
        }
    }

    public LinkedList() {
        header = new Entry(null, null, null);
        size = 0;
        modCount = 0;
        header.next = header.previous = header;
    }

    public Object getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            return header.next.element;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Object getLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        } else {
            return header.previous.element;
        }
    }

    public Object removeFirst() {
        return remove(header.next);
    }

    public Object removeLast() {
        return remove(header.previous);
    }

    public void addFirst(Object e) {
        addBefore(e, header.next);
    }

    public void addLast(Object e) {
        addBefore(e, header);
    }

    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    public int size() {
        return size;
    }

    public boolean add(Object e) {
        addBefore(e, header);
        return true;
    }

    public boolean remove(Object o) {
        if (o == null) {
            for (Entry e = header.next; e != header; e = e.next) {
                if (e.element == null) {
                    remove(e);
                    return true;
                }
            }

        } else {
            for (Entry e = header.next; e != header; e = e.next) {
                if (o.equals(e.element)) {
                    remove(e);
                    return true;
                }
            }

        }
        return false;
    }

    public void clear() {
        Entry next;
        for (Entry e = header.next; e != header; e = next) {
            next = e.next;
            e.next = e.previous = null;
            e.element = null;
        }

        header.next = header.previous = header;
        size = 0;
        modCount++;
    }

    public Object get(int index) {
        return entry(index).element;
    }

    public Object set(int index, Object element) {
        Entry e = entry(index);
        Object oldVal = e.element;
        e.element = element;
        return oldVal;
    }

    public void add(int index, Object element) {
        addBefore(element, index != size ? entry(index) : header);
    }

    public Object remove(int index) {
        return remove(entry(index));
    }

    private Entry entry(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Entry e = header;
        if (index < size >> 1) {
            for (int i = 0; i <= index; i++) {
                e = e.next;
            }

        } else {
            for (int i = size; i > index; i--) {
                e = e.previous;
            }

        }
        return e;
    }

    public int indexOf(Object o) {
        int index = 0;
        if (o == null) {
            for (Entry e = header.next; e != header; e = e.next) {
                if (e.element == null) {
                    return index;
                }
                index++;
            }

        } else {
            for (Entry e = header.next; e != header; e = e.next) {
                if (o.equals(e.element)) {
                    return index;
                }
                index++;
            }

        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        int index = size;
        if (o == null) {
            for (Entry e = header.previous; e != header; e = e.previous) {
                index--;
                if (e.element == null) {
                    return index;
                }
            }

        } else {
            for (Entry e = header.previous; e != header; e = e.previous) {
                index--;
                if (o.equals(e.element)) {
                    return index;
                }
            }

        }
        return -1;
    }

    public Object peek() {
        if (size == 0) {
            return null;
        } else {
            return getFirst();
        }
    }

    public Object element() {
        return getFirst();
    }

    public Object poll() {
        if (size == 0) {
            return null;
        } else {
            return removeFirst();
        }
    }

    public Object remove() {
        return removeFirst();
    }

    public boolean offer(Object e) {
        return add(e);
    }

    public boolean offerFirst(Object e) {
        addFirst(e);
        return true;
    }

    public boolean offerLast(Object e) {
        addLast(e);
        return true;
    }

    public Object peekFirst() {
        if (size == 0) {
            return null;
        } else {
            return getFirst();
        }
    }

    public Object peekLast() {
        if (size == 0) {
            return null;
        } else {
            return getLast();
        }
    }

    public Object pollFirst() {
        if (size == 0) {
            return null;
        } else {
            return removeFirst();
        }
    }

    public Object pollLast() {
        if (size == 0) {
            return null;
        } else {
            return removeLast();
        }
    }

    public void push(Object e) {
        addFirst(e);
    }

    public Object pop() {
        return removeFirst();
    }

    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    public boolean removeLastOccurrence(Object o) {
        if (o == null) {
            for (Entry e = header.previous; e != header; e = e.previous) {
                if (e.element == null) {
                    remove(e);
                    return true;
                }
            }

        } else {
            for (Entry e = header.previous; e != header; e = e.previous) {
                if (o.equals(e.element)) {
                    remove(e);
                    return true;
                }
            }

        }
        return false;
    }

    public Enumeration listIterator(int index) {
        return new ListItr(index);
    }

    private Entry addBefore(Object e, Entry entry) {
        Entry newEntry = new Entry(e, entry, entry.previous);
        newEntry.previous.next = newEntry;
        newEntry.next.previous = newEntry;
        size++;
        modCount++;
        return newEntry;
    }

    private Object remove(Entry e) {
        if (e == header) {
            throw new NoSuchElementException();
        } else {
            Object result = e.element;
            e.previous.next = e.next;
            e.next.previous = e.previous;
            e.next = e.previous = null;
            e.element = null;
            size--;
            modCount++;
            return result;
        }
    }

    public Enumeration descendingIterator() {
        return new DescendingIterator(null);
    }

    public Object[] toArray() {
        Object result[] = new Object[size];
        int i = 0;
        for (Entry e = header.next; e != header; e = e.next) {
            result[i++] = e.element;
        }

        return result;
    }
    private transient Entry header;
    private transient int size;
    int modCount;
    private static final long serialVersionUID = 0xc29535d4a608822L;
}
