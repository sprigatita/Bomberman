package Model;

public class TileModel {
	


	private boolean exploding = false;

	private int matrix_pos_row;
	private int matrix_pos_col;
	private int model_num;
	private boolean collision = true;
	private boolean is_destructible = true;
	public int destruction_counter = 20;
	public boolean containsBomb = false;
	public boolean getCollision() {
		return collision;
	}
	
	
	public boolean isExploding() {
		return exploding;
	}
	
	
	public void setExploding(boolean exploding) {
		this.exploding = exploding;
	}
	
	public void setCollision(boolean setCollision) {
		this.collision = setCollision;
	}
	
	public int getMatrix_pos_row() {
		return matrix_pos_row;
	}
	
	
	public int getMatrix_pos_col() {
		return matrix_pos_col;
	}
	
	public void setDestructible(boolean setDestructible) {
		this.is_destructible = setDestructible;
	}
	
	public boolean getDestructible() {
		return this.is_destructible;
	}
	
	
	public int getModel_num() {
		return model_num;
	}
	
	public void setModel_num(int i) {
		this.model_num = i;
	}

	public TileModel(int i) {
		this.model_num = i;
	}
	


	public void setColCoord(int col) {
		this.matrix_pos_col = col;
	}


	public void setRowCoord(int row) {
		this.matrix_pos_row = row;
		
	}
	
	
	
	
	
}
