class ConnectedComponent {

    private int[] parent;
    private int count;

    public ConnectedComponent(Graph g) {
        count = g.V();
        parent = new int[g.V()];

        for (int i = 0; i < g.V(); i++) {
            parent[i] = i;
        }

        traversal(g);
    }

    public int count() {
        return count;
    }

    public int find(int x) {
        while (parent[x] != x) {
            x = parent[x];
        }
        return x;
    }

    public void union(int x, int y) {
        int p = find(x);
        int q = find(y);

        if (p != q) {
            parent[p] = parent[q];
            count--;
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

    public void traversal(Graph g) {
        for (int v = 0; v < g.V(); v++) {
            for (int w : g.adj(v)) {
                union(w, v);
            }
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In("tinyG.txt"));
        System.out.println(g);

        ConnectedComponent c = new ConnectedComponent(g);
        int s = 3;

        System.out.format("component number: %d\n", c.count());

        for (int i = 0; i < g.V(); i++) {
            System.out.format("%d => %d. %s\n", i, c.find(i), c.connected(s, i));
        }
    }
}

