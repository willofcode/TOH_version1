package TowerOfHanoi;

import java.awt.*;
import java.awt.event.*;
import java.util.Stack;
import javax.swing.*;


public class towerOfHanoi extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JButton moveButton, resignButton;
	private JLabel moveCounterLabel;

	private final int numDisks;
	private int movesCount;
	private final Color[] colorArr = {new Color(94, 79, 162), new Color(50, 136, 189), new Color(102, 194, 165), 
            new Color(171, 221, 164), new Color(230, 245, 152), new Color(254, 224, 139), new Color(253, 174, 97), 
            new Color(244, 109, 67), new Color(213, 62, 79), new Color(158,1,66)};

	// Array of stacks to represent disks on each peg
	private Stack<JLabel>[] disksOnPegs;

	private final int[] pegYCoors = {166, 442, 717};
	private int timerSeconds = 1000;
	
	private final Stack<int[]> moves = new Stack<>();
	private String defaultEndScreen = "You Won!";
	
	// Constructor
	public towerOfHanoi(int numDisks) {
		this.numDisks = numDisks;
		initDisks();
		initComponents();
	}

	// Initialize components
	private void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 200, 900, 600);
		getContentPane().setLayout(null);
		
		for(int i = 1; i <= 3; i++){
			JLabel pegLabel = new JLabel("Peg " + i);
			pegLabel.setBounds(pegYCoors[i-1] - 16, 375, 40, 150);
			getContentPane().add(pegLabel);
			
			JLabel pegStem = new JLabel();
			pegStem.setBounds(pegYCoors[i-1] - 3, 175, 6, 250);
			pegStem.setOpaque(true);
			pegStem.setBackground(Color.black);
			getContentPane().add(pegStem);
			
			JLabel pegBottom = new JLabel();
			pegBottom.setBounds(pegYCoors[i-1] - 125, 425, 250, 6);
			pegBottom.setOpaque(true);
			pegBottom.setBackground(Color.black);
			getContentPane().add(pegBottom);
		}
		
		moveButton = new JButton("Move");
		moveButton.setBounds(250, 500, 120, 45);
		getContentPane().add(moveButton);
		moveButton.addActionListener((ActionEvent e) -> {
                    moveDiskPrompt();
                });

		resignButton = new JButton("Resign");
		resignButton.setBounds(530, 500, 120, 45);
		getContentPane().add(resignButton);
		resignButton.addActionListener((ActionEvent e) -> {
                    resign();
                });

		// Initialize move counter label
		moveCounterLabel = new JLabel("Moves: 0");
		moveCounterLabel.setBounds(425, 500, 80, 45);
		getContentPane().add(moveCounterLabel);
	}

	// Method to initialize disks on pegs
	@SuppressWarnings("unchecked")
	private void initDisks() {
		movesCount = 0;
		disksOnPegs = new Stack[3];
		for (int i = 0; i < 3; i++) {
			disksOnPegs[i] = new Stack<>();
		}

		// Calculate the initial positions for disks on peg1
		int startX = pegYCoors[0]; // X-coordinate of the first peg
		int startY = 425; // Y-coordinate of the top of pegs
		int diskHeight = 20; // Height of each disk
		int diskWidthIncrement = 20; // Width increment between disks

		// Initialize disks on peg1
		for (int i = numDisks - 1; i >= 0; i--) {
			JLabel disk = createDisk(colorArr[i]);
			int diskWidth = 60 + i * diskWidthIncrement; // Width of the current disk
			int x = startX - (diskWidth / 2); // X-coordinate of the current disk
			int y = startY - (numDisks - i) * diskHeight; // Y-coordinate of the current disk
			disk.setBounds(x, y, diskWidth, diskHeight);
			getContentPane().add(disk);
			disksOnPegs[0].push(disk);
		}
	}

	// Method to create a disk
	private JLabel createDisk(Color color) {
		JLabel disk = new JLabel();
		disk.setOpaque(true);
		disk.setBackground(color);
		return disk;
	}

	// Method to move a disk from one peg to another
	private void moveDiskPrompt() {
		String input = JOptionPane.showInputDialog(null, "Enter move (e.g., 1 to 3):");
		if(input == null) {
		    return;
		} 
		int fromPeg, toPeg;
		try {
			fromPeg = Integer.parseInt(input.substring(0, 1));
			toPeg = Integer.parseInt(input.substring(input.length() - 1));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid move!", "Error", JOptionPane.ERROR_MESSAGE);
			moveDiskPrompt();
			return;
		}
		if (fromPeg < 1 || fromPeg > 3 || toPeg < 1 || toPeg > 3) {
			JOptionPane.showMessageDialog(null, "Please pick between Pegs 1, 2, or 3", "Error",
					JOptionPane.ERROR_MESSAGE);
			moveDiskPrompt();
			return;
		}
		// Implement move logic here
		if (isValidMove(fromPeg, toPeg)) {
			// Move disk
			moveDisk(fromPeg, toPeg);
			int[] move = {fromPeg, toPeg};
			moves.push(move);
			movesCount++;
			updateMoveCounter();
		} else {
			moveDiskPrompt();
		}
	}

	// Method to check if a move is valid
	private boolean isValidMove(int fromPeg, int toPeg) {
		JLabel fromDisk = disksOnPegs[fromPeg - 1].isEmpty() ? null : disksOnPegs[fromPeg - 1].peek();
		JLabel toDisk = disksOnPegs[toPeg - 1].isEmpty() ? null : disksOnPegs[toPeg - 1].peek();
		if (fromDisk == null) {
			JOptionPane.showMessageDialog(null, "Peg " + fromPeg + " is empty", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		else if(!(fromDisk == null || (toDisk == null || fromDisk.getWidth() < toDisk.getWidth()))){
			JOptionPane.showMessageDialog(null, "Illegal move!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return fromDisk == null || (toDisk == null || fromDisk.getWidth() < toDisk.getWidth());
	}

	// Method to move disk from one peg to another
	private void moveDisk(final int fromPeg, final int toPeg) {
		Stack<JLabel> fromStack = disksOnPegs[fromPeg - 1];
		Stack<JLabel> toStack = disksOnPegs[toPeg - 1];
		JLabel disk = fromStack.pop();
		toStack.push(disk);
		int xCoord = pegYCoors[toPeg - 1] - (disk.getWidth() / 2);
		int yCoord = 425 - 20 * toStack.size();
		disk.setBounds(xCoord, yCoord, disk.getWidth(), disk.getHeight());
		gameOver();
	}

	private void moveDiskWithDelay(final int fromPeg, final int toPeg) {
		Timer timer = new Timer(timerSeconds, (ActionEvent e) -> {
                    // Implement logic to move disk UI representation from one peg to another
                    ((Timer) e.getSource()).stop();
                    moveDisk(fromPeg, toPeg);
                    int[] move = {fromPeg, toPeg};
                    moves.push(move);
                    movesCount++;
                    updateMoveCounter();
                });
		timer.start();
		timerSeconds += 1000;
	}
	
	// Method to handle resignation
	private void resign() {
	    resignButton.setEnabled(false); // Disable resign button to prevent multiple resignations
	    moveButton.setEnabled(false); // Disable move button while resigning
	    defaultEndScreen = "You Lost!";
	    while(!moves.isEmpty()){
	    	int[] move = moves.pop();
	    	moveDisk(move[1], move[0]);
	    }
    	movesCount = 0;
	    updateMoveCounter();
	    solveTOH(numDisks, 1, 3, 2); // Start Tower of Hanoi algorithm
	}

	// Tower of Hanoi algorithm
	private void solveTOH(int n, int fromPeg, int toPeg, int auxPeg) {
	    if (n == 0) {
	        return;
	    }
	    solveTOH(n - 1, fromPeg, auxPeg, toPeg);
	    moveDiskWithDelay(fromPeg, toPeg);
	    solveTOH(n - 1, auxPeg, toPeg, fromPeg);
	}


	// Method to update the move counter label
	private void updateMoveCounter() {
		moveCounterLabel.setText("Moves: " + movesCount);
	}

	private void gameOver() {
		if(disksOnPegs[2].size() == numDisks){
			int option = JOptionPane.showConfirmDialog(null, defaultEndScreen + "\nDo you want to play again?", defaultEndScreen,
	                JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				startGame();
				this.setVisible(false);
			}
			if (option == JOptionPane.NO_OPTION) {
				this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			}
		}
	}

	public static void startGame(){
		EventQueue.invokeLater(() -> {
                    String input = JOptionPane.showInputDialog(null, "Enter the number of disks:", "Number of Disks",
                            JOptionPane.QUESTION_MESSAGE);
                    if(input == null){
                        return;
                    }
                    int numDisks1;
                    try {
                        numDisks1 = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        numDisks1 = 0;
                    }
                    if (numDisks1 <= 0) {
                        JOptionPane.showMessageDialog(null, "Invalid number of disks.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        startGame();
                        return;
                    }
                    if (numDisks1 > 10) {
                        JOptionPane.showMessageDialog(null, "The maximum number of disks allowed is 10", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        startGame();
                        return;
                    }
                    try {
                        towerOfHanoi frame = new towerOfHanoi(numDisks1); // Change the number of disks as needed
                        frame.setTitle("Tower of Hanoi Puzzle Game");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setVisible(true);
                        frame.setLayout(null);
                        frame.setResizable(false);
                        ;
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                });
	}
	
	public static void main(String[] args) {
		startGame();
	}
}
