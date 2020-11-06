package persistence;

import application.ChipAndChap;
import maze.Monster;
import maze.Player;
import maze.*;

import javax.json.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Writes the current state of the game to a JSON file.
 */
public class StateSaver {
    private ChipAndChap game;

    /**
     * Constructor that stores the game to save
     * @param game ChipAndChap object to write to a file
     */
    public StateSaver(ChipAndChap game) {
        this.game = game;

    }

    /**
     * Saves the level to load in a file
     * @param level int indicating which level to resume
     */
    public void saveAsLevelResumeState(int level){
        JsonObject root = Json.createObjectBuilder()
                .add("Resume", level).build();

        File jsonFile = new File("launchInfo.json");

        //Writing the root object to the file
        try {
            FileWriter jsonFileWriter = new FileWriter(jsonFile);
            jsonFileWriter.write(root.toString());
            jsonFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves a launch information file with a default value
     */
    public void saveAsResumeState(){
        JsonObject root = Json.createObjectBuilder()
                .add("Resume", -1).build();

        File jsonFile = new File("launchInfo.json");

        //Writing the root object to the file
        try {
            FileWriter jsonFileWriter = new FileWriter(jsonFile);
            jsonFileWriter.write(root.toString());
            jsonFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Save the state of the Game to a JSON file.
     */
    public void saveState(){
        //Get each individual object saved into a JsonObject
        JsonObject playerJson = getPlayerJson(game.getPlayer());
        JsonObject mazeJson = getMazeJson(game.getMaze());
        JsonObject gameInfoJson = getGameInfoJson();

        //This is the root JsonObject (or node), the player, maze and gameInfo
        //json objects will be children of the root.
        JsonObject root = Json.createObjectBuilder().add("Player", playerJson)
                .add("Maze", mazeJson)
                .add("Game Info", gameInfoJson).build();

        System.out.println(root.toString());
        //File to save the Json info to.
        File jsonFile = new File("LastSavedState.json");

        //Writing the root object to the file
        try {
            FileWriter jsonFileWriter = new FileWriter(jsonFile);
            jsonFileWriter.write(root.toString());
            jsonFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonObject launchJson = Json.createObjectBuilder()
                .add("Resume", 11).build();


        File launchFile = new File("launchInfo.json");

        try {
            FileWriter jsonFileWriter = new FileWriter(launchFile);
            jsonFileWriter.write(launchJson.toString());
            jsonFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get the info for the game info (level number, time left and treasure count)
     * and store them into a JsonObject.
     * @return a JsonObject with the Game Info
     */
    private JsonObject getGameInfoJson(){
        JsonObject gameInfoJson = Json.createObjectBuilder()
                .add("Level Number", game.getCurrentLevel().getLevelNumber())
                .add("Time Left", game.getRenderer().getTimeLeft())
                .add("Treasure Count", game.getRenderer().getTreasureCount()).build();

        return gameInfoJson;
    }

    /**
     * Get all required info about the Maze and store them into a JsonObject.
     * @param maze the maze we are extracting information from.
     * @return a JsonObject with the Maze info
     */
    private JsonObject getMazeJson(Maze maze){
        //Call each method to get the info for the maze.
        JsonObject mazeJson = Json.createObjectBuilder()
                .add("Num Rows", maze.getNumRows())
                .add("Num Cols", maze.getNumCols())
                .add("Tiles", getAllTilesJson(maze))
                .add("Monsters", getAllMonstersJson(maze)).build();
        return mazeJson;
    }

    /**
     * Get all the info about each monster in the Maze.
     * @param maze the maze we are extracting information from.
     * @return a JsonArray with info about each Monster
     */
    private JsonArray getAllMonstersJson(Maze maze){
        JsonArrayBuilder monstersBuilder = Json.createArrayBuilder();

        for(Monster m : maze.getMonstersInMaze()){
            JsonObject currentMonsterJson = Json.createObjectBuilder()
                    .add("Row", m.getRow())
                    .add("Col", m.getCol())
                    .add("Steps Per Move", m.getStepsPerMove())
                    .add("Steps Left", m.getStepsLeft())
                    .add("Next Direction", m.getNextDirection().toString())
                    .add("Image", m.getImageFileName()).build();
            monstersBuilder.add(currentMonsterJson);
        }
        return monstersBuilder.build();
    }

    /**
     * Get all the info about each Tile in the Maze.
     * @param maze the maze we are extracting information from.
     * @return a JsonArray with info about each Tile
     */
    private JsonArray getAllTilesJson(Maze maze){
        JsonArrayBuilder tilesBuilder = Json.createArrayBuilder();

        for(int r = 0; r < maze.getNumRows(); r++){
            JsonArrayBuilder currentRowBuilder = Json.createArrayBuilder();
            for(int c = 0; c < maze.getNumCols(); c++){
                Tile currentTile = maze.getTile(r, c);

                JsonObjectBuilder currentTileJson = Json.createObjectBuilder()
                        .add("Type", currentTile.getClass().getSimpleName())
                        .add("Row", currentTile.getRow())
                        .add("Col", currentTile.getCol())
                        .add("isAccessible", currentTile.isAccessible())
                        .add("Image", currentTile.getImageFileName());


                if(currentTile instanceof WallTile || currentTile instanceof LockedTile
                        || currentTile instanceof ItemTile){
                    currentTileJson.add("Color", currentTile.getColour());
                    if(currentTile instanceof ItemTile){
                        currentTileJson.add("Has Key", ((ItemTile) currentTile).hasKey());
                    }
                }

                currentRowBuilder.add(currentTileJson.build());

            }
            tilesBuilder.add(currentRowBuilder.build());
        }

        return tilesBuilder.build();
    }

    /**
     * Get all the info about the Player and store it into a JsonObject.
     * @param player the player we are extracting information from.
     * @return a JsonObject with all the info about the Player.
     */
    private JsonObject getPlayerJson(Player player){
        int treasure = player.getTreasureCount();

        JsonObject playerJson = Json.createObjectBuilder()
                //Players position
                .add("Row", player.getRow())
                .add("Col", player.getCol())
                //Players Image
                .add("Image", player.getImageFileName())
                //Players Inventory
                .add("Treasure", player.getTreasureCount())
                .add("Inventory", getInventoryJson(player.getInventory())).build();

        return playerJson;
    }

    /**
     * Get all the info about each Item in the Players inventory.
     * @param inventory the inventory we are extrating information from.
     * @return a JsonArray with all the info of each item in the Playrers inventory.
     */
    private JsonArray getInventoryJson(List<Item> inventory){
        JsonArrayBuilder inventoryJsonBuilder = Json.createArrayBuilder();

        for(Item item :inventory){
            if(item instanceof Treasure){
                JsonObject itemJson = Json.createObjectBuilder()
                        .add("Colour", item.getColour()).build();
                inventoryJsonBuilder.add(itemJson);
            } else {
                JsonObject itemJson = Json.createObjectBuilder()
                        .add("Colour", item.getColour())
                        .add("Image", item.getImageFileName()).build();
                inventoryJsonBuilder.add(itemJson);
            }

        }
        return inventoryJsonBuilder.build();
    }
}
