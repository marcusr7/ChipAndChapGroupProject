package maze;

import application.ChipAndChap;
import maze.Maze;
import maze.Tile;
import renderer.Renderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Monster class that moves around in the game,
 * if the Player hits this Monster, they lose.
 */
public class Monster {
    private int row;
    private int col;
    private int stepsLeft;
    private int stepsPerMove;
    private Image icon;

    private Tile monsterTile;

    private ChipAndChap.Direction nextDirection;

    private String imageFileName;

    public Monster(Tile startTile, int stepsPerMove){
        setIcon("images/dungeon/zombie.png");
        this.monsterTile = startTile;
        this.stepsPerMove = stepsPerMove;
        stepsLeft = stepsPerMove;
        this.row = monsterTile.getRow();
        this.col = monsterTile.getCol();
    }


    /**
     * Returns x value of the tile the player is on
     *
     * @return x value of the tile the player is on
     */
    public int getX() {
        return monsterTile.getX();
    }

    /**
     * Returns y value of the tile the player is on
     *
     * @return y value of the tile the player is on
     */
    public int getY() {
        return monsterTile.getY();
    }

    /**
     * Get the row of the Player
     *
     * @return the row of the Player
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the col of the Player
     *
     * @return the col of the Player
     */
    public int getCol() {
        return col;
    }

    /**
     * Setting the icon of the player, this is used to represent what the player
     * looks like.
     *
     * @param filename name of the image file
     */

    public void setIcon(String filename) {
        try {
            icon = ImageIO.read(new File(filename));
            icon = icon.getScaledInstance(Renderer.TILE_SIZE, Renderer.TILE_SIZE, Image.SCALE_DEFAULT);
            imageFileName = filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the icon of the Player.
     * @return the icon of the player.
     */
    public Image getIcon(){
        return icon;
    }


    /**
     * Sets a new tile for the Monster
     *
     * @param tile represents the new tile
     */
    public void setMonsterTile(Tile tile) {
        this.monsterTile.setMonster(null);
        this.monsterTile = tile;
        this.row = tile.getRow();
        this.col = tile.getCol();
        tile.setMonster(this);
    }

    /**
     * Get the Tile that the Monster is on.
     */
    public Tile getMonsterTile() {
        return monsterTile;
    }

    /**
     * Moves the monster depending on its next move.
     */
    public void doNextMove(Maze maze){
        if(nextDirection == ChipAndChap.Direction.SOUTH) {
            if (maze.getTile(row + 1, col) != null) {
                setMonsterTile(maze.getTile(row + 1, col));
                stepsLeft--;
                if(stepsLeft == 0){
                    nextDirection = ChipAndChap.Direction.WEST;
                    stepsLeft = stepsPerMove;}
            }
        } else if(nextDirection == ChipAndChap.Direction.WEST){
            if(maze.getTile(row, col - 1) != null) {
                setMonsterTile(maze.getTile(row, col - 1));
                stepsLeft--;
                if(stepsLeft == 0){
                    nextDirection = ChipAndChap.Direction.NORTH;
                    stepsLeft = stepsPerMove;}
            }
        } else if(nextDirection == ChipAndChap.Direction.NORTH){
            if(maze.getTile(row - 1, col) != null) {
                setMonsterTile(maze.getTile(row - 1, col));
                stepsLeft--;
                if(stepsLeft == 0){
                    nextDirection = ChipAndChap.Direction.EAST;
                    stepsLeft = stepsPerMove;}
            }
        } else { //Next direction == east
            if(maze.getTile(row, col + 1) != null) {
                setMonsterTile(maze.getTile(row, col + 1));
                stepsLeft--;
                if(stepsLeft == 0){
                    nextDirection = ChipAndChap.Direction.SOUTH;
                    stepsLeft = stepsPerMove;}
            }
        }
        if(monsterTile.hasPlayer()){
            JOptionPane.showMessageDialog(null, "You Lost! Monster ate you, nom nom.");
            ChipAndChap.endGame();
        }
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public ChipAndChap.Direction getNextDirection() {
        return nextDirection;
    }

    public void setNextDirection(String nextDir) {
        if(nextDir.equals("WEST")){
            nextDirection = ChipAndChap.Direction.WEST;
        } else if(nextDir.equals("EAST")){
            nextDirection = ChipAndChap.Direction.EAST;
        } else if(nextDir.equals("SOUTH")){
            nextDirection = ChipAndChap.Direction.SOUTH;
        } else if(nextDir.equals("NORTH")){
            nextDirection = ChipAndChap.Direction.NORTH;
        }
    }

    public int getStepsPerMove() {
        return stepsPerMove;
    }

    public int getStepsLeft() {
        return stepsLeft;
    }

    public void setStepsLeft(int stepsLeft) {
        this.stepsLeft = stepsLeft;
    }
}
