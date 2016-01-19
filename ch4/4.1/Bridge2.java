
class Bridge2 {
    private int[] pre;
    private int[] low;
    private int count;

    public Bridge2(Graph G) {
        pre = new int[G.V()];
        low = new int[G.V()];

        for (int v = 0; v <G.V(); v++) {
            pre[v] = -1;
            low[v] = -1;
        }

        for (int v = 0; v < G.V(); v++) {
            if (pre[v] == -1) {
                dfs(G, v, -1);
            }
        }
    }

    public void dfs(Graph G, int v, int from) {
        pre[v] = count++;
        low[v] = pre[v];

        for (int w : G.adj(v)) {
            if (pre[w] == -1) {
                dfs(G, w, v);
                low[v] = Math.min(low[v], low[w]);
            } else if (w != from) {
                low[v] = Math.min(low[v], low[w]);
            }
        }
    }

    public boolean isBridge(int v, int w) {
        return (pre[v] == low[v]) && (pre[w] == low[w]);
    }

    public int[] pre() {
        return pre;
    }

    public int[] low() {
        return low;
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In("tinyG.txt"));
        System.out.println(g);

        Bridge2 b = new Bridge2(g);


        for (int v = 0; v < g.V(); v++) {
            System.out.format("%d: pre %d, low %d\n", v, b.pre()[v], b.low()[v]);

            for (int w : g.adj(v)) {
                System.out.format("%d to %d: %s\n", v, w, b.isBridge(v, w));
            }
        }
    }
}

