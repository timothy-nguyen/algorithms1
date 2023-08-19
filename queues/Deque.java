/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n;       // size of the stack
    private Node first;  // top of the stack
    private Node last;   // bottom of the stack

    private class Node {
        Item item;
        Node next;
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
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldlast = last;
        last = new Node();
        last.item = item;

        // if stack is empty then first node is same as last node
        if (isEmpty()) first = last;
        else oldlast.next = last;
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
        last = null;
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
        /*
        Deque<String> dqFront = new Deque<String>(); // add items to front
        Deque<String> dqBack = new Deque<String>();  // add items to back

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();

            // add to front of stack. FILO
            if (!item.equals("-")) dqFront.addFirst(item);
            else if (!dqFront.isEmpty()) {
                StdOut.print(dqFront.removeLast() + " ");
            }

            // add to end of stack. FIFO
            if (!item.equals("-")) dqBack.addLast(item);
            else if (!dqBack.isEmpty()) {
                StdOut.print(dqBack.removeFirst() + " ");
            }
        }
        StdOut.println("(" + dqFront.size() + " left on dqFront queue)");
        for (String s : dqFront) StdOut.println(s);
        StdOut.println();
        StdOut.println("(" + dqFront.size() + " left on dqBack queue)");
        for (String s : dqBack) StdOut.println(s);
        */
    }
}
