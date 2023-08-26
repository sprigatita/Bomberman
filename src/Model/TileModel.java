package Model;

public class TileModel {
	


	private int model_num;
	private boolean collision = true;
	public int destruction_counter = 20;
	public boolean getCollision() {
		return collision;
	}
	
	
	public void setCollision(boolean setCollision) {
		this.collision = setCollision;
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
