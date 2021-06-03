import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
    private Percolation per;
    private int size;
    private int times;
    private double[] result;
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        result = new double[trials];
        size = n;
        times = trials;
        for (int i = 0; i < trials; i++) {
            per = new Percolation(n);
            while (!per.percolates()) {
                int row = StdRandom.uniform(size) + 1;
                int col = StdRandom.uniform(size) + 1;
                if (!per.isOpen(row, col)) {
                    per.open(row, col);
                }
            }
            result[i] = (double) per.numberOfOpenSites() / (double) (size * size);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(result);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(result);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(times);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(times);
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats result = new PercolationStats(Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));
        System.out.println(result.mean());
        System.out.println(result.stddev());
        System.out.println("[" + result.confidenceLo() + ", " + result.confidenceHi() + "]");
    }
}
