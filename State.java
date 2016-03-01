import java.util.Arrays;
/**
 * A State class
 * 
 * @author Paul Lewis
 * @version 1.0
 * @since 3/17/2015
 */
public class State {
	
	private int state;
	private Transition[] transitions;
	private boolean isStart;
	private boolean isEnd;
	private int numTransitions;
	private int transCount;
	private boolean nonDet;
	private char[] inputs;
	/**
	 * A Constructor to instantiate a State class
	 * 
	 * @param state the state number
	 * @param numTransitions the number of outgoing transitions from the state
	 * @param isStart boolean indicating whether state is a start state
	 * @param isEnd boolean indicating whether state is an end state
	 */
	public State(int state, int numTransitions, boolean isStart, boolean isEnd) {
		
		this.state = state;
		this.numTransitions = numTransitions;
		this.transitions = new Transition[this.numTransitions];
		this.isStart = isStart;
		this.isEnd = isEnd;
		this.transCount = 0;
		this.nonDet = false;
		this.inputs = new char[transitions.length];
	}
	/**
	 * Method to split and parse transition and add it to the state
	 * 
	 * @param trans String containing the transition
	 */
	public void addTransition(String trans) {
		
		String[] parts = trans.split("[\\(||\\)||:]");
		
		if(this.transCount < this.numTransitions) {
			transitions[this.transCount] = new Transition(Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), parts[3].charAt(0));
			this.inputs[this.transCount] = parts[3].charAt(0);
			this.transCount++;
		}
	}
	/**
	 * Mutator method for state number
	 * 
	 * @param state the state number
	 */
	public void setState(int state) {
		this.state = state;
	}
	/**
	 * Accessor method for state number
	 * 
	 * @return int the state number
	 */
	public int getState() {
		return this.state;
	}
	/**
	 * Mutator method for start state boolean
	 * 
	 * @param isStart start state boolean
	 */
	public void setIsStart(boolean isStart) {
		this.isStart = isStart;
	}
	/**
	 * Accessor method for start state boolean
	 * 
	 * @return start state boolean
	 */
	public boolean getIsStart() {
		return this.isStart;
	}
	/**
	 * Mutator method for end state boolean
	 * 
	 * @param isEnd end state boolean
	 */
	public void setIsEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	/**
	 * Accessor method for end state boolean
	 * 
	 * @return end state boolean
	 */
	public boolean getIsEnd() {
		return this.isEnd;
	}
	/**
	 * Accessor method for input array
	 * 
	 * @return array containg valid inputs for state
	 */
	public char[] getInputs() {
		if(this.numTransitions == this.transCount) {
			return this.inputs;
		} else {
			return null;
		}
	}
	/**
	 * Accessor method for transition array
	 * 
	 * @return array of transitions
	 */
	public Transition[] getTransitions() {
		if(this.numTransitions == this. transCount) {
			return this.transitions;
		} else {
			return null;
		}
	}
	
	public int getNumTransitions() {
		return this.numTransitions;
	}
 	/**
 	 * Method to check if state has two outgoing
 	 * transitions with the same input
 	 * 
 	 * @return true or false
 	 */
	public boolean checkIfNFA() {
		Arrays.sort(inputs);
		for (int i = 1; i < inputs.length; i++)
	        if (inputs[i] == inputs[i - 1])
	            this.nonDet = true;
		
		return this.nonDet;
	}
}
