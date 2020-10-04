package hw2;


import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double[] thresholds;
    private int N;
    private int T;
    private PercolationFactory pf;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N and T should both be larger than 0");
        }
        thresholds = new double[T];
        this.N = N;
        this.T = T;
        this.pf = pf;
    }

    private void simulate() {
        for (int t = 0; t < T; t++) {
            Percolation world = pf.make(N);
            while (!world.percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                world.open(row, col);
            }
            thresholds[t] = (double) world.numberOfOpenSites() / (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - (1.96 * stddev() / Math.sqrt(T));
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + (1.96 * stddev() / Math.sqrt(T));
    }
}
