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
    public Percolation(int n) throws IllegalArgumentException {
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
            int rowIdx = row - 1; // revert back to zero indexing
            int colIdx = col - 1;
            grid[rowIdx][colIdx] = 1;
            countOpen += 1;

            // If adjacent is open then union
            int[] rowNbr = { -1, 0, 0, 1 };
            int[] colNbr = { 0, -1, 1, 0 };
            for (int i = 0; i < rowNbr.length; i++) {
                // System.out.format("i: %d\n", i);
                if (isOpen(rowIdx + rowNbr[i] + 1, colIdx + colNbr[i] + 1)) {
                    // System.out.println("isOpen");
                    // System.out.println(rowIdx + rowNbr[i]);
                    // System.out.println(colIdx + colNbr[i]);

                    // p is current {row, col}. q is adjacent site
                    // need to reference p and q in the flattened tree array
                    // System.out.println("Points");
                    int p = rowIdx * n + colIdx;
                    int q = (rowIdx + rowNbr[i]) * n + (colIdx + colNbr[i]);

                    // System.out.println(p);
                    // System.out.println(q);

                    tree.union(p, q);
                    // System.out.println("Root:");
                    // System.out.println(tree.find(p));
                    // System.out.println(tree.find(q));
                }
            }
        }
        catch (IllegalArgumentException e) {
            return;
        }
    }

    // is the site (row, col) open?
    // * throw  IllegalArgumentException if input is outside [1, n]
    public boolean isOpen(int row, int col) throws IllegalArgumentException,
                                                   ArrayIndexOutOfBoundsException {
        try {
            if (grid[row - 1][col - 1] == 1) return true;
            return false;
        }
        catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }

    // is the site (row, col) full?
    // is full if the site is connected to the top (i.e. root is 0 to n - 1)
    public boolean isFull(int row, int col) throws IllegalArgumentException,
                                                   ArrayIndexOutOfBoundsException {
        try {
            if (tree.find((row - 1) * n + (col - 1)) < n && isOpen(row, col)) return true;
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
        Percolation perc = new Percolation(4);
        perc.open(1, 1);
        perc.open(1, 2);
        perc.open(2, 3);
        perc.open(4, 4);
        perc.open(3, 4);
        perc.open(3, 1);
        perc.open(4, 1);
        perc.open(3, 2);
        // System.out.println(perc.tree.find(6));
        // System.out.println(perc.tree.find(0));
        // System.out.println(perc.tree.find(15));
        PercolationVisualizer.draw(perc, 4);
    }
}
