import java.util.HashMap;

class Edge {
    private int v;
    private int w;

    public Edge(int vv, int ww) {
        if (vv > ww) {
            v = ww;
            w = vv;
        } else {
            v = vv;
            w = ww;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Edge) {
            Edge o1 = (Edge) o;
            return o1.v == this.v && o1.w == this.w;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return v * 137 + w;
    }
}

class Bridge {
    private boolean[] marked;
    private int[] edgeTo;
    private HashMap<Edge, Boolean> edges;

    public Bridge(Graph G) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        edges = new HashMap<Edge, Boolean>();

        for (int v = 0; v <G.V(); v++) {
            edgeTo[v] = -1;
        }

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v, -1);
            }
        }
    }

    public void dfs(Graph G, int v, int from) {
        marked[v] = true;

        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                edges.put(new Edge(v, w), true);
                dfs(G, w, v);
            } else if (w != from) {
                for (int x = v; x != w && edgeTo[x] != -1; x = edgeTo[x]) {
                    edges.put(new Edge(x, edgeTo[x]), false);
                    System.out.format("(%d - %d) ", x, edgeTo[x]);
                }

                edges.put(new Edge(w, v), false);
                System.out.format("(%d - %d)\n", w, v);
            }
        }
    }

    public boolean isBridge(int v, int w) {
        return edges.get(new Edge(v, w));
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In("tinyG.txt"));
        System.out.println(g);

        Bridge b = new Bridge(g);

        for (int v = 0; v < g.V(); v++) {
            for (int w : g.adj(v)) {
                System.out.format("%d to %d: %s\n", v, w, b.isBridge(v, w));
            }
        }
    }
}

