class Cycle {

    private int[] edgeTo;
    private boolean[] marked;
    private boolean hasCycle;
    private int start;

    public Cycle(Graph G) {
        edgeTo = new int[G.V()];
        marked = new boolean[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                if (dfs(G, v, -1)) {
                    hasCycle = true;
                    break;
                }
            }
        }
    }

    public boolean dfs(Graph G, int v, int from) {
        marked[v] = true;

        for (int w : G.adj(v)) {
            if (!marked[w]) {
                edgeTo[w] = v;
                if (dfs(G, w, v)) {
                    return true;
                }
            } else if (w != from) {
                start = w;
                edgeTo[w] = v;
                return true;
            }
        }

        return false;
    }

    public boolean hasCycle() {
        return hasCycle;
    }

    public Iterable<Integer> one() {
        if (!hasCycle) return null;

        Stack<Integer> s = new Stack<Integer>();

        s.push(start);
        for (int v = edgeTo[start]; v != start; v = edgeTo[v]) {
            s.push(v);
        }
        s.push(start);
        return s;
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In("tinyG.txt"));
        System.out.println(g);

        Cycle c = new Cycle(g);

        if (c.hasCycle()) {
            System.out.format("g has cycle: %s\n", c.one());
        } else {
            System.out.println("no cycle.");
        }
    }
}

