package maze;

import application.ChipAndChap;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Any actors can move freely onto these tiles.
 */
public class FreeTile extends Tile{
	private String colour = "f";
	
    public FreeTile(int row, int col){
        super(true, row, col);
        super.setIcon("images/dungeon/floor_1.png");

    }

	@Override
	public String getColour() {
		return colour;
	}

	public String toString(){
		return "_";
	}
}
