
public class CHEdge {
	private boolean shortcut;
	private int from;
	private int to;
	private int u;

	public CHEdge(int from, int to) {
		this.from = from;
		this.to = to;
		this.shortcut = false;
		this.u = -1;
	}

	public CHEdge(int from, int u, int to) {
		this(from, to);
		this.shortcut = false;
		this.u = u;
	}

	public int from() {
		return from;
	}

	public int to() {
		return to;
	}
}

public class CHGraph {
	private Bag<CHEdge>[] adj;
	private int[] orderOf;
	private int[] nodeOrderings;
	private Graph G;
	private int V;
	private int E;

	public CHGraph(Graph G) {
		this.G = G;
		this.V = G.V();
		this.E = G.E();

		adj = (Bag<CHEdge>[]) new Bag[V];
		orderOf = new int[V];
		nodeOrderings = new int[V];

		for (int v = 0; v < V; v++) {
			for (Edge e : G.adj(v)) {
				addEdge(new CHEdge(e.from(), e.to()));
			}
		}

		order(G);

		for (int u = 0; u < nodeOrderings; u++) {
			contractNode(u);
		}
	}

	public void order(Graph G) {
		for (int v = 0; v < G.V(); v++) {
			nodeOrderings[v] = v;
			orderOf[v] = v;
		}
	}

	public Iterable<CHEdge> outEdges(int u) {
		Iterable<CHEdge> edges = new ArrayList<CHEdge>();

		for (CHEdge e : adj[u]) {
			if (e.from() == u) {
				edges.add(e);
			}
		}

		return edges;
	}

	public Iterable<CHEdge> inEdges(int u) {
		Iterable<CHEdge> edges = new ArrayList<CHEdge>();

		for (CHEdge e : adj[u]) {
			if (e.to() == u) {
				edges.add(e);
			}
		}

		return edges;
	}

	public addEdge(CHEdge e) {
		int v = e.from();
		int w = e.to();

		if (G.orderOf(v) > G.orderOf(w)) {
			adj[w] = e;
		} else {
			adj[v] = e;
		}

		E++;
	}

	public boolean hasWitness(int v, int u, int w) {

	}

	public contractNode(int u) {
		for (CHEdge e1 : inEdges(u)) {
			int v = e1.from();

			for (CHEdge e2 : outEdges(u)) {
				int w = e2.to();

				if (!hasWitness(v, u, w)) {
					addEdge(new CHEdge(v, u, w));
				}
			}
		}
	}
}


public class CHQuery {
	private double[] fDistTo;
	private double[] bDistTo;
	private CHEdge[] fEdgeTo;
	private CHEdge[] bEdgeTo;

	private static final boolean Forward = true;
	private static final boolean Backward = true;

	public CHQuery(CHGraph G) {
		fDistTo = new double[G.V()];
		bDistTo = new double[G.V()];
		fEdgeTo = new CHEdge[G.V()];
		bEdgeTo = new CHEdge[G.V()];
	}

	public void reset() {
		for (int v = 0; v < G.V(); v++) {
			fDistTo[v] = Double.POSITIVE_INFINITY;
			bDistTo[v] = Double.POSITIVE_INFINITY;
			fEdgeTo[v] = null;
			bEdgeTo[v] = null;
		}
	}

	public boolean visited(double[] distTo, int v) {
		return distTo[v] != Double.POSITIVE_INFINITY;
	}

	public Iterable<CHEdge> pathTo(int s, int t) {
		reset();

		fDistTo[s] = 0.00;
		bDistTo[t] = 0.00;

		IndexMinPQ<Double> fq = new IndexMinPQ<Double>();
		IndexMinPQ<Double> bq = new IndexMinPQ<Double>();

		fq.insert(s, fDistTo[s]);
		bq.insert(t, bDistTo[t]);

		boolean direction = Forward;

		double min = Double.POSITIVE_INFINITY;

		int v = -1;

		while (!fq.isEmpty() || !bq.isEmpty()) {
			if (direction == Forward) {
				direction = Backward;
				if (fq.isEmpty()) continue;

				int f = fq.delMin();
				if (fDistTo[f] >= min) {
					fq.clear();
					continue;
				}

				if (min > fDistTo[f] + bDistTo[f]) {
					v = f;
					min = fDistTo[f] + bDistTo[f]; 
				} else {
					relaxForward(f);
				}
			} else {
				direction = Forward;
				if (bq.isEmpty()) continue;

				int b = bq.delMin();
				if (bDistTo[b] >= min) {
					bq.clear();
					continue;
				}

				if (min > fDistTo[b] + bDistTo[b]) {
					v = b;
					min = fDistTo[b] + bDistTo[b]; 
				} else {
					relaxBackward(b);
				}
			}
		}
	}

}

