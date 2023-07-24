/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.UF;

public class Percolation {
    private int[][] grid;  // n * n grid
    private UF tree;       // union-find object size n * n
    private int n;         // n
    private int countOpen; // number of open sites
    private int countFull; // number of full sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        countOpen = 0;
        countFull = 0;
        tree = new UF(n * n);
        grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = 0;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) throws IllegalArgumentException {
        try {
            // If already open then do nothing
            if (isOpen(row, col)) {
                return;
            }

            // Else open site
            grid[row - 1][col - 1] = 1;
            countOpen += 1;

            // If adjacent is open then union
            int[] rowNbr = { -1, 0, 0, 1 };
            int[] colNbr = { 0, -1, 1, 0 };
            for (int i = 0; i < rowNbr.length; i++) {
                if (isOpen(row + rowNbr[i], col + colNbr[i])) {
                    // p is current {row, col}. q is adjacent site
                    // need to reference p and q in the flattened tree array
                    int p = (row - 1) * n + col;
                    int q = (row + rowNbr[i] - 1) * n + col + colNbr[i];
                    tree.union(p, q);
                }
            }
        }
        catch (IllegalArgumentException e) {
            return;
        }
    }

    // is the site (row, col) open?
    // * throw  IllegalArgumentException if input is outside [1, n]
    public boolean isOpen(int row, int col) throws IllegalArgumentException {
        try {
            if (grid[row - 1][col - 1] == 1) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) throws IllegalArgumentException {
        try {
            if (grid[row - 1][col - 1] == 1) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    // system percolates if any bottom node is connected to any top node
    // or if we keep track of isFull then if bottom is full then it percolates
    public boolean percolates() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; i < n; j++) {
                int bottomIdx = (n - 1) * n + i;
                if (tree.find(bottomIdx) == tree.find(j)) return true;
            }
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
