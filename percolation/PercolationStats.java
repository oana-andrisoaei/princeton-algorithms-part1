import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
	private static final double VAL = 1.96;

	private final double[] fractions;
	private final int trials;
	private final double mean;
	private final double stddev;
	
	public PercolationStats(int n, int trials) {
		
		if (n <= 0 || trials <= 0)
			throw new java.lang.IllegalArgumentException();
		
		fractions = new double[trials];
		this.trials = trials;
		
		for (int t = 0; t < trials; t++) {
			Percolation perc = new Percolation(n);
		    int row, col;
		    int count = 0;
		    while (!perc.percolates()) {
		      do {
		        row = StdRandom.uniform(n) + 1;
		        col = StdRandom.uniform(n) + 1;
		      } while (perc.isOpen(row,col));
		      count++;
		      perc.open(row, col);
		    }
		    fractions[t] = count/(Math.pow(n,2));
		}
		
		mean = StdStats.mean(fractions);
		stddev = StdStats.stddev(fractions);
	}
	   
	public double mean() {
		return mean;
	}
	   
	public double stddev() {
		return stddev;
	}
	   
	public double confidenceLo() {
		return mean - (VAL * stddev / Math.sqrt(trials));
	}
	   
	public double confidenceHi() {
		return mean + (VAL * stddev / Math.sqrt(trials));	   
	}
	   
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats stats = new PercolationStats(n, trials);
		System.out.println("mean                    = " + stats.mean());
		System.out.println("stddev                  = " + stats.stddev());
		System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
	}
}
