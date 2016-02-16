import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    class Entry {
        Item item;
        Entry prev;
        Entry next;

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

        sentinel.next = new Entry(item, sentinel, sentinel.next);
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("Item cannot be null!");

        sentinel.prev = new Entry(item, sentinel.prev, sentinel);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty!");

        Entry first = sentinel.next;

        sentinel.next = first.next;
        sentinel.next.prev = sentinel;

        return first.item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty!");

        Entry last = sentinel.prev;
        sentinel.prev = last.prev;
        sentinel.prev.next = sentinel;

        return last.item;
    }

    class DequeIterator implements Iterator<Item> {
        private int count;
        private Entry current;

        public DequeIterator() {
            count = 0;
            current = sentinel.next;
        }

        public Item next() {
            if (count == size) throw new NoSuchElementException("Iterator runs out!");

            Entry e = current;
            current = current.next;
            count++;
            return e.item;
        }

        public boolean hasNext() {
            return count < size;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("Cannot remove item when iterating.");
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing
    public static void main(String[] args) {
    }
}
