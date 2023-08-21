/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n;       // size of the stack
    private Node first;  // top of the stack
    private Node last;   // bottom of the stack

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null || last == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;

        // if stack empty then last node is same as first node, and next is null
        if (isEmpty()) {
            last = first;
            first.next = null;
        }
        else {
            first.next = oldfirst;
            if (last.prev == null) last.prev = first;
        }
        first.prev = null;
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldlast = last;
        last = new Node();
        last.item = item;

        // if stack is empty then first node is same as last node
        if (isEmpty()) {
            first = last;
            first.prev = null;
        }
        else {
            oldlast.next = last;
            last.prev = oldlast;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    private class LinkedIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        int n = 5;
        Deque<Integer> queue = new Deque<Integer>();
        StdOut.println("Empty queue: " + queue.isEmpty());

        // add to back
        StdOut.println("FIFO: addLast and removeFirst");
        for (int i = 0; i < n; i++) queue.addLast(i);
        for (int i : queue) StdOut.println(i); // iterate front to back
        StdOut.println();
        for (int i = 0; i < n; i++) {
            StdOut.println(queue.removeFirst()); // expect same as above
            StdOut.println("Size: " + queue.size());
        }
        StdOut.println(queue.isEmpty());

        // add to front
        StdOut.println("FIFO: addFirst and removeLast");
        for (int i = 0; i < n; i++) queue.addFirst(i);
        for (int i : queue) StdOut.println(i); // LIFO
        StdOut.println();
        for (int i = 0; i < n; i++) {
            StdOut.println(queue.removeLast());
            StdOut.println("Size: " + queue.size());
        }
        StdOut.println(queue.isEmpty());

        // test multiple iterators simultaneously
        for (int i = 0; i < n; i++) queue.addFirst(i);
        for (int a : queue) {
            for (int b : queue) {
                StdOut.print(a + "-" + b + " ");
            }
            StdOut.println();
        }
    }
}
