package maze;

public abstract class Level {
	
	public abstract String getLevel();
	
	public abstract int getStartRow();

	public abstract int getStartCol();
	
	public abstract int getTreasure();

	public abstract int totalRows();
	
	public abstract int totalCols();
	
	public abstract int getAllocatedTime();
	
	public abstract String getHelp();

	public abstract int getLevelNumber();

	@Override
	public String toString() {
		return "Level";
	}
}
