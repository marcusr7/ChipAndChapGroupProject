package maze;

import java.awt.*;

public class Treasure extends Item {
	private String colour;
	private String type;
	
	public Treasure() {
        super();
		this.colour = "t";
		this.type = "treasure";
	}

	public Treasure(Image icon) {
		this();
		this.setIcon(icon);
	}
	
	@Override
	public String getColour() {
		return colour;
	}
	
	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return getType();
	}
}
