package persistence;

import application.ChipAndChap;
import maze.Monster;
import maze.Player;
import maze.*;
import renderer.Renderer;

import javax.imageio.ImageIO;
import javax.json.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Load's a state from a JSON File.
 */
public class StateLoader {
    private File stateFile;
    private Renderer renderer;

    /**
     * Constructor for StateLoader
     * @param stateFile the file to save the State to.
     */
    public StateLoader(File stateFile){
        this.stateFile = stateFile;
        renderer = new Renderer();
    }

    /**
     * Load a State.
     * @return a new game with the loaded State.
     */
    public ChipAndChap loadState(){

        //Reading the JSON file into a String
        StringReader stringReader = null;
        try {
            //Using byte data to do so.
            FileInputStream inputStream = new FileInputStream(stateFile);
            byte[] data = new byte[(int) stateFile.length()];
            inputStream.read(data);
            stringReader = new StringReader(new String(data, "UTF-8"));
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(stringReader == null){
            return null;
        }

        //Reading JSON file
        JsonReader jsonReader = Json.createReader(stringReader);

        //Get root object (parent node)
        JsonObject root = jsonReader.readObject();

        //Get each required component to load the state
        JsonObject mazeJson = root.getJsonObject("Maze");
        Maze maze = loadMaze(mazeJson);

        JsonObject playerJson = root.getJsonObject("Player");
        Player player = loadPlayer(playerJson, maze);

        JsonObject gameInfo = root.getJsonObject("Game Info");
        int level = gameInfo.getInt("Level Number");
        int timeLeft = gameInfo.getInt("Time Left");

        //Create a new game with the loaded components.
        return new ChipAndChap(player, maze, renderer, level, timeLeft);
    }

    /**
     * Load the player from the specified JsonObject.
     * @param playerJson the json object holding the Player info.
     * @param maze the pre loaded maze.
     * @return a Player object with all the info of the last saved state.
     */
    private Player loadPlayer(JsonObject playerJson, Maze maze){
        int playerRow = playerJson.getInt("Row");
        int playerCol = playerJson.getInt("Col");

        Tile playerTile = maze.getTile(playerRow, playerCol);

        Player player = new Player(playerTile, renderer);

        for(Item item : loadInventory(playerJson)){
            player.addToInventory(item);
        }
        System.out.println(player.getInventory());

        player.setTreasureCount(playerJson.getInt("Treasure"));
        return player;
    }

    /**
     * Get a list of the Inventory from the specified Json player object.
     * @param playerJson the JsonObjct to get all the required info.
     * @return a list containing all the Items in the Players inventory.
     */
    private List<Item> loadInventory(JsonObject playerJson){
        JsonArray inventoryJson = playerJson.getJsonArray("Inventory");
        List<Item> inventory = new ArrayList<>();
        //Go through the JsonArray
        for(int i = 0; i < inventoryJson.size(); i++){
            JsonObject currentItemJson = inventoryJson.getJsonObject(i);
            if(currentItemJson.getString("Colour").equals("t")){
                Treasure treasure = new Treasure();
                inventory.add(treasure);
            } else {
                String itemImageFileName = currentItemJson.getString("Image");
                Image icon = null; //Load the Image
                try {
                    icon = ImageIO.read(new File(itemImageFileName));
                    icon = icon.getScaledInstance(Renderer.TILE_SIZE, Renderer.TILE_SIZE, Image.SCALE_DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //Crate the item, set its file name then add it to the lsit.
                Key item = new Key(currentItemJson.getString("Colour"), icon);
                item.setImageFileName(itemImageFileName);
                inventory.add(item);
            }
        }
        return inventory;
    }

    /**
     * Load a maze from the specified JsonObject.
     * @param mazeJson the Json object to get all the required info from.
     * @return a Maze object with all the information from the previous state.
     */
    private Maze loadMaze(JsonObject mazeJson){
        Maze maze = new Maze(
                mazeJson.getInt("Num Rows"),
                mazeJson.getInt("Num Cols"),
                renderer, true);
        //Get the array that contains all the info about each row.
        JsonArray tileRows = mazeJson.getJsonArray("Tiles");

        for(int r = 0; r < maze.getNumRows(); r++){
            //Get the array of the current row which has all the info about each Tile in the row.
            JsonArray currentRowJson = tileRows.getJsonArray(r);
            for(int c = 0; c < maze.getNumCols(); c++){
                JsonObject currentTileJson = currentRowJson.get(c).asJsonObject();

                String type = currentTileJson.getString("Type");
                int row = currentTileJson.getInt("Row");
                int col = currentTileJson.getInt("Col");
                switch (type) { //Create the Tile depending on what type of Tile it is.
                    case "WallTile":
                        maze.setTile(new WallTile(currentTileJson.getString("Color"), row, col), row, col);
                        break;
                    case "LockedTile":
                        maze.setTile(new LockedTile(currentTileJson.getString("Color"), row, col), row, col);
                        break;
                    case "ItemTile":
                        boolean hasKey = currentTileJson.getBoolean("Has Key");
                        maze.setTile(new ItemTile(hasKey, currentTileJson.getString("Color"), row, col), row, col);
                        break;
                    case "HelpTile":
                        maze.setTile(new HelpTile(row, col), row, col);
                        break;
                    case "FreeTile":
                        maze.setTile(new FreeTile(row, col), row, col);
                        break;
                    case "ExitTile":
                        maze.setTile(new ExitTile(row, col), row, col);
                        break;
                }

            }
        }

        //Load all the Monsters
        for(Monster m : loadMonsters(mazeJson, maze)){
            maze.addMonsterToMaze(m);
        }

        return maze;
    }

    /**
     * Loads all the monsters from the specified Json Object.
     * @param mazeJson the Json object we are extracting info from.
     * @param maze the preloaded maze object, needed to get the Tile for the Monster
     * @return a List of all the Monsters with the info of the previous state.
     */
    private List<Monster> loadMonsters(JsonObject mazeJson, Maze maze){
        JsonArray monstersJson = mazeJson.getJsonArray("Monsters");
        List<Monster> monsters = new ArrayList<>();
        for(int i = 0; i < monstersJson.size(); i++){
            JsonObject currentMonsterJson = monstersJson.getJsonObject(i);
            int row = currentMonsterJson.getInt("Row");
            int col = currentMonsterJson.getInt("Col");
            int stepsPerMove = currentMonsterJson.getInt("Steps Per Move");
            int stepsLeft = currentMonsterJson.getInt("Steps Left");

            Monster currentMonster = new Monster(maze.getTile(row, col), stepsPerMove);
            currentMonster.setNextDirection(currentMonsterJson.getString("Next Direction"));
            currentMonster.setStepsLeft(stepsLeft);
            monsters.add(currentMonster);
        }
        return monsters;
    }

}
