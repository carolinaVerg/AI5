import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
public class main {
    public static Graph world=null;
    public static BeliefSpace beliefSpace=null;
  

	public static void main(String[] args) {
		File file = new File("/home/carolina/eclipse-workspace/AI2/tests/test3.txt"); //graph description
        BufferedReader br = null;
		String st = "";
		world = initWorld(br,world,st,file);
		Agent agent = initializeAgent(world);
		beliefSpace=new BeliefSpace(agent.getState());
		beliefSpace.generateBeliefSpace();
		simulator‬‬(agent);
	}

	private static Graph initWorld(BufferedReader br, Graph world, String st, File file){
        try {
            br = new BufferedReader(new FileReader(file));
            try {
                st = br.readLine();
                world = new Graph(Integer.parseInt(st.split(" ")[1]));
                while ((st = br.readLine()) != null) {
                    String[] data = st.split(" ");
                    switch(data[0]){
                        case "V":
                            updateVertex(data,world);
                            break;
                        case "E":
                            updateEdge(data,world);
                            break;
                        case "D":
                            updateDeadline(data, world);
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return world;
    }

    private static Agent initializeAgent(Graph world) {
    	Scanner reader = new Scanner(System.in); 
    	Agent agent;
        String input;
        BeliefState startState;
        int peopleToSave= world.getPeopleNotRescude();
        System.out.format("please choose the starting location");
        input = reader.nextLine();
        Vertex startV = world.getVertexById(Integer.parseInt(input));
        int deadline = world.getDeadLine();
        startState=new BeliefState(startV.getId(), 0, peopleToSave, deadline,
        		Vertex.verticesDeepCopy(world.getVertices()), 0);
        agent = new Agent(startState);
        
        return agent;
        
    }

    public static void simulator‬‬(Agent agent) {
 		Action newAction;
 		while (world.getDeadLine() > 0 && world.getPeopleNotRescude()>0) {	
             newAction = agent.agentFunc();
             updateWorld(newAction);
             displayAgentInWorld(agent);
                     //display current state	
 		}
 		displayWorld(world);
 		// print world at end of deadline
     }

    private static void displayAgentInWorld(Agent agent) {
        System.out.println("--------------------------------");
        System.out.format("Current deadline: %d\n", main.world.getDeadLine());
	    System.out.println("Agent State:");
	    System.out.format("current  vertex:           %d\n",agent.State.getLocation());
        System.out.format("Number of people on agent: %d\n",agent.State.getPeopleOn());
        System.out.format("Number of people to save:  %d\n",agent.State.getPeopleNotSaved());
        System.out.format("Number of people saved:       %d\n",agent.getState().getPeopleSaved());
    }

    private static void displayWorld(Graph world) {
        System.out.println("\n--------------------------------");
        System.out.format("\nTotal number of people saved:       %d\n",world.getPeopleRescude());
        System.out.format("Number of people Not saved: %d\n",world.getPeopleNotRescude());
    }

    private static void updateWorld(Action newAction) {
		world.setPeopleNotRescude(world.getPeopleNotRescude() -newAction.getPeopleSaved());
		world.setPeopleRescude(world.getPeopleRescude()+ newAction.getPeopleSaved());
		world.setDeadLine(newAction.getDeadline());
        world.getVertexById(newAction.getVertexLocation()).setPeople(0);  
	}

    public static void updateVertex(String[] data,Graph world){
        int vid = Integer.parseInt(data[1]);
        Boolean isShelter = ("S".equals(data[2]));
        int numOfPeople = 0;
        if(!isShelter){
            numOfPeople = Integer.parseInt(data[3]);
        }
        Vertex newV = world.getVertexById(vid);
        newV.setIsShelter(isShelter);
        newV.setPeople(numOfPeople);
        world.setPeopleNotRescude(world.getPeopleNotRescude() + numOfPeople);
    }

    public static void updateEdge(String[] data,Graph world){
        Vertex vfirst = world.getVertexById(Integer.parseInt(data[1]));
        Vertex vsecond = world.getVertexById(Integer.parseInt(data[2]));
        int weight = Integer.parseInt(data[4]);
        double bProbability= Double.parseDouble(data[6]);
        int blocked =0; //unKnown
        if(bProbability==0)
        	blocked=-1;
        vfirst.addEdge(weight,vsecond,bProbability, blocked);
        vsecond.addEdge(weight,vfirst, bProbability, blocked);
    }

    public static void updateDeadline(String[] data, Graph world){
       world.setDeadLine(Integer.parseInt(data[1]));
    }

	
	}

