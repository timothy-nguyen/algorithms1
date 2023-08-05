/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;          // n * n grid
    private WeightedQuickUnionUF tree; // union-find object size n * n
    private int n;                     // n
    private int countOpen;             // number of open sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 0) throw new IllegalArgumentException();

        this.n = n;
        countOpen = 0;
        tree = new WeightedQuickUnionUF(n * n + 2); // add top and bottom virtual sites
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }

        // connect virtual top and bottom sites
        // we assign index n - 2 and n - 1 as the virtual top and bottom sites respectively
        for (int k = 1; k <= n; k++) {
            int qTop = map2Dto1D(1, k);
            int qBottom = map2Dto1D(n, k);
            tree.union(n * n, qTop);
            tree.union(n * n + 1, qBottom);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkValidRange(row, col);
        if (isOpen(row, col)) return;

        // Else open site
        grid[row - 1][col - 1] = true; // revert to zero indexing for grid array
        countOpen += 1;

        // If adjacent is open then union
        int[] rowNbr = { -1, 0, 0, 1 };
        int[] colNbr = { 0, -1, 1, 0 };
        for (int i = 0; i < rowNbr.length; i++) {
            if (isOpen(row + rowNbr[i], col + colNbr[i])) {
                int p = map2Dto1D(row, col);
                int q = map2Dto1D(row + rowNbr[i], col + colNbr[i]);
                tree.union(p, q);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        try {
            if (grid[row - 1][col - 1]) return true;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    // is the site (row, col) full?
    // if full if site is connected to virtual node at top
    public boolean isFull(int row, int col) {
        checkValidRange(row, col);
        int p = map2Dto1D(row, col);
        if (isOpen(row, col) && tree.find(p) == tree.find(n * n)) return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        if (tree.find(n * n) == tree.find(n * n + 1)) return true;
        return false;
    }

    private void checkValidRange(int row, int col) {
        if (row <= 0 || row > n || col <= 0 || col > n) throw new IllegalArgumentException();
    }

    private int map2Dto1D(int row, int col) {
        // assume inputs are indexed from 1 to n, so we minus 1 to revert to zero indexing
        return (row - 1) * n + (col - 1);
    }

    // test client (optional)
    public static void main(String[] args) {

        // Test roots are the same for adjacent opened cells
        // Percolation perc = new Percolation(4);
        // perc.open(1, 1);
        // perc.open(1, 2);
        // System.out.println(perc.tree.find(perc.map2Dto1D(1, 1)));
        // System.out.println(perc.tree.find(perc.map2Dto1D(1, 2)));
        // System.out.println(perc.tree.find(4 * 4 - 2));
        // System.out.println(perc.tree.find(4 * 4 - 1));
        // PercolationVisualizer.draw(perc, 4);
    }
}
