package Model;

public class TrapModel extends Entity {
	
	private int row;
	private int col;
	private int duration = 100;
	public int getDuration() {
		return this.duration;
	}
	
	public TrapModel(int row, int col) {
		this.row = row;
		this.col = col;
	}
}
