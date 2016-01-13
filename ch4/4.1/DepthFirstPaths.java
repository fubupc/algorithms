import java.util.ArrayList;

class DepthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;

    public DepthFirstPaths(Graph g, int s) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];

        for (int i = 0; i < g.V(); i++) {
            edgeTo[i] = -1;
            marked[i] = false;
        }

        dfs(g, s);
    }

    public void dfs(Graph g, int v) {
        marked[v] = true;
        for (int w : g.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(g, w);
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!marked[v]) return null;

        Stack<Integer> path = new Stack<Integer>();

        while (v != -1) {
            path.push(v);
            v = edgeTo[v];
        }

        return path;
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In("tinyG.txt"));
        System.out.println(g);

        int s = 3;
        DepthFirstPaths paths = new DepthFirstPaths(g, s);

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
