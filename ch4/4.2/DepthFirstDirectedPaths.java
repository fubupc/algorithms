class DepthFirstDirectedPaths {
    private boolean[] marked;
    private int[] edgeTo;

    public DepthFirstDirectedPaths(Digraph G, int s) {

        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];

        for (int v = 0; v < G.V(); v++) {
            edgeTo[v] = -1;
        }

        dfs(G, s);
    }

    public DepthFirstDirectedPaths(Digraph G, int[] sources) {

        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];

        for (int v = 0; v < G.V(); v++) {
            edgeTo[v] = -1;
        }

        for (int i = 0; i < sources.length; i++) {
            int s = sources[i];
            if (!marked[s])
                dfs(G, s);
        }
    }

    public void dfs(Digraph G, int v) {
        marked[v] = true;

        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;

        Stack<Integer> st = new Stack<Integer>();

        for (int x = v; x != -1; x = edgeTo[x]) {
            st.push(x);
        }

        return st;
    }

    public static void main(String[] args) {
        Digraph g = new Digraph(new In("tinyDG.txt"));
        System.out.println(g);

        int s = 3;
        DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(g, s);

        for (int v = 0; v < g.V(); v++) {
            if (dfs.hasPathTo(v)) {
                System.out.format("%d -> %d: %s\n", s, v, dfs.pathTo(v));
            } else {
                System.out.format("%d -> %d: not reachable.\n", s, v);
            }
        }

        DepthFirstDirectedPaths dfs2 = new DepthFirstDirectedPaths(g, new int[]{0, 5, 6});

        System.out.println();
        for (int v = 0; v < g.V(); v++) {
            if (dfs2.hasPathTo(v)) {
                System.out.format("%d: %s\n", v, dfs2.pathTo(v));
            } else {
                System.out.format("%d: not reachable.\n", v);
            }
        }
    }

}


