class Digraph {
    private int V;
    private int E;

    private Bag<Integer>[] adj;

    public Digraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (Bag<Integer>[]) new Bag[V];

        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Integer>();
        }
    }

    public Digraph(In in) {
        this(in.readInt());
        this.E = in.readInt();

        while (!in.isEmpty()) {
            int v = in.readInt();
            int w = in.readInt();
            adj[v].add(w);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    public Digraph reverse() {
        Digraph r = new Digraph(V());

        for (int v = 0; v < V(); v++) {
            for (int w : adj(v)) {
                r.addEdge(w, v);
            }
        }

        return r;
    }

    @Override
    public String toString() {
        String out = "";

        for (int v = 0; v < V(); v++) {
            out += v + ":";

            for (int w : adj(v)) {
                out += " " + w;
            }

            out += "\n";
        }
        return out;
    }


    public static void main(String[] args) {
        Digraph g = new Digraph(new In("tinyDG.txt"));
        System.out.println(g);
    }

}

