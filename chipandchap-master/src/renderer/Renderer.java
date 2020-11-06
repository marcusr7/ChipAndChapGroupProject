package renderer;

import application.ChipAndChap;
import maze.Monster;
import maze.Player;
import maze.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Draws all the GUI components and icons.
 */
public class Renderer {

    //Need to make it respond to window Size
    public final static int TILE_SIZE = 40;
    public final static int TOP_PADDING = 20;
    public final static int LEFT_PADDING = 20;

    private final int RENDER_SIZE = 4;

    public static int renderStartRow = 0;
    public static int renderStartCol = 0;

    public static int renderEndRow = 0;
    public static int renderEndCol = 0;

    private int timeLeft = 100;
    private boolean help = false;

    private int treasureCount;

    private Color brown = new Color(67, 53, 48);

    /**
     * Draws each Tile onto the screen.
     * @param maze the maze to render
     * @param g the graphics Object to render on
     */
    public void render(Maze maze, Player p, Graphics g){
        //Render rows and cols. I.e where to start rendering in the board
        // and where to finish
        int rectWidth = 650;
        int rectHeight = 500;

        g.setColor(brown);
        g.fillRect(0, 0, rectWidth, rectHeight);

        renderStartRow = Math.max(0, p.getRow() - RENDER_SIZE);
        renderStartCol = Math.max(0, p.getCol() - RENDER_SIZE);

        renderEndRow = Math.min(maze.getNumRows(), p.getRow() + RENDER_SIZE);
        renderEndCol = Math.min(maze.getNumCols(), p.getCol() + RENDER_SIZE);

        if(renderStartRow == 0){
            renderEndRow = 8;
        }

        if(renderStartCol == 0){
            renderEndCol = 8;
        }

        if(renderEndRow == maze.getNumRows()){
            renderStartRow = renderEndRow - 9;
        }

        if(renderEndCol == maze.getNumCols()){
            renderStartCol = renderEndCol - 9;
        }

        int treasureCount = 0;

       // System.out.println("End row: " + renderEndRow);
        for(int r = 0; r < maze.getNumRows(); r++){
            for(int c = 0; c < maze.getNumCols(); c++){
                Tile currentTile = maze.getTile(r, c);
                if(currentTile == null){
                    continue;
                }
                else if (currentTile.getColour().equals("t")) {
                    treasureCount++;
                }
                drawTile(currentTile, g);
                //currentTile.draw(g);
            }
        }

        drawPlayer(p, g);
        for(Monster m : maze.getMonstersInMaze()){
            drawMonster(m, g);
        }

        drawInfoPanel(p, treasureCount, g);

        if(!ChipAndChap.isRunning()){
            Color rectColour = new Color(101, 67, 33, 200);
            g.setColor(rectColour);
            g.fillRect(0, 0, ChipAndChap.getFrame().getWidth(), ChipAndChap.getFrame().getHeight());
            Font font = new Font("Time New Roman", Font.BOLD,80);
            FontMetrics fontSizeCalc = g.getFontMetrics(font);

            g.setFont(font);

            int titleX = 0 + ChipAndChap.getFrame().getWidth()/2 - fontSizeCalc.stringWidth("Paused")/2;

            g.setColor(Color.yellow);
            g.drawString("Paused", titleX, ChipAndChap.getFrame().getHeight()/2);
        }
    }

    /**
     * Draw a Tile onto the screen.
     * @param t the tile to draw
     * @param g Graphics object used to draw on the GUI
     */
    private void drawTile(Tile t, Graphics g){
        if(t.withinRenderWindow()) {
            g.drawImage(t.getIcon(), t.getX(), t.getY(), null);
        }
    }

    /**
     * Draws the Player onto the screen.
     * @param p the Player object
     * @param g Graphics object used to draw on the GUI
     */
    private void drawPlayer(Player p, Graphics g){
        g.drawImage(p.getIcon(), p.getX(), p.getY(), null);
    }

    /**
     * Draws the Monster onto the screen.
     * @param m the Monster object
     * @param g Graphics object used to draw on the GUI
     */
    private void drawMonster(Monster m, Graphics g){

        if(m.getMonsterTile().withinRenderWindow()){
            g.drawImage(m.getIcon(), m.getX(), m.getY(), null);
        }
    }

    /**
     * Draws the information panel that contains the level count, timer, chips left, and inventory items
     * @param p The player whose inventory is to be drawn
     * @param treasureCount Count of treasure items left on the level
     * @param g Graphics object used to draw on the GUI
     */
	public void drawInfoPanel(Player p, int treasureCount, Graphics g) {
		int containerX = 400; // Position of the containing rectangle
		int containerY = 20;

		//g.drawRect(containerX, containerY, 180, 360);
        g.setColor(brown);
        g.fillRect(containerX, containerY, 180, 360);
        g.setColor(Color.YELLOW);
        g.drawRect(containerX, containerY, 180, 360);

		int counterOffsetX = containerX; // X offset of first counter
		int counterOffsetY = 50; // Y offset of first counter

		if (help) {
			//draws the help instead of the inventory
			//drawNumberedPanel("HELP", ChipAndChap.getCurrentLevel().getHelp(), counterOffsetX, counterOffsetY + 180, 180, g);
            g.fillRect(containerX, containerY + 270, 180, 90);
            g.setColor(brown);
            g.setFont(new Font("Times New Roman", Font.BOLD, 12));
			popupHelp("HELP", ChipAndChap.getCurrentLevel().getHelp(), counterOffsetX + 5, counterOffsetY + 260, 180, g);
		} else {
			drawInventory(p.inventory, g);
		}
		drawNumberedPanel("LEVEL", ChipAndChap.getCurrentLevel().toString(), counterOffsetX, counterOffsetY, 180, g);
		drawNumberedPanel("CHIPS LEFT", "" + treasureCount, counterOffsetX, counterOffsetY + 65, 180, g);
		drawNumberedPanel("TIME LEFT", "" + timeLeft, counterOffsetX, counterOffsetY + 130, 180, g);
	}

    /**
     * Draws a panel with a centred title and a centred numeric value below it
     * @param title String to be drawn
     * @param value Numeric value to be drawn
     * @param xOffset Left x value of the area to center the text in
     * @param y Y position of the panel
     * @param width Size of area to center text in (horizontally)
     * @param g Graphics object used to draw on the GUI
     */
    public void drawNumberedPanel(String title, String value, int xOffset, int y, int width, Graphics g) {
        Font font = new Font("Times New Roman", Font.BOLD,18);
        FontMetrics fontSizeCalc = g.getFontMetrics(font);

        g.setFont(font);
        g.setColor(Color.YELLOW);

        int titleX = xOffset + width/2 - fontSizeCalc.stringWidth(title)/2;
        int scoreX = xOffset + width/2 - fontSizeCalc.stringWidth(value)/2;

        g.drawString(title, titleX, y);
        g.drawString(value, scoreX, y+30);
    }


    /**
     * When a player stands on the '?' tile in game will display help for the level.
     * @param title String to be drawn
     * @param value Numeric value to be drawn
     * @param xOffset Left x value of the area to center the text in
     * @param y Y position of the panel
     * @param width Size of area to center text in (horizontally)
     * @param g Graphics object used to draw on the GUI
     */
    public void popupHelp(String title, String value, int xOffset, int y, int width, Graphics g) {
    	Image icon = null;
		try {
			icon = ImageIO.read(new File("images/dungeon/popup2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	//g.drawImage(icon, xOffset, 150, width, y, null);

		for (String line : value.split("\n")) {
			g.drawString(line, xOffset, y += g.getFontMetrics().getHeight());
		}
    }

    /**
     * Draws the player's inventory onto the GUI
     * @param inventory The items in the player's inventory
     * @param g The graphics object used to draw onto the GUI
     */
    public void drawInventory(ArrayList<Item> inventory, Graphics g) {
        //To fix: If player has inventory full of treasure chests then keys are not drawn in inventory
        int xOffset = 410;
        int yOffset = 290;
        int offset = 0;
        int cellSize = 40;
        int bootCount = 0;

        for (int i = 0; i < 8; i++) {
            int x = xOffset + ((i % 4) * cellSize);
            int y = yOffset + (cellSize * (i / 4));

            g.clearRect(x, y, cellSize, cellSize);
            g.setColor(brown);
            g.fillRect(x, y, cellSize, cellSize);
            g.setColor(Color.YELLOW);
            g.drawRect(x, y, cellSize, cellSize);


            if (inventory.size()-1 >= i) {
                int index = i-offset-bootCount;
                Item item = inventory.get(i);

                if (item instanceof Key && (!item.getColour().equals("q") && !item.getColour().equals("u"))) {
                    g.drawImage(item.getIcon(), xOffset + ((index % 4) * cellSize), yOffset + (cellSize * (index / 4)), null);
                }
                else if (item instanceof Key) {
                    g.drawImage(item.getIcon(), xOffset + bootCount * cellSize, yOffset - 50, null);
                    bootCount++;
                }
                else {
                    offset++;
                }
            }
        }

        //System.out.println("Inventory: " + inventory);
    }

    /**
     * Sets the time left
     * @param numberToDraw value of time left
     */
    public void setTimeLeft(int numberToDraw) {
    	this.timeLeft = numberToDraw;

    }

    /**
     * When a user stands on the '?' it sets the variable to true. False otherwise.
     * @param b boolean which sets the value to true or false.
     */
    public void setHelp(boolean b) {
    	this.help = b;
    }

    /**
     * Returns the treasure count
     * @return int Amount of treasure chests left in maze
     */
    public int getTreasureCount() {
        return treasureCount;
    }

    /**
     * Returns the time left
     * @return int Time left in Maze
     */
    public int getTimeLeft() {
        return timeLeft;
    }
}
