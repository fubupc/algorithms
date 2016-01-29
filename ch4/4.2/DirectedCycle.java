class DirectedCycle {

    private boolean[] marked;
    private boolean[] onStack;
    private int[] edgeTo;
    private Stack<Integer> cycle;

    public DirectedCycle(Digraph G) {
        marked = new boolean[G.V()];
        onStack = new boolean[G.V()];
        edgeTo = new int[G.V()];
        cycle = new Stack<Integer>();

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    public void dfs(Digraph G, int v) {
        marked[v] = true;

        onStack[v] = true;

        for (int w : G.adj(v)) {
            if (cycle.size() > 0) return;

            if (!marked[w]) {
                edgeTo[w] = v;

                dfs(G, w);
            } else if (onStack[w]) {
                cycle.push(w);
                for (int x = v; x != w; x = edgeTo[x]) {
                    cycle.push(x);
                }
                cycle.push(w);
                return;
            }
        }

        onStack[v] = false;
    }

    public boolean hasCycle() {
        return cycle.size() > 0;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }


    public static void main(String[] args) {
        Digraph g = new Digraph(3);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(0, 2);

        DirectedCycle c = new DirectedCycle(g);

        if (c.hasCycle()) {
            System.out.println("cycle found: " + c.cycle());
        } else {
            System.out.println("cycle not found.");
        }

        Digraph g2 = new Digraph(3);
        g2.addEdge(0, 2);
        g2.addEdge(0, 1);
        g2.addEdge(1, 2);

        DirectedCycle c2 = new DirectedCycle(g2);

        if (c2.hasCycle()) {
            System.out.println("cycle found: " + c2.cycle());
        } else {
            System.out.println("cycle not found.");
        }
    }
}

