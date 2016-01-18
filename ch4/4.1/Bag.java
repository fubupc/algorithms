import java.util.Iterator;
import java.util.NoSuchElementException;


class Bag<Item> implements Iterable<Item> {
    private static class Node<Item> {
        Item item;
        Node<Item> next;

        public Node(Item i, Node<Item> n) {
            item = i;
            next = n;
        }
    }

    private Node<Item> head;
    private int size;

    public Bag() {
        head = null;
        size = 0;
    }

    public void add(Item item) {
        head = new Node<Item>(item, head);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public boolean contains(Item item) {
        for (Node<Item> current = head; current != null; current = current.next) {
            if (current.item == item || item.equals(current.item)) return true;
        }

        return false;
    }

    public Iterator<Item> iterator() {
        return new BagIterator();
    }

    private class BagIterator implements Iterator<Item> {
        private Node<Item> current;

        public BagIterator() {
            current = head;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}
