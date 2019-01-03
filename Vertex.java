import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;


public class Vertex {
	private int People;
	private boolean IsShelter;
	private int Id;
	private LinkedList<Edge> Edges;
	

	public Vertex(int people,boolean isShelter, int id) {
		this.People=people;
		this.IsShelter=isShelter;
		this.Id=id;
		this.Edges= new LinkedList<>();
	}
	
	
	public static LinkedList<Vertex>verticesDeepCopy(LinkedList<Vertex> vertices){
		 LinkedList<Vertex> newVertices=new LinkedList<>();
		 Iterator<Vertex> verIter=vertices.listIterator(0);
		 Vertex curentVer;
		 Vertex newVer;
		 while(verIter.hasNext()) {
			 curentVer=verIter.next();
			 newVer=new Vertex(curentVer.getPeople(), curentVer.isIsShelter(), curentVer.getId());
			 newVertices.add(newVer);
		 }
		 Iterator<Vertex> newVerIter=newVertices.listIterator(0);
		 Iterator<Edge> edgeIter;
		 while(newVerIter.hasNext()) {
			 curentVer=newVerIter.next();
			 edgeIter=vertices.get(curentVer.getId()-1).getEdges().listIterator(0);
			 Edge currentEdge;
			 while(edgeIter.hasNext()) {
				currentEdge=edgeIter.next();
				curentVer.addEdge(currentEdge.getWeight(),
						newVertices.get(currentEdge.getVertex().Id-1), currentEdge.getbProbability(), currentEdge.getBlocked());
			 }
		 }
		 return newVertices;
	}
	
	
	
	public void addEdge(int weight, Vertex v, double bProbability, int blocked) {
		Edges.add(new Edge(v, weight, bProbability, blocked));
	}
	
	public void removeEdgeById(int id) {
		Iterator<Edge> iter=Edges.listIterator(0);
		Edge current;
		while (iter.hasNext()) {
			current=iter.next();
			if(current.getVertex().Id==id)
				iter.remove();
			
		}
		
	}

	public int getPeople() {
		return People;
	}

	public void setPeople(int people) {
		People = people;
	}

	public boolean isIsShelter() {
		return IsShelter;
	}

	public void setIsShelter(boolean isShelter) {
		IsShelter = isShelter;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public LinkedList<Edge> getEdges() {
		return Edges;
	}

	public Vertex getNeighborByVid(int vId){
		Iterator<Edge> edgeIter = Edges.listIterator(0);
		while(edgeIter.hasNext()) {
			Vertex v = edgeIter.next().getVertex();
			if(v.getId() == vId){
				return v;
			}
		}
		return null;
	}
	public int getEdgeWeight(int vId){
		Iterator<Edge> iter= getEdges().listIterator(0);
		Edge currentEdge;
		while (iter.hasNext()) {
			currentEdge = iter.next();
			if(currentEdge.getVertex().getId() == vId){
				return currentEdge.getWeight();
			}
		}
		return 0;
	}
	public Edge getEdge(int vId) {
		Iterator<Edge> iter= getEdges().listIterator(0);
		Edge currentEdge;
		while (iter.hasNext()) {
			currentEdge = iter.next();
			if(currentEdge.getVertex().getId() == vId){
				return currentEdge;
			}
		}
		return null;
	}
	public void setEdges(LinkedList<Edge> edges) {
		Edges = edges;
	}
	

}
