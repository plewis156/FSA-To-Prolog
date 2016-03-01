/**
 * A class which represents an finite
 * state automaton as a string.
 * 
 * @author Paul Lewis
 * @version 1.0
 * @since 3/17/2015
 */
public class FSA {

	/* number of states */
	private String numStates;
	/* alphabet */
	private String alphabet;
	/* transitions */
	private String transitions;
	/* start state */
	private String startState;
	/* end state */
	private String endStates;
	
	/**
	 * Instantiates an FSA class.
	 */
	public FSA() {
		
		numStates = "";
		alphabet = "";
		transitions = "";
		startState = "";
		endStates = "";
	}
	/**
	 * Mutator for number of states
	 * 
	 * @param numStates a string containing the number of states
	 */
	public void setNumStates(String numStates) {
		this.numStates = numStates;
	}
	/**
	 * Accessor for number of states
	 * 
	 * @return the number of states
	 */
	public String getNumStates() {
		return this.numStates;
	}
	/**
	 * Mutator for alphabet
	 * 
	 * @param alphabet a string containing the alphabet
	 */
	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
	}
	/**
	 * Accessor for the alphabet
	 * 
	 * @return the alphabet
	 */
	public String getAlphabet() {
		return this.alphabet;
	}
	/**
	 * Mutator for the alphabet
	 * 
	 * @param transitions a string containing the transitions
	 */
	public void setTransitions(String transitions) {
		this.transitions = transitions;
	}
	/**
	 * Accessor for the transitions
	 * 
	 * @return the transitions
	 */
	public String getTransitions() {
		return this.transitions;
	}
	/**
	 * Mutator for the start state
	 * 
	 * @param startState a string containing the start state
	 */
	public void setStartState(String startState) {
		this.startState = startState;
	}
	/**
	 * Accessor for the start state
	 * 
	 * @return the start state
	 */
	public String getStartState() {
		return this.startState;
	}
	/**
	 * Mutator for the end states
	 * 
	 * @param endStates a string containing the end states
	 */
	public void setEndStates(String endStates) {
		this.endStates = endStates;
	}
	/**
	 * Accessor for the end states
	 * 
	 * @return the end states
	 */
	public String getEndStates() {
		return this.endStates;
	}
	/**
	 * Method to convert fsa to string
	 * 
	 * @return the FSA as a string
	 */
	@Override
	public String toString() {
		return this.getNumStates() + ";" + this.getAlphabet() + ";" + this.getTransitions() + ";"
				+ this.getStartState() + ";" + this.getEndStates() + ";";
	}
}
