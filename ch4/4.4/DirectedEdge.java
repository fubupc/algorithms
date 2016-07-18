public class DirectedEdge {
	private int from;
	private int to;
	private double weight;

	public DirectedEdge(int f, int t, double weight) {
		from = f;
		to = t;
		this.weight = weight;
	}

	public int from() {
		return from;
	}

	public int to() {
		return to;
	}

	public double weight() {
		return weight;
	}


	/**
	 * Returns a string representation of the directed edge.
	 * @return a string representation of the directed edge
	 */
	public String toString() {
		return from + "->" + to + " " + String.format("%5.2f", weight);
	}
}
