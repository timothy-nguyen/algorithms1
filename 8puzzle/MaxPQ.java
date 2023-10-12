/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class MaxPQ<Key extends Comparable<Key>> {
    private Key[] pq; // pq[i] = ith element on priority queue (pq)
    private int N;    // number of elements on pq

    public MaxPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity + 1]; // idx 0  is empty. Root is idx 1
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public void insert(Key key) {
        pq[++N] = key;
        swim(N); // swim key upwards
    }

    public Key delMax() {
        // exchange last key with root. Then delete last key
        Key max = pq[1];
        exch(1, N--);
        sink(1);
        pq[N + 1] = null; // recall idx 0 is empty. Root is idx 1
        return max;
    }

    private void swim(int k) {
        // promotion in heap
        // parent node of k is at k/2
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    private void sink(int k) {
        // demotion in heap
        // child of parent k is at 2k and 2k + 1
        while (2 * k <= N) {
            int j = 2 * k;
            // always exchange with larger of child. Better subordinate promoted
            if (j < N && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    private boolean less(int i, int j) {
        return pq[i].compareTo(pq[j]) < 0;
    }

    private void exch(int i, int j) {
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    public static void main(String[] args) {

    }
}
