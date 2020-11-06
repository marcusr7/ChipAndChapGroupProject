package maze;

import java.awt.*;

/**
 * Tile with an Item on it, i.e a Key, or Treasure.
 */
public class ItemTile extends Tile{
    private Item item;
    private String colour;
    private boolean hasKey = false;

    public ItemTile(boolean hasKey, String color, int row, int col){
        super(true, row, col);
        this.hasKey = hasKey;
        if (hasKey) {
            if(color.equalsIgnoreCase("r")){
                super.setIcon("images/dungeon/red_key.png");
                colour = "r";
            }else if(color.equalsIgnoreCase("g")){
                super.setIcon("images/dungeon/green_key.png");
                colour = "g";
            }else if(color.equalsIgnoreCase("b")){
                super.setIcon("images/dungeon/blue_key.png");
                colour = "b";
            }

        } else if(color.equalsIgnoreCase("t")){
            super.setIcon("images/dungeon/treasure.png");
            colour = "t";  //no colour but avoid a null pointer.
        }else if(color.equalsIgnoreCase("q")) {
        	super.setIcon("images/dungeon/ice_boots_22.png");
        	colour = "q";
        }else if(color.equalsIgnoreCase("u")) {
        	super.setIcon("images/dungeon/fire_boots_22.png");
        	colour = "u";
        }
    }

/**
     * Returns the colour of this Item. EG a blue key returns 'b'
     * @return return a string which represents a colour of the Item.
     */
    public String getColour(){
        return colour;
    }

    public boolean hasKey() {
        return hasKey;
    }

    public String toString(){
        return getColour();
    }
}
