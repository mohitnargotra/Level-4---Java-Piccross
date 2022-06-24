package piccross;

import static piccross.Game.DIM;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;

/**
 * This class mainly contains action listeners for the components created in
 * GameView class GameView class will call action listeners in this class
 * 
 * @author mohit
 *
 */
public class GameController {

	private static boolean mchecker = false;

	// Points counter and number of clicks counter to be used to determine when to
	// class splash functions
	static int counter;
	static int pointsCounter;

	// --------------- Colors defined for right, wrong and marked boxes

	static Color RIGHT = new Color(39, 148, 33);
	static Color WRONG = new Color(255, 0, 0);
	static Color MARKED = new Color(255, 240, 0);

	/**
	 * My class constructor
	 * 
	 * @param gameView
	 * This takes gameView class as its parameter
	 * @param gameModel
	 * 
	 * This constructor takes gameModel class as its
	 *        
	 * 
	 */
	public GameController(GameView gameView, GameModel gameModel) {
		//gameView.splash(3); // Defines that splash screen should only run for 3 seconds in the beginning
		gameView.createFrame(); // calls create frame method from GameView class
	}

	/**
	 * 
	 * This method is an action listener for the check box in markPanel
	 * 
	 * @param cb This takes checkbox component as an argument
	 * 
	 *           This method is called in GameView class.
	 * 
	 * 
	 */
	public static void markActListen(JCheckBox cb) {
		cb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox cbLog = (JCheckBox) e.getSource();
				if (cbLog.isSelected()) {
					mchecker = true;
					GameView.getTA().append("  Mark box checked;\n\n");
				} else {
					GameView.getTA().append("  Mark box unchecked;\n\n");
					mchecker = false;
				}
			}
		});
	}

	/**
	 * 
	 * This method is an action listener for the buttons in the mainPanel of the
	 * game
	 * 
	 * @param button
	 * 
	 *               This takes buttons component as an argument
	 * 
	 *               This method is called in GameView class.
	 * 
	 */
	public static void buttonActListen(JButton button) {
		button.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// GameView.getTA().append("Pos clicked;\n");
				ArrayList<JButton> buttonArray = GameView.getButtonArray();
				boolean[][] dimGrid = GameModel.getDimGrid();
				int index = buttonArray.indexOf(button);
				int offSet = index % DIM;
				int onSet = (int) Math.floor(index / DIM);

				if (mchecker) {
					if (dimGrid[onSet][offSet]) {
						buttonArray.get(index).setBackground(WRONG);
						counter += 1;
						pointsCounter--;
						GameView.getTA().append("  Button " + index + " Clicked;\n");
						GameView.getTA().append("  Points -1;\n\n");
						GameView.getPointsField().setText("" + pointsCounter);
						endSplash(5);
					} else {
						buttonArray.get(index).setBackground(MARKED);
						counter++;
						pointsCounter++;
						GameView.getTA().append("  Button " + index + " Clicked;\n");
						GameView.getTA().append("  Points +1;\n\n");
						GameView.getPointsField().setText("" + pointsCounter);
						endSplash(5);
					}
				} else if (!mchecker) {
					if (dimGrid[onSet][offSet]) {
						buttonArray.get(index).setBackground(RIGHT);
						counter++;
						pointsCounter++;
						GameView.getTA().append("  Button " + index + " Clicked;\n");
						GameView.getTA().append("  Points +1;\n\n");
						GameView.getPointsField().setText("" + pointsCounter);
						endSplash(5);
					} else {
						buttonArray.get(index).setBackground(WRONG);
						counter++;
						pointsCounter--;
						GameView.getTA().append("  Button " + index + " Clicked;\n");
						GameView.getTA().append("  Points -1;\n\n");
						GameView.getPointsField().setText("" + pointsCounter);
						endSplash(5);
					}
				}
				// buttonArray.get(index).removeActionListener((this));
				buttonArray.get(index).setEnabled(false);

				// j++;
			}
		});

	}

	/**
	 * 
	 * This method is an action listener for the reset button in GameView class
	 * 
	 * @param button
	 * 
	 *               This takes buttons component as an argument
	 * 
	 *               This method is called in GameView class.
	 * 
	 */
	public static void resetActListen(JButton button) {
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<JButton> buttonArray = GameView.getButtonArray();
				for (int i = 0; i < (DIM * DIM); i++) {
					JButton button = buttonArray.get(i);
					// button.removeActionListener((this));
					button.setBackground(Color.WHITE);
					button.setEnabled(true);
					// buttonActListen(button);
				}

				GameView.getTA().setText(null);
				// GameView.getCB().setSelected(false);
				GameView.getPointsField().setText(null);
				GameView.mins = 0;
				GameView.seconds = 0;
				GameView.getTimeField().setText(null);
				GameView.timer.restart();
				counter = 0;
				pointsCounter = 0;
			}
		});
	}

	/**
	 * @return true or false This method checks if the user won or lost This method
	 *         goes through all the buttons and checks their color If even one
	 *         button is red, it instantly stops and returns false which means the
	 *         user lost
	 * 
	 */
	public static boolean winChecker1() {

		boolean winChecker1 = true;
		ArrayList<JButton> buttonArray = GameView.getButtonArray();

		for (int i = 0; i < (DIM * DIM); i++) {

			Color a = buttonArray.get(i).getBackground();

			if (a == WRONG) {
				winChecker1 = false;
				i = DIM * DIM;
			}
		}

		return winChecker1;

	}

	/**
	 * 
	 * This method is used to call the right splash screen based on if the user won
	 * or lost This method is called in GameView class
	 * 
	 * @param secs
	 * 
	 *             This method takes number of seconds as an argument It uses
	 *             seconds argument to decide how many seconds should the winner or
	 *             loser splash screen last
	 * 
	 */
	public static void endSplash(int secs) {
		if (getWinChecker() && counter == DIM * DIM) {
			GameView.getTA().append("  CONGRATULATIONS!\n  YOU WIN!\n\n");
			GameView.timer.stop();
			GameView.splashWin(secs);

		} else if (!getWinChecker() && counter == DIM * DIM) {
			GameView.getTA().append("  GAME OVER!\n  YOU LOST\n\n");
			GameView.timer.stop();
			GameView.splashLoser(secs);

		}
	}

	/**
	 * @return winChecker This method is a getter for checking if the user won or
	 *         lost
	 *
	 */
	public static boolean getWinChecker() {
		return winChecker1();
	}

	/**
	 * This method is called to show user the solution
	 */
	public static void showSolution() {

		ArrayList<JButton> buttonArray = GameView.getButtonArray();
		new JButton();
		boolean[][] dimGrid = GameModel.getDimGrid();

		for (int i = 0; i < DIM * DIM; i++) {
			int offSet = i % DIM;
			int onSet = (int) Math.floor(i / DIM);
			if (dimGrid[onSet][offSet]) {
				buttonArray.get(i).setBackground(RIGHT);
				buttonArray.get(i).setEnabled(false);
			} else {
				buttonArray.get(i).setBackground(MARKED);
				buttonArray.get(i).setEnabled(false);
			}
		}
		GameView.timer.stop();
		GameView.getTA().append("  Showing Solution; \n");

	}

}
//End of class