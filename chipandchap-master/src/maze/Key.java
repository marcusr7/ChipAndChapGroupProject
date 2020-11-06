package maze;

import java.awt.*;

public class Key extends Item {
	
	private String colour;
	private String type;
	private String imageFileName;

	public Key(String colour) {
		super();
		this.colour = colour;
		this.type = "key";
	}

	public Key(String colour, Image icon) {
		this(colour);
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
	public String getImageFileName() {
		return imageFileName;
	}

	@Override
	public void setImageFileName(String fileName) {
		imageFileName = fileName;
	}

	@Override
	public String toString(){
		return colour + " " + type;
	}


}
