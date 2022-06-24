package piccross;

import static piccross.Game.DIM;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

/**
 * @author mohit
 * 
 * This class creates all the components of the game and calls action listeners from Game Controller class
 *
 */

public class GameView extends JFrame {

	// --------------- Declaring Components ---------------

	private static JTextArea ta;
	private static JCheckBox cb;
	public static Timer timer;
	private static ArrayList<JButton> buttonArray = new ArrayList<>(DIM * DIM);
    private static final long serialVersionUID = 1L;
	static short mins = 0;
	static short seconds = 0;
	private static JTextField time1;
	private static JTextField points1;

	static JLabel labelWin;
	static JLabel labelEnd;
	static JLabel labelAbout;

	// --------------- Creating Layouts ---------------

	GridBagLayout GL = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();
	GridLayout GL1 = new GridLayout(DIM, DIM, 0, 0);

	// --------------- Declaring Panels ---------------

	JPanel boardPanel = new JPanel();
	JPanel leftPanel = new JPanel();
	JPanel markPanel = new JPanel();
	JPanel topPanel = new JPanel();
	JPanel mainPanel = new JPanel(GL);
	JPanel controlPanel = new JPanel();
	JFrame frame = new JFrame();

	ArrayList<ArrayList<Integer>> topRow;

	// --------------- Declaring Colors ---------------

	final Color teal = new Color(32, 178, 170);
	Color leftPanelColor = new Color(210, 180, 222);
	Color topPanelColor = new Color(210, 180, 222);
	// final Color lPurple = new Color(247, 174, 166);
	Color boardPanelColor = new Color(135, 206, 250);
	Color controlPanelColor = new Color(74, 35, 90);
	Color menuBarColor = new Color(204, 209, 209);
	Color markPanelColor = new Color(210, 180, 222);
	Color button1C;
	Color button2C;
	Color button3C;

	// --------------- Beginning Splash Screen ---------------

	/**
	 * This method creates beginning splash screen
	 * 
	 * @param duration This method takes number of seconds as parameter
	 * 
	 */
	public void splash(int duration) {
		duration *= 500;
		JPanel content = new JPanel();
		int width = 640 + 10;
		int height = 340 + 10;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		setBounds(x, y, width, height);
		JLabel label;
		try {
			label = new JLabel(new ImageIcon(getClass().getResource("piccross.png")));
		} catch (Exception e) {
			// e.printStackTrace();
			label = new JLabel("No image");
		}
		JLabel demo = new JLabel("Mohit", JLabel.CENTER);
		demo.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 14));
		content.add(label, BorderLayout.CENTER);
		content.add(demo, BorderLayout.SOUTH);
		setContentPane(content);
		setVisible(true);
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			/* log an error here? *//* e.printStackTrace(); */}
		dispose();
	}

	// --------------- Winner Splash Screen ---------------

	/**
	 * This method creates splash screen if the user wins
	 * 
	 * @param duration This method takes number of seconds as parameter
	 * 
	 */
	public static void splashWin(int duration) {
		duration *= 1000;
		JDialog frame12 = new JDialog();
		frame12.setTitle("You Win!");
		JPanel content = new JPanel();
		int width = 800 + 10;
		int height = 550 + 10;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		frame12.setBounds(x, y, width, height);
		JLabel demo = new JLabel("Winner!", JLabel.CENTER);
		demo.setFont(new Font("Consolas", Font.BOLD, 40));
		content.add(labelWin, BorderLayout.CENTER);
		content.add(demo, BorderLayout.SOUTH);
		frame12.add(content);
		frame12.setVisible(true);
		frame12.setResizable(false);
	}

	// --------------- Loser Splash Screen ---------------

	/**
	 * This method creates splash screen if the user loses the game
	 * 
	 * @param duration This method takes number of seconds as parameter
	 * 
	 */
	public static void splashLoser(int duration) {
		duration *= 1000;
		JDialog frame12 = new JDialog();
		frame12.setTitle("Game Over! You Lost!");
		JPanel content = new JPanel();
		// Set the window's bounds, position the window in the center of the screen
		int width = 485 + 10;
		int height = 300 + 10;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		frame12.setBounds(x, y, width, height);
		JLabel demo = new JLabel("", JLabel.CENTER);
		demo.setFont(new Font("Consolas", Font.ITALIC, 40));
		content.add(labelEnd, BorderLayout.CENTER);
		content.add(demo, BorderLayout.SOUTH);
		frame12.add(content);
		// frame.pack();
		frame12.setVisible(true);
		frame12.setResizable(false);
		// frame12.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// --------------- About Splash Screen ---------------

	/**
	 * This method creates splash screen if the user wants to see About the game
	 *  
	 */
	public static void splashAbout() {

		// create content pane
		JButton ok = new JButton(" OK ");
		// GridBagLayout GL = new GridBagLayout();
		JDialog frame12 = new JDialog();
		frame12.setTitle("About Piccross");
		JPanel content = new JPanel();
		// content.setLayout(GL);
		// Set the window's bounds, position the window in the center of the screen
		int width = 650 + 10;
		int height = 375 + 10;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (screen.width - width) / 2;
		int y = (screen.height - height) / 2;
		frame12.setBounds(x, y, width, height);		
		content.add(labelAbout, BorderLayout.CENTER);		
		content.add(ok);
		frame12.add(content);
		frame12.setTitle("About Piccross");		
		frame12.setVisible(true);
		frame12.setResizable(false);
		ok.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				frame12.dispose();
			}
		});

		// frame12.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	// --------------- Setting up Timer ---------------

	/**
	 * This method is setting up the timer for the game
	 */
	public void setTimer() {
		timer = new Timer(1000, e -> {
			short mins = getTime()[0];
			short seconds = getTime()[1];

			if (seconds == 59)
				mins = (short) ((++mins) % 60);
			seconds = (short) ((++seconds) % 60);
			setTime(new short[] { mins, seconds });

			String timeField = "";
			StringBuilder sb = new StringBuilder(timeField);
			if (mins < 10)
				sb.append("0");
			sb.append(mins + ":");
			if (seconds < 10)
				sb.append("0");
			sb.append(seconds);
			timeField = sb.toString();

			getTimeField().setText(timeField);
		});
		timer.setInitialDelay(1000);
		timer.start();
		revalidate();
		repaint();
	}

	// --------------- Color Chooser ---------------

	/**
	 * This method creates color chooser for the user to customize colors for the
	 * boxes in the game
	 */
	public void colorChooser() {

		JFrame paletteBar = new JFrame("Color Palette Selector");
		JPanel paletteBarPanel = new JPanel(new GridLayout(2, 12));
		JColorChooser colorSwatch = new JColorChooser();
		colorSwatch.setPreferredSize(new Dimension(600, 300));
		colorSwatch.setVisible(true);
		JPanel color1 = new JPanel(), color2 = new JPanel(), color3 = new JPanel();
		JPanel colorAll = new JPanel(), color4 = new JPanel(), color5 = new JPanel(), color6 = new JPanel();
		JButton button1 = new JButton("Correct");
		JButton button2 = new JButton("Marked");
		JButton button3 = new JButton("Error");
		JButton buttonAll = new JButton("All Panels");
		JButton button4 = new JButton("Left Panel");
		JButton button5 = new JButton("Top Panel");
		JButton button6 = new JButton("Right Panel");

		Dimension sharedDimension = new Dimension(100, 30);

		color1.setBackground(GameController.RIGHT);
		color1.setPreferredSize(sharedDimension);
		color2.setBackground(GameController.MARKED);
		color2.setPreferredSize(sharedDimension);
		color3.setBackground(GameController.WRONG);
		color3.setPreferredSize(sharedDimension);
		colorAll.setBackground(controlPanelColor);
		colorAll.setPreferredSize(sharedDimension);
		color4.setBackground(leftPanelColor);
		color4.setPreferredSize(sharedDimension);
		color5.setBackground(topPanelColor);
		color5.setPreferredSize(sharedDimension);
		color6.setBackground(controlPanelColor);
		color6.setPreferredSize(sharedDimension);

		// Button 1 and Color 1
		// Adding action listener

		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Color newColorC;
				newColorC = JColorChooser.showDialog(null, "Choose a color", Color.GREEN);

				for (int i = 0; i < (DIM * DIM); i++) {

					Color a = buttonArray.get(i).getBackground();
					if (a == GameController.RIGHT) {
						buttonArray.get(i).setBackground(newColorC);
					}
				}

				color1.setBackground(newColorC);
				GameController.RIGHT = newColorC;
			}
		});
		button1.setPreferredSize(sharedDimension);

		// Button 2 and Color 2
		// Adding action listener
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Color newColorC;
				newColorC = JColorChooser.showDialog(null, "Choose a color", Color.YELLOW);

				for (int i = 0; i < (DIM * DIM); i++) {

					Color a = buttonArray.get(i).getBackground();
					if (a == GameController.MARKED) {
						buttonArray.get(i).setBackground(newColorC);
					}
				}
				color2.setBackground(newColorC);
				GameController.MARKED = newColorC;
			}
		});
		button2.setPreferredSize(sharedDimension);

		// Button 3 and Color 3
		// Adding action listener
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Color newColorC;
				newColorC = JColorChooser.showDialog(null, "Choose a color", Color.RED);

				for (int i = 0; i < (DIM * DIM); i++) {

					Color a = buttonArray.get(i).getBackground();
					if (a == GameController.WRONG) {
						buttonArray.get(i).setBackground(newColorC);
					}
				}

				color3.setBackground(newColorC);
				GameController.WRONG = newColorC;
			}
		});

		button3.setPreferredSize(sharedDimension);

		// Button to change colors of all the panels
		// Adding action listener

		buttonAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Color newColorC;
				newColorC = JColorChooser.showDialog(null, "Choose a color", leftPanelColor);
				colorAll.setBackground(newColorC);
				// GameController.MARKED = newColorC;
				// leftPanelColor = newColorC;
				leftPanel.setBackground(newColorC);
				leftPanel.revalidate();
				leftPanel.repaint();
				topPanel.setBackground(newColorC);
				topPanel.revalidate();
				topPanel.repaint();
				markPanel.setBackground(newColorC);
				markPanel.revalidate();
				markPanel.repaint();
				controlPanel.setBackground(newColorC);
				controlPanel.revalidate();
				controlPanel.repaint();
			}
		});
		button4.setPreferredSize(sharedDimension);

		// Button 4 and Color 4
		// Adding action listener
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Color newColorC;
				newColorC = JColorChooser.showDialog(null, "Choose a color", leftPanelColor);
				color4.setBackground(newColorC);
				// GameController.MARKED = newColorC;
				// leftPanelColor = newColorC;
				leftPanel.setBackground(newColorC);
				leftPanel.revalidate();
				leftPanel.repaint();

			}
		});
		button4.setPreferredSize(sharedDimension);

		// Button 5 and Color 5
		// Adding action listener
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Color newColorC;
				newColorC = JColorChooser.showDialog(null, "Choose a color", topPanelColor);
				color5.setBackground(newColorC);
				// GameController.MARKED = newColorC;
				// topPanelColor = newColorC;
				topPanel.setBackground(newColorC);
				topPanel.revalidate();
				topPanel.repaint();
				markPanel.setBackground(newColorC);
				markPanel.revalidate();
				markPanel.repaint();
			}
		});
		button5.setPreferredSize(sharedDimension);

		// Button 6 and Color 61
		// Adding action listener
		button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Color newColorC;
				newColorC = JColorChooser.showDialog(null, "Choose a color", controlPanelColor);
				color6.setBackground(newColorC);
				// GameController.MARKED = newColorC;
				// controlPanelColor = newColorC;
				controlPanel.setBackground(newColorC);
				controlPanel.revalidate();
				controlPanel.repaint();
			}
		});
		button5.setPreferredSize(sharedDimension);

		paletteBarPanel.add(color1);
		paletteBarPanel.add(color2);
		paletteBarPanel.add(color3);
		paletteBarPanel.add(colorAll);
		paletteBarPanel.add(color4);
		paletteBarPanel.add(color5);
		paletteBarPanel.add(color6);
		paletteBarPanel.add(button1);
		paletteBarPanel.add(button2);
		paletteBarPanel.add(button3);
		paletteBarPanel.add(buttonAll);
		paletteBarPanel.add(button4);
		paletteBarPanel.add(button5);
		paletteBarPanel.add(button6);
		paletteBar.add(colorSwatch);
		paletteBar.add(paletteBarPanel);
		paletteBar.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		paletteBar.pack();
		paletteBar.setResizable(false);
		paletteBar.setVisible(true);
	}

	/**
	 * @param DIM
	 * 
	 *            This method takes Dimension as the argument and generates labels
	 *            for the left panel The number of labels generated depends on the
	 *            dimension
	 *
	 */
	public void genColLabels(int DIM) {
		ArrayList<ArrayList<Integer>> colClue = GameModel.getColClue();
		for (int x = 0; x < DIM; x++) {
			String clue = "";
			for (Integer i : colClue.get(x)) {
				clue += String.valueOf(i) + ", ";
			}
			if (clue.length() != 0) {
				int n = clue.length() - 2;
				String clue1 = clue.substring(0, n); // Change the second zero to n after out of bounds testing
				JLabel label6 = new JLabel(clue1, JLabel.CENTER);
				leftPanel.add(label6);
			} else {
				clue = "0";
				JLabel label6 = new JLabel(clue, JLabel.CENTER);
				leftPanel.add(label6);
			}
		}
	}

	/**
	 * @param DIM
	 * 
	 *            This method takes Dimension as the argument and generates labels
	 *            for the left panel The number of labels generated depends on the
	 *            dimension
	 *
	 */
	public void genRowLabels(int DIM) {
		ArrayList<ArrayList<Integer>> rowClue = GameModel.getRowClue();
		for (int x = 0; x < DIM; x++) {
			String clue = "";
			for (Integer i : rowClue.get(x)) {
				clue += String.valueOf(i) + ", ";
			}
			if (clue.length() != 0) {

				int n = clue.length() - 2;
				String clue1 = clue.substring(0, n); // Change the second zero to n after out of bounds testing
				JLabel label6 = new JLabel(clue1, JLabel.CENTER);
				topPanel.add(label6);
			} else {

				clue = "0";
				JLabel label6 = new JLabel(clue, JLabel.CENTER);
				topPanel.add(label6);
			}
		}
	}

	/**
	 * @param DIM
	 * 
	 *            This method takes as dimensions as the argument and creates
	 *            buttons for the main panel
	 * 
	 */
	public void buttonsCreater(int DIM) {
		for (int i = 0; i < (DIM * DIM); i++) {

			JButton button = new JButton();

			GameController.buttonActListen(button);
			boardPanel.add(button, c);
			buttonArray.add(button);
			button.setBackground(Color.white);
		}
	}

	/**
	 * This method regenerates the board panel and recreates the buttons and their
	 * labels We use this method to make the game dynamic
	 * 
	 * @param DIM
	 * This method takes the game dimension as arguments and takes string
	 * @param str
	 * 
	 *            
	 * and takes tring as parameter to if we want to create a custom board
	 *
	 */
	public void dimensionMenu(int DIM, String str) {
		new GameModel(str);
		System.out.println(str);
		boardPanel.removeAll();
		boardPanel.setLayout(new GridLayout(DIM, DIM, 0, 0));
		leftPanel.removeAll();
		leftPanel.setLayout(new GridLayout(DIM, 1, 0, 0));
		topPanel.removeAll();
		topPanel.setLayout(new GridLayout());
		genRowLabels(DIM);
		genColLabels(DIM);
		buttonArray.clear();
		buttonsCreater(DIM);
		boardPanel.revalidate();
		boardPanel.repaint();
		mins = 0;
		seconds = 0;
		timer.restart();
		GameController.counter = 0;
		GameController.pointsCounter = 0;
		points1.setText(null);
		time1.setText(null);
		ta.setText(null);
		revalidate();
		repaint();
	}

	/**
	 * This is a setter for the timer function
	 * 
	 * @param time
	 * 
	 *             This method takes the time to set time for the timer function
	 */
	public static void setTime(short[] time) {
		mins = time[0];
		seconds = time[1];
	}

	/**
	 * This is one of the most important functions of this class This function
	 * initializes and works on all the components in this class
	 */
	public void createFrame() {

		// Creating custom labels with pictures to be used in splash screen
		try {
			labelEnd = new JLabel(new ImageIcon(getClass().getResource("gameover.gif")));
		} catch (Exception e) {
			// e.printStackTrace();
			labelEnd = new JLabel("No image");
		}
		try {
			labelWin = new JLabel(new ImageIcon(getClass().getResource("winner.gif")));
		} catch (Exception e) {
			// e.printStackTrace();
			labelWin = new JLabel("No image");
		}
		try {
			labelAbout = new JLabel(new ImageIcon(getClass().getResource("piccross.png")));
		} catch (Exception e) {
			// e.printStackTrace();
			labelAbout = new JLabel("No image");
		}

		// Calling functions created within this class so I don't have to call them in
		// the constructor created in GameController class

		genColLabels(DIM);
		genRowLabels(DIM);
		buttonsCreater(DIM);
		setTimer();

		ta = new JTextArea();

		final boolean DEBUG = true;

		// CONFIGURING MAIN PANEL
	

		mainPanel.setBackground(boardPanelColor);

		// CONFIGURING MARK PANEL
		

		markPanel.setPreferredSize(new Dimension(150, 125));
		if (DEBUG) {
			markPanel.setBorder(BorderFactory.createLineBorder(Color.black, 0));
		}
		markPanel.setLayout(new GridBagLayout());
		markPanel.setBackground(markPanelColor);

		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;

		// Adding markPanle to the main Panel
		mainPanel.add(markPanel, c);

		// Adding Mark check box
		cb = new JCheckBox(" Mark");
		cb.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		 cb.setPreferredSize(new Dimension(70,50));

		//JLabel jl = new JLabel(" Mark");
		//jl.setFont(new Font("Consolas", Font.BOLD, 18));

		markPanel.add(cb);
		//markPanel.add(jl);

		GameController.markActListen(cb);

		// Configuring top panel and adding components

		topRow = GameModel.getRowClue();

		topPanel.setPreferredSize(new Dimension(600, 125));
		if (DEBUG) {
			topPanel.setBorder(BorderFactory.createLineBorder(Color.black, 0));
		}

		topPanel.setLayout(new GridLayout());

		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		c.gridheight = 1;
		topPanel.setBackground(topPanelColor);
		mainPanel.add(topPanel, c);

		// Configuring left panel and adding components

		leftPanel.setPreferredSize(new Dimension(150, 600));
		if (DEBUG) {
			leftPanel.setBorder(BorderFactory.createLineBorder(Color.black, 0));
		}
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 2;
		leftPanel.setBackground(leftPanelColor);
		mainPanel.add(leftPanel, c);

		GridLayout GL5 = new GridLayout(DIM, 1, 10, 10);
		leftPanel.setLayout(GL5);

		// Configuring board panel and adding components

		boardPanel.setPreferredSize(new Dimension(600, 600));
		if (DEBUG) {
			boardPanel.setBorder(BorderFactory.createLineBorder(Color.black, 0));
		}

		boardPanel.setLayout(GL1);
		c.weightx = 0;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		c.gridheight = 2;
		boardPanel.setBackground(boardPanelColor);
		mainPanel.add(boardPanel, c);

		// Configuring control panel and adding components

		controlPanel.setPreferredSize(new Dimension(200, 725));
		if (DEBUG) {
			controlPanel.setBorder(BorderFactory.createLineBorder(Color.black, 0));
		}

		c.weightx = 0.8;
		c.gridx = 3;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 3;
		controlPanel.setBackground(controlPanelColor);
		mainPanel.add(controlPanel, c);

		// create an ImageIcon from the specified file
		ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource("piccrossNameMin.jpg")).getImage()
				.getScaledInstance(190, 30, Image.SCALE_SMOOTH));

		// create a center aligned label (horizontal alignment)
		JLabel label = new JLabel(icon, JLabel.CENTER);

		// add label to frame
		controlPanel.add(label);

		JLabel points = new JLabel("Points: ");
		points.setForeground(Color.WHITE);
		points.setFont(new Font("Consolas", Font.BOLD, 18));

		controlPanel.add(points);
		points1 = new JTextField(5);
		points1.setEditable(false);
		controlPanel.add(points1);

		// JTextArea ta = new JTextArea("Hello there!!");
		ta.setEditable(false);
		ta.setLineWrap(true);
		// ta.setPreferredSize(new Dimension(150,500));

		JScrollPane scroll = new JScrollPane(ta);

		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(175, 575));
		controlPanel.add(scroll);

		// -------------------------- TIME FIELD ---------------------------//
		JLabel time = new JLabel("Time: ");
		time.setForeground(Color.WHITE);
		time.setFont(new Font("Consolas", Font.BOLD, 18));
		controlPanel.add(time);

		time1 = new JTextField("00:00", 8);
		time1.setEditable(false);
		controlPanel.add(time1);

		JButton reset = new JButton("Reset");
		GameController.resetActListen(reset);

		controlPanel.add(reset);
		add(mainPanel);

		// CREATING AND CONFIGURING MENU BAR

		JMenu menu, menu1, submenu1, hSubMenu1;
		JMenuItem exit, i1, i2, i3, i4, i5, i6, i7, submenu2, about, colorItem;
		JMenuBar mb = new JMenuBar();
		mb.setBackground(menuBarColor);
		menu = new JMenu("Game");
		menu1 = new JMenu("Help");
		submenu1 = new JMenu("New Game");
		submenu2 = new JMenuItem("Solution");
		hSubMenu1 = new JMenu("Colors");
		i1 = new JMenuItem("Random");
		i2 = new JMenuItem("2 X 2");
		i3 = new JMenuItem("3 X 3");
		i4 = new JMenuItem("4 X 4");
		i5 = new JMenuItem("5 X 5");
		i6 = new JMenuItem("6 X 6");
		i7 = new JMenuItem("Default");
		about = new JMenuItem("About");
		colorItem = new JMenuItem("Color Chooser");
		exit = new JMenuItem("Exit");
		menu.add(submenu1);
		menu.add(submenu2);
		menu1.add(hSubMenu1);
		menu1.add(about);
		menu.add(exit);
		submenu1.add(i7);
		submenu1.add(i1);
		submenu1.add(i2);
		submenu1.add(i3);
		submenu1.add(i4);
		submenu1.add(i5);
		submenu1.add(i6);

		hSubMenu1.add(colorItem);

		submenu1.setIcon(new ImageIcon(getClass().getResource("piciconnew.gif")));
		submenu2.setIcon(new ImageIcon(getClass().getResource("piciconsol.gif")));
		exit.setIcon(new ImageIcon(getClass().getResource("piciconext.gif")));
		hSubMenu1.setIcon(new ImageIcon(getClass().getResource("piciconcol.gif")));
		about.setIcon(new ImageIcon(getClass().getResource("piciconabt.gif")));

		// Action listeners for menu bar items
		// creating action listener for each menu item here as different buttons do
		// different things
		i1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Random rand = new Random();
				int upperbound = 6;
				int randomDim = (rand.nextInt(upperbound)) + 2;

				Game.DIM = randomDim;
				String rs = GameModel.randStr();
				dimensionMenu(DIM, rs);
			}
		});

		i2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Game.DIM = 2;
				String rs = GameModel.randStr();

				dimensionMenu(DIM, rs);
			}
		});

		i3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Game.DIM = 3;
				String rs = GameModel.randStr();

				dimensionMenu(DIM, rs);
			}
		});

		i4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Game.DIM = 4;
				String rs = GameModel.randStr();

				dimensionMenu(DIM, rs);
			}
		});

		i5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Game.DIM = 5;
				String rs = GameModel.randStr();

				dimensionMenu(DIM, rs);
			}
		});

		i6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Game.DIM = 6;
				String rs = GameModel.randStr();

				dimensionMenu(DIM, rs);
			}
		});

		i7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				Game.DIM = 5;
				String defaultStr = "0010000100111110111001010";
				dimensionMenu(DIM, defaultStr);
			}
		});

		colorItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				colorChooser();
			}
		});

		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				System.exit(0);
			}
		});

		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				splashAbout();
			}
		});

		submenu2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				GameController.showSolution();
			}
		});

		mb.add(menu);
		mb.add(menu1);

		//CREATING AND CONFIGURING JFRAME
		frame.setJMenuBar(mb);
		frame.getContentPane().add(mainPanel);
		frame.setTitle("Mohit Nargotra | Java Assignment Fall 2021");
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// ----------------------------- GETTERS ------------------------//

	// returns mins and seconds for time
	public static short[] getTime() {
		return new short[] { mins, seconds };
	}

	// returns time field content
	public static JTextField getTimeField() {
		return time1;
	}

	// returns points field content
	public static JTextField getPointsField() {
		return points1;
	}

	// returns text area content
	public static JTextArea getTA() {
		return ta;
	}

	// returns buttons array list
	public static ArrayList<JButton> getButtonArray() {
		return buttonArray;
	}

	// returns Jcheckbox
	public static JCheckBox getCB() {
		return cb;
	}

}

//End of class
