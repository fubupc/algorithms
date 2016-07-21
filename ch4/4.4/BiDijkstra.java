import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.IndexMinPQ;

public class BiDijkstra {
	private boolean Forward = true;
	private boolean Backward = false;

	private double[] forwardDistTo;
	private double[] backwardDistTo;

	private DirectedEdge[] forwardEdgeTo;
	private DirectedEdge[] backwardEdgeTo;

	private BiDigraph G;

	public BiDijkstra(BiDigraph G) {
		forwardDistTo = new double[G.V()];
		backwardDistTo = new double[G.V()];

		forwardEdgeTo = new DirectedEdge[G.V()];
		backwardEdgeTo = new DirectedEdge[G.V()];

		this.G = G;
	}

	private void reset() {
		for (int v = 0; v < G.V(); v++) {
			forwardDistTo[v] = Double.POSITIVE_INFINITY;
			backwardDistTo[v] = Double.POSITIVE_INFINITY;

			forwardEdgeTo[v] = null;
			backwardEdgeTo[v] = null;
		}
	}

	public Iterable<DirectedEdge> pathTo(int s, int t) {
		reset();

		forwardDistTo[s] = 0.00;
		backwardDistTo[t] = 0.00;

		IndexMinPQ<Double> fq = new IndexMinPQ<Double>(G.V());
		IndexMinPQ<Double> bq = new IndexMinPQ<Double>(G.V());

		fq.insert(s, 0.00);
		bq.insert(t, 0.00);

		boolean direction = Forward;
		int meet = -1;

		while (!fq.isEmpty() && !bq.isEmpty()) {
			if (direction == Forward) {
				int f = fq.delMin();

				if (backwardDistTo[f] != Double.POSITIVE_INFINITY) {
					meet = f;
				}

				for (DirectedEdge e : G.outEdges(f)) {
					int to = e.to();
					if (forwardDistTo[to] > forwardDistTo[f] + e.weight()) {
						forwardDistTo[to] = forwardDistTo[f] + e.weight();
						forwardEdgeTo[to] = e; 

						if (fq.contains(to)) {
							fq.changeKey(to, forwardDistTo[to]);
						} else {
							fq.insert(to, forwardDistTo[to]);
						}
					}
				}

				direction = Backward;
			} else {
				int b = bq.delMin();

				if (forwardDistTo[b] != Double.POSITIVE_INFINITY) {
					meet = b;
				}

				for (DirectedEdge e : G.inEdges(b)) {
					int from = e.from();
					if (backwardDistTo[from] > backwardDistTo[b] + e.weight()) {
						backwardDistTo[from] = backwardDistTo[b] + e.weight();
						backwardEdgeTo[from] = e; 

						if (bq.contains(from)) {
							bq.changeKey(from, backwardDistTo[from]);
						} else {
							bq.insert(from, backwardDistTo[from]);
						}
					}
				}

				direction = Forward;
			}

			if (meet != -1) {
				StdOut.printf("meet at %d!\n", meet);
				int v = meet;

				double min = forwardDistTo[v] + backwardDistTo[v];

				while (!fq.isEmpty()) {
					int u = fq.delMin();
					if (min > forwardDistTo[u] + backwardDistTo[u]) {
						min = forwardDistTo[u] + backwardDistTo[u];
						v = u;
					}
				}

				while (!bq.isEmpty()) {
					int w = bq.delMin();
					if (min > forwardDistTo[w] + backwardDistTo[w]) {
						min = forwardDistTo[w] + backwardDistTo[w];
						v = w;
					}
				}

				StdOut.printf("vertex %d on shortest path!\n", v);

				Stack<DirectedEdge> st = new Stack<DirectedEdge>();
				for (DirectedEdge e = forwardEdgeTo[v]; e != null; e = forwardEdgeTo[e.from()]) {
					st.push(e);
				}

				Queue<DirectedEdge> q = new Queue<DirectedEdge>();
				for (DirectedEdge e : st) {
					q.enqueue(e);
				}

				for (DirectedEdge e = backwardEdgeTo[v]; e != null; e = backwardEdgeTo[e.to()]) {
					q.enqueue(e);
				}

				return q;
			}
		}

		return null;
	}

	public static void main(String[] args) {
		String filename = args[0];
		int s = Integer.parseInt(args[1]);

		BiDigraph G = new BiDigraph(new In(filename));
		BiDijkstra bd = new BiDijkstra(G);

		for (int v = 0; v < G.V(); v++) {
			StdOut.printf("%d to %d: %s\n", s, v, bd.pathTo(s, v));
		}
	}


}
