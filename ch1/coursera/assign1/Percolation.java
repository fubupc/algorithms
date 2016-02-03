class Percolation {
    public static void main(String[] args) {
        int N = 2;
        Percolation p = new Percolation(N);

        int count = 0;

        while (true) {
            int r = (int) (Math.random() * (N * N)) + 1;
            int i = r / N + 1;
            int j = r % N + 1;
            
            if (!p.isOpen(i, j)) {
                count++;
                p.open(i, j);
                if (p.percolates()) break;
            }
        }

        System.out.println("rate: " + (float) count / (N * N));
    }

    private int N;
    private boolean[][] opened;
    private int[][][] parent;
    private int[][] size;

    public Percolation(int N) {
        this.N = N;
        opened = new boolean[N + 2][N + 2];
        parent = new int[N + 2][N + 2][];
        size = new int[N + 2][N + 2];

        for (int i = 0; i < N + 2; i++) {
            for (int j = 0; j < N + 2; j++) {
                size[i][j] = 1;
                parent[i][j] = new int[]{i, j};
            }
        }

        for (int i = 0; i < N + 2; i++) {
            opened[0][i] = true;
            opened[N + 1][i] = true;

            connect(0, 0, 0, i);
            connect(N + 1, N + 1, N + 1, i);
        }
    }

    public boolean isFull(int i, int j) {
        if (!opened[i][j]) return false;
        return connected(0, 0, i, j);
    }

    public boolean isOpen(int i, int j) {
        return opened[i][j];
    }

    public boolean percolates() {
        return isFull(N + 1, N + 1);
    }

    public void open(int i, int j) {
        if (opened[i][j]) return;

        opened[i][j] = true;

        if (opened[i - 1][j]) connect(i, j, i - 1, j);
        if (opened[i + 1][j]) connect(i, j, i + 1, j);
        if (opened[i][j - 1]) connect(i, j, i, j - 1);
        if (opened[i][j + 1]) connect(i, j, i, j + 1);
    }

    public int[] find(int i, int j) {
        int [] p;
        int count = 0; 
        while (true) {
            if (count > 10) {
                System.out.format("find loop: (%d, %d).\n%s\n", i, j, this); 
                throw new IllegalArgumentException();
            }

            p = parent[i][j];
            if (p[0] == i && p[1] == j) break;

            i = p[0];
            j = p[1];

            count++;
        }

        return p;
    }

    public boolean connected(int i, int j, int x, int y) {
        int[] p = find(i, j);
        int[] q = find(x, y);


        int ri = p[0];
        int rj = p[1];
        int rx = q[0];
        int ry = q[1];

        if (ri == rx && rj == ry) return true;
        return false;
    }

    public void connect(int i, int j, int x, int y) {

        int[] p = find(i, j);
        int[] q = find(x, y);

        int ri = p[0];
        int rj = p[1];
        int rx = q[0];
        int ry = q[1];

        if (ri == rx && rj == ry) return;

        if (size[ri][rj] < size[rx][ry]) {
            parent[ri][rj] = new int[]{rx, ry};
            size[rx][ry] += size[ri][rj];
        } else {
            parent[rx][ry] = new int[]{ri, rj};
            size[ri][rj] += size[rx][ry];
        }
    }

    @Override
    public String toString() {
        String out = "";

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (opened[i][j]) {
                    out += "■ ";
                } else {
                    out += "□ ";
                }
            }
            out += "\n";
        }
        for (int i = 0; i < N + 2; i++) {
            for (int j = 0; j < N + 2; j++) {
                out += "(" + parent[i][j][0] + "," + parent[i][j][1] + ") ";
            }
            out += "\n";
        }
        return out;
    }

}

