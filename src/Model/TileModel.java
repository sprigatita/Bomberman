package Model;

public class TileModel {
	


	private int model_num;
	private boolean collision = true;
	
	public boolean getCollision() {
		return collision;
	}
	
	
	public void setCollision(boolean setCollision) {
		this.collision = setCollision;
	}
	
	
	
	public int getModel_num() {
		return model_num;
	}


	public TileModel(int i) {
		this.model_num = i;
	}
	
	
	
	
}
