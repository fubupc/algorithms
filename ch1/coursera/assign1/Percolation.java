import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

class Percolation {
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

    /* Percolation grid size N. */
    private int N;

    /* record open cells.
     * NOTE: use one-dimensional to represent two-dimensional/table here.
     */
    private boolean[] opened;

    /* WeightedQuickUnionUF */
    private WeightedQuickUnionUF uf;

    /* Convert two dimensional coordinate into one dimensional index */
    private int toIndex(int i, int j) {
        return i * (N + 2) + j;
    }

    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException("N must be integer greater than 0.");

        this.N = N;
        int total = (N + 2) * (N + 2);

        opened = new boolean[total];
        uf = new WeightedQuickUnionUF(total);

        for (int i = 0; i < N + 2; i++) {
            opened[i] = true;
            opened[toIndex(N + 1, i)] = true;

            uf.union(0, i);
            uf.union(total - 1, toIndex(N + 1, i));
        }
    }

    public boolean isFull(int i, int j) {
        if (!((1 <= i && i <= N) && (1 <= j && j <= N)))
            throw new IndexOutOfBoundsException("(" + i + ", " + j + ") is outside of range: " + N);

        return uf.connected(0, toIndex(i, j));
    }

    public boolean isOpen(int i, int j) {
        if (!((1 <= i && i <= N) && (1 <= j && j <= N)))
            throw new IndexOutOfBoundsException("(" + i + ", " + j + ") is outside of range: " + N);

        return opened[toIndex(i, j)];
    }

    public boolean percolates() {
        return uf.connected(0, (N + 2) * (N + 2) - 1);
    }

    public void open(int i, int j) {
        if (!((1 <= i && i <= N) && (1 <= j && j <= N)))
            throw new IndexOutOfBoundsException("(" + i + ", " + j + ") is outside of range: " + N);

        int idx = toIndex(i, j);

        if (opened[idx]) return;

        opened[idx] = true;

        if (opened[toIndex(i - 1, j)]) uf.union(idx, toIndex(i - 1, j));
        if (opened[toIndex(i + 1, j)]) uf.union(idx, toIndex(i + 1, j));
        if (opened[toIndex(i, j - 1)]) uf.union(idx, toIndex(i, j - 1));
        if (opened[toIndex(i, j + 1)]) uf.union(idx, toIndex(i, j + 1));
    }

    @Override
    public String toString() {
        String out = "";

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (isOpen(i, j)) {
                    out += "■ ";
                } else {
                    out += "□ ";
                }
            }
            out += "\n";
        }
        return out;
    }
}

