package maze;


public class LevelTwo extends Level {

	public static int startRow = 4;  //-1 if 0 index can't remember how I did it
	public static int startCol = 29;
	public static int treasure = 24;

	public static int totalRows = 32;   //32 0 index 32 without
	public static int totalCols = 31;    //30 0 index 31 without
	
	public static int totalAllocatedTime = 120;
	

	
	@Override
	public String getLevel() {

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
		return "002";
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "        COLLECT ALL THE\n" +
				"        HIDDEN TREASURE\n"+
				"  BEFORE TIME RUNS OUT!";
	}

	@Override
	public int getLevelNumber() {
		return 2;
	}
	
	@Override
	public int getAllocatedTime() {
		return totalAllocatedTime;
	}
	
	/*
	 * 			"_____t_wew_t_____" + // 0 rows
				"____wwxwdwxww____" + // 1
				"_____z____gy_____" + // 2
				"_____wb_?_rw_____" + // 3
				"____wwt_c_tww____" + // 4
				"_____wb___rw_____" + // 5
				"_____y__t_gz_____" + // 6
				"____wwwwwwwww____" + // 7
				"_____w__w__w_____" + // 8
				"_____w__w__w_____" + // 9
				"_____w__w__w_____" + // 10
				"_____wb___rw_____" + // 11
				"____wwxwdwxww____" + // 12
				"_____wb_?_rw_____";  // 13
	 */
}
