package Model;

import java.util.Observable;

public class BombModel extends Observable {
	
	public boolean scoreUpdated = false;
	private int fuse = 66;
	private boolean hasExploded;
	private boolean hasExpired;
	public boolean soundPlayed = false;
	public int animationCounter = 0;

	
	private int pos_x;
	private int pos_y;
	
	public int getPos_x() {
		return pos_x;
	}
	
	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}
	
	public int getPos_y() {
		return pos_y;
	}
	
	public void setPos_y(int pos_y) {
		this.pos_y = pos_y;
	}
	
	
	public BombModel(int x, int y) {
		setPos_x(x);
		setPos_y(y);
	}
	
	public void updateAnimationCounter() {
		if (animationCounter == 12) {
			animationCounter = 0;
		}
		else animationCounter++;
	}
	
	public void fireFuse() {
		 
        if (fuse == 30) {
            explode(); // Facciamo esplodere la bomba quando il timer raggiunge zero
        }
        if (fuse == 0) {
        	hasExpired = true;
        }
        fuse--;
        updateAnimationCounter();
	}
	

	public void explode() { //implementare tramite Observer-Observable
		this.hasExploded = true;
	}
	
	public boolean hasExploded() {
		return hasExploded;
	}
	
	public boolean hasExpired() {
		return hasExpired;
	}
}
