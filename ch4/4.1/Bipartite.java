import java.util.ArrayList;

class Bipartite {
    private boolean[] marked;
    private boolean[] color;
    private int[] edgeTo;
    private Stack<Integer> oddCycle;

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public Bipartite(Graph G) {
        marked = new boolean[G.V()];
        color = new boolean[G.V()];
        edgeTo = new int[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v, RED);
            }
        }
    }

    public void dfs(Graph G, int v, boolean col) {
        marked[v] = true;
        color[v] = col;

        for (int w: G.adj(v)) {
            if (oddCycle != null) return;

            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w, !col);
            } else if (col == color[w]) {
                oddCycle = new Stack<Integer>();

                for (int x = v; x != w; x = edgeTo[x]) {
                    oddCycle.push(x);
                }

                oddCycle.push(w);
                oddCycle.push(v);
            }
        }
    }

    public boolean bipartite() {
        return oddCycle == null;
    }

    public Iterable<Integer> oddCycle() {
        return oddCycle;
    }

    public Iterable<Integer> getRed() {
        ArrayList<Integer> r = new ArrayList<Integer>();

        for (int i = 0; i < color.length; i++) {
            if (color[i] == RED) r.add(i);
        }

        return r;
    }

    public Iterable<Integer> getBlack() {
        ArrayList<Integer> r = new ArrayList<Integer>();

        for (int i = 0; i < color.length; i++) {
            if (color[i] == BLACK) r.add(i);
        }

        return r;
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In("tinyG.txt"));
        System.out.println(g);

        Bipartite b = new Bipartite(g);

        if (b.bipartite()) {
            System.out.println("g can be bipartited.");
            System.out.println("red:" + b.getRed());
            System.out.println("black:" + b.getBlack());
        } else {
            System.out.println("g can not be bipartited.");
            System.out.println("odd cycle: " + b.oddCycle());
        }
    }
}
