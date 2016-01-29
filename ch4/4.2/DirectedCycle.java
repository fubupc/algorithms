class DirectedCycle {

    private boolean[] marked;
    private int[] pre;
    private int[] edgeTo;
    private int count;
    private Stack<Integer> cycle;

    public DirectedCycle(Digraph G) {
        marked = new boolean[G.V()];
        pre = new int[G.V()];
        edgeTo = new int[G.V()];
        cycle = new Stack<Integer>();

        count = 0;

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    public void dfs(Digraph G, int v) {
        marked[v] = true;
        pre[v] = count++;

        for (int w : G.adj(v)) {
            if (cycle.size() > 0) return;

            if (!marked[w]) {
                edgeTo[w] = v;

                dfs(G, w);
            } else if (pre[v] > pre[w]) {
                cycle.push(w);
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                return;
            }
        }
    }

    public boolean hasCycle() {
        return cycle.size() > 0;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }


    public static void main(String[] args) {
        Digraph g = new Digraph(new In("tinyDG.txt"));

        DirectedCycle c = new DirectedCycle(g);

        if (c.hasCycle()) {
            System.out.println("cycle found: " + c.cycle());
        } else {
            System.out.println("cycle not found: ");
        }
    }
}

