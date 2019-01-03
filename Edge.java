
public class Edge {
	private Vertex vertex;
	private int weight;
	private double bProbability;
	int blocked;   // 0 unknown; 1 blocked; -1 unblocked
	
	public Edge(Vertex newV, int weight, double bProbability, int blocked) {
		this.vertex=newV;
		this.weight=weight;
		this.bProbability=bProbability;
		this.blocked=blocked;
	}

	public Vertex getVertex() {
		return vertex;
	}

	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public double getbProbability() {
		return bProbability;
	}

	public void setbProbability(double bProbability) {
		this.bProbability = bProbability;
	}

	public int getBlocked() {
		return blocked;
	}

	public void setBlocked(int blocked) {
		this.blocked = blocked;
	}
	
}
