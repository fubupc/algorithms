import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

public class Percolation {
    /* Percolation grid size N. */
    private int N;

    /* record open cells.
     * NOTE: use one-dimensional to represent two-dimensional/table here.
     */
    private boolean[] opened;

    /* WeightedQuickUnionUF */
    private WeightedQuickUnionUF uf;

    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("N must be integer greater than 0.");

        this.N = N;
        int total = (N + 2) * (N + 2);

        opened = new boolean[total];
        uf = new WeightedQuickUnionUF(total);

        for (int i = 0; i < N + 2; i++) {
            opened[i] = true;
            opened[xyTo1D(N + 1, i)] = true;

            uf.union(0, i);
            uf.union(total - 1, xyTo1D(N + 1, i));
        }
    }

    /* Convert two dimensional coordinate into one dimensional index */
    private int xyTo1D(int i, int j) {
        return i * (N + 2) + j;
    }

    private void checkXY(int x, int y) {
        if (!((1 <= x && x <= N) && (1 <= y && y <= N)))
            throw new IndexOutOfBoundsException("(" + x + ", " + 
                    y + ") is outside of range: " + N);
    }

    public boolean isFull(int i, int j) {
        checkXY(i, j);
        return uf.connected(0, xyTo1D(i, j));
    }

    public boolean isOpen(int i, int j) {
        checkXY(i, j);
        return opened[xyTo1D(i, j)];
    }

    public boolean percolates() {
        return uf.connected(0, (N + 2) * (N + 2) - 1);
    }

    public void open(int i, int j) {
        checkXY(i, j);
        int idx = xyTo1D(i, j);

        if (opened[idx]) return;

        opened[idx] = true;

        if (opened[xyTo1D(i - 1, j)]) uf.union(idx, xyTo1D(i - 1, j));
        if (opened[xyTo1D(i + 1, j)]) uf.union(idx, xyTo1D(i + 1, j));
        if (opened[xyTo1D(i, j - 1)]) uf.union(idx, xyTo1D(i, j - 1));
        if (opened[xyTo1D(i, j + 1)]) uf.union(idx, xyTo1D(i, j + 1));
    }

    /*
    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (isOpen(i, j)) {
                    buf.append("■ ");
                } else {
                    buf.append("□ ");
                }
            }
            buf.append("\n");
        }
        return buf.toString();
    }
    */

    public static void main(String[] args) {
        int N = 10;
        Percolation p = new Percolation(N);

        int count = 0;

        while (true) {
            int i = StdRandom.uniform(1,  N + 1);
            int j = StdRandom.uniform(1,  N + 1);

            if (!p.isOpen(i, j)) {
                count++;
                p.open(i, j);
                if (p.percolates()) break;
            }
        }

        System.out.format("total: %d, open: %d\n", N * N,  count);
        System.out.println("rate: " + (float) count / (N * N));
        System.out.println(p);
    }

}

