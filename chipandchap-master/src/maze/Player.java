package maze;

import application.ChipAndChap;
import maze.*;
import renderer.Renderer;

import javax.imageio.ImageIO;
import javax.swing.*;

import application.ChipAndChap.Direction;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class represents the player "Chap".
 */

public class Player {

    private int row;
    private int col;

    private Tile playerTile;
    private Renderer render;
    private Image icon;
    public ArrayList<Item> inventory = new ArrayList<Item>(); //Temporarily public, add a
    private String imageFileName;
    private int treasureCount;
    /**
     * Player constructor which includes the tile that the player is on.
     *
     * @param tile the tile that the player is on
     */
    public Player(Tile tile, Renderer render) {
        	setIcon("images/knight_default.png"); // need a default image this can change though depending on movement.
        this.playerTile = tile;
        tile.setPlayer(this);
        this.row = tile.getRow();
        this.col = tile.getCol();
        this.render = render;
    }

    /**
     * Setting the icon of the player, this is used to represent what the player
     * looks like.
     *
     * @param filename
     */

    void setIcon(String filename) {
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
     * Returns the tile that the player is on.
     *
     * @return
     */
    public Tile getPlayerTile() {
        return this.playerTile;
    }

    /**
     * Sets a new tile for the player
     *
     * @param tile represents the new tile
     */
    public void setPlayerTile(Tile tile) {
        playerTile.setPlayer(null);
        this.playerTile = tile;
        tile.setPlayer(this);
        this.row = tile.getRow();
        this.col = tile.getCol();
    }

    // need to check valid movement in here

    /**
     * Moves the player based on a given direction
     *
     * @param maze      current state of the maze
     * @param direction direction the player is moving
     */
    public void move(Maze maze, Direction direction) {
        if (direction.equals(ChipAndChap.Direction.SOUTH) && row != maze.getNumRows()) {

            if (maze.getTile(row + 1, col) instanceof WallTile || maze.getTile(row + 1, col) instanceof LockedTile) {
                if (maze.getTile(row + 1, col) instanceof LockedTile) {
                    if (checkNextTile(maze, row + 1, col)) {
                        this.setPlayerTile(maze.getTile(row + 1, col)); // move
                        setIcon("images/knight_down.png");  //down
                    }
                }
            } else {
                this.setPlayerTile(maze.getTile(row + 1, col));
                checkCurrentTile(maze);
                setIcon("images/knight_down.png");  //down
            }
        }

        if (direction.equals(ChipAndChap.Direction.NORTH) && row != 0) {
            if (maze.getTile(row - 1, col) instanceof WallTile || maze.getTile(row - 1, col) instanceof LockedTile) {
                if (maze.getTile(row - 1, col) instanceof LockedTile) {
                    if (checkNextTile(maze, row - 1, col)) {
                        this.setPlayerTile(maze.getTile(row - 1, col)); // move
                        setIcon("images/knight_up.png");  //up
                    }
                }
            } else {
                this.setPlayerTile(maze.getTile(row - 1, col));
                setIcon("images/knight_up.png");  //up
                checkCurrentTile(maze);
            }
        }

        if (direction.equals(ChipAndChap.Direction.EAST) && col != maze.getNumCols()) {

            if (maze.getTile(row, col + 1) instanceof WallTile || maze.getTile(row, col + 1) instanceof LockedTile) {
                if (maze.getTile(row, col + 1) instanceof LockedTile) {
                    if (checkNextTile(maze, row, col + 1)) {
                        this.setPlayerTile(maze.getTile(row, col + 1)); // move
                        setIcon("images/knight_right.png");  //right
                    }
                }
            } else {
                this.setPlayerTile(maze.getTile(row, col + 1));
                setIcon("images/knight_right.png");  //right
                checkCurrentTile(maze);
            }
        }

        if (direction.equals(ChipAndChap.Direction.WEST) && col != 0) {

            if (maze.getTile(row, col - 1) instanceof WallTile || maze.getTile(row, col - 1) instanceof LockedTile) {
                if (maze.getTile(row, col - 1) instanceof LockedTile) {
                    if (checkNextTile(maze, row, col - 1)) {
                        this.setPlayerTile(maze.getTile(row, col - 1)); // move
                       setIcon("images/knight_left.png");  //left
                    }
                }
            } else {
                this.setPlayerTile(maze.getTile(row, col - 1));
                checkCurrentTile(maze);
                setIcon("images/knight_left.png");  //right
            }
        }

     if(playerTile.hasMonster()){
         JOptionPane.showMessageDialog(null, "You Lost! Monster ate you, nom nom.");
         ChipAndChap.endGame();
     }
    }

    /**
     * Returns x value of the tile the player is on
     *
     * @return x value of the tile the player is on
     */
    public int getX() {
        return playerTile.getX();
    }

    /**
     * Returns y value of the tile the player is on
     *
     * @return y value of the tile the player is on
     */
    public int getY() {
        return playerTile.getY();
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
     * A method which checks specifically what the tile is AFTER MOVEMENT. IE does
     * the tile contain a key? If true then pick the key up and place in inventory.
     * Then change the tile to a 'FreeTile' through the maze class.
     *
     * @param maze used to check the location of the player
     */
    private void checkCurrentTile(Maze maze) {
        Tile tileToCheck = maze.getTile(this.getRow(), this.getCol());
        //System.out.println("Current tile " + tileToCheck.getColour());
        render.setHelp(false);
        //bring up a popup for the help
        if (tileToCheck.getColour().equals("?")) {
        	render.setHelp(true);
        } else if (tileToCheck.getColour().equals("e")) {
            ChipAndChap.finishLevel();

        } else if (tileToCheck instanceof ItemTile) {

            // b is a blue key which can be picked up and unlock a door with the letter z.
            // r is a red key which can be picked up and unlock a door with the letter y.
            // g is a green key which can be picked up and unlock a door with the letter x.
            // t is treasure which can be picked up
            if (tileToCheck.getColour().equals("t")) {
                Treasure treasure = new Treasure(tileToCheck.getIcon());

                treasureCount++;
                maze.setTile(new maze.FreeTile(row, col), row, col);
            } else {
                Key key = new Key(tileToCheck.getColour(), tileToCheck.getIcon());
                key.setImageFileName(tileToCheck.getImageFileName());
                getInventory().add(key);
                switch (tileToCheck.getColour()) {
                    case "t":
                        treasureCount++;
                        maze.setTile(new maze.FreeTile(row, col), row, col);
                        break;

                }
                maze.setTile(new FreeTile(row, col), row, col);
            }

        for (Item k : getInventory()) {
            System.out.println("Inventory item: " + k.toString());
        
        }
        }
    }

    /**
     * Check the next tile a player wants to move to. Used to open doors by checking
     * if the player has the correct key if they do then door gets replaced with a
     * 'FreeTile' and key is removed from inventory.
     *
     * @param maze maze player is in
     * @param row  row which player wants to move to
     * @param col  column what player wants to move to
     * @return true if there is something to unlock
     */
    private boolean checkNextTile(Maze maze, int row, int col) {
        Tile tileToCheck = maze.getTile(row, col);

        if (tileToCheck instanceof LockedTile) {
            System.out.println(tileToCheck.getColour());
            LockedTile lock = (LockedTile) tileToCheck;
            String lockColour = lock.getColour();

            if (lockColour.equalsIgnoreCase("r")) {
                if (correctKey("r")) {
                    maze.setTile(new FreeTile(row, col), row, col);
                    return true;
                }
            } else if (lockColour.equalsIgnoreCase("g")) {
                if (correctKey("g")) {
                    maze.setTile(new FreeTile(row, col), row, col);
                    return true;
                }
            } else if (lockColour.equalsIgnoreCase("b")) {
                if (correctKey("b")) {
                    maze.setTile(new FreeTile(row, col), row, col);
                    return true;
                }
            } else if (tileToCheck.getColour().equalsIgnoreCase("d")) {
                System.out.println(checkTreasure());
                if (checkTreasure()) {
                    maze.setTile(new FreeTile(row, col), row, col);
                    return true;
                }
            }else if(tileToCheck.getColour().equalsIgnoreCase("l")) {
            	if(correctKey("u")) {  //technically not a key but boots but functions the same
                    return true;
            	}
            }else if(tileToCheck.getColour().equalsIgnoreCase("v")) {
            	if(correctKey("q")) {  //technically not a key but boots but functions the same
                    return true;
            	}
            }
        }

        return false;
    } 

	/**
     * Checks that the door a player wishes to go through has the matching
     * key in the players inventory. Removes the key from the inventory if its used.
     * If the item we are checking is boots we don't remove the boots from the inventory 
     * after they have been used.
     *
     * @param colour Colour of the door that the player wants to pass.
     * @return true if key is in inventory false otherwise.
     */
    private boolean correctKey(String colour) {
        for (Item i : getInventory()) {
            if (i instanceof Key && i.getColour().equalsIgnoreCase(colour)) {
            	if(i.getColour().equalsIgnoreCase("u") || i.getColour().equalsIgnoreCase("q")) {
            		 System.out.println("Don't remove boots");
            	}else {
            		getInventory().remove(i);
            	}
               
                return true;
            }
        }
        return false;
    }

    /**
     * Checks the player has the correct amount of treasure in order to pass the
     * locked door.
     *
     * @return true if the player has enough treasure. False otherwise.
     */
    // Depending on the level the player is on the amount of treasure will change
    // add a way to get the level player is on to check the amount of treasure
    // required.
    private boolean checkTreasure() {
        int amountRequired = ChipAndChap.getCurrentLevel().getTreasure();
        System.out.println(treasureCount + " "  + amountRequired);
        if (treasureCount >= amountRequired) {
            return true;
        }

        return false;
    }

    public int getTreasureCount() {
        return treasureCount;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void addToInventory(Item item){
        inventory.add(item);
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setTreasureCount(int treasureCount) {
        this.treasureCount = treasureCount;
    }
}
