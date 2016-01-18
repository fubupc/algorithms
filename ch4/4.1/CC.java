class CC {
    private int count;
    private boolean[] marked;
    private int[] id;
    private int[] size;

    public CC(Graph g) {

        count = 0;
        marked = new boolean[g.V()];
        id = new int[g.V()];
        size = new int[g.V()];


        for (int i = 0; i < g.V(); i++) {
            if (!marked[i]) {
                dfs(g, i);
                count++;
            }
        }
    }

    public void dfs(Graph g, int s) {
        marked[s] = true;
        id[s] = count;
        size[count]++;

        for (int v : g.adj(s)) {
            if (!marked[v]) {
                dfs(g, v);
            }
        }
    }

    public int size(int id) {
        return size[id];
    }
    
    public int id(int v) {
        return id[v];
    }

    public int count() {
        return count;
    }

    public boolean connected(int v, int w) {
        return id(v) == id(w);
    }

    public static void main(String[] args) {
        Graph g = new Graph(new In("tinyG.txt"));
        System.out.println(g);

        CC cc = new CC(g);

        System.out.println("Connected Components: " + cc.count());

        for (int i = 0; i < cc.count(); i++) {
            System.out.format("id %d size: %d\n", i, cc.size(i));
        }
    }

}

