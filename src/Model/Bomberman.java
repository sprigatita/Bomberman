package Model;

import View.BombermanView;
import View.GamePanel;
import Controller.ControlsHandler;
import Controller.Coordinates;

public class Bomberman extends Character{
	//Inizio Singleton
	private static Bomberman bomberman; //Istanza di se stesso
	private PowerUpModel power_up = null;
	private boolean shield = false;
	private boolean kicks_bombs = false;
	private int ghosting_timer = 0;
	private int explosion_limit = 1;


	
	public int getExplosion_limit() {
		return explosion_limit;
	}
	


	public void setPower_up(PowerUpModel power_up) {
		this.power_up = power_up;
	}

	public int getMoveSpeed() {
		return this.move_speed;
	}
	
	public void setMoveSpeed(int i) {
		this.move_speed = i;
	}

	public void setKicksBombs() {
		this.kicks_bombs = true;
	}
	private Bomberman() {
		this.setHealth(1);
		
	}
	
	public static Bomberman getInstance() {
		if (bomberman == null) {
			bomberman = new Bomberman();
		}
		return bomberman;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	public PowerUpModel getPower_up() {
		return power_up;
	}

	public void resetPowerUps() {
		this.shield = false;
		this.move_speed = 4;
		this.kicks_bombs = false;
		this.ghosting_timer = 0;
	}

	public int getGhosting_timer() {
		return ghosting_timer;
	}

	public void setGhosting_timer(int ghosting_timer) {
		this.ghosting_timer = ghosting_timer;
	}
	
	public void decreaseGhosting_timer() {
		this.ghosting_timer -= 1;
	}

	public void setShield() {
		this.shield = true;
	}
	
	public void increaseExplosionRange() {
		if (this.explosion_limit < 10) {
			this.explosion_limit+=1;
		}
	}

	
	@Override
	public void damage() {
		if (this.shield) {
			this.shield = false;
		}
		else {
			super.damage();
		}
	}

	@Override
	public void move(int tile_size, TileModel[][] map_structure, ControlsHandler controls) {
		if (!isDead()) {
	//		int HitBoxUpperLeft_x = b.getPos_x()+tile_size/5;
	//		int HitBoxUpperLeft_y = b.getPos_y()+tile_size*2/5;
	//		int HitBoxUpperRight_x = b.getPos_x() + tile_size - tile_size/5;
	//		int HitBoxUpperRight_y = b.getPos_y()+tile_size*2/5;
	//		int HitBoxBottomLeft_x = b.getPos_x()+tile_size/5;
	//		int HitBoxBottomLeft_y = b.getPos_y()+tile_size-1;
	//		int HitBoxBottomRight_x = b.getPos_x() + tile_size - tile_size/5;
	//		int HitBoxBottomRight_y = b.getPos_y()+ tile_size-1;
				Coordinates[] hit_box = this.collisionHitBox(tile_size);
				PowerUpModel power_up = this.getPower_up();
				boolean needs_to_ghost = false;
				for (Coordinates c : hit_box) {
					int c_row = c.j/GamePanel.FINAL_TILE_SIZE;
					int c_col = c.i/GamePanel.FINAL_TILE_SIZE;
					if (map_structure[c_row][c_col].getCollision()) {
						needs_to_ghost = true;
						break;
					}
				}
				boolean ghosting = false;
				if (power_up != null) {
					ghosting = power_up.getId() == 5;
				}
				if (getGhosting_timer() > 0) {
					decreaseGhosting_timer();
				
				}
				else {
					ghosting = false;
				}
				if (controls.isUp() == true && 	getPos_y()-Bomberman.getInstance().getMoveSpeed() >= 0) {
					boolean canMove = !checkCollision(hit_box, Direction.UP, map_structure, tile_size);
					if (canMove || ghosting || needs_to_ghost) {
						up();
						this.setChanged();
	//				bv.setNextUp();				
					}
				}
				else if (controls.isDown() == true && getPos_y()+tile_size+Bomberman.getInstance().getMoveSpeed() <= 
						GamePanel.getPanelHeight()) {
					boolean canMove = !checkCollision(hit_box, Direction.DOWN, map_structure, tile_size);		
					if (canMove || ghosting || needs_to_ghost) {				
						down();
						this.setChanged();
	//				bv.setNextDown();
					}
				}
				else if (controls.isLeft() == true && getPos_x()-Bomberman.getInstance().getMoveSpeed() >= 0) {
					boolean canMove = !checkCollision(hit_box, Direction.LEFT, map_structure, tile_size);
					if (canMove || ghosting || needs_to_ghost) {
						left();
						this.setChanged();
	//				bv.setNextLeft();				
					}
					
				}
				else if (controls.isRight() == true && getPos_x()+tile_size+Bomberman.getInstance().getMoveSpeed() <=GamePanel.getPanelWidth())  {
					boolean canMove = !checkCollision(hit_box, Direction.RIGHT, map_structure, tile_size);
					if (canMove || ghosting || needs_to_ghost) {
						right();
						this.setChanged();
	//				bv.setNextRight();			
					}
					
				}
				
				this.notifyObservers(this.getDir());
				this.clearChanged();
			}
			
		}

	@Override
	public boolean checkCollision(Coordinates[] hit_box, Direction dir, TileModel[][] map_structure, int tile_size) {
		boolean canPass1 = false;
		boolean canPass2 = false;
		switch(dir) {
		case UP:
			canPass1 = !map_structure[(hit_box[0].j-this.getMoveSpeed())/tile_size][hit_box[0].i/tile_size].getCollision();
			canPass2 = !map_structure[(hit_box[1].j-this.getMoveSpeed())/tile_size][hit_box[1].i/tile_size].getCollision();
			break;
		case RIGHT:
			canPass1 = !map_structure[hit_box[1].j/tile_size][(hit_box[1].i+this.getMoveSpeed())/tile_size].getCollision();
			canPass2 = !map_structure[hit_box[2].j/tile_size][(hit_box[2].i+this.getMoveSpeed())/tile_size].getCollision();
			break;
		case DOWN:
			canPass1 = !map_structure[(hit_box[2].j+this.getMoveSpeed())/tile_size][hit_box[2].i/tile_size].getCollision();
			canPass2 = !map_structure[(hit_box[3].j+this.getMoveSpeed())/tile_size][hit_box[3].i/tile_size].getCollision();
			break;
		case LEFT:
			canPass1 = !map_structure[hit_box[3].j/tile_size][(hit_box[3].i-this.getMoveSpeed())/tile_size].getCollision();
			canPass2 = !map_structure[hit_box[0].j/tile_size][(hit_box[0].i-this.getMoveSpeed())/tile_size].getCollision();
			break;
		default:
		}
		if (canPass1 && canPass2) {
			return false;
		}
		else {
			return true;
		}

	}

}
