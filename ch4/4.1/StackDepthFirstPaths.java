
class StackDepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;

    public StackDepthFirstPaths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];

        for (int v = 0; v < G.V(); v++) {
            edgeTo[v] = -1;
        }

        dfs(G, s);
    }

    public void dfs(Graph G, int v) {
        Stack<Integer> st = new Stack<Integer>();

        st.push(v);

        while (!st.isEmpty()) {
            int w = st.pop();

            if (marked[w]) continue;

            marked[w] = true;

            Stack<Integer> reverse = new Stack<Integer>();
            for (int u : G.adj(w)) {
                reverse.push(u);
            }

            for (int x : reverse) {
                if (!marked[x]) {
                    st.push(x);
                    edgeTo[x] = w;
                }
            }

        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;

        Stack<Integer> st = new Stack<Integer>();
        for (int w = v; w != -1; w = edgeTo[w]) {
            st.push(w);
        }

        return st;
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In("tinyG.txt"));
        System.out.println(g);

        int s = 3;
        StackDepthFirstPaths paths = new StackDepthFirstPaths(g, s);

        for (int v = 0; v < g.V(); v++) {
            if (paths.hasPathTo(v)) {
                System.out.format("%d to %d: ", s, v);

                for (int w : paths.pathTo(v)) {
                    System.out.format(w + " ");
                }
            } else {
                System.out.format("%d to %d: not connected.", s, v);
            }
            System.out.format("\n");
        }
    }

}
