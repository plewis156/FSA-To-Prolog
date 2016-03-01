import javax.swing.JFrame;
/**
 * 
 * Class containing the main method
 * 
 * @author Paul Lewis
 * @version 1.0
 * @since 3/17/2015
 */
public class FSAProcessorDriver {
	/**
	 * Main method
	 * Instantiates GUI frame
	 * 
	 * @param args no arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame frame = new ProcessorGui();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Finite State Machine");
        frame.setVisible(true);

	}

}
