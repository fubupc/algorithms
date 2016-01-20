import java.util.Queue;
import java.util.LinkedList;

class BreadthFirstSearch {
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;

    private final int s;

    public BreadthFirstSearch(Graph g, int s) {
        marked = new boolean[g.V()];
        edgeTo = new int[g.V()];
        distTo = new int[g.V()];

        this.s = s;

        bfs(g, s);
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
                    System.out.format("enqueu: %d\n", w);
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

        for (int w = v; w != this.s; w = edgeTo[w]) {
            path.push(w);
        }

        path.push(this.s);

        return path;
    }

    public int distTo(int v) {
        return distTo[v];
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In("tinyG.txt"));
        System.out.println(g);

        int s = 3;
        BreadthFirstSearch paths = new BreadthFirstSearch(g, s);

        for (int v = 0; v < g.V(); v++) {
            if (paths.hasPathTo(v)) {
                System.out.format("%d to %d (%d): ", s, v, paths.distTo(v));

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

