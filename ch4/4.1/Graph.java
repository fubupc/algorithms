
class Graph {
    private int V;
    private int E;
    private Bag<Integer>[] adjList;

    public Graph(int V) {
        if (V < 0) throw new IllegalArgumentException("Vertices must be greater than 0, but " + V + " provided.");
        this.V = V;
        this.E = 0;
        adjList = (Bag<Integer>[]) new Bag[V];

        for (int i = 0; i < V; i++) {
            adjList[i] = new Bag<Integer>();
        }
    }
    
    public Graph(In in) {
        this(in.readInt());

        int E = in.readInt();

        for (int i = 0; i < E; i++) {
            int s = in.readInt();
            int d = in.readInt();
            addEdge(s, d);
        }
    }

    public Graph(Graph G) {
        this(G.V());

        this.E = G.E();

        for (int v = 0; v < G.V(); v++) {
            Stack<Integer> reverse = new Stack<Integer>();

            for (int w : G.adjList[v]) {
                reverse.push(w);
            }

            for (int x : reverse) {
                adjList[v].add(x);
            }
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    void addEdge(int v, int w) {
        if (v >= V || w >= V) {
            throw new IllegalArgumentException("vertex is out of range: v " + v + ", w " + w);
        }

        E++;

        adjList[v].add(w);
        adjList[w].add(v);
    }

    public Iterable<Integer> adj(int v) {
        return adjList[v];
    }

    public String toString() {
        String out = "";
        for (int v = 0; v < V; v++) {
            out += v + ":";
            for (int w : adjList[v]) {
                out += " " + w;
            }
            out += "\n";
        }

        return out;
    }

}


