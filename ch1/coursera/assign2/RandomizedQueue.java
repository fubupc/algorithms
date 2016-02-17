import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 1;

    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[INIT_CAPACITY];
        size = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the queue
    public int size() {
        return size;
    }

    private void resize(int newSize) {
        Item[] tmp = (Item[]) new Object[newSize];

        for (int i = 0; i < size; i++) {
            tmp[i] = items[i];
        }

        items = tmp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("Deque is empty!");

        if (size == items.length) resize(size * 2);
        items[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty!");

        int rand = StdRandom.uniform(size);
        Item item = items[rand];

        if (rand != size - 1) {
            items[rand] = items[size - 1];
        }
        items[size - 1] = null;
        size--;

        if (size <= (items.length / 4) && items.length / 2 >= INIT_CAPACITY)
            resize(items.length / 2);

        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty!");

        int rand = StdRandom.uniform(size);
        return items[rand];
    }

    private class RandIterator implements Iterator<Item> {
        private Item[] copy;
        private int count;

        public RandIterator() {
            copy = (Item[]) new Object[size];
            count = size;

            for (int i = 0; i < size; i++) {
                copy[i] = items[i];
            }
        }

        public Item next() {
            if (count == 0)
                throw new NoSuchElementException("Iterator runs out of items.");

            int rand = StdRandom.uniform(count);
            Item item = copy[rand];

            if (rand == count - 1) {
                copy[rand] = null;
            } else {
                copy[rand] = copy[count - 1];
            }
            count--;

            return item;
        }

        public boolean hasNext() {
            return count > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove unsupported.");
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandIterator();
    }

    // unit testing
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<String>();
        String[] inputs = "A B C D E F G H I".split(" ");

        for (int i = 0; i < inputs.length; i++) {
            q.enqueue(inputs[i]);
        }

        for (int j = 0; j < 8; j++) {
            q.dequeue();
        }

        for (String s : q) {
            System.out.print(s + " ");
        }

        System.out.println();
    }

}
