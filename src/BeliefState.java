import java.util.LinkedList;

public class BeliefState {
	int locationId;
	int peopleSaved;
	int peopleNotSaved;
	int deadLine;
	int peopleOn;
	LinkedList<Vertex> vertices;
	LinkedList<BeliefState> succBStates;
	double utility;

	public BeliefState(int location, int peopleSaved,int peopleNotSaved, int deadLine,LinkedList<Vertex> vertices,int peopleOn ) {
		this.locationId=location;
		this.peopleSaved=peopleSaved;
		this.peopleNotSaved=peopleNotSaved;
		this.deadLine=deadLine;
		this.vertices=vertices;
		this.succBStates=new LinkedList<>();
		this.peopleOn=peopleOn;
		this.utility=0;
	}
	
	public void generateNextBeliefStates() {
		if(!isGoalState()) {
		for(Edge e: vertices.get(locationId).getEdges()) {
			if(e.getBlocked()==-1) {
			int locationIdNew=e.getVertex().getId();
			for(Edge e2: vertices.get(locationIdNew).getEdges()) {
				int peopleSavedNew=this.getPeopleSaved();
				int peopleNotSavedNew=this.getPeopleNotSaved();
				int deadLineNew= this.getDeadLine();
				int peopleOnNew= this.getPeopleOn();
				LinkedList<Vertex> verticesNew =Vertex.verticesDeepCopy(this.vertices);
				if(verticesNew.get(locationIdNew).getPeople()>0) {
					peopleOnNew=peopleOnNew+verticesNew.get(locationIdNew).getPeople();	
					verticesNew.get(locationIdNew).setPeople(0);	
				}
				if(verticesNew.get(locationIdNew).isIsShelter()) {
					peopleSavedNew=peopleSavedNew+ peopleOnNew;
					peopleNotSavedNew=peopleNotSavedNew-peopleOnNew;
					peopleOnNew=0;
				}
				deadLineNew=deadLineNew-e.getWeight();
				if(e2.blocked==0) {
					LinkedList<Vertex> verticesNewBlocked=Vertex.verticesDeepCopy(verticesNew);
					verticesNewBlocked.get(locationIdNew).getEdge(locationId).setBlocked(1);
					verticesNewBlocked.get(locationId).getEdge(locationIdNew).setBlocked(1);
					BeliefState toAddBlocked= new BeliefState(locationIdNew, peopleSavedNew,peopleNotSavedNew, deadLineNew,verticesNewBlocked , peopleOnNew);
					LinkedList<Vertex> verticesNewUnBlocked=Vertex.verticesDeepCopy(verticesNew);
					verticesNewUnBlocked.get(locationIdNew).getEdge(locationId).setBlocked(-1);
					verticesNewUnBlocked.get(locationId).getEdge(locationIdNew).setBlocked(-1);
					BeliefState toAddUnBlocked= new BeliefState(locationIdNew, peopleSavedNew,peopleNotSavedNew, deadLineNew, verticesNewUnBlocked, peopleOnNew);
					this.getSuccBStates().add(toAddBlocked);
					this.getSuccBStates().add(toAddUnBlocked);
				}
				else {
					BeliefState toAdd= new BeliefState(locationIdNew, peopleSavedNew,peopleNotSavedNew, deadLineNew, verticesNew, peopleOnNew);
					this.getSuccBStates().add(toAdd);
				}
				
			}	
		}
		}
		for(BeliefState b: this.getSuccBStates())
			b.generateNextBeliefStates();
		}
	}
	public boolean isGoalState() {
		if(this.deadLine<=0 || this.peopleNotSaved==0)
			return true;
		return false;
	}
	public double findBestPolices() {
		double maxValue=Integer.MIN_VALUE;
		double reward=this.getPeopleSaved();
		double sumOfUtilities;
		for(Edge action: vertices.get(locationId).getEdges()) {
			sumOfUtilities=0;
			if(action.getBlocked()==-1) {
				for(BeliefState b: this.getSuccBStates()) {
					if(b.getLocation()==action.getVertex().getId())
						sumOfUtilities=sumOfUtilities+b.findBestPolices()*(1-action.getbProbability());
				}
			}
			if(maxValue<sumOfUtilities)
				maxValue=sumOfUtilities;
		}
		this.utility=reward+maxValue;
		return this.utility;
	}
	
	public int getLocation() {
		return locationId;
	}

	public void setLocation(int location) {
		this.locationId = location;
	}

	public int getPeopleSaved() {
		return peopleSaved;
	}

	public void setPeopleSaved(int peopleSaved) {
		this.peopleSaved = peopleSaved;
	}

	public int getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(int deadLine) {
		this.deadLine = deadLine;
	}

	public LinkedList<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(LinkedList<Vertex> vertices) {
		this.vertices = vertices;
	}

	public int getPeopleOn() {
		return peopleOn;
	}

	public void setPeopleOn(int peopleOn) {
		this.peopleOn = peopleOn;
	}

	public LinkedList<BeliefState> getSuccBStates() {
		return succBStates;
	}

	public void setSuccBStates(LinkedList<BeliefState> succBStates) {
		this.succBStates = succBStates;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public int getPeopleNotSaved() {
		return peopleNotSaved;
	}

	public void setPeopleNotSaved(int peopleNotSaved) {
		this.peopleNotSaved = peopleNotSaved;
	}

	public double getUtility() {
		return utility;
	}

	public void setUtility(double utility) {
		this.utility = utility;
	}

}
