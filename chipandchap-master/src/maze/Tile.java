package maze;

import renderer.Renderer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Abstract class for all Tiles in the game.
 */
public abstract class Tile {
    private int x, y;
    private int row, col;
    private boolean isAccessible;
    private boolean hasPlayer;
    private Image icon;
    private Player player;
    private String name;
    private String imageFileName;
    private Monster monster;
    /**
     * Tile Constructor
     * @param isAccessible can the player walk on this Tile
     * @param row row of the Tile
     * @param col col of the Tile
     */
    Tile(boolean isAccessible, int row, int col){
        this.isAccessible = isAccessible;
        this.row = row;
        this.col = col;
        hasPlayer = false;
    }

    /**
     * Set's the icon for the player.
     * @param filename name of the image file
     */
    void setIcon(String filename){
        try {
            icon = ImageIO.read(new File(filename));
            icon = icon.getScaledInstance(Renderer.TILE_SIZE, Renderer.TILE_SIZE, Image.SCALE_DEFAULT);
            imageFileName = filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract String getColour();

    /**
     * Checks whether the player can walk on this Tile.
     * @return whether the PLayer can walk on this Tile
     */
    public boolean isAccessible(){
        return isAccessible;
    }


    /**
     * Checks wherther the Tile is withing the render window
     * @return true if it is, false if it is not
     */
    public boolean withinRenderWindow(){
        if(row >= Renderer.renderStartRow && row <= Renderer.renderEndRow){
            if(col >= Renderer.renderStartCol && col <= Renderer.renderEndCol){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns x value of tile.
     * @return x value of tile
     */
    public int getX(){
        int numColsToRender = Renderer.renderEndCol - Renderer.renderStartCol;
        return Renderer.LEFT_PADDING + Math.abs(((Renderer.renderEndCol - col) - numColsToRender) * Renderer.TILE_SIZE);
    }

    /**
     * Returns y value of tile.
     * @return y value of the tile
     */
    public int getY(){
        int numRowsToRender = Renderer.renderEndRow- Renderer.renderStartRow;
        return Renderer.TOP_PADDING + Math.abs(((Renderer.renderEndRow - row) - numRowsToRender) * Renderer.TILE_SIZE);
    }

    /**
     * Sets the hasPlayer boolean.
     * @param player the Player to set on the Tile
     */
    public void setPlayer(Player player){
        this.player = player;
    }

    /**
     * Get the row of the Tile
     * @return the row of the Tile
     */
    public int getRow(){
        return row;
    }

    /**
     * Get the col of the Tile
     * @return the col of the Tile
     */
    public int getCol(){
        return col;
    }

    public Image getIcon() {
        return icon;
    }

    public String getImageFileName(){
        return imageFileName;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public boolean hasMonster() {
        return monster != null;
    }

    public boolean hasPlayer() {
        return player != null;
    }
}
