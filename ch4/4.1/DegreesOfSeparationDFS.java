class DegreesOfSeparationDFS {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Please provide 3 parameters: filename, separator and vertex.");
            return;
        }

        String filename = args[0];
        String separator = args[1];
        String vertex = args[2];

        SymbolGraph sg = new SymbolGraph(filename, separator);
        Graph g = sg.G();
        DepthFirstPaths dfs = new DepthFirstPaths(g, sg.index(vertex));

        while (StdIn.hasNextLine()) {
            String symbol = StdIn.readLine();

            if (sg.contains(symbol)) {
                int v = sg.index(symbol);

                if (dfs.hasPathTo(v)) {
                    for (int w : dfs.pathTo(v)) {
                        System.out.println("  " + sg.name(w));
                    }
                } else {
                    System.out.println("There is no path to: " + symbol);
                }
            } else {
                System.out.println("Not in database.");
            }
        }
    }
}
