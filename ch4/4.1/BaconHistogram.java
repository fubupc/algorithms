import java.util.ArrayList;

class BaconHistogram {
    public static void main(String[] args) {
        SymbolGraph sg = new SymbolGraph("movies.txt", "/");
        String name = "Bacon, Kevin";

        Graph g = sg.G();
        BreadthFirstPaths bfs = new BreadthFirstPaths(g, sg.index(name));

        ArrayList<Integer>[] histogram = (ArrayList<Integer>[]) new ArrayList[g.V()];
        ArrayList<Integer> notConnected = new ArrayList<Integer>();

        for (int v = 0; v < g.V(); v++) {
            if (bfs.hasPathTo(v)) {
                int dist = bfs.distTo(v);

                if (histogram[dist] == null) {
                    histogram[dist] = new ArrayList<Integer>();
                }

                histogram[dist].add(v);
            } else {
                notConnected.add(v);
            }
        }

        for (int i = 0; histogram[i] != null; i += 2) {
            System.out.format("%4d %8d\n", i / 2, histogram[i].size());
        }

        System.out.println("Infinite: " + notConnected.size());

        /*for (int j = 0; j < notConnected.size(); j++) {
            System.out.format("[%s] \t\t", sg.name(j));
        }

        System.out.println();
        */

    }

}
