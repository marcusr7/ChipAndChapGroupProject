package maze;


import java.awt.*;

/**
 * Part of a wall, player cannot move onto this Tile.
 * The 'Walltile' has a colour which changes the png image it will have.
 * w is a standard wall. 
 */
public class WallTile extends Tile {
	private String colour = "w";

    public WallTile(String colour, int row, int col){
        super(false, row, col);
        this.colour = colour;
        if(colour.equalsIgnoreCase("w")) {
        	 super.setIcon("images/dungeon/standard_wall.png");
        }else if(colour.equalsIgnoreCase("p")) {  //right wall
        	 super.setIcon("images/dungeon/wall_corner_right.png");
        }else if(colour.equalsIgnoreCase("o")) {
        	super.setIcon("images/dungeon/wall_corner_left.png");	//left wall
        }else if(colour.equalsIgnoreCase("i")) {
        	super.setIcon("images/dungeon/wall_corner_top_left.png"); //top
        }
    }

	@Override
	public String getColour() {
		return colour;
	}

	public String toString(){
    	return getColour();
	}

}
