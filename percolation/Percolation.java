/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;  // n * n grid
    private WeightedQuickUnionUF tree;       // union-find object size n * n
    private int n;         // n
    private int countOpen; // number of open sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        countOpen = 0;
        tree = new WeightedQuickUnionUF(n * n);
        grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        try {
            // If already open then do nothing
            if (isOpen(row, col)) {
                return;
            }

            // Else open site
            int rowIdx = row - 1; // revert back to zero indexing
            int colIdx = col - 1;
            // System.out.printf("rowIdx: %d. colIdx: %d\n", rowIdx, colIdx);
            grid[rowIdx][colIdx] = 1;
            countOpen += 1;

            // If adjacent is open then union
            int[] rowNbr = { -1, 0, 0, 1 };
            int[] colNbr = { 0, -1, 1, 0 };
            for (int i = 0; i < rowNbr.length; i++) {
                if (isOpen(rowIdx + rowNbr[i] + 1, colIdx + colNbr[i] + 1)) {
                    // p is current {row, col}. q is adjacent site
                    // need to reference p and q in the flattened tree array
                    int p = rowIdx * n + colIdx;
                    int q = (rowIdx + rowNbr[i]) * n + (colIdx + colNbr[i]);

                    tree.union(p, q);
                }
            }
        }
        catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            return;
        }
    }

    // is the site (row, col) open?
    // * throw  IllegalArgumentException if input is outside [1, n]
    public boolean isOpen(int row, int col) {
        try {
            if (grid[row - 1][col - 1] == 1) return true;
            return false;
        }
        catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    // is the site (row, col) full?
    // is full if the site is connected to the top (i.e. root is 0 to n - 1) [does not work if root is in middle of the grid]
    // if full if site is connected to a node at the top
    public boolean isFull(int row, int col) {
        try {
            // System.out.printf("Tree index: %d\n", (row - 1) * n + (col - 1));
            // System.out.printf("Tree root: %d\n", tree.find((row - 1) * n + (col - 1)));
            for (int j = 0; j < n; j++) {
                if (tree.find((row - 1) * n + (col - 1)) == tree.find(j) && isOpen(row, col))
                    return true;
            }
            return false;
        }
        catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    // if bottom is full then it percolates
    public boolean percolates() {
        for (int j = 1; j <= n; j++) {
            if (isFull(n, j)) return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
