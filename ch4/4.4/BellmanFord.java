import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BellmanFord {
	private Queue<Integer> q;
	private double[] distTo;
	private DirectedEdge[] edgeTo;
	private boolean[] onQueue;

	public BellmanFord(EdgeWeightedDigraph G, int s) {
		distTo = new double[G.V()];
		for (int v = 0; v < G.V(); v++) {
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		distTo[s] = 0.00;

		edgeTo = new DirectedEdge[G.V()];
		onQueue = new boolean[G.V()];

		q = new Queue<Integer>();
		search(G, s);
	}

	public void search(EdgeWeightedDigraph G, int s) {
		q.enqueue(s);
		onQueue[s] = true;

		while (!q.isEmpty()) {
			int v = q.dequeue();
			onQueue[v] = false;

			for (DirectedEdge e : G.adj(v)) {
				int w = e.to();

				if (distTo[w] > distTo[v] + e.weight()) {
					distTo[w] = distTo[v] + e.weight();
					edgeTo[w] = e;

					if (!onQueue[w]) {
						q.enqueue(w);
						onQueue[w] = true;
					}
				}
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

		BellmanFord bf = new BellmanFord(G, s);

		for (int v = 0; v < G.V(); v++) {
			StdOut.printf("%d to %d %.2f:  ", s, v, bf.distTo(v));
			StdOut.println(bf.pathTo(v));
		}
	}

}
