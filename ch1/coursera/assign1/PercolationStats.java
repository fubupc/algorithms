class PercolationStats {
    private double[] threshold;
    private int N;
    private int T;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("N, T must be greater than 0.");

        this.N = N;
        this.T = T;

        threshold = new double[T];

        for (int r = 0; r < T; r++) {
            int count = 0;
            Percolation p = new Percolation(N);

            while (true) {
                int i = StdRandom.uniform(N) + 1;
                int j = StdRandom.uniform(N) + 1;

                if (!p.isOpen(i, j)) {
                    count++;
                    p.open(i, j);
                    if (p.percolates()) break;
                }
            }

            threshold[r] = (double) count / (N * N);
        }
    }

    public double mean() {
        return StdStats.mean(threshold);
    }

    public double stddev() {
        return StdStats.stddev(threshold);
    }

    public double confidenceLo() {
        double m = mean();
        double d = stddev();

        return m - (1.96 * d) / Math.sqrt(T);
    }

    public double confidenceHi() {
        double m = mean();
        double d = stddev();

        return m + (1.96 * d) / Math.sqrt(T);
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(N, T);

        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
    }

}

