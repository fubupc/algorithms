import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;

/* Resolve backwash problem 
 *
 * Reference: http://www.sigmainfy.com/blog/avoid-backwash-in-percolation.html
 *
 * */

public class Percolation {
    /* Percolation grid size N. */
    private int N;

    /* record cell status: open, top connected, bottom connected.  */
    private byte[] status;

    private boolean percolates;

    /* WeightedQuickUnionUF */
    private WeightedQuickUnionUF uf;

    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException("N must be integer greater than 0.");

        this.N = N;
        percolates = false;
        status = new byte[(N + 2) * (N + 2)];
        uf = new WeightedQuickUnionUF((N + 2) * (N + 2));

        for (int i = 1; i <= N; i++) {
            setTopConnected(xyTo1D(1, i));
            setBottomConnected(xyTo1D(N, i));
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

    private void setTopConnected(int idx) {
        status[idx] = (byte) (status[idx] | 0b100);
    }

    private void setBottomConnected(int idx) {
        status[idx] = (byte) (status[idx] | 0b010);
    }

    private void setOpened(int idx) {
        status[idx] = (byte) (status[idx] | 0b001);
    }

    private boolean topConnected(int idx) {
        return (status[idx] & 0b100) >> 2 == 1;
    }

    private boolean bottomConnected(int idx) {
        return (status[idx] & 0b010) >> 1 == 1;
    }

    private boolean isOpen(int idx) {
        return (status[idx] & 0b001) == 1;
    }

    public boolean isOpen(int i, int j) {
        checkXY(i, j);
        return isOpen(xyTo1D(i, j));
    }

    public boolean isFull(int i, int j) {
        checkXY(i, j);

        int root = uf.find(xyTo1D(i, j));

        return isOpen(xyTo1D(i, j)) && topConnected(root);
    }

    public boolean percolates() {
        return percolates;
    }

    public void open(int i, int j) {
        checkXY(i, j);

        int idx = xyTo1D(i, j);

        if (isOpen(idx)) return;

        setOpened(idx);

        boolean top = false;
        boolean bottom = false;

        int[] neighbors = new int[4];
        neighbors[0] = xyTo1D(i - 1, j);
        neighbors[1] = xyTo1D(i + 1, j);
        neighbors[2] = xyTo1D(i, j - 1);
        neighbors[3] = xyTo1D(i, j + 1);

        for (int n = 0; n < neighbors.length; n++) {
            if (!isOpen(neighbors[n])) continue;

            int root = uf.find(neighbors[n]);

            if (topConnected(root)) top = true;
            if (bottomConnected(root)) bottom = true;

            uf.union(idx, root);
        }

        int newRoot = uf.find(idx);

        if (top || topConnected(idx)) setTopConnected(newRoot);
        if (bottom || bottomConnected(idx)) setBottomConnected(newRoot);

        if (topConnected(newRoot) && bottomConnected(newRoot))
            percolates = true;
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

