/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int N = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (N == q.length) resize(q.length * 2);
        q[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        // return last element after shuffle
        StdRandom.shuffle(q);
        Item item = q[--N];
        q[N] = null;
        if (N > 0 && N == q.length / 4) resize(q.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int i = StdRandom.uniformInt(N + 1);
        return q[i];
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private int i = N;
        private Item[] rq = q;

        public RandomizedIterator() {
            StdRandom.shuffle(rq);
        }

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return rq[--i];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");
        queue.enqueue("E");

        // test sampling. Size should be the same
        StdOut.println(queue.sample());
        StdOut.println(queue.size());

        // test random dequeue
        StdOut.println(queue.size());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.size());

        // test iterator. Should be independent
        for (String s : queue) StdOut.println(s);
        StdOut.println();
        for (String s : queue) StdOut.println(s);
    }
}
