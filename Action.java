
public class Action {
	private int deadline;
	private int peopleSaved;
	private int vertexLocation;
	
	public Action(int deadline,int peopleSaved, int vlocation) {
		this.deadline = deadline;
		this.peopleSaved = peopleSaved;
		this.vertexLocation = vlocation;
	}
///////////////////////////////// UPDATE GETERS SETERS!!!! /////////////////////////////////

	public int getDeadline() {
		return deadline;
	}

	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}

	public int getPeopleSaved() {
		return peopleSaved;
	}

	public void setPeopleSaved(int peopleSaved) {
		this.peopleSaved = peopleSaved;
	}

	public int getVertexLocation() {
		return vertexLocation;
	}

	public void setVertexLocation(int vertexLocation) {
		this.vertexLocation = vertexLocation;
	}
}

