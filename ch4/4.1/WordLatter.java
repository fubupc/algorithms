class WordLatter {
    public static void main(String[] args) {
        String filename = args[0];
        String start = args[1];
        String end = args[2];

        SymbolGraph sg = new SymbolGraph(filename, ",");
        Graph g = sg.G();

        boolean[] marked = new boolean[g.V()];

        dfs(start, sg, marked);

        BreadthFirstPaths bfs = new BreadthFirstPaths(g, sg.index(start));

        if (bfs.hasPathTo(sg.index(end))) {
            for (int v : bfs.pathTo(sg.index(end))) {
                System.out.format("%s ", sg.name(v));
            }
        } else {
            System.out.println("no path");
        }

        System.out.println();
    }

    public static void dfs(String word, SymbolGraph sg, boolean[] marked) {
        int curr = sg.index(word);
        int next;
        String w;

        marked[curr] = true;

        for (int i = 0; i < word.length(); i++) {
            for (int j = 0; j < 26; j++) {
                if (j == (int) word.charAt(i) - (int) 'a') continue;

                char c = (char) ((int) 'a' + j);

                if (i == 0) {
                    w = c + word.substring(1);
                } else {
                    w = word.substring(0, i) + c + word.substring(i + 1);
                }

                if (sg.contains(w)) {
                    next = sg.index(w);
                    sg.G().addEdge(curr, next);

                    if (!marked[next]) {
                        dfs(w, sg, marked);
                    }
                }
            }
        }
    }

}

