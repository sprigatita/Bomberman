package Model;
import java.util.Random;

public class TileModel {
	


	private boolean exploding = false;
	private Random r = new Random();
	private int matrix_pos_row;
	private int matrix_pos_col;
	private int model_num;
	private PowerUpModel power_up;
	private boolean collision = true;
	private boolean is_destructible = true;
	public int destruction_counter = 20;
	public BombModel placedBomb = null;
	public BombModel getPlacedBomb() {
		return placedBomb;
	}
	
	private boolean border = false;

	public void setPlacedBomb(BombModel placedBomb) {
		this.placedBomb = placedBomb;
	}

	public boolean isBorder() {
		return this.border;
	}
	
	public void setBorder() {
		this.border = true;
	}
	public boolean getCollision() {
		return collision;
	}
	
	public boolean containsPowerUp() {
		return this.power_up != null;
	}
	
	public boolean hasPowerUp() {
		int i = r.nextInt(3);
		if (i == 0) {
			return true;
		}
		return false;
	}
	
	public boolean isExploding() {
		return exploding;
	}
	
	
	public PowerUpModel getPowerUp() {
		return this.power_up;
	}
	
	public void setPower_up(PowerUpModel power_up) {
		this.power_up = power_up;
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
