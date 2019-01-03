import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;

public class Agent {
    protected BeliefState State;

    public Agent(BeliefState state) {
        this.State = state;
    }

    public Action agentFunc() {
    	double maxUtility= Integer.MIN_VALUE;
    	BeliefState bestNextState=State;
    	for(Edge action: main.world.getVertices().get(State.getLocation()-1).getEdges()) {
			if(action.getBlocked()==-1) {
				for(BeliefState b: this.State.getSuccBStates()) {
					if(b.getLocation()==action.getVertex().getId())
						if(maxUtility<b.getUtility()) {
							maxUtility=b.getUtility();
							bestNextState=b;
						}
				
				}
			}
    	}
    	this.State=bestNextState;
    	Action newAction = new Action(State.getDeadLine(),State.getPeopleSaved(),State.getLocationId());
    	return newAction;
    	
    }

    public BeliefState getState() {
        return State;
    }

    public void setState(BeliefState state) {
        State = state;
    }
}
