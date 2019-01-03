import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;

public class Agent {
    protected BeliefState State;

    public Agent(BeliefState state) {
        this.State = state;
    }

    public Action agentFunc() {
//    	Edge transitionEdge=this.getState().getVertices()
//				.get(getState().getLocation()).getEdge(State.bestAction.getId());
    	for(BeliefState BS: this.getState().getSuccBStates()){
    		if(compareKnowledge(BS.getVertices(), main.world.getVertices())&&BS.getLocation()==this.getState().bestAction.getId())
				this.State=BS;
		}
		Action newAction = new Action(State.getDeadLine(),State.getPeopleSaved(),State.getLocationId());
		return newAction;
//    	int deadLine=this.getState().getDeadLine()-transitionEdge.getWeight();
//		int locationId = this.getState().bestAction.getId();
//		int peopleSaved
//		int peopleNotSaved;
//		int peopleOn;

//		LinkedList<Edge> knownEdges=main.world.getVertices().get(this.getState().getLocationId()-1).getEdges();
//		for(Edge e: knownEdges) {
//			this.getState().getVertices().get(getState().getLocation() - 1)
//					.getEdge(e.getVertex().getId()).setBlocked(e.getBlocked());
//			this.getState().getVertices().get(e.getVertex().getId() - 1)
//					.getEdge(getState().getLocation()).setBlocked(e.getBlocked());
//		}
//    	double maxUtility= Integer.MIN_VALUE;
//    	BeliefState bestNextState=State;
//    	for(Edge action: main.world.getVertices().get(State.getLocation()-1).getEdges()) {
//			if(action.getBlocked()==-1) {
//				for(BeliefState b: this.State.getSuccBStates()) {
//					if(b.getLocation()==action.getVertex().getId()) {
//						if(compareKnowledge(b.vertices, this.getState().getVertices())) {
//							if (maxUtility < b.getUtility()) {
//								maxUtility = b.getUtility();
//								bestNextState = b;
//							}
//						}
//					}
//
//				}
//			}
//    	}
//    	this.State=bestNextState;
//    	Action newAction = new Action(State.getDeadLine(),State.getPeopleSaved(),State.getLocationId());
//    	return newAction;
    	
    }

	private boolean compareKnowledge(LinkedList<Vertex> beliefVertices, LinkedList<Vertex> knownVertices) {
    	for(Vertex v: beliefVertices){
    		for(Edge ev: v.getEdges())
    			if(ev.getBlocked()!=0)
    				if(knownVertices.get(v.getId()-1).getEdge(ev.getVertex().getId()).getBlocked()
						!=ev.getBlocked())
    						return false;
		}
		return true;
	}

	public BeliefState getState() {
        return State;
    }

    public void setState(BeliefState state) {
        State = state;
    }

}
