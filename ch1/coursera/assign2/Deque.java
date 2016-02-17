import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private class Entry {
        private Item item;
        private Entry prev;
        private Entry next;

        public Entry(Item item, Entry prev, Entry next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }

        public Entry(Item item) {
            this(item, null, null);
        }
    }

    private Entry sentinel;
    private int size;

    // construct an empty deque
    public Deque() {
        sentinel = new Entry(null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;

        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("Item cannot be null!");

        Entry oldFirst = sentinel.next;
        Entry newFirst = new Entry(item, sentinel, oldFirst);
        oldFirst.prev = newFirst;
        sentinel.next = newFirst;

        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("Item cannot be null!");

        Entry oldLast = sentinel.prev;
        Entry newLast = new Entry(item, oldLast, sentinel);
        oldLast.next = newLast;
        sentinel.prev = newLast;

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty!");

        Entry first = sentinel.next;

        sentinel.next = first.next;
        sentinel.next.prev = sentinel;
        size--;

        return first.item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty!");

        Entry last = sentinel.prev;
        sentinel.prev = last.prev;
        sentinel.prev.next = sentinel;
        size--;

        return last.item;
    }

    private class DequeIterator implements Iterator<Item> {
        private int count;
        private Entry current;

        public DequeIterator() {
            count = 0;
            current = sentinel.next;
        }

        public Item next() {
            if (count == size)
                throw new NoSuchElementException("Iterator runs out!");

            Entry e = current;
            current = current.next;
            count++;
            return e.item;
        }

        public boolean hasNext() {
            return count < size;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported.");
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing
    public static void main(String[] args) {
        Deque<Integer> q = new Deque<Integer>();

        q.addFirst(1);
        q.addFirst(2);
        q.addLast(3);
        q.addFirst(4);
        q.addLast(5);
        q.addLast(6);

        q.removeFirst();
        q.removeLast();

        StdOut.println("q size(): " + q.size());

        for (int i : q) {
            StdOut.println(i);
        }
    }
}
