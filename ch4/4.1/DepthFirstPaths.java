import java.util.ArrayList;

class DepthFirstPaths {
    private boolean[] marked;
    private ArrayList<Integer>[] paths;

    public DepthFirstPaths(Graph g, int s) {
        paths = (ArrayList<Integer>[]) new ArrayList[g.V()];
        marked = new boolean[g.V()];

        for (int i = 0; i < g.V(); i++) {
            paths[i] = null;
            marked[i] = false;
        }

        dfs(g, new ArrayList<Integer>(), s);
    }

    public void dfs(Graph g, ArrayList<Integer> path, int v) {
        if (marked[v]) return;

        marked[v] = true;

        ArrayList<Integer> copy = new ArrayList<Integer>(path);
        copy.add(v);

        paths[v] = copy;

        for (int w : g.adj(v)) {
            dfs(g, copy, w);
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        return paths[v];
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In("tinyG.txt"));

        System.out.println(g);

        DepthFirstPaths paths = new DepthFirstPaths(g, 3);

        int[] vertices = {0, 6, 7, 8, 9, 10};

        for (int i = 0; i < vertices.length; i++) {
            System.out.println("connected to " + vertices[i] + "? " + paths.hasPathTo(vertices[i]));
            System.out.println("path to " + vertices[i] + ": " + paths.pathTo(vertices[i]));
        }
    }

}
