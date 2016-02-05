class WeightedQuickUnionUF {
    private int[] parent;
    private int[] size;
    private int count;

    public WeightedQuickUnionUF(int N) {
        parent = new int[N];
        size = new int[N];
        count = N;

        for (int i = 0; i < N; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public void union(int p, int q) {
        int pr = find(p);
        int qr = find(q);

        if (pr == qr) return;

        if (size[pr] < size[qr]) {
            parent[pr] = qr;
            size[qr] += size[pr];
        } else {
            parent[qr] = pr;
            size[pr] += size[qr];
        }

        count--;
    }

    public int find(int p) {
        while (parent[p] != p) {
            p = parent[p];
        }

        return p;
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public static void main(String[] args) {
        WeightedQuickUnionUF u = new WeightedQuickUnionUF(10);

        u.union(0, 1);
        u.union(2, 3);
        u.union(3, 4);
        u.union(1, 4);
        u.union(8, 9);
        u.union(5, 7);
        u.union(6, 5);

        System.out.println("count: " + u.count());
        System.out.format("%d - %d: %s\n", 0, 3, u.connected(0, 3));
        System.out.format("%d - %d: %s\n", 0, 5, u.connected(0, 5));
    }
}

