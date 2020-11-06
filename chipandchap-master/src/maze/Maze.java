package maze;

import java.util.HashSet;
import java.util.Set;

import application.ChipAndChap;
import renderer.Renderer;

/**
 * Stores the current state of the game, eg where are objects currently are,
 * what actions are allowed etc.
 */
public class Maze {
	private Tile[][] maze;
	private Renderer renderer;
	private Set<Monster> monstersInMaze = new HashSet<>();

	/**
	 * Maze Constructor
	 *
	 * @param numRows number of rows in this maze
	 * @param numCols number of columns in this maze
	 */
	public Maze(int numRows, int numCols, Renderer renderer) {
		this.renderer = renderer;
		maze = new Tile[numRows][numCols];
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				maze[r][c] = new FreeTile(r, c);
			}
		}

		String layout = ChipAndChap.getCurrentLevel().getLevel();
		int stringCounter = 0;
		// Checks the current char and sets the appropriate type of Tile in the board.
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {

				char currentChar = layout.charAt(stringCounter);

				if (currentChar == '_') { // FreeTile
					setTile(new FreeTile(row, col), row, col);
				} else if(currentChar == '%') {
					setTile(new FreeTile(row, col), row, col);
					addMonsterToMaze(new Monster(getTile(row, col),1));
				}	 else if(currentChar == '#'){
						setTile(new FreeTile(row, col), row, col);
						Monster m =  new Monster(getTile(row, col), 2);
						m.setNextDirection(ChipAndChap.Direction.EAST.toString());
						addMonsterToMaze(m);
				} else if (currentChar == 'w') { // WallTile
					setTile(new WallTile("w", row, col), row, col);
				} else if (currentChar == 'p') {
					setTile(new WallTile("p", row, col), row, col);
				} else if (currentChar == 'i') {
					setTile(new WallTile("i", row, col), row, col);
				} else if (currentChar == 'o') {
					setTile(new WallTile("o", row, col), row, col);

					// pickable things
				} else if (currentChar == 't') { // Treasure Tile
					setTile(new ItemTile(false, "t", row, col), row, col);
				} else if (currentChar == 'r') { // key tiles
					setTile(new ItemTile(true, "r", row, col), row, col);
				} else if (currentChar == 'g') {
					setTile(new ItemTile(true, "g", row, col), row, col);
				} else if (currentChar == 'b') {
					setTile(new ItemTile(true, "b", row, col), row, col);

				} else if (currentChar == 'z') { // locked door tiles
					setTile(new LockedTile("b", row, col), row, col);
				} else if (currentChar == 'y') {
					setTile(new LockedTile("r", row, col), row, col);
				} else if (currentChar == 'x') {
					setTile(new LockedTile("g", row, col), row, col);
				} else if (currentChar == 'e') {
					setTile(new ExitTile(row, col), row, col);
				} else if (currentChar == 'd') {
					setTile(new LockedTile("d", row, col), row, col);
				}else if(currentChar == 'l') {
					setTile(new LockedTile("l", row, col), row, col);
				}else if(currentChar == 'v') {
					setTile(new LockedTile("v", row, col), row, col);
				} else if (currentChar == '?') {
					setTile(new HelpTile(row, col), row, col);
				} else if (currentChar == 'q') {
					setTile(new ItemTile(false, "q", row, col), row, col);
				} else if (currentChar == 'u') {
					setTile(new ItemTile(false, "u", row, col), row, col);
				}

				// add the ? and Chap
				stringCounter++;
			}
		}
	}

	/**
	 * Second constructor that is called when the Game is loaded from a JSON
	 * file.
	 * @param numRows number of rows in this maze
	 * @param numCols number of columns in this maze
	 * @param renderer renderer to draw on
	 * @param loadFromJson added to differentiate the two constructors
	 */
	public Maze(int numRows, int numCols, Renderer renderer, boolean loadFromJson) {
		this.renderer = renderer;
		maze = new Tile[numRows][numCols];
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numCols; c++) {
				maze[r][c] = new FreeTile(r, c);
			}
		}


	}

	/**
	 * Returns a Tile on the specified row and col. Returns null if row or col are
	 * out of bounds.
	 *
	 * @param row the row of the tile
	 * @param col the col of the tile
	 * @return
	 */
	public Tile getTile(int row, int col) {
		if (row < 0 || row >= maze.length) {
			return null;
		} else if (col < 0 || col >= maze[row].length) {
			return null;
		}
		return maze[row][col];
	}

	/**
	 * Sets the Tile at the specified row and col to be the NewTile.
	 *
	 * @param newTile the new tile to overwrite the old tile
	 * @param row     row of the tile
	 * @param col     col of the tile
	 */
	public void setTile(Tile newTile, int row, int col) {
		if (row < 0 || row >= maze.length) {
			return;
		} else if (col < 0 || col >= maze[row].length) {
			return;
		}
		maze[row][col] = newTile;
	}

	/**
	 * Returns number of rows in this Maze
	 *
	 * @return number of rows in this Maze
	 */
	public int getNumRows() {
		return maze.length;
	}

	/**
	 * Returns number of cols in this Maze
	 *
	 * @return number of cols in this Maze
	 */
	public int getNumCols() {
		// Maze is a square 2D array, so each row has the same amount of Columns
		return maze[0].length;
	}

	/**
	 * Adds a monster to the maze arrayList which can be iterated over to give
	 * actions to.
	 *
	 * @param monster to add to the arraylist of monsters in the maze.
	 */
	public void addMonsterToMaze(Monster monster) {
		monstersInMaze.add(monster);
		System.out.println("Added monster to the maze list");
	}

	/**
	 * Gets the monsters in the maze.
	 * @return a set of the monsters in the maze.
	 */
	public Set<Monster> getMonstersInMaze(){
		return monstersInMaze;
	}

	/**
	 * Move all the Monster in the Maze.
	 */
	public void moveMonsters(){
		for(Monster m : monstersInMaze){
			m.doNextMove(this);
		}
	}

	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		for (int r = 0; r < getNumRows(); r++) {
			for (int c = 0; c < getNumCols(); c++) {
				builder.append(getTile(r, c).toString());
			}
			builder.append("\n");
		}
		return builder.toString();
	}
}
