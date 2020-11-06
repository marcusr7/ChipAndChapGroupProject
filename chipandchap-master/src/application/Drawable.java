package application;

import java.awt.*;

/**
 * Will represent any object that can be drawn onto the Canvas.
 */
public interface Drawable {
    /**
     * Draws the Object onto the Canvas.
     * Coordinates will be specified within the Objects
     * own Class.
     * @param g the Graphics object to render on
     */
    void draw(Graphics g);
}
