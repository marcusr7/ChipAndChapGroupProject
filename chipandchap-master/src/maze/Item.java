package maze;

import javax.swing.*;
import java.awt.*;

/**
 * Represents an Item that cna be in the Players
 * inventory, i.e a Key or a piece of Treasure
 */
public abstract class Item{
	
    private String colour;
    private Image icon;

    private String imageFileName;

    public abstract String getColour();

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }
}
