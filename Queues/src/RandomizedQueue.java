import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private Node sentinel;
    private class Node<Item> {
        Node prev;
        Item item;
        Node next;
        Node(Node prev, Item item, Node next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
    public RandomizedQueue() {
        sentinel = new Node(null, 322, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        sentinel.prev = new Node(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(size);
        Node temp = sentinel.next;
        for (int i = 0; i < random; i++) {
            temp = temp.next;
        }
        Item result = (Item) temp.item;
        temp.next.prev = temp.prev;
        temp.prev.next = temp.next;
        size -= 1;
        return result;
    }
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(size);
        Node temp = sentinel.next;
        for (int i = 0; i < random; i++) {
            temp = temp.next;
        }
        Item result = (Item) temp.item;
        return result;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node temp;
        private boolean[] record;
        private int count;
        ListIterator() {
            record = new boolean[size];
            for (int i = 0; i < size; i++) {
                record[i] = false;
            }
            count = size;
        }
        public boolean hasNext() {
            return count != 0;
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            temp = sentinel.next;
            int random = StdRandom.uniform(size);
            while (record[random]) {
                random = StdRandom.uniform(size);
            }
            for (int i = 0; i < random; i++) {
                temp = temp.next;
            }
            Item item = (Item) temp.item;
            count -= 1;
            record[random] = true;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> R = new RandomizedQueue<>();
        R.enqueue("1");
        R.enqueue("2");
        R.enqueue("3");
        R.enqueue("4");
        R.enqueue("5");
        R.enqueue("6");
        R.enqueue("7");
        R.enqueue("8");
        R.enqueue("0");
        StdOut.println(R.dequeue());
        StdOut.println(R.dequeue());
        StdOut.println(R.dequeue());
        for (String s : R) {
            StdOut.println("(" + s + ")");
        }
    }
}
