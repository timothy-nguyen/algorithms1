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
        if (item == null) throw new IllegalArgumentException();
        if (N == q.length) resize(q.length * 2);
        q[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        // return last element after shuffle
        StdRandom.shuffle(q, 0, N - 1);
        Item item = q[--N];
        q[N] = null;
        if (N > 0 && N == q.length / 4) resize(q.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int i = StdRandom.uniformInt(N);
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
        private int i;
        private Item[] rq;

        public RandomizedIterator() {
            i = N - 1;
            rq = q;
            StdRandom.shuffle(rq, 0, i);
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return rq[i--];
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
        StdOut.println(queue.isEmpty());
        StdOut.println(queue.sample());
        StdOut.println(queue.size());

        // test random dequeue
        StdOut.println(queue.size());
        StdOut.println(queue.dequeue());
        StdOut.println(queue.size());
        StdOut.println();

        // test iterator. Should be independent
        int n = 5;
        RandomizedQueue<Integer> queue2 = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue2.enqueue(i);
        for (int a : queue2) {
            for (int b : queue2)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
    }
}
