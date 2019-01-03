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

    public BeliefState(int location, int peopleSaved, int peopleNotSaved, int deadLine, LinkedList<Vertex> vertices, int peopleOn) {
        this.locationId = location;
        this.peopleSaved = peopleSaved;
        this.peopleNotSaved = peopleNotSaved;
        this.deadLine = deadLine;
        this.vertices = vertices;
        this.succBStates = new LinkedList<>();
        this.peopleOn = peopleOn;
        this.utility = 0;
    }

    public void generateNextBeliefStates() {
        if (!isGoalState()) {
            for (Edge e : vertices.get(locationId - 1).getEdges()) {
                if (e.getBlocked() == -1) {
                    int locationIdNew = e.getVertex().getId();
                    LinkedList<Edge> unknownBlock = new LinkedList();
                    for (Edge e2 : vertices.get(locationIdNew - 1).getEdges()) {
                        if (e2.blocked == 0) {// unknown edge
                            unknownBlock.add(e2);
                        }
                    }
                    int peopleSavedNew = this.getPeopleSaved();
                    int peopleNotSavedNew = this.getPeopleNotSaved();
                    int deadLineNew = this.getDeadLine();
                    int peopleOnNew = this.getPeopleOn();
                    LinkedList<Vertex> verticesNew = Vertex.verticesDeepCopy(this.vertices);
                    if (verticesNew.get(locationIdNew - 1).getPeople() > 0) {
                        peopleOnNew = peopleOnNew + verticesNew.get(locationIdNew - 1).getPeople();
                        verticesNew.get(locationIdNew - 1).setPeople(0);
                    }
                    if (verticesNew.get(locationIdNew - 1).isIsShelter()) {
                        peopleSavedNew = peopleSavedNew + peopleOnNew;
                        peopleNotSavedNew = peopleNotSavedNew - peopleOnNew;
                        peopleOnNew = 0;
                    }
                    deadLineNew = deadLineNew - e.getWeight();
                    for (int i = 0; i < Math.pow(2, unknownBlock.size()); i++) {
                        if (unknownBlock.size() == 0) {
                            BeliefState toAdd = new BeliefState(locationIdNew, peopleSavedNew, peopleNotSavedNew, deadLineNew, Vertex.verticesDeepCopy(verticesNew), peopleOnNew);
                            this.getSuccBStates().add(toAdd);
                        } else {
                            String binPer = String.format("%0" + unknownBlock.size() + "d", Integer.parseInt(Integer.toBinaryString(i)));
                            String[] binPerArr = binPer.split("");
                            for (int j = 0; j < unknownBlock.size(); j++) {
                                Edge currentEdge = unknownBlock.get(j);
                                int V1 = currentEdge.getVertex().getId();
                                int V2 = locationIdNew;
                                if (binPerArr[j].equals("0")) {
                                    verticesNew.get(V1 - 1).getEdge(V2).setBlocked(-1);
                                    verticesNew.get(V2 - 1).getEdge(V1).setBlocked(-1);
                                } else {
                                    verticesNew.get(V1 - 1).getEdge(V2).setBlocked(1);
                                    verticesNew.get(V2 - 1).getEdge(V1).setBlocked(1);
                                }
                            }
                            BeliefState toAdd = new BeliefState(locationIdNew, peopleSavedNew, peopleNotSavedNew, deadLineNew, Vertex.verticesDeepCopy(verticesNew), peopleOnNew);
                            this.getSuccBStates().add(toAdd);
                        }
                    }

                }
            }
        }
        for (BeliefState b : this.getSuccBStates()) {
            b.generateNextBeliefStates();
        }
    }

    public boolean isGoalState() {
        if (this.deadLine <= 0 || this.peopleNotSaved == 0)
            return true;
        return false;
    }

    public double findBestPolices() {
        double maxValue = Integer.MIN_VALUE;
        double reward = this.getPeopleSaved();
        double sumOfUtilities;
        for (Edge action : vertices.get(locationId-1).getEdges()) {
            sumOfUtilities = 0;
            if (action.getBlocked() == -1) {
                for (BeliefState b : this.getSuccBStates()) {
                    if (b.getLocation() == action.getVertex().getId()) {
                        double probability =1;
                        for(Edge e: this.getVertices().get(b.getLocation()-1).getEdges()) {
                            if (e.blocked == 0){
                                if(b.getVertices().get(b.getLocation()-1).getEdge(e.getVertex().getId()).blocked==1){
                                    probability=probability*e.getbProbability();
                                }
                                else
                                    probability=probability*(1-e.getbProbability());
                            }
                        }
                        sumOfUtilities=sumOfUtilities+b.findBestPolices()*probability;
                    }
                }
            }

            if (maxValue < sumOfUtilities)
                maxValue = sumOfUtilities;
        }
        this.utility = reward + maxValue;
        System.out.println("the utility of state: location:"+this.locationId+
                "\ndeadline:"+this.deadLine+"people saved:"+this.getPeopleSaved()+ "is:"+this.utility);
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
