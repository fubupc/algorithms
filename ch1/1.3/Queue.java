/** A array-based Queue */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 2;
    private Item[] items;
    private int head;
    private int tail;
    private int size;

    public Queue() {
        items = (Item[]) new Object[INIT_CAPACITY];
        head = -1;
        tail = -1;
        size = 0;
    }

    public int size() {
        return size;
    }

    private void resize(int newSize) {
        Item[] tmp = (Item[]) new Object[newSize];
        int h = head;
        for (int i = 0; i < size; i++) {
            tmp[i] = items[h];
            h = (h + 1) % items.length;
        }

        items = tmp;
        head = 0;
        tail = size - 1;
    }

    public void enqueue(Item item) {
        if (size == items.length) resize(items.length * 2);

        if (head == -1) head = 0;

        if (++tail == items.length) tail = 0;

        items[tail] = item;
        size++;
    }

    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException("Queue is empty!");

        Item item = items[head++];

        if (head == items.length) head = 0;

        size--;

        return item;
    }

    private class QueueIterator implements Iterator<Item> {
        private int h;
        private int t;
        private int count;

        public QueueIterator() {
            h = head;
            count = size;
        }

        public boolean hasNext() {
            return count > 0;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("Iterator runs out.");

            Item item = items[h];
            h = (h + 1) % items.length;
            count--;

            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    public static void main(String[] args) {
        Queue<Integer> q = new Queue<Integer>();

        System.out.println("Queue init, size: " + q.size());

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.dequeue();
        q.dequeue();
        q.enqueue(5);
        q.enqueue(6);
        q.enqueue(7);
        q.dequeue();

        System.out.println("size: " + q.size());

        for (int i : q) {
            System.out.println(i);
        }
    }
}


