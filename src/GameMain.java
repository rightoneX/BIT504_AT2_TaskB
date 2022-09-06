import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

//BIT504 Programming 1 Assessment 2
//Student: Alexander Ustinov
//Student Number: 5062598

public class GameMain extends JPanel implements MouseListener{
	//Constants for game 
	// number of ROWS by COLS cell constants 
	public static final int ROWS = 3;     
	public static final int COLS = 3;  
	public static final String TITLE = "Tic Tac Toe";

	//call random function
	public static Random random;
	//constants for dimensions used for drawing
	//cell width and height
	public static final int CELL_SIZE = 120;
	//drawing canvas
	public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
	public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
	//Noughts and Crosses are displayed inside a cell, with padding from border
	public static final int CELL_PADDING = CELL_SIZE / 6;    
	public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;    
	public static final int SYMBOL_STROKE_WIDTH = 8;

//	private Board board;

	private static int currentState;
	
	// the current player
	private static Player currentPlayer;
	// for displaying game status message
	private static JLabel statusBar;
	
	private JPanel statusPanel;

	private static JButton[][] buttons;

	private static JPanel buttonPanel;

	private static JFrame frame;
	/** Constructor to set up the UI and game components on the panel */
	public GameMain() {
		// Set up the status bar (JLabel) to display status message
		statusBar = new JLabel(" ");
		statusBar.setBackground(new Color(255, 255, 255));
		statusBar.setForeground(new Color(0, 0, 255));
		statusBar.setFont(new Font("Ink Free", Font.PLAIN,16));
		statusBar.setHorizontalAlignment(JLabel.LEFT);
		statusBar.setOpaque(true);
		statusBar.setText("Game is on");
		statusPanel = new JPanel();
		statusPanel.setLayout(new BorderLayout());
		statusPanel.setBounds(0,10,600,100);

		statusPanel.add(statusBar);

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3,3));
		buttonPanel.setBackground(new Color(150,150,150));

		buttons = new JButton[ROWS][COLS];
		for(int row = 0; row < ROWS ; row++){
			for(int col = 0; col < COLS ; col++) {
				buttons[row][col] = new JButton();
				buttonPanel.add(buttons[row][col]);
				buttons[row][col].setFont(new Font("Ink Free", Font.BOLD, 80));
				buttons[row][col].setFocusable(false);
				buttons[row][col].addMouseListener(this);
			}
		}
		frame.add(statusPanel, BorderLayout.PAGE_END);
		frame.add(buttonPanel);
	}
	
	public static void main(String[] args) {
		    // Run GUI code in Event Dispatch thread for thread safety.
		SwingUtilities.invokeLater(new Runnable() {
	         public void run() {
				//create a main window to contain the panel
				frame = new JFrame(TITLE);
				frame.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
			    frame.getContentPane().setBackground(new Color(50, 50, 50));
			    frame.setLayout(new BorderLayout());
				frame.setVisible(true);

				GameMain game = new GameMain();
				frame.add(game.statusPanel, BorderLayout.PAGE_END);
				frame.add(game.buttonPanel);
				//the game is on
				initGame();
	         }
		 });
	}

		public static void initGame() {
		//game is enabled
			currentState = 1;
		//reset the field
		for(int row = 0; row < ROWS ; row++) {
			for (int col = 0; col < COLS; col++) {
				buttons[row][col].setText("");
				buttons[row][col].setBackground(Color.gray);
			}
		}
		//select the first player
		firstPlayer();
		}

		public static Player firstPlayer(){
			random = new Random();
			if(random.nextInt(2) == 1){
				currentPlayer = Player.Cross;
			}else{
				currentPlayer = Player.Nought;
			}
			//inform the players
			statusBar.setText(currentPlayer.toString() + " Player first Move");
			return currentPlayer;
		}

	public void mouseClicked(MouseEvent e) {
	   // get the coordinates of where the click event happened
	   if (currentState == GameState.Playing) {
		   for (int row = 0; row < ROWS; row++) {
			   for (int col = 0; col < COLS; col++) {
				   if (e.getSource() == buttons[row][col] && buttons[row][col].getText() == "") {

					   if ( currentPlayer == Player.Cross) {
						   buttons[row][col].setForeground(new Color(0, 255, 0));
						   buttons[row][col].setText("X");
						   currentPlayer = Player.Nought;
					   } else
					   {
						   buttons[row][col].setForeground(new Color(0, 0, 255));
						   buttons[row][col].setText("O");
						   currentPlayer = Player.Cross;
					   }
					   statusBar.setText(currentPlayer.toString() + " Player Next Move");
					   GameStatusCheck();
				   }
			   }
		   }
		}
	}

	public void GameStatusCheck(){
		if(buttons[0][0].getText() != "" && buttons[0][0].getText() == buttons[0][1].getText() && buttons[0][1].getText() == buttons[0][2].getText()){
			PlayerWins(0,0,0,1,0,2);
		}
		if(buttons[1][0].getText() != "" && buttons[1][0].getText() == buttons[1][1].getText() && buttons[1][1].getText() == buttons[1][2].getText()){
			PlayerWins(1,0,1,1,1,2);
		}
		if(buttons[2][0].getText() != "" && buttons[2][0].getText() == buttons[2][1].getText() && buttons[2][1].getText() ==buttons[2][2].getText()){
			PlayerWins(2,0,2,1,2,2);
		}
		if(buttons[0][0].getText() != "" && buttons[0][0].getText() == buttons[1][0].getText() && buttons[1][0].getText() == buttons[2][0].getText()){
			PlayerWins(0,0,1,0,2,0);
		}
		if(buttons[0][1].getText() != "" && buttons[0][1].getText() == buttons[1][1].getText() && buttons[1][1].getText() == buttons[2][1].getText()){
			PlayerWins(0,1,1,1,2,1);
		}
		if(buttons[0][2].getText() != "" && buttons[0][2].getText() == buttons[1][2].getText() && buttons[1][2].getText() == buttons[2][2].getText()){
			PlayerWins(0,2,1,2,2,2);
		}
		if(buttons[0][0].getText() != "" && buttons[0][0].getText() == buttons[1][1].getText() && buttons[1][1].getText() == buttons[2][2].getText()){
			PlayerWins(0,0,1,1,2,2);
		}
		if(buttons[0][2].getText() != "" && buttons[0][2].getText() == buttons[1][1].getText() && buttons[1][1].getText() == buttons[2][0].getText()){
			PlayerWins(0,2,1,1,2,0);
		}
		isDraw();
	}

	public void isDraw(){
		int filedEmpty = 0;
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (buttons[row][col].getText() == "") {
					filedEmpty++;
				}
			}
		}
		if(filedEmpty == 0){
			//finish the game by draw
			currentState = 0;
			statusBar.setText("Draw, No Winner.");
			DialogBox("Draw, No Winner. \n Would you like to play again?", "Game Status");
		}
	}

	public void PlayerWins(int row1, int col1, int row2, int col2, int row3, int col3){
		//highlight the win combination
		buttons[row1][col1].setBackground(Color.RED);
		buttons[row2][col2].setBackground(Color.RED);
		buttons[row3][col3].setBackground(Color.RED);
		//recall the dialog box
		if(currentPlayer != Player.Cross) {
			statusBar.setText("Player 'X' WON !");
			DialogBox("Congratulations, Cross Player you WON ! \n Would you like to play again?", "Game Status");
		}else {
			statusBar.setText("Player 'O' WON !");
			DialogBox("Congratulations, Nought Player you WON ! \n Would you like to play again?", "Game Status");
		}
	}
	public static void DialogBox(String infoMessage, String titleBar)
	{
		currentState = 0;
		JDialog.setDefaultLookAndFeelDecorated(true);
		int response = JOptionPane.showConfirmDialog(null, infoMessage, "Confirm",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.NO_OPTION) {
			System.exit(0);
		} else if (response == JOptionPane.YES_OPTION) {
			//play again
			initGame();
		} else if (response == JOptionPane.CLOSED_OPTION) {
			//exit the game
			System.exit(0);
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		//  Auto-generated, event not used
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		//  Auto-generated, event not used
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// Auto-generated,event not used
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// Auto-generated, event not used
		
	}

}
