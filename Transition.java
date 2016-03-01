/**
 * Class for a Finite State Machine transition
 * 
 * @author Paul Lewis
 * @version 1.0
 * @since 3/17/2015
 */
public class Transition {
	
	private int fromState;
	private int toState;
	private char input;
	/**
	 * Constructor to instantiate a Transition
	 * 
	 * @param fromState the source state
	 * @param toState the destination state
	 * @param input the input
	 */
	public Transition(int fromState, int toState, char input) {
		this.fromState = fromState;
		this.toState = toState;
		this.input = input;
	}
	/**
	 * Accessor method for source state
	 * 
	 * @return the source state
	 */
	public int getFromState() {
		return this.fromState;
	}
	/**
	 * Mutator method for source state
	 * 
	 * @param fromState the source state
	 */
	public void setFromState(int fromState) {
		this.fromState = fromState;
	}
	/**
	 * Accessor method for destination state
	 * 
	 * @return the destination state
	 */
	public int getToState() {
		return this.toState;
	}
	/**
	 * Mutator method for destination state
	 * 
	 * @param toState the destination state
	 */
	public void setToState(int toState) {
		this.toState = toState;
	}
	/**
	 * Accessor method for input
	 * 
	 * @return the input
	 */
	public char getInput() {
		return this.input;
	}
	/**
	 * Mutator method for the input
	 * 
	 * @param input the input
	 */
	public void setInput(char input) {
		this.input = input;
	}

}
