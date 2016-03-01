import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * A class to check the validity of an FSA
 * 
 * @author Paul Lewis
 * @version 1.0
 * @since 3/17/15
 */
public class FSAChecker {
	
	private static Pattern patternNum;
	private static Pattern patternAlpha;
	private static Pattern patternTrans;
	private static Pattern patternStart;
	private static Pattern patternEnd;
	private static Pattern patternFull;
    private static Matcher numMatcher;
    private static Matcher alphaMatcher;
    private static Matcher transMatcher;
    private static Matcher startMatcher;
    private static Matcher endMatcher;
    private static Matcher fullMatcher;
    private int numStates;
	private char[] alphabet;
    
	/**
	 * Instantiates an FSA checker class
	 */
	public FSAChecker() {
		
		patternNum = Pattern.compile("[1-9][0-9]*");
		patternAlpha = Pattern.compile(".(,.)*");
		patternTrans = Pattern.compile("\\(\\d+:\\d+:.\\)(,\\(\\d+:\\d+:.\\))*");
		patternStart = Pattern.compile("\\d+");
		patternEnd = Pattern.compile("\\d+(,\\d+)*");
		patternFull = Pattern.compile("[1-9][0-9]*;.(,.)*;\\(\\d+:\\d+:.\\)(,\\(\\d+:\\d+:.\\))*;\\d+;\\d+(,\\d+)*;");
	}
	/**
	 * A method to split the FSA into parts and check
	 * each part for validity
	 * 
	 * @param fsa a string containing the FSA
	 * @return boolean indicating validity
	 */
	public boolean splitFSA(String fsa) {
		String[] parts;
		if(!checkPatternFull(fsa))
			return false;
		parts = fsa.split(";");
		if(!checkPatternNum(parts[0]))
			return false;
		if(!checkPatternAlpha(parts[1]))
			return false;
		if(!checkPatternTrans(parts[2]))
			return false;
		if(!checkPatternStart(parts[3]))
			return false;
		if(!checkPatternEnd(parts[4]))
			return false;
		return true;
	}
	/**
	 * A method to check the validity of the number of state
	 * 
	 * @param numStates a string containing the number of states
	 * @return boolean indicating validity
	 */
	public boolean checkPatternNum(String numStates) {
		numMatcher = patternNum.matcher(numStates);
		if(numMatcher.matches()) {
			this.numStates = Integer.parseInt(numStates);
		}
		return numMatcher.matches();
	}
	/**
	 * A method to check the validity of the alphabet
	 * 
	 * @param alphabet a string containing the alphabet
	 * @return boolean indicating validity
	 */
	public boolean checkPatternAlpha(String alphabet) {
		alphaMatcher = patternAlpha.matcher(alphabet);
		if(alphaMatcher.matches()) {
			String[] parts = alphabet.split(",");
			this.alphabet = new char[parts.length];
			for(int i = 0; i<parts.length; i++) {
				this.alphabet[i] = parts[i].charAt(0);
			}
		}
		return alphaMatcher.matches();
	}
	/**
	 * A method to check the validity of the transitions
	 * 
	 * @param transitions a string containing the transitions
	 * @return boolean indicating validity
	 */
	public boolean checkPatternTrans(String transitions) {
		transMatcher = patternTrans.matcher(transitions);
		String[] transits;
		String parts[];
		int from;
		int to;
		char alpha;
		if(transMatcher.matches()) {
			transits = transitions.split(",");
			for(int i=0; i<transits.length; i++) {
				parts = transits[i].split("[\\(||\\)||:]");
				from = Integer.parseInt(parts[1]);
				to = Integer.parseInt(parts[2]);
				alpha = parts[3].charAt(0);
				if( (from >= this.numStates) || (to >= this.numStates) ) {
					return false;
				}
				if(!containsChar(this.alphabet, alpha)) {
					return false;
				}
			}
		}
		
		return transMatcher.matches();
	}
	/**
	 * A method to check the validity of the start state
	 * 
	 * @param startState a string containing the start state
	 * @return a boolean indicating validity
	 */
	public boolean checkPatternStart(String startState) {
		startMatcher = patternStart.matcher(startState);
		if(startMatcher.matches()) {
			if(Integer.parseInt(startState) >= this.numStates) {
				return false;
			}
		}
		return startMatcher.matches();
	}
	/**
	 * A method to check the validity of the end states
	 * 
	 * @param endStates a string containing the end states
	 * @return boolean indicating validity
	 */
	public boolean checkPatternEnd(String endStates) {
		String[] parts;
		int theEnds[];
		int i;
		endMatcher = patternEnd.matcher(endStates);
		if(endMatcher.matches()) {
			parts = endStates.split(",");
			
			theEnds = new int[parts.length];
			for(i = 0; i<parts.length; i++) {
				theEnds[i] = Integer.parseInt(parts[i]);
			}
			for(i = 0; i<theEnds.length; i++) {
				if(theEnds[i] >= this.numStates) {
					return false;
				}
			}
		}
		
		return endMatcher.matches();
	}
	/**
	 * A method to check the validity of an FSA string
	 * 
	 * @param fullFSA string containing the FSA in string form
	 * @return boolean indicating validity
	 */
	public boolean checkPatternFull(String fullFSA) {
		fullMatcher = patternFull.matcher(fullFSA);
		
		return fullMatcher.matches();
	}
	/**
	 * A method to check if a given character is contained in an array
	 * 
	 * @param array the array of characters
	 * @param theChar the character
	 * @return boolean
	 */
	public boolean containsChar(char[] array, char theChar) {
        for (char i : array) {
            if (i == theChar) {
                return true;
            }
        }
        return false;
    }
}
