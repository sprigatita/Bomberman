package Model;

public class BombModel extends Entity {

	private int fuse;
	private boolean hasExploded;
	
	public BombModel(int x, int y) {
		setPos_x(x);
		setPos_y(y);
	}
	
	public void fireFuse(int fuse) {
		 
        if (fuse == 0) {
            explode(); // Facciamo esplodere la bomba quando il timer raggiunge zero
        }
        fuse--;

	}
	
	public void explode() {
		
	}
	
	public boolean afterExplosion() {
		return hasExploded;
	}
	
}
