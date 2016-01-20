import java.util.Queue;
import java.util.LinkedList;

class BreadthFirstSearch {
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;

    public BreadthFirstSearch(Graph g, int s) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        distTo = new int[g.V()];

        bfs(g, s);
    }

    public BreadthFirstSearch(Graph G, int[] sources) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];

        bfs(G, sources);
    }

    public void bfs(Graph G, int[] sources) {
        Queue<Integer> q = new LinkedList<Integer>();

        for (int s : sources) {
            marked[s] = true;
            edgeTo[s] = s;
            distTo[s] = 0;
            q.add(s);
        }

        while (!q.isEmpty()) {
            int v = q.remove();

            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    q.add(w);
                }
            }
        }
    }

    public void bfs(Graph g, int s) {
        Queue<Integer> q = new LinkedList<Integer>();

        marked[s] = true;
        edgeTo[s] = s;
        distTo[s] = 0;
        q.add(s);

        while (!q.isEmpty()) {
            int v = q.remove();

            for (int w : g.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    q.add(w);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (! hasPathTo(v)) return null;

        Stack<Integer> path = new Stack<Integer>();

        int w = v;
        for (; w != edgeTo[w]; w = edgeTo[w]) {
            path.push(w);
        }

        path.push(w);

        return path;
    }

    public int distTo(int v) {
        return distTo[v];
    }

    public static void main(String[] args) {
        BreadthFirstSearch bfs;

        Graph g = new Graph(new In("tinyG.txt"));
        System.out.println(g);

        int s = 3;
        bfs = new BreadthFirstSearch(g, s);

        System.out.println("Single Sources 3:");
        for (int v = 0; v < g.V(); v++) {
            if (bfs.hasPathTo(v)) {
                System.out.format("%d to %d (%d): ", s, v, bfs.distTo(v));

                for (int w : bfs.pathTo(v)) {
                    System.out.format(w + " ");
                }
            } else {
                System.out.format("%d to %d: not connected.", s, v);
            }
            System.out.format("\n");
        }

        int[] sources = {3, 6, 12};
        bfs = new BreadthFirstSearch(g, sources);

        System.out.println("\nMultiple Sources (3, 6, 12):");
        for (int v = 0; v < g.V(); v++) {
            if (bfs.hasPathTo(v)) {
                System.out.format("%d (%d): ", v, bfs.distTo(v));

                for (int w : bfs.pathTo(v)) {
                    System.out.format(w + " ");
                }
            } else {
                System.out.format("%d to %d: not connected.", s, v);
            }
            System.out.format("\n");
        }
    }

}

