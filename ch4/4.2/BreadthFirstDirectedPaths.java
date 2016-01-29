class BreadthFirstDirectedPaths {
    private int[] edgeTo;
    private boolean[] marked;

    public BreadthFirstDirectedPaths(Digraph G, int s) {
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];

        bfs(G, s);
    }

    public void bfs(Digraph G, int s) {
        Queue<Integer> q = new Queue<Integer>();

        marked[s] = true;
        edgeTo[s] = s;
        q.enqueue(s);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;

        int x;
        Stack<Integer> st = new Stack<Integer>();
        for (x = v; x != edgeTo[x]; x = edgeTo[x]) {
            st.push(x);
        }

        st.push(x);
        return st;
    }

    public static void main(String[] args) {
        Digraph g = new Digraph(new In("tinyDG.txt"));
        System.out.println(g);

        int s = 3;
        BreadthFirstDirectedPaths dfs = new BreadthFirstDirectedPaths(g, s);

        for (int v = 0; v < g.V(); v++) {
            if (dfs.hasPathTo(v)) {
                System.out.format("%d -> %d: %s\n", s, v, dfs.pathTo(v));
            } else {
                System.out.format("%d -> %d: not reachable.\n", s, v);
            }
        }

    }

}

