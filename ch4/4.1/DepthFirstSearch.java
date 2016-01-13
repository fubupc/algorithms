class DepthFirstSearch {
    private boolean[] connect;
    private int count = 0;

    private void search(Graph g, int s) {
        if (connect[s]) return;

        connect[s] = true;
        count++;

        for (int v : g.adj(s)) {
            search(g, v);
        }
    }

    public DepthFirstSearch(Graph g, int s) {
        connect = new boolean[g.V()];
        search(g, s);
    }

    public boolean marked(int v) {
        return connect[v];
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In("tinyG.txt"));
        DepthFirstSearch search = new DepthFirstSearch(g, 3);

        int[] vertices = {0, 6, 7, 8, 9, 10};

        for (int i = 0; i < vertices.length; i++) {
            System.out.println("connect " + vertices[i] + "? " + search.marked(vertices[i]));
        }
    }

}
