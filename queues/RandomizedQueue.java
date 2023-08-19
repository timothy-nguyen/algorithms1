/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int N = 0;

    // construct an empty randomized queue
    public RandomizedQueue(int k) {
        q = (Item[]) new Object[k];
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
        q[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        int i = StdRandom.uniformInt(size() + 1); // choose a random number
        Item item = q[i];
        q[i] = null;
        N--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {

    }

    private
    // unit testing (required)
    public static void main(String[] args)

}
