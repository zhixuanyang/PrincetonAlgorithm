import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private class DequeNode<Item> {
        private Item item;
        private DequeNode prev;
        private DequeNode next;
        DequeNode(DequeNode p, Item i, DequeNode n) {
            prev = p;
            item = i;
            next = n;
        }
    }
    private DequeNode sentinel;
    private int size;

    public Deque() {
        sentinel = new DequeNode(null, 322, null);
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

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        sentinel.next = new DequeNode(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        sentinel.prev = new DequeNode(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    public Item removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Item result = (Item) sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return result;
    }

    public Item removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        Item result = (Item) sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return result;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private DequeNode curr = sentinel.next;
        public Item next() {
            if (curr == sentinel) {
                throw new NoSuchElementException();
            }
            Item item = (Item) curr.item;
            curr = curr.next;
            return item;
        }

        public boolean hasNext() {
            return curr != sentinel;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<>();
        d.addFirst("a");
        d.addLast("c");
        d.addFirst("b");
        d.addFirst("1");
        StdOut.println(d.size());
        StdOut.println(d.isEmpty());
        StdOut.println(d.removeFirst());
        StdOut.println(d.removeLast());
        StdOut.println(d.removeFirst());
        StdOut.println(d.removeFirst());
        StdOut.println(d.isEmpty());
    }
}
