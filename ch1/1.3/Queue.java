/** A array-based Queue */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 2;
    private Item[] items;
    private int head;
    private int tail;

    public Queue() {
        items = (Item[]) new Object[INIT_CAPACITY];
        head = -1;
        tail = -1;
    }

    public int size() {
        if (head == -1) return 0;
        else if (head <= tail) return tail - head + 1;
        else return items.length - head + tail + 1;
    }

    private void resize(int newSize) {
        Item[] tmp = (Item[]) new Object[newSize];

        int h = head;
        int count = size();

        for (int i = 0; i < count; i++) {
            tmp[i] = items[h];
            h = (h + 1) % items.length;
        }

        items = tmp;
        head = 0;
        tail = count - 1;
    }

    public void enqueue(Item item) {
        if (size() == items.length) resize(items.length * 2);

        if (size() == 0) {
            items[0] = item;
            head = 0;
            tail = 0;
        } else {
            items[++tail] = item;
        }
    }

    public Item dequeue() {
        if (size() == 0)
            throw new NoSuchElementException("Queue is empty!");

        Item item;
        if (head == tail) {
            item = items[head];
            head = -1;
            tail = -1;
        } else {
            item = items[head];
            head++;
        }

        return item;
    }

    private class QueueIterator implements Iterator<Item> {
        private int h;
        private int t;
        private int count;

        public QueueIterator() {
            h = head;
            t = tail;
            count = size();
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
        q.enqueue(5);
        q.dequeue();
        q.dequeue();
        q.dequeue();
        q.dequeue();
        q.dequeue();
        q.enqueue(6);
        q.enqueue(7);
        q.dequeue();

        System.out.println("size: " + q.size());

        for (int i : q) {
            System.out.println(i);
        }
    }
}


