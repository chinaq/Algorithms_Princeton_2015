import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private int N;
    private int T;
    private double[] x;
    
    private double u;
    private double o;
    private double lo;
    private double hi;
    
    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new java.lang.IllegalArgumentException();       
        this.N = N;
        this.T = T;
        x = new double[T];        
        doExperiments();
    }
    
    private void doExperiments() {    
        for (int t = 0; t < T; t++) {
            setThresholdx(t);
        }
        
        u = StdStats.mean(x);
        o = StdStats.stddev(x);
        lo = u - 1.96*o/java.lang.Math.sqrt(T);
        hi = u + 1.96*o/java.lang.Math.sqrt(T);
    }
    
    
    private void setThresholdx(int t) {        
          Percolation percolation = new Percolation(N);
          int addedCount = 0;
          int i, j;
          while (!percolation.percolates()) {          
              i = StdRandom.uniform(1, N+1);
              j = StdRandom.uniform(1, N+1);
              if (!percolation.isOpen(i, j)) {
                  percolation.open(i, j);
                  addedCount++;
              }
          }
          x[t] = (double) addedCount/ (double) (N*N);
    }
    
    // sample mean of percolation threshold
    public double mean()                      
    {
        return u;
    }

    // sample standard deviation of percolation threshold
    public double stddev()                    
    {
        return o;    
    }
    
    // low  endpoint of 95% confidence interval
    public double confidenceLo()              
    {
        return lo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()              
    {
        return hi;
    }
        
    // test client (described below)
    public static void main(String[] args)    
    {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats perStats = new PercolationStats(n, t);
        
        StdOut.println("mean = " + perStats.mean());
        StdOut.println("stddev = " + perStats.stddev());
        StdOut.println("95% ci = " 
                           + perStats.confidenceLo() + ", " 
                           + perStats.confidenceHi());
    }

}