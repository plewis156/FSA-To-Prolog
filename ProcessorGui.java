import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
/**
 * A class for a GUI for opening an FSA,
 * opening an input string and processing 
 * that input on the FSA
 * 
 * @author Paul Lewis
 * @version 3.0
 * @since 4/21/2015
 */
public class ProcessorGui extends JFrame {
	
	private static final long serialVersionUID = 1L;
	/* frame width and height */
	private final int FRAME_WIDTH = 1020;
	private final int FRAME_HEIGHT = 170;
	/* the height of the text area */
    private final int AREA_ROWS = 1;
    /* the width of the text area */
    private final int AREA_COLUMNS = 90;
	/* the buttons */
	private JButton openFSAButton;
	private JButton openInputButton;
	private JButton processInputButton;
	private JButton generatePrologButton;
	/* the text areas */
	private final JTextArea fsaArea;
	private final JTextArea inputArea;
	/* the menu bar */
	private final JMenuBar menuBar;
	/* the FSA string */
	private String fsa;
	/* the input string */
	private String inputString;
	/* FSA validity checker object */
	private FSAChecker checker;
	/* FSA processor object */
	private Machine machine;
	/* booleans */
	private boolean isNFA;
	private boolean accepted;
	private boolean fsaScanned;
	private boolean inputScanned;
	/**
	 * A Constructor for instantiating the GUI
	 */
	public ProcessorGui() {
		fsa = null;
		inputString = null;
		checker = new FSAChecker();
		isNFA = false;
		fsaScanned = false;
		inputScanned = false;
		accepted = false;
		
		this.setBackground(Color.GRAY);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.GRAY);
		bottomPanel.setLayout(new GridLayout(1,4));
		
		this.createOpenFSAButton();
		bottomPanel.add(openFSAButton);
		this.createOpenInputButton();
		bottomPanel.add(openInputButton);
		this.createProcessInputButton();
		bottomPanel.add(processInputButton);
		this.createGeneratePrologButton();
		bottomPanel.add(generatePrologButton);
		
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		JPanel textPanel = new JPanel();
        textPanel.setLayout(new GridLayout(2,1));
		this.fsaArea = new JTextArea(this.AREA_ROWS, this.AREA_COLUMNS);
        this.fsaArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(this.fsaArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        textPanel.add(scrollPane);
        this.inputArea = new JTextArea(this.AREA_ROWS, this.AREA_COLUMNS);
        this.inputArea.setEditable(false);
        JScrollPane scrollPane2 = new JScrollPane(this.inputArea);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        textPanel.add(scrollPane);
        textPanel.add(scrollPane2);
        this.add(textPanel, BorderLayout.NORTH);
        
        this.menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(createFileMenu());
        
        this.fsaArea.setText("FSA");
        this.inputArea.setText("INPUT");
		
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
	}
	/**
	 * Method to create a file menu
	 * 
	 * @return the file menu
	 */
	private JMenu createFileMenu() {
        JMenu menu = new JMenu("File");
        menu.add(createFileExitItem());
        return menu;
    }
	/**
	 * Method to create the file menu exit function
	 * 
	 * @return the menu item
	 */
	public JMenuItem createFileExitItem() {
        JMenuItem item = new JMenuItem("Exit");
        /**
         * ActionListener class for the Exit menu item
         */
        class MenuItemListener implements ActionListener {
            @Override
            /**
             * Method to close GUI
             */
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        }
        ActionListener listener = new MenuItemListener();
        item.addActionListener(listener);
        return item;
    }
	/**
	 * Method to create a button for Generating a 
	 * Prolog program
	 */
	public void createGeneratePrologButton() {
		this.generatePrologButton = new JButton("Save Prolog Code");
		/**
		 * ActionListener class for Generate Prolog Button
		 */
		class GeneratePrologButtonListener implements ActionListener {
			/**
			 *  Action performed method to open the
			 *  File using a JFileChooser then generate
			 *  Prolog code in the file.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				if(fsaScanned) {
					boolean check = checker.splitFSA(fsa);
					
					if(check) {
						machine = new Machine(fsa);
						try {
							JFileChooser fileChooser = new JFileChooser();
							File file;
							int returnVal = fileChooser.showSaveDialog(ProcessorGui.this);
							if(returnVal == JFileChooser.APPROVE_OPTION) {
								file = fileChooser.getSelectedFile();
								machine.generateProlog(file);
							} else {
								// do Nothing
							}
						} catch(HeadlessException h) {
							System.out.println("Error opening file");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Not a valid FSA. Cannot process.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Must open FSA String before generating Prolog code.");
				}
			}
		}
		ActionListener listener = new GeneratePrologButtonListener();
        this.generatePrologButton.addActionListener(listener);
	}
	/**
	 * Method to create a button to open the FSA
	 * and display it in text area
	 */
	public void createOpenFSAButton() {
		this.openFSAButton = new JButton("Open FSA");
		/**
		 * ActionListener class for the Open FSA button
		 */
		class OpenFSAButtonListener implements ActionListener {
			@Override
			/**
			 * Action performed method to
			 * open the file with a JFileChooser
			 * then get the string the display string
			 * in text area
			 */
			public void actionPerformed(ActionEvent e) {
				try {
                    JFileChooser fileChooser = new JFileChooser();
                    File file;
                    String line;
                    int returnVal = fileChooser.showOpenDialog(ProcessorGui.this);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        file = fileChooser.getSelectedFile();
                        if(file.canRead() && file.exists()) {
                        	Scanner inFile = new Scanner(new FileReader(file));
                        	line = inFile.next();
                        	fsa = line;
                        	fsaArea.setText(fsa);
                        	fsaScanned = true;
                        	inFile.close();
                        }
                    } else {
                        // do Nothing
                    }
                } catch(HeadlessException | IOException ex) {
                    System.out.println("Error opening file");
                }
			}
		}
		ActionListener listener = new OpenFSAButtonListener();
        this.openFSAButton.addActionListener(listener);
	}
	/**
	 * Method to create a button to open the Input
	 * string and display it in text area
	 */
	public void createOpenInputButton() {
		this.openInputButton = new JButton("Open Input String");
		/**
		 * ActionListener class for the Open Input button
		 */
		class OpenInputButtonListener implements ActionListener {
			@Override
			/**
			 * Action performed method to
			 * open the file with a JFileChooser
			 * then get the string the display string
			 * in text area
			 */
			public void actionPerformed(ActionEvent e) {
				try {
                    JFileChooser fileChooser = new JFileChooser();
                    File file;
                    String line;
                    int returnVal = fileChooser.showOpenDialog(ProcessorGui.this);
                    if(returnVal == JFileChooser.APPROVE_OPTION) {
                        file = fileChooser.getSelectedFile();
                        if(file.canRead() && file.exists()) {
                        	Scanner inFile = new Scanner(new FileReader(file));
                        	line = inFile.next();
                        	inputString = line;
                        	inputArea.setText(inputString);
                        	inputScanned = true;
                        	inFile.close();
                        }
                    } else {
                        // do Nothing
                    }
                } catch(HeadlessException | IOException ex) {
                    System.out.println("Error opening file");
                }
			}
		}
		ActionListener listener = new OpenInputButtonListener();
        this.openInputButton.addActionListener(listener);
	}
	/**
	 * Method to create the Process Input Button
	 */
	public void createProcessInputButton() {
		this.processInputButton = new JButton("Process Input");
		/**
		 * ActionListener class for the Process input Button
		 */
		class ProcessInputButtonListener implements ActionListener {
			@Override
			/**
			 * actionPerformed method
			 * Instantiates Machine
			 * Checks if NFA
			 * Processes input
			 */
			public void actionPerformed(ActionEvent e) {
				if(fsaScanned && inputScanned) {
					boolean check = checker.splitFSA(fsa);
					if(check) {
						machine = new Machine(fsa);
						isNFA = machine.getIsNonDeterministic();
						if(isNFA) {
							JOptionPane.showMessageDialog(null, "Machine is Nondeterministic. Cannot process.");
						} else {
							accepted = machine.processInput(inputString);
							if(accepted) {
								JOptionPane.showMessageDialog(null, "FSA accepted input string. Input valid");
							} else {
								JOptionPane.showMessageDialog(null, "FSA does not accept input string. Input invalid");
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Not a valid FSA. Cannot process.");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Must open FSA String and Input String before processing");
				}
				
			}
		}
		ActionListener listener = new ProcessInputButtonListener();
		this.processInputButton.addActionListener(listener);
	}

}
