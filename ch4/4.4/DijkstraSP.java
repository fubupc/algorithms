import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;

public class DijkstraSP {
	private DirectedEdge[] edgeTo;
	private double[] distTo;
	private EdgeWeightedDigraph G; 
	private IndexMinPQ<Double> q;

	public DijkstraSP(EdgeWeightedDigraph G, int s) {
		this.G = G;

		int V = G.V();
		edgeTo = new DirectedEdge[V];
		distTo = new double[V];
		q = new IndexMinPQ<Double>(G.V());

		for (int i = 0; i < V; i++) {
			distTo[i] = Double.POSITIVE_INFINITY;
		}

		ST(G, s);
	}

	public void ST(EdgeWeightedDigraph G, int s) {
		edgeTo[s] = null;
		distTo[s] = 0.00;

		q.insert(s, distTo[s]);

		while (!q.isEmpty()) {
			int v = q.delMin();
			for (DirectedEdge e : G.adj(v)) {
				relaxEdge(e);
			}
		}
	}

	public void relaxEdge(DirectedEdge e) {
		int v = e.from();
		int w = e.to();

		if (distTo[w] > distTo[v] + e.weight()) {
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;

			if (q.contains(w)) {
				q.changeKey(w, distTo[w]);
			} else {
				q.insert(w, distTo[w]);
			}
		}
	}

	public boolean hasPathTo(int v) {
		return distTo[v] != Double.POSITIVE_INFINITY;
	}

	public double distTo(int v) {
		return distTo[v];
	}

	public Iterable<DirectedEdge> pathTo(int v) {
		if (!hasPathTo(v)) return null;

		Stack<DirectedEdge> st = new Stack<DirectedEdge>();

		for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
			st.push(e);
		}

		return st;
	}

	public static void main(String[] args) {
		EdgeWeightedDigraph G = new EdgeWeightedDigraph(new In(args[0]));
		int s = Integer.parseInt(args[1]);

		DijkstraSP sp = new DijkstraSP(G, s);

		for (int v = 0; v < G.V(); v++) {
			StdOut.printf("%d to %d (%.2f)  ", s, v, sp.distTo(v));
			StdOut.println(sp.pathTo(v));
		}
	}
}
