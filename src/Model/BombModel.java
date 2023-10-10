package Model;
import java.util.HashSet;
import java.util.Observable;

import View.GamePanel;

public class BombModel extends Entity {
	
	public boolean processed_explosion = false;
	public int up_explosion_limit = 2;
	public int right_explosion_limit = 2;
	public int down_explosion_limit = 2;
	public int left_explosion_limit = 2;
	public boolean scoreUpdated = false;
	private int fuse = 100;
	private boolean hasExploded;
	private boolean hasExpired;
	public boolean soundPlayed = false;
	public int animationCounter = 0;
	private HashSet<Character> damagedCharacter = new HashSet<Character>();
	public int explosionAnimationCounter = 0;
	private Direction slide_dir;
	private boolean can_slide = false;
	
//	private int pos_x;
//	private int pos_y;
//	
//	public int getPos_x() {
//		return pos_x;
//	}
	
	public void damaged(Character c) {
		this.damagedCharacter.add(c);
	}
	
	public boolean hasDamaged(Character c) {
		return this.damagedCharacter.contains(c);
	}
	
	
//	public void setPos_x(int pos_x) {
//		this.pos_x = pos_x;
//	}
//	
//	public int getPos_y() {
//		return pos_y;
//	}
//	
//	public void setPos_y(int pos_y) {
//		this.pos_y = pos_y;
//	}
//	
	
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
	
	public void fireFuse(int tile_size) {
		 
        if (fuse == 30) {
        	if (this.getPos_x()%tile_size != 0 || this.getPos_y()%tile_size != 0) {
        		return;
        	}
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

	public boolean isCan_slide() {
		return can_slide;
	}

	public void setCan_slide(boolean can_slide) {
		this.can_slide = can_slide;
	}

	public void slide(Direction dir, TileModel[][] map_structure) {
		if (this.can_slide && !this.hasExploded) {
			
			int row_tile = this.getPos_y()/GamePanel.FINAL_TILE_SIZE;
			int col_tile = this.getPos_x()/GamePanel.FINAL_TILE_SIZE;
			
			switch(slide_dir) {
			case UP:
				int above_row_tile = (this.getPos_y() - 1)/GamePanel.FINAL_TILE_SIZE;
				if (!map_structure[above_row_tile][col_tile].getCollision()) {
					this.setPos_y(this.getPos_y()-6);
				}
				break;
			case RIGHT:
				int right_col_tile = (this.getPos_x()+GamePanel.FINAL_TILE_SIZE)/GamePanel.FINAL_TILE_SIZE;
				if (!map_structure[row_tile][right_col_tile].getCollision()) {
					this.setPos_x(this.getPos_x()+6);
				}
				break;
			case DOWN:
				int below_row_tile = (this.getPos_y() + GamePanel.FINAL_TILE_SIZE)/GamePanel.FINAL_TILE_SIZE;
				if (!map_structure[below_row_tile][col_tile].getCollision()) {
					this.setPos_y(this.getPos_y()+6);
				}
				break;
			case LEFT:
				int left_col_tile = (this.getPos_x()-1)/GamePanel.FINAL_TILE_SIZE;
				if (!map_structure[row_tile][left_col_tile].getCollision()) {
					this.setPos_x(this.getPos_x()-6);
				}
				break;
			default:
				
			}
		}
	}

	public void setSlide_dir(Direction slide_dir) {
		this.slide_dir = slide_dir;
	}
	
	
}
