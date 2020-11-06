package maze;

import java.awt.*;

/**
 * Tile with the key lock, player needs to
 * walk on this tile with a Key to access it.
 */
public class LockedTile extends Tile {
    private String colour;

    public LockedTile(String color, int row, int col){  //Color changes which image, ie red green blue
        super(false, row, col);
        if (color.equalsIgnoreCase("r")) {
        	super.setIcon("images/dungeon/red_lock.png"); 
            colour = "r";
        } else if(color.equalsIgnoreCase("b")){
            super.setIcon("images/dungeon/blue_lock.png"); 
            colour = "b";
        }else if(color.equalsIgnoreCase("g")){
        	super.setIcon("images/dungeon/green_lock.png"); 
            colour = "g";
        }else if(color.equalsIgnoreCase("d")) {
        	super.setIcon("images/dungeon/ogre32_1.png");  //different then standard ogre has tile under
            colour = "d";
        }else if(color.equalsIgnoreCase("l")) {
        	super.setIcon("images/dungeon/Lava_1.png");
            colour = "l";
        }else if(color.equalsIgnoreCase("v")) {
        	super.setIcon("images/dungeon/water.png");
            colour = "v";
        }
    }

/**
     * Returns the colour of this Item. EG a blue key returns 'b'
     * @return return a string which represents a colour of the Item.
     */
    public String getColour(){
        return colour;
    }

    public String toString(){
        return getColour();
    }
}
