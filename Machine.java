import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class for creating and testing a finite state machine
 * 
 * @author Paul Lewis
 * @version 3.0
 * @since 4/21/2015
 */
public class Machine {
	
	private String fullFSA;
	private int numStates;
	private int startState;
	private int numTransitions;
	private int[] endStates;
	private String[] transitions;
	private State[] states;
	private boolean isNonDeterministic;
	/**
	 * Constructor to initialize a Machine object
	 * 
	 * @param fullFSA the FSA in string form
	 */
	public Machine(String fullFSA) {
		this.fullFSA = fullFSA;
		this.isNonDeterministic = false;
		splitTheFSA(this.fullFSA);
		states = new State[this.numStates];
		createStates();
		createTransitions();
	}
	/**
	 * A method for processing the FSA with a given input
	 * and checking validity
	 * 
	 * @param inputString the input string
	 * @return boolean indicating validity
	 */
	public boolean processInput(String inputString) {
		boolean accepted;
		int currentState = this.startState;
		System.out.println("Start State == " + currentState);
		int steps = inputString.length();
		System.out.println("Number of Steps == " + steps);
		char[] inputArray = inputString.toCharArray();
		
		for(int i = 0; i<steps; i++) {
			System.out.println("Current input character == " + inputArray[i]);
			currentState = nextState(this.states[currentState], inputArray[i]);
			System.out.println("Current State == " + currentState);
			if(currentState < 0) {
				System.out.println("Input Not Found. Dropping State");
				accepted = false;
				return accepted;
			}
		}
		System.out.println("After Loop, Current State is " + currentState);
		if(containsInt(this.endStates, currentState)) {
			accepted = true;
		} else {
			accepted = false;
		}
		
		return accepted;
	}
	/**
	 * A method for Generating a LISP program
	 * based on a given FSA
	 * 
	 * @param file the Lisp File
	 */
	public void generateProlog(File file) throws IOException {
		
		Transition[] t;
		int i, j;
		
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		
		out.write("fsa(L) :- start(S), process(S,L).");
		out.newLine();
		out.newLine();
		out.write("process(X,[H|T]) :- trans(X,H,Y), process(Y,T).");
		out.newLine();
		out.write("process(X,[]) :- accepting(X).");
		out.newLine();
		out.newLine();
		out.write("start(" + this.startState + ").");
		out.newLine();
		out.newLine();
		
		for(i = 0; i < this.endStates.length; i++) {
			out.write("accepting(" + this.endStates[i] + ").");
			out.newLine();
		}
		out.newLine();
		
		for(i = 0; i < this.numStates; i++) {
			t = states[i].getTransitions();
			for(j = 0; j < states[i].getNumTransitions(); j++) {
				out.write("trans(" + i + "," + t[j].getInput() + "," + t[j].getToState() + ").");
				out.newLine();
			}
		}
		
		out.close();
	}
	/**
	 * Method to find next state based on a state
	 * and a given input character
	 * 
	 * @param state the current state
	 * @param input the input character
	 * @return int the next state, -1 if invalid character
	 */
	public int nextState(State state, char input) {
		Transition[] transitions = state.getTransitions();
		char anInput;
		for(int i = 0; i< transitions.length; i++) {
			anInput = transitions[i].getInput();
			if(anInput == input) {
				return transitions[i].getToState();
			}
		}
		
		return -1;
	}
	/**
	 * Method to fill Machine with states based on 
	 * FSA string
	 */
	public void createStates() {
		int i;
		String[] parts;
		boolean start = false;
		boolean end = false;
		int[] numStateOutputs = new int[this.numStates];
		for(i=0; i<this.numStates; i++) {
			numStateOutputs[i] = 0;
		}
		
		for(i=0; i<this.numTransitions; i++) {	
			parts = transitions[i].split("[\\(||\\)||:]");
			numStateOutputs[Integer.parseInt(parts[1])]++;
		}
		
		for(i=0; i<this.numStates; i++) {
			if(containsInt(endStates, i)) {
				end = true;
			}
			if(i == this.startState) {
				start = true;
			}
			states[i] = new State(i, numStateOutputs[i], start, end);
			start = false;
			end = false;
		}
		
	}
	/**
	 * Method to fill Machine with transitions based
	 * on given FSA string
	 */
	public void createTransitions() {
		int i;
		int from;
		String[] parts;
		
		for(i=0; i<this.numTransitions; i++) {
			parts = transitions[i].split("[\\(||\\)||:]");
			from = Integer.parseInt(parts[1]);
			states[from].addTransition(transitions[i]);
		}
	}
	/**
	 * Method to indicate if machine is nondeterministic
	 * 
	 * @return boolean indicating whether or not is deterministic
	 */
	public boolean getIsNonDeterministic() {
		
		boolean is = false;
		
		for(int i=0; i<this.numStates; i++) {
			is = this.states[i].checkIfNFA();
			if(is) {
				this.isNonDeterministic = true;
			}
		}
		
		return this.isNonDeterministic;
	}
	/**
	 * A method to check if a given integer is contained in an array
	 * 
	 * @param array the array of integers
	 * @param theInt the integer
	 * @return boolean
	 */
	public boolean containsInt(int[] array, int theInt) {
        for (int i : array) {
            if (i == theInt) {
                return true;
            }
        }
        return false;
    }
	/**
	 * Method to split and parse the FSA string
	 * 
	 * @param fsa the FSA string
	 */
	public void splitTheFSA(String fsa) {
		
		String[] parts = splitFSAString(fsa);
		this.numStates = Integer.parseInt(parts[0]);
		this.transitions = splitTransitions(parts[2]);
		this.numTransitions = transitions.length;
		this.startState = Integer.parseInt(parts[3]);
		this.endStates = splitEnds(parts[4]);	
	}
	/**
	 * Method to split the FSA string into tokens
	 * 
	 * @param fsa the FSA string
	 * @return the array of String tokens
	 */
	public String[] splitFSAString(String fsa) {
		String[] parts = fsa.split(";");
		
		return parts;
	}
	/**
	 * Method to split and parse the end states
	 * 
	 * @param ends String containing the end states
	 * @return the array of end state integers
	 */
	public int[] splitEnds(String ends) {
		String[] parts = ends.split(",");
		
		int[] theEnds = new int[parts.length];
		for(int i = 0; i<parts.length; i++) {
			theEnds[i] = Integer.parseInt(parts[i]);
		}
		
		return theEnds;
	}
	/**
	 * Method to split the transitions into tokens
	 * 
	 * @param trans String containing the transitions
	 * @return array of String tokens
	 */
	public String[] splitTransitions(String trans) {
		String[] transits = trans.split(",");
		
		return transits;
	}
}
