package Model;

public class TileModel {
	


	private int model_num;
	private boolean collision = true;
	private boolean is_destructible = true;
	public int destruction_counter = 20;
	public boolean containsBomb = false;
	public boolean getCollision() {
		return collision;
	}
	
	
	public void setCollision(boolean setCollision) {
		this.collision = setCollision;
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
	
	
	
	
}
