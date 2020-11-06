package application;

import maze.*;
import persistence.StateSaver;
import renderer.Renderer;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main Class of the game.
 */
public class ChipAndChap extends JPanel implements KeyListener {

	private static Renderer renderer;
	private static Maze maze;
	private static Player player;
	private static JFrame frame;

	private JMenuBar menu;
	private JPanel panel;
	private Color brown = new Color(67, 53, 48);

	private int startRow = 0;
	private int startCol = 0;
	private int treasure = 0;
	private int totalRows = 0;
	private int totalCols = 0;

	private static boolean timeLeft = true;

	private static ArrayList<Level> levels = new ArrayList<Level>();

	private int level = 1;

	private static JavaTimer tickTimer;
	private static JavaTimer countdownTimer;

	private static boolean running = true;


	private boolean hasLost = false;

	public enum Direction {
		NORTH, SOUTH, EAST, WEST;
	}

	public Graphics getGraphics() {
		return frame.getGraphics();
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {


	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if (timeLeft && running) {
			if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
				player.move(maze, Direction.SOUTH);
				// frame.repaint();
			}

			if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
				player.move(maze, Direction.NORTH);
				// frame.repaint();
			}

			if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
				player.move(maze, Direction.WEST);
				// frame.repaint();
			}

			if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
				player.move(maze, Direction.EAST);
				// frame.repaint();
			}

			if(keyEvent.getKeyCode() == KeyEvent.VK_X && keyEvent.isControlDown()) {
				//current game is closed. New one is started again.
				StateSaver saver = new StateSaver(this);
				saver.saveAsLevelResumeState(getCurrentLevel().getLevelNumber());
				frame.setVisible(false);
				frame.dispose();
				System.exit(1);
			}

			if(keyEvent.getKeyCode() == KeyEvent.VK_S && keyEvent.isControlDown()) {
				//exits the game saves it's current state before doing so.
				saveGame();
				frame.setVisible(false);
				frame.dispose();
				System.exit(0);


			}

			if(keyEvent.getKeyCode() == KeyEvent.VK_P && keyEvent.isControlDown()) {
				//start game at last unfinished level
				//SAI HERE

				RunGame.loadFromLastLevel(levels.get(0).getLevelNumber());
				frame.setVisible(false);
				frame.dispose();

			}

			if(keyEvent.getKeyCode() == KeyEvent.VK_R && keyEvent.isControlDown()) {
				//start game at level 1
				frame.setVisible(false);
				frame.dispose();
				RunGame.resumeSavedGame(new File("LastSavedState.json"));
			}

			if(keyEvent.getKeyCode() == KeyEvent.VK_1 && keyEvent.isControlDown()) {
				frame.setVisible(false);
				frame.dispose();
				new ChipAndChap();
			}

		}


		if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE){
			//pauses the game with a popup.

			setRunning(true);


			frame.repaint();
			//saveGame();  //close the game is paused dialog and resume the game.
		}

		//this is outside of is running check as it directly changes the boolean variable 'running'
		if(keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
			setRunning(false);


			frame.repaint();
		}
	}

	/**
	 * Sets the value of the running variable
	 * @param running Boolean indicating of game is running
	 */
	public static void setRunning(boolean running) {
		ChipAndChap.running = running;
	}

	/**
	 * Saves the the current Game
	 * into a JSON file.
	 */
	public void saveGame(){
		StateSaver stateSaver = new StateSaver(this);
		stateSaver.saveState();

	}

	/**
	 * Class Constructor, initialises GUI when called.
	 */
	public ChipAndChap() {
		levels.add(new LevelOne());
		levels.add(new LevelTwo());
		initialiseGUI(false);
		frame.repaint();
		frame.add(panel);
		panel.setBackground(brown);
	}

	/**
	 * Constructor which allows level to be selected
	 * @param level int indicating which level to load
	 */
	public ChipAndChap(int level){
		if(level == 1){
			levels.add(new LevelOne());
			levels.add(new LevelTwo());
		} else if (level == 2){
			levels.add(new LevelTwo()) ;
		}

		initialiseGUI(false);
		frame.repaint();
	}

	/**
	 * Constructor
	 * @param p Player in game
	 * @param m Maze object to store
	 * @param r Renderer used to draw the game
	 * @param level
	 * @param timeLeft
	 */
	public ChipAndChap(Player p, Maze m, Renderer r, int level, int timeLeft) {
		// initalize all the levels.
		levels.add(new LevelOne());
		levels.add(new LevelTwo());
		player = p;
		maze = m;
		renderer = r;
		initialiseGUI(true);
		System.out.println(tickTimer.getTimeLeft());
		frame.repaint();
	}

	/**
	 * Redraws the game with the Graphics object
	 * @param g Graphics object used to draw
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		renderer.render(maze, player, g);
		setVisible(true);
	}

	/**
	 * Sets up all the Swing Components needed for the GUI.
	 */
	private void initialiseGUI(boolean loadedFromJson) {
		menu = new JMenuBar();
		panel = new JPanel();

		JMenu option = new JMenu("Options");
		JMenuItem rules = new JMenuItem(new AbstractAction("Rules") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				String message = "Use the arrow keys to move the player.\n" +
						"The aim of the game is to collect all of the chips\n" +
						"before times runs out.\n" +
						"Be careful, as there will be monsters trying to stop you\n" +
						"from achieving your goal";

				JOptionPane.showMessageDialog(null, message, "Rules", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		JMenuItem exit = new JMenuItem(new AbstractAction("Exit") {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				System.exit(0);
			}
		});


		option.add(rules);
		option.add(exit);


		menu.add(option);


		menu.setBackground(Color.YELLOW);
		menu.setForeground(brown);

		if(!loadedFromJson) {
			renderer = new Renderer();
			setup();
		}

		tickTimer = new JavaTimer(this, 16, false, null, null);
		countdownTimer = new JavaTimer(this, 1000, true, renderer, tickTimer);
		frame = new JFrame("Chip and Chap");
		frame.add(this);
		frame.setJMenuBar(menu);
		// Need to change size to be based off of Screen size
		frame.setMinimumSize(new Dimension(650, 550));
		frame.setVisible(true);
		frame.addKeyListener(this);
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(frame,
						"Are you sure you want to close this window?", "Close Window?",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
					tickTimer.stopTime();
					running = false;
				}
			}
		});
	}

	/**
	 * Return the current level that is being played
	 *
	 * @return return current level.
	 */
	public static Level getCurrentLevel() {
		return levels.get(0);
	}

	/**
	 * Sets up the maze and the player Location. This is called when changing maps.
	 * Will also change the countdown timer value back to default when the level changes.
	 */
	private static void setup() {
		maze = new Maze(levels.get(0).totalRows(), levels.get(0).totalCols(), renderer);
		player = new Player(maze.getTile(levels.get(0).getStartRow(), levels.get(0).getStartCol()), renderer);
		if(countdownTimer != null) {
			countdownTimer.resetCountdown(getCurrentLevel().getAllocatedTime());
		}
	}

	/**
	 * Once a level has been completed remove from the list then call setup to
	 * change the map.
	 */
	public static void finishLevel() {
		if(getCurrentLevel().getLevelNumber() == 2){
			JOptionPane.showMessageDialog(null, "Congratulations YOU WIN!");
			System.exit(0);
		}
		levels.remove(0);
		setup();


	}

	/**
	 * When the timer reaches 0 set the timeLeft to false.
	 * Can't move the character anymore.
	 */
	public static void setTimeOver() {
		timeLeft = false;
	}

	/**
	 * Gets the maze
	 * @return Maze
	 */
	public Maze getMaze(){
		return maze;
	}

	/**
	 * Gets the current player
	 * @return Player
	 */
	public Player getPlayer(){
		return player;
	}

	/**
	 * Returns the renderer used to render the game
	 * @return Renderer
	 */
	public Renderer getRenderer(){
		return renderer;
	}

	/**
	 * Returns the status of the game
	 * @return boolean indicating if game is running
	 */
	public static boolean isRunning(){
		return running;
	}

	/**
	 * Gets the frame window
	 * @return JFrame component
	 */
	public static JFrame getFrame() {
		return frame;
	}

	/**
	 * Closes the application
	 */
	public static void endGame(){
		System.exit(0);
	}

	/**
	 * Event handler, called when a key is released
	 */
	@Override
	public void keyReleased(KeyEvent e) {
	}

}
