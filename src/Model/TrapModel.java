package Model;

public class TrapModel extends Entity {
	
	private int row;
	private int col;
	private int duration = 3000;
	public int getDuration() {
		return this.duration;
	}
	
	public TrapModel(int row, int col) {
		this.row = row;
		this.col = col;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
