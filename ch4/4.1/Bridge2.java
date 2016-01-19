
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
                low[v] = Math.min(low[v], pre[w]);
                //low[v] = Math.min(low[v], low[w]);
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

    public static void print(Graph g) {
        System.out.println(g);

        Bridge2 b = new Bridge2(g);

        for (int v = 0; v < g.V(); v++) {
            System.out.format("%d: pre %d, low %d\n", v, b.pre()[v], b.low()[v]);
        }
    }

    public static void main(String[] args) {
        //Graph g = new Graph(new In("tinyG.txt"));
        Graph g1 = new Graph(5);
        g1.addEdge(0, 2);
        g1.addEdge(0, 1);
        g1.addEdge(1, 4);
        g1.addEdge(1, 3);
        g1.addEdge(3, 4);
        g1.addEdge(1, 2);

        print(g1);

        Graph g2 = new Graph(6);
        g2.addEdge(0, 3);
        g2.addEdge(0, 1);
        g2.addEdge(1, 5);
        g2.addEdge(1, 2);
        g2.addEdge(2, 4);
        g2.addEdge(2, 3);
        g2.addEdge(4, 5);

        System.out.println("");
        print(g2);

        Graph g3 = new Graph(6);
        g3.addEdge(0, 1);
        g3.addEdge(1, 5);
        g3.addEdge(1, 2);
        g3.addEdge(2, 4);
        g3.addEdge(2, 3);
        g3.addEdge(3, 5);
        g3.addEdge(3, 4);

        System.out.println("");
        print(g3);
    }
}

