package Model;

public class PowerUpModel {
	private int id;
	private int row;
	private int col;
	public int getId() {
		return id;
	}
	public PowerUpModel(int id, int row, int col) {
		this.id = id;
		this.row = row;
		this.col = col;
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}

}
