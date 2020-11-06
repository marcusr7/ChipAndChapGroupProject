package application;

import maze.*;
import persistence.StateLoader;
import persistence.StateSaver;
import renderer.Renderer;

import org.junit.Test;

import static org.junit.Assert.assertFalse;

import java.io.File;

public class Tests {

    /**
     * Tests the mechanics of the game
     */

	/**
	 * Tests if the game runs correctly without exceptions (will fail if exception is thrown)
	 */
    @Test
    public void gameSetupTest(){
        new ChipAndChap();
    }

    /**
     * Ensures that the player can move left and that the resulting position is correct
     */
    @Test
    public void moveLeft() {
        new ChipAndChap();
        Renderer r = new Renderer();


        Player p = new Player(new FreeTile(14, 20), r);
        Maze m = new Maze(16, 21, r);

        p.move(m, ChipAndChap.Direction.WEST);


        assert (p.getRow() == 14 && p.getCol() == 19);
    }

    /**
     * Ensures that the player can move right and that the resulting position is correct
     */
    @Test
    public void moveRight(){
        new ChipAndChap();
        Renderer r = new Renderer();
        Player p = new Player(new FreeTile(14, 15), r);
        Maze m = new Maze(16, 21, r);

        p.move(m, ChipAndChap.Direction.EAST);

        assert(p.getRow() == 14 && p.getCol() == 16);
    }

    /**
     * Ensures that the player can move up and that the resulting position is correct
     */
    @Test
    public void moveUp(){
        new ChipAndChap();
        Renderer r = new Renderer();
        Player p = new Player(new FreeTile(14, 14), r);
        Maze m = new Maze(16, 21, r);

        p.move(m, ChipAndChap.Direction.NORTH);

        assert(p.getRow() == 13 && p.getCol() == 14);
    }

    /**
     * Ensures that the player can move down and that the resulting position is correct
     */
    @Test
    public void moveDown(){
        new ChipAndChap();
        Renderer r = new Renderer();
        Player p = new Player(new FreeTile(13, 14), r);
        Maze m = new Maze(16,21, r);

        p.move(m, ChipAndChap.Direction.SOUTH);

        assert(p.getRow() == 14 && p.getCol() == 14);
    }

    //b is key
    //t is treasure

    /**
     * Ensures that the player can't move through a wall
     */
    @Test
    public void moveToWall(){
        new ChipAndChap();
        Renderer r = new Renderer();
        Player p = new Player(new FreeTile(14, 20), r);
        Maze m = new Maze(16,21, r);

        p.move(m, ChipAndChap.Direction.SOUTH); //move to the wall
        p.move(m, ChipAndChap.Direction.NORTH);

        assert(p.getRow() == 14 && p.getCol() == 20);
    }




    /**
     * Tests if player game pick up keys
     */
    @Test
    public void obtainKey(){
        new ChipAndChap();
        Renderer r = new Renderer();
        Player p = new Player(new FreeTile(14, 11), r);
        Maze m = new Maze(16,21, r);

        assert(p.inventory.isEmpty());

        p.move(m, ChipAndChap.Direction.WEST);

        Item i = p.inventory.get(0);

        System.out.println(p.getInventory());

        assert(p.inventory.contains(i));
        assertFalse(i instanceof Treasure);
        assert(i instanceof Key);

    }

    /**
     * Tests if player can unlock a door
     */
    @Test
    public void unlockDoor(){
        new ChipAndChap();
        Renderer r = new Renderer();
        Player p = new Player(new FreeTile(9, 16), r);
        Maze m = new Maze(16,21, r);

        p.inventory.add(new Key("b"));
        p.move(m, ChipAndChap.Direction.EAST);

        assert(p.getRow() == 9 && p.getCol() == 17);
    }

    /**
     * Tests if player can open door without a key
     */
    @Test
    public void lockDoor(){
        new ChipAndChap();
        Renderer r = new Renderer();
        Player p = new Player(new FreeTile(9, 16), r);
        Maze m = new Maze(16,21, r);

        p.move(m, ChipAndChap.Direction.EAST);

        assertFalse(p.getRow() == 9 && p.getCol() == 17);

    }

    /**
     * Tests if Maze loads the same way every time
     */
    @Test
    public void checkMazeState(){
        ChipAndChap game1 = new ChipAndChap();
        game1.saveGame();

        ChipAndChap game2 = new ChipAndChap();
        game2.saveGame();

        assert(game1.getMaze().toString().equals(game2.getMaze().toString()));
    }


    /**
     * Tests two different Maze states match if identical moves are made
     */
    @Test
    public void checkMazeState2(){
        ChipAndChap game1 = new ChipAndChap();
        game1.saveGame();

        ChipAndChap game2 = new ChipAndChap();
        game2.saveGame();

        Player p1 = game1.getPlayer();
        Maze m1 = game1.getMaze();
        p1.move(m1, ChipAndChap.Direction.WEST);
        p1.move(m1, ChipAndChap.Direction.WEST);
        p1.move(m1, ChipAndChap.Direction.WEST);

        Player p2 = game1.getPlayer();
        Maze m2 = game1.getMaze();
        p2.move(m2, ChipAndChap.Direction.WEST);
        p2.move(m2, ChipAndChap.Direction.WEST);
        p2.move(m2, ChipAndChap.Direction.WEST);
        assert(game1.getMaze().toString().equals(game2.getMaze().toString()));
    }


    /**
     * Checks if JSON loads correctly
     */
    @Test
    public void checkLoader() {
    	ChipAndChap game = new ChipAndChap();

    	game.saveGame();
    	File file = new File("LastSavedState.json");

    	StateLoader loader = new StateLoader(file);
    	loader.loadState();
    }

    /**
     * Tests if monsters can be moved without crashing
     */
    @Test
    public void checkMonster() {
    	ChipAndChap game = new ChipAndChap(2);
    	System.out.println(ChipAndChap.getCurrentLevel().getLevelNumber());
    	for(int i = 0; i < 4; i++) {
    		game.getMaze().moveMonsters();
    	}
    }

    /**
     * Tests if monster is in correct position
     */
    @Test
    public void monsterPosition() {
    	Tile tile = new FreeTile(5, 3);
    	Monster m = new Monster(tile, 1);

    	m.getX();
    	m.getY();

    	assert (m.getRow() == 5 && m.getCol() == 3);
    }

    /**
     * Tests if monster icon loads
     */
    @Test
    public void monsterIconLoads() {
    	Monster m = new Monster(new FreeTile(5, 3), 1);
    	FreeTile tile = new FreeTile(10, 5);

    	m.toString();

    	m.setMonsterTile(tile);

    	assert(m.getMonsterTile() == tile);
    }


    /**
     * Tests if a treasure chest has correct colour and type
     */
    @Test
    public void treasureChest() {
    	Treasure treasure = new Treasure();

    	assert (treasure.getColour().equals("t"));
    	System.out.println(treasure.getType() + "=================@");
    	assert (treasure.getType().equals("treasure"));
    }

    /**
     * Tests if state saving is correct
     */
    @Test
    public void checkStateSaving() {
    	StateSaver s = new StateSaver(new ChipAndChap());

    	s.saveAsLevelResumeState(1);
    	s.saveAsResumeState();

    	File f = new File("launchInfo.json"); //Delete launch configuration once done

    	f.delete();
    }

    /**
     * Tests if level twos layout is valid
     */
    @Test
    public void levelTwoValidLayout() {
    	String layout =

				"iiiiiiiiiiiiiiiiiiiiiiiiiiiiiii"+
						"owwwwwwwwwwwwwwwwwwwwwwwwwwwwwp"+
						"o__t_________________wtgw____bp"+
						"o______wwwwww_wwwvvvvw%_w_wwwwp"+
						"o_____ww________wvvvvw__w____cp"+
						"owwwyww_t_____t_wtvvvw__wzwwwwp"+
						"ot___w__________wvvvvw__w_#___p"+
						"o____w_____t____wvvvvw__w__t__p"+
						"o_t__w__________wtvvtw__w_____p"+
						"o____w__t____t__wvvvvw__y_____p"+
						"o____w__________wvvvvwwwwwwww_p"+
						"oww__wwwwwwwwwwwwwddwwllllllw_p"+
						"oqw__llllllllllbw____wl____lw_p"+
						"o_w__wwwwwwwwwwww____wl_tt_lw_p"+
						"o_w__w__________w____wl_tt_lw_p"+
						"o_w__w__________w____wl____lw_p"+
						"o_w__w_r___t____w____wllllllw_p"+
						"o_w_____________w_e__wllllllwrp"+
						"ozwwwwwwwwwwwww_wwwwwwww__wwwxp"+
						"o_______________w_____________p"+
						"o_______________w_____________p"+
						"owwwwwwwwwwwwwwxw__wwwwwwwwwwwp"+
						"o_______gwwwwww_w__w__________p"+
						"o___t_____wwwww_w__w_wwwwwwww_p"+
						"o___________www_w__w_wg___t_w_p"+
						"o_t____t________w__w_wwww___w_p"+
						"o__________wwwwww__w______www_p"+
						"owwzwwwwwwwwllllw__wwwwwwww___p"+
						"o__________tlllly_____________p"+
						"o__wwwwwywwwwlllw__wwwwwwwxwwwp"+
						"otrw______b_wlllw__wr_______u_p"+
						"owwwwwwwwwwwwwwwwwwwwwwwwwwwwwp";

    	assert(new LevelTwo().getLevel().equals(layout));
    }

    /**
     * Tests if the main menu launches without crashing
     */
    @Test
    public void testMainMenu() {
    	RunGame r = new RunGame();
    }



}