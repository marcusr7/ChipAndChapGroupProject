package maze;

/**
 * If chap steps on this tile help message will be displayed.
 */
public class HelpTile extends Tile {
	private String colour = "?";
	
    public HelpTile(int row, int col) {
        super(true,row, col);
        super.setIcon("images/dungeon/help.png");
    }

	@Override
	public String getColour() {
		return colour;
	}

	public String toString(){
		return "?";
	}

}
