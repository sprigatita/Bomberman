package Model;

import View.GamePanel;
import Controller.Coordinates;

public class Bomberman extends Character {
	//Inizio Singleton
	private static Bomberman bomberman; //Istanza di se stesso
	private PowerUpModel power_up = null;
	private boolean shield = false;
	private boolean kicks_bombs = false;
	private int ghosting_timer = 0;
	
	
	
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
	
	public Coordinates[] collisionHitBox(int tile_size) {
		int hitBoxUpperLeft_x = getPos_x()+tile_size/5;
		int hitBoxUpperLeft_y = getPos_y()+tile_size*2/5;
		int hitBoxUpperRight_x = getPos_x() + tile_size - tile_size/5;
		int hitBoxUpperRight_y = getPos_y()+tile_size*2/5;
		int hitBoxBottomLeft_x = getPos_x()+tile_size/5;
		int hitBoxBottomLeft_y = getPos_y()+tile_size-1;
		int hitBoxBottomRight_x = getPos_x() + tile_size - tile_size/5;
		int hitBoxBottomRight_y = getPos_y()+ tile_size-1;
		Coordinates[] hit_box = new Coordinates[4];
		hit_box[0] = new Coordinates(hitBoxUpperLeft_x, hitBoxUpperLeft_y);
		hit_box[1] = new Coordinates(hitBoxUpperRight_x, hitBoxUpperRight_y);
		hit_box[2] = new Coordinates(hitBoxBottomRight_x, hitBoxBottomRight_y);
		hit_box[3] = new Coordinates(hitBoxBottomLeft_x, hitBoxBottomLeft_y);
		return hit_box;
		
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

}
