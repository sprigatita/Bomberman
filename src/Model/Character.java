package Model;

import Controller.Coordinates;
import View.GamePanel;

public abstract class Character extends Entity{
	
	protected boolean dead;
	protected int health;
	protected int death_animation_counter = 60;
	protected int move_speed = 4;
	private Direction dir;
	
	public Character() {
		setPos_x(96);
		setPos_y(96);
	}
	
	public void up() {
		setPos_y(getPos_y() - move_speed);
		this.dir = Direction.UP;
	}

	public void down() {
		setPos_y(getPos_y() + move_speed);
		this.dir = Direction.DOWN;
	}

	public void left() {
	    setPos_x(getPos_x() - move_speed);
	    this.dir = Direction.LEFT;

	}
	
	public void right() {
		setPos_x(getPos_x() + move_speed);
		this.dir = Direction.RIGHT;
		
	}

	public int getDeath_animation_counter() {
		return death_animation_counter;
	}
	
	public void damage() {
		this.health -= 1;
		if (this.health == 0) {
			this.dead = true;
		}
		
	}
	public boolean isDead() {
		return dead;
	}

	public boolean decreaseDeathAnimationCounter() {
		if (death_animation_counter > 0) {
			this.death_animation_counter -= 1;			
		}
		
		if (death_animation_counter <= 0) {
			return true;
		}
		else {
			return false;
		}
	}

	public Direction getDir() {
		return dir;
	}
	
	public Coordinates[] hitBox(int tile_size) {
		Coordinates[] hit_box = new Coordinates[4];
		hit_box[0] = new Coordinates(this.getPos_x(), this.getPos_y());
		hit_box[1] = new Coordinates(this.getPos_x()+tile_size-1, this.getPos_y());
		hit_box[2] = new Coordinates(this.getPos_x()+tile_size-1, this.getPos_y()+tile_size-1);
		hit_box[3] = new Coordinates(this.getPos_x(), this.getPos_y()+tile_size-1);
		return hit_box;
	}

}
