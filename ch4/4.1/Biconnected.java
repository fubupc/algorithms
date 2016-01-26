class Biconnected {
    private boolean[] marked;
    private boolean[] articulation;

    private int count;

    private int[] low;
    private int[] pre;

    public Biconnected(Graph G) {
        count = 0;

        marked = new boolean[G.V()];
        articulation = new boolean[G.V()];

        low = new int[G.V()];
        pre = new int[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v])
                dfs(G, v, -1);
        }
    }

    private void dfs(Graph G, int v, int from) {
        marked[v] = true;

        pre[v] = count++;
        low[v] = pre[v];

        int branch = 0;

        for (int w : G.adj(v)) {
            if (!marked[w]) {

                dfs(G, w, v);

                low[v] = Math.min(low[v], low[w]);

                branch++;

                if (from != -1 && low[w] >= pre[v]) {
                    articulation[v] = true;
                }
            } else if (w != from) {
                low[v] = Math.min(low[v], pre[w]);
            }
        }

        if (from == -1 && branch > 1) {
            articulation[v] = true;
        }

    }

    public boolean articulation(int v) {
        return articulation[v];
    }

    public boolean bridge(int v, int w) {
        return pre[v] == low[v] || pre[w] == low[w];
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In("tinyG.txt"));
        System.out.println(g);

        Biconnected bc = new Biconnected(g);

        for (int v = 0; v < g.V(); v++) {
            System.out.format("%d: %s\n", v, bc.articulation(v));
        }
    }
}


