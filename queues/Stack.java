/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;

public class Stack<Item> implements Iterable<Item> {
    // generics allows implementation of stack for different types (e.g. Strings, ints, objects)

    // Iterable is an object that returns an Iterator. It allows client to support "foreach" statement
    // implements java.lang.Iterable iterface

    private Node first = null;

    private class Node {
        Item item; // note: generic type name referenced here
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void push(Item item) {
        // push a generic type name
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
    }

    public Item pop() {
        // return first item
        Item item = first.item;
        first = first.next; // remove first item from linked list
        return item;
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    public class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() { /* not supported. Should throw UnsupportedOperationException */ }

        public Item next() {
            // should throw NoSuchElementException if no more items in iteration
            Item item = current.item;
            current = current.next; // always prepare item for invokation of method
            return item;
        }


    }

    public static void main(String[] args) {

    }
}
