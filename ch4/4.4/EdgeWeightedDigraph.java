import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class EdgeWeightedDigraph {
	private int V;
	private int E;
	private Bag<DirectedEdge>[] adj;

	public EdgeWeightedDigraph(int V) {
		this.V = V;
		this.E = 0;

		adj = (Bag<DirectedEdge>[]) new Bag[V];
		for (int v = 0; v < V; v++) {
			adj[v] = new Bag<DirectedEdge>();
		}
	}

	public EdgeWeightedDigraph(In in) {
		this(in.readInt());
		int E = in.readInt();
		if (E < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
		for (int i = 0; i < E; i++) {
			int v = in.readInt();
			int w = in.readInt();
			if (v < 0 || v >= V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
			if (w < 0 || w >= V) throw new IndexOutOfBoundsException("vertex " + w + " is not between 0 and " + (V-1));
			double weight = in.readDouble();
			addEdge(new DirectedEdge(v, w, weight));
		}
	}

	public int V() {
		return V;
	}

	public int E() {
		return E;
	}

	public void addEdge(DirectedEdge e) {
		int v = e.from();
		int w = e.to();
		adj[v].add(e);
		E++;
	}

	public Bag<DirectedEdge> adj(int v) {
		return adj[v];
	}

	/**
	 * Returns a string representation of this edge-weighted digraph.
	 *
	 * @return the number of vertices <em>V</em>, followed by the number of edges <em>E</em>,
	 *         followed by the <em>V</em> adjacency lists of edges
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(V + " " + E + "\n");
		for (int v = 0; v < V; v++) {
			s.append(v + ": ");
			for (DirectedEdge e : adj[v]) {
				s.append(e + "  ");
			}
			s.append("\n");
		}
		return s.toString();
	}

	/**
	 * Unit tests the <tt>EdgeWeightedDigraph</tt> data type.
	 */
	public static void main(String[] args) {
		In in = new In(args[0]);
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
		StdOut.println(G);
	}

}
