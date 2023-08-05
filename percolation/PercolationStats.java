/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int trials;
    private double[] results;
    private double CONFIDENCE_95 = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        this.trials = trials;
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                // System.out.printf("row: %d. col: %d\n", row, col);
                perc.open(row, col);
            }
            // System.out.println("Trial " + i + " complete\n");
            double countOpen = 0;
            for (int j = 1; j < n + 1; j++) {
                for (int k = 1; k < n + 1; k++) {
                    if (perc.isOpen(j, k)) countOpen += 1;
                }
            }
            results[i] = countOpen / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
        // double ssq = 0;
        // for (int i = 0; i < results.length; i++) {
        //     ssq += (results[i] - mean()) * (results[i] - mean());
        // }
        // return ssq / (trials - 1);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * Math.sqrt(stddev()) / Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * Math.sqrt(stddev()) / Math.sqrt(trials));
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        // System.out.printf("n: %d. trials: %d\n", n, trials);
        PercolationStats percStats = new PercolationStats(n, trials);
        StdOut.printf("mean                    = %f\n", percStats.mean());
        StdOut.printf("stddev                  = %f\n", percStats.stddev());
        StdOut.println("95% confidence interval = [" + percStats.confidenceLo() + ", "
                               + percStats.confidenceHi() + "]");
    }

}
