
public class BeliefSpace {
	BeliefState start;

	public BeliefSpace(BeliefState start) {
		this.start=start;
	}
	public void generateBeliefSpace() {
		this.start.generateNextBeliefStates();
		this.start.findBestPolices();
		
	}

}
