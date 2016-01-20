import java.util.HashMap;
import java.util.ArrayList;

class SymbolGraph {

    private int count;
    private HashMap<String, Integer> st;
    private ArrayList<String> names;
    private Graph G;

    public SymbolGraph(String filename, String delim) {
        st = new HashMap<String, Integer>();
        names = new ArrayList<String>();

        In f = new In(filename);
        while (f.hasNextLine()) {
            String[] words = f.readLine().split(delim);

            for (int i = 0; i < words.length; i++) {
                if (!st.containsKey(words[i])) {
                    names.add(words[i]);
                    st.put(words[i], count++);
                }
            }

        }

        G = new Graph(count);

        f = new In(filename);
        while (f.hasNextLine()) {
            String[] words = f.readLine().split(delim);
            int v = st.get(words[0]);

            for (int i = 1; i < words.length; i++) {
                G.addEdge(v, st.get(words[i]));
            }
        }
    }

    public boolean contains(String key) {
        return st.containsKey(key);
    }

    public int index(String key) {
        return st.get(key);
    }

    public String name(int v) {
        return names.get(v);
    }

    public Graph G() {
        return G;
    }

    public ArrayList<String> names() {
        return names;
    }

    public static void main(String[] args) {
        SymbolGraph sg = new SymbolGraph("routes.txt", " ");

        System.out.println(sg.G());

        for (int i = 0; i < sg.names().size(); i++) {
            System.out.format("%d: %s\n", i, sg.name(i));
        }
    }
}


