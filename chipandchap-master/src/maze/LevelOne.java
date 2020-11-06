package maze;

public class LevelOne extends Level {
	
	// 9 columns (0 index so 8)
	// 9 rows (0 index so 8)

	// w is a wall not passable by the actor.
	// '_' is free tile passable by the actor.

	// b is a blue key which can be picked up and unlock a door with the letter z.
	// r is a red key which can be picked up and unlock a door with the letter y.
	// g is a green key which can be picked up and unlock a door with the letter x.
	// t is treasure which can be picked up
	// q is ice boots
	// u is lava boots

	// z blue door.
	// y red door.
	// x green door.
	// d is an exit lock behaves like a wall time for Chap as long as there are
	// still uncollected treasures
	// e is the exit tile which which reached chap leaves the level.
	// l  is lava floor tile
	// v is water tile

	// c is the spawn location of chap.
	// h is the info field
	
	//p is the right hand wall. Used for drawing.
	//o is left wall. Used for drawing
	
	//% is an enemy

	public static int startRow = 14;
	public static int startCol = 19;
	public static int treasure = 12;
	public static int totalRows = 16;
	public static int totalCols = 21;
	public static int totalAllocatedTime = 55;
	
	public String getLevel() {

		String layout = 
 				"iiiiiiiiiiiiiiiiiiiii" + // 0
				"owwwwwwwwwwwwwwwwwwwp" + // 1 rows  
				"or_______x____t__wwgp" + // 2
				"owwwwwwwwwu_____gw__p" + // 3
				"o_____b__w__t____w_wp" + // 4
				"o_t______w______tw__p" + // 5
				"owwwwww__ww____wwww_p" + // 6
				"o_w___w___wwxwww____p" + // 7
				"oed_t_y__________wwwp" + // 8
				"o_w___w____wwwww_z__p" + // 9
				"owwwwwwzwwww_____w__p" + // 10
				"o___t____w_t___www__p" + // 11
				"o________w__m__www_tp" + // 12
				"o____t___w__t__wwwwwp" + // 13
				"o_t_____twb_?__q___cp" + // 14
				"owwwwwwwwwwwwwwwwwwwp" + // 15
				"iiiiiiiiiiiiiiiiiiiii" ; // 16

				return layout;
	}

	@Override
	public int getStartRow() {
		return startRow;
	}

	@Override
	public int getStartCol() {
		return startCol;
	}

	@Override
	public int getTreasure() {
		return treasure;
	}

	@Override
	public int totalRows() {
		return totalRows;
	}

	@Override
	public int totalCols() {
		return totalCols;
	}

	@Override
	public String toString() {
		return "001";
	}
	
	@Override
	public String getHelp() {
		return "        COLLECT ALL THE\n" +
				"        HIDDEN TREASURE\n"+
				"  BEFORE TIME RUNS OUT!";
	}

	@Override
	public int getLevelNumber() {
		return 1;
	}

	@Override
	public int getAllocatedTime() {
		return totalAllocatedTime;
	}


}
/*
public static String getLevelOneAlt() {  testing

	//public static int levelOneStartRow = 4;
	//public static int levelOneStartCol = 8;
	//public static int levelOneTreasure = 5;
	 * 
	public static int levelOneTotalRows = 13;
	public static int levelOneTotalCols = 17;
	maze = new Maze(13,17); //row, col
	 
	// green keys?
	String layout =
			"_t_wew_t_" + // 0 rows
			"wwxwdwxww" + // 1
			"_z____gy_" + // 2
			"_wb_?_rw_" + // 3
			"wwt_c_tww" + // 4
			"_wb___rw_" + // 5
			"_y__t_gz_" + // 6
			"wwwwwwwww" + // 7
			"_w__w__w_";  // 8

	layout =
			"_____t_wew_t_____" + // 0 rows
			"____wwxwdwxww____" + // 1
			"_____z____gy_____" + // 2
			"_____wb_?_rw_____" + // 3
			"____wwt_c_tww____" + // 4
			"_____wb___rw_____" + // 5
			"_____y__t_gz_____" + // 6
			"____wwwwwwwww____" + // 7
			"_____w__w__w_____" +  // 8
			"_____w__w__w_____"+  // 9
			"_____w__w__w_____" +  // 10
			"_____wb___rw_____" +  //11
			"____wwxwdwxww____" +  //12
			"_____wb_?_rw_____" ; // 13
	return layout;
}

*/
