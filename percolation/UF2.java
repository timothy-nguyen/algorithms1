/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class UF2 {
    private int[] id;  // site indexed component id
    private int[] sz;  // size of component for roots (site indexed)
    private int count; // number of components

    public UF2(int N) {
        // initialise components in id array
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
        sz = new int[N];
        for (int i = 0; i < N; i++) sz[i] = 1;
    }

    // methods
    public int count() {
        return count;
    }

    // quick-find
    public int find(int p) {
        return id[p];
    }

    public void union(int p, int q) {
        int pID = find(p);
        int qID = find(q);

        if (pID == qID) return;

        for (int i = 0; i < id.length; i++) {
            if (id[i] == pID) id[i] = qID;
        }
        count--; // reduce number of components after union
    }

    // quick union
    public int find2(int p) {
        // find component name i.e. root of tree
        // if singular node, then return itself
        while (p != id[p]) p = id[p];
        return p;
    }

    public void union2(int p, int q) {
        int pRoot = find2(p);
        int qRoot = find2(q);
        if (pRoot == qRoot) return;

        id[pRoot] = qRoot;

        count--;
    }

    // weighted quick union
    private int find3(int p) {
        // follow links back to root
        while (p != id[p]) p = id[p];
        return p;
    }

    public void union3(int p, int q) {
        int i = find3(p);
        int j = find3(q);
        if (i == j) return;

        // make smaller root point to larger one
        if (sz[i] < sz[j]) {
            id[i] = j;
            sz[j] += sz[i]; // increment the size of the larger tree by the size of the smaller tree
        }
        else {
            id[j] = i;
            sz[i] += sz[j];
        }
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public static void main(String[] args) {

    }
}
