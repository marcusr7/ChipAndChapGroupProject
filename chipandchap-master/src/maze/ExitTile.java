package maze;

/**
 * Once chap reaches this Tile, the game level is finished.
 */
public class ExitTile extends Tile {
	private String colour = "e";

    public ExitTile(int row, int col) {
        super(true,row, col);
        super.setIcon("images/dungeon/floor_ladder.png");
    }

	@Override
	public String getColour() {
		return colour;
	}

	public String toString(){
		return getColour();
	}
}
